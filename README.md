WeatherApp
===================

The motivation for creating the application is the ability to collect weather data for selected cities.
This data will be used to check how the weather in a given region behaves in different months.


### Requirements

- Java 11
- Spring Boot 2.5.4
- Vaadin 14.6.8

### Installation

1. Open your terminal or command prompt.
2. Navigate to the root directory of your Spring Boot project.
3. Make sure you have Maven installed and added to your system's PATH.
4. Run the following command to build the project and package it into an executable JAR file:
```bash
mvn clean install
```
5. Once the build is successful application can be started in two approaches
- First approach. Just run:
```bash
mvn spring-boot:run
```
- Second approach. Navigate to the target directory:
```bash
cd target
```
Run the following command to start your Spring Boot application:
```bash
java -jar <name-of-jar-file>.jar
```
Replace `<name-of-jar-file>` with the actual name of the JAR file generated in the previous step.

**Steps 4 and 5 should be done both for frontend and backend application**
