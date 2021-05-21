# Service-NoSQL

**REST API** used to communicate with a MongoDB database (**NoSQL**) via **HTTP** request. <br>
In such, the application provides differents endpoints related to **CRUD** operations.

For more information about the available endpoints, feel free to checkout the swagger's documentation.


## Stack

- **Java 15** *(should run smoothly on older versions)*
- **Spring Boot 2.4.5**
- **Spring Boot MongoDB Data**
- **Spring Boot AOP 2.4.5**
- **Spring Boot Test**
- **JaCoCo 0.8.6**
- **Swagger 3.0.0**
- **MongoDB**
- **MongoDB Embeded Database**
- **Gradle 6.8.3**


## Installation

The application is quite easy to install; it only needs to be imported in your preferred IDE as a gradle project. <br>
Then it needs you to set up a MongoDB database locally or to have the access to one. <br>

**Listen on port: 8082**


## Test

The application is entirely tested and reach a **90%** + code coverage. <br>
You can do end-to-end tests by doing HTTP request with the differents endpoints provided, f.e with Postman. <br>
If you wish to add or tweak some tests, you can find them under the traditional **src/test/java** package. <br>


## Logs

The application is logged by making use of the **Aspect Oriented Programming**. <br>
*LoggingAspect* is the class using **AOP** which define the logging. <br>


## Documentation

The application uses Swagger2 to build up the documentation. <br>
To access the documentation, run the application and reach those links: <br>
[UI](http://localhost:8082/swagger-ui/) | [JSON](http://localhost:8082/v2/api-docs)



