package com.backend.config;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.sma.common.web.RestJsonExceptionResolver;
import com.sma.common.web.SkipNullObjectMapper;
import com.sma.security.config.UserServiceNoneGaeConfig;
import com.sma.security.interceptor.AuthorizationConfigPolicy;
import com.sma.security.interceptor.HttpAction;
import com.sma.security.interceptor.SecurityModule;
import com.sma.security.service.ClientCredentials;
import com.sma.security.service.UserSecurityService;
import com.sma.security.service.UserSecurityService.ExpirePolicy;

@EnableWebMvc
@Configuration
@Import(value = {UserServiceNoneGaeConfig.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
@ComponentScan({ "com.backend.web", "com.backend.dao", "com.backend.service"} )
public class AppConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private DataSource          dataSource;

    @Autowired
    private Environment         env;

    @Autowired
    private UserSecurityService userSecurityService;

    public static final String  ROOT_ACCOUNT_EMAIL = "admin@company.com";
    private static final String ROOT_ACCOUNT_PWD   = "tVeQxaGOfAhGNg0r8d1PWua29f";

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        final Flyway flyway = new Flyway();
        flyway.setBaselineOnMigrate(true);
        flyway.setLocations("classpath:/db/migration");
        flyway.setDataSource(dataSource);
        return flyway;
    }

    // -------------- Message Converters ----------------------
    @Override
    public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {

        final SkipNullObjectMapper skipNullMapper = new SkipNullObjectMapper();
        skipNullMapper.init();
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(skipNullMapper);

        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        skipNullMapper.setDateFormat(formatter);

        converters.add(converter);

    }

    // replace-holder properties using @value annotation
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    // -------------- Controllers ----------------------

    private String getProperty(final String key) {
        return env.getProperty(key);
    }

    // -------------- View Stuff -----------------------

    @Override
    public void configureHandlerExceptionResolvers(final List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(restJsonExceptionResolver());
    }
//
    @Bean
    public RestJsonExceptionResolver restJsonExceptionResolver() {
        final RestJsonExceptionResolver bean = new RestJsonExceptionResolver();
        RestJsonExceptionResolver.registerExceptionWithHTTPCode(org.springframework.beans.TypeMismatchException.class, 400);
        RestJsonExceptionResolver.registerExceptionWithHTTPCode(MissingServletRequestParameterException.class, 400);
        RestJsonExceptionResolver.registerExceptionWithHTTPCode(MethodArgumentNotValidException.class, 400);
        RestJsonExceptionResolver.registerExceptionWithHTTPCode(ServletRequestBindingException.class, 400);

        // bean.setErrorIdGenerator(new GaeErrorIdGenerator());
        bean.setOrder(100);

        bean.setDiagnosticsDisabled(Boolean.parseBoolean(getProperty("json.diagnosticsDisabled")));
        // set general error message
        RestJsonExceptionResolver.setCustomMsg(getProperty("json.errormsg"));

        bean.setOrder(100);
        return bean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityModule()).addPathPatterns("/**");
    }

    @Bean
   // @DependsOn("flyway")
    public SecurityModule securityModuleBean() {
        // just create only new instance and no further setting configuration
        // the property listableBeanFactory, userSecurityService will auto injected on the fly
        return new SecurityModule();
    }

    public SecurityModule securityModule() {
        final SecurityModule securityModule = securityModuleBean();

        /** set sender fromEmail , fromName */
        securityModule.setFromEmailAndName(getProperty("email.from.address"), getProperty("email.from.name"));

        /** Override time boxed limit in minute and number of max request */
        SecurityModule.setRequestRateThrottleLimit(15, 500);

        /** set cookie in secure mode by default true */
        SecurityModule.setCookieSecure(Boolean.parseBoolean(getProperty("user.security.cookie.mode")));

        
        /** set Authorization Config here */
        /**set default Authorization roles*/
        securityModule.setDefaultAuthorization(HttpAction.ALL, "/api", AuthorizationConfigPolicy.ROLE_ADMIN);
        
        
        // admin account will have ROLEs : ROLE_ADMIN ,
        // ROLE_BACKOFFICE_ADMIN,ROLE_USER 4hour expiration
        userSecurityService.createSuperRootAdminAccount(ROOT_ACCOUNT_EMAIL, ROOT_ACCOUNT_PWD,
                ExpirePolicy.AFTER_LOGIN, TimeUnit.HOURS.toSeconds(4L));

        // create client credentials 4hour
        final List<ClientCredentials> clientCredentailsList = new LinkedList<>();
        // client type webapp 4hour
        clientCredentailsList.add(
                new ClientCredentials("webapp", "1ho8ISSE0qiUWtdhXz3", ExpirePolicy.LAST_ACCESS, TimeUnit.DAYS.toSeconds(30L)));

        // client type Backoffice 4hour
        clientCredentailsList.add(new ClientCredentials("backoffice", "8AE8ISSE0qiUWtdhYz9", ExpirePolicy.LAST_ACCESS,
                TimeUnit.HOURS.toSeconds(4L)));

        // client type android 60days
        clientCredentailsList.add(
                new ClientCredentials("android", "5Jo94h5nrMibIYYkOrv", ExpirePolicy.AFTER_LOGIN, TimeUnit.DAYS.toSeconds(30L)));

        // client type iOS
        clientCredentailsList
                .add(new ClientCredentials("iOS", "NZnkNMcNhddv45vsIC7", ExpirePolicy.AFTER_LOGIN, TimeUnit.DAYS.toSeconds(30L)));

        userSecurityService.createClientCredentials(clientCredentailsList);

        // userAccountController.setListener(userAccountControllerListener());
        // oAuth2Controller.setListener(oAuth2ControllerListener());

        return securityModule;
    }

    @Bean
    public PasswordEncoder passwordEncode() {
        final PasswordEncoder bean = new StandardPasswordEncoder("ffD0QWERXFYUIKGFssdfksdf12958ds");
        return bean;
    }

}
