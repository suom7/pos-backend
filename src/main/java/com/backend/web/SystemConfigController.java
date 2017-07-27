//package com.backend.web;
//
//import java.util.Collection;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.backend.dao.SystemConfigDao;
//import com.backend.domain.SystemConfig;
//import com.backend.json.ResponseList;
//import com.sma.common.ObjectNotFoundException;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//
//@RestController
//@RequestMapping("/api/system")
//@Slf4j
//@Api(tags = "System Config API")
//public class SystemConfigController {
//
//    private final static String CONSTRAIN_APPLICATION_JSON = "application/json";
//
//    @Resource(name = "systemConfigDaoImpl")
//    protected SystemConfigDao dao;
//
//    /**
//     * create node with json
//     * 
//     * @param request
//     * @param body
//     * @return
//     */
//    @RequestMapping(value = "v1/json", method = RequestMethod.POST, produces = CONSTRAIN_APPLICATION_JSON)
//    @ApiOperation(value = "create node with json", produces = CONSTRAIN_APPLICATION_JSON)
//    public ResponseEntity<SystemConfig> create(HttpServletRequest request, @RequestBody SystemConfig body) {
//        log.info("============= create ==========");
//        setCreatedByUser(body);  // remove later
//        dao.add(body);
//        return new ResponseEntity<SystemConfig>(body, HttpStatus.OK);
//    }
//
//    /**
//     * create node with form
//     * 
//     * @param request
//     * @param domain
//     * @return
//     */
//    @RequestMapping(value = "v1", method = RequestMethod.POST)
//    @ApiOperation(value = "create node with json")
//    public ResponseEntity<SystemConfig> createWithForm(HttpServletRequest request, @ModelAttribute SystemConfig domain) {
//        log.info("============= create ==========");
//        setCreatedByUser(domain); // remove later
//        dao.add(domain);
//        return new ResponseEntity<SystemConfig>(domain, HttpStatus.OK);
//    }
//
//    /**
//     * find node by id
//     * 
//     * @param request
//     * @param id
//     * @return
//     */
//    @RequestMapping(value = "v1/{id}", method = RequestMethod.GET)
//    @ApiOperation(value = "find node by id")
//    public ResponseEntity<SystemConfig> findById(HttpServletRequest request, @PathVariable Long id) {
//        return new ResponseEntity<SystemConfig>(dao.findById(id), HttpStatus.OK);
//    }
//
//    /**
//     * update node with json
//     * 
//     * @param request
//     * @param id
//     * @param body
//     * @return
//     */
//    @RequestMapping(value = "v1/{id}", method = RequestMethod.PUT, produces = CONSTRAIN_APPLICATION_JSON)
//    @ApiOperation(value = "update node with json", produces = CONSTRAIN_APPLICATION_JSON)
//    public ResponseEntity<SystemConfig> update(HttpServletRequest request, @PathVariable Long id, @RequestBody SystemConfig body) {
//        SystemConfig domain = dao.findById(id);
//        if (domain == null) {
//            throw new ObjectNotFoundException("Not found...");
//        }
//        convert(domain, body);
//        setCreatedByUser(domain);  // remove later
//        dao.update(domain);
//        return new ResponseEntity<SystemConfig>(domain, HttpStatus.OK);
//    }
//
//    /**
//     * get node with pagination
//     * 
//     * @param request
//     * @param limit
//     * @param offset
//     * @return
//     */
//    @RequestMapping(value = "v1", method = RequestMethod.GET)
//    @ApiOperation(value = "get node with pagination")
//    public ResponseEntity<ResponseList<SystemConfig>> getPage(HttpServletRequest request,
//            @RequestParam(value = "limit", defaultValue = "10") int limit,
//            @RequestParam(value = "offset", defaultValue = "0") String offset) {
//        log.info("============= getPage ==========");
//        return new ResponseEntity<ResponseList<SystemConfig>>(dao.getPage(limit, offset), HttpStatus.OK);
//    }
//
//    /**
//     * get all nodes
//     * 
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "v1/all", method = RequestMethod.GET)
//    @ApiOperation(value = "get all nodes")
//    public ResponseEntity<Collection<SystemConfig>> getAll(HttpServletRequest request) {
//        log.info("============= getAll ==========");
//        return new ResponseEntity<>(dao.getAll(), HttpStatus.OK);
//    }
//
//    private SystemConfig convert(SystemConfig existing, SystemConfig newDomain) {
//        existing.setCompanyName(newDomain.getCompanyName());
//        existing.setCompanyAddress(newDomain.getCompanyEmail());
//        existing.setCompanyTel(newDomain.getCompanyTel());
//        existing.setCompanyEmail(newDomain.getCompanyEmail());
//        existing.setCompanyLogo(newDomain.getCompanyLogo());
//        existing.setCompanyCurrencyRate(newDomain.getCompanyCurrencyRate());
//        existing.setCompanyRule(newDomain.getCompanyRule());
//        existing.setCompanyOtherInfo(newDomain.getCompanyOtherInfo());
//        existing.setCreatedBy(newDomain.getCreatedBy());
//        existing.setUpdatedBy(newDomain.getUpdatedBy());
//        return existing;
//    }
//
//    // remove it later and replace by user login
//    private SystemConfig setCreatedByUser(SystemConfig domain) {
//        if (StringUtils.isEmpty(domain.getCreatedBy())) {
//            domain.setCreatedBy("1");
//        }
//        if (StringUtils.isEmpty(domain.getUpdatedBy())) {
//            domain.setUpdatedBy("1");
//        }
//        return domain;
//    }
//}
