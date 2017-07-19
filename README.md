Spring Boot (Java 8)
============================

### Technologies

* [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Gradle](https://gradle.org/gradle-download/) (optional)
* [Spring Boot](https://projects.spring.io/spring-boot/)
* [MyBatis](http://www.mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/index.html)
* [Flyway](https://flywaydb.org/)
* [Lombok](https://projectlombok.org/)
* [Swagger](http://swagger.io/)

### Initialize DB once time

    DROP DATABASE IF EXISTS pos;
    CREATE DATABASE pos CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';
    USE pos;

    DROP USER 'pos'@'localhost';
    CREATE USER 'pos'@'localhost' IDENTIFIED BY 'DrQi3kclsqO4v';
    GRANT  ALL PRIVILEGES ON pos.* TO 'pos'@'localhost';
    
### Run

	$ ./gradlew bootRun

### deployment jar with profile on server
    $ java -jar -DSpring.profiles.active=TEST|STAGE|PROD  app.jar
    
     more customization 
    
    $java -server\
         -Xms256m\
         -Xmx512m\
         -XX:MaxMetaspaceSize=256m\
         -XX:SurvivorRatio=8\
         -XX:+UseConcMarkSweepGC\
         -XX:+CMSParallelRemarkEnabled\
         -XX:+UseCMSInitiatingOccupancyOnly\
         -XX:CMSInitiatingOccupancyFraction=70\
         -XX:+ScavengeBeforeFullGC\
         -XX:+CMSScavengeBeforeRemark\
         -XX:+PrintGCDateStamps\
         -Dsun.net.inetaddr.ttl=180\
         -XX:+HeapDumpOnOutOfMemoryError\
         -jar\
         /opt/pos-backend/active-version/app.jar\
         --spring.profiles.active=TEST\
         --server.port=8080\

    
    
### Create Flyway Patch

	$ ./gradlew createFlywayPatch

### Build Docker

	$ ./gradlew clean buildDocker

### Swagger - API document

	UI : http://localhost:8080/swagger-ui.html

	JSON : http://localhost:8080/v2/api-docs

### Spring DevTools

	Enable hot reloading, so every time we make a change in our 
	files an application restart will be triggered automatically

