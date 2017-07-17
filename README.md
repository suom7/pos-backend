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

### Run

	$ ./gradlew bootRun

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

### Initialize DB 

	DROP DATABASE IF EXISTS pos;
	CREATE DATABASE pos CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';
	USE pos;

	DROP USER 'pos'@'localhost';
	CREATE USER 'pos'@'localhost' IDENTIFIED BY 'DrQi3kclsqO4v';
	GRANT  ALL PRIVILEGES ON pos.* TO 'pos'@'localhost';
