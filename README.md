# Simple servlet framework
## Description
This project is being developed by me for improve my knowledge in Reflection and learn how Spring Framework working inside.
## Tech stack
- Java 11
- Jakarta Servlet as Core
- Apache Tomcat as Servlet container
- Reflections for class search
- Lombok to avoid boilerplate code
- Jackson for JSON mapping
- JDBC for working with the database
- DBCP as connection pool
## Now implemented
1. Dispatcher servlet that handles all http-requests and directs them to controllers;
2. Servlet filter to avoid blocking requests by CORS policy;
3. @Controller and @RestController annotations. Difference between them in the data mapping. @Controller displaying data as jsp page, @RestController - as JSON;
4. @GetMapping, @PutMapping, @PostMapping, @DeleteMapping and @OptionsMapping annotations for handling http-requests;
5. @Component, @Service, @ComponentDao for instantiate singletones (beans) after application deployment;
6. @Autowired for injecting beans;
7. Connection pool;
8. Auto-creation tables in the database;
9. @PathVariable annotation is used on the values of storage path variables.
10. @RequestBody annotation is used to json serialize
## Modules
### Core
#### - @Component and @Service annotations
    These annotations are used over classes for instantiate singletones (beans) after deployment. 
    There is no different between them. @Service annotation was created for convenience while writing code and uses over classes that doing business logic.
#### - Context class
    This class created for getting beans
#### - ContextListener
    In this class beans are initialized, after that dependencies are injected
#### - @RequestBody annotation
    This annotation is used to json serialize
### MVC
#### - DispatcherServlet
    This servlet handling all of HTTP-requests and direct them to the required controllers
#### - @Controller and @RestController annotations
    These annotations are used over classes for intercepting incoming requests.
    We can write start of uri to be intercepted.
    Difference between them in the data mapping. @Controller displaying data as jsp page, @RestController - as JSON.
#### - @GetMapping, @PostMapping, @PutMapping, @DeleteMapping, @OptionsMapping
    These annotations are used over the methods in the controller class for handling certain types of requests.
#### - @PathVariable annotation
    This annotation is used on the values of storage path variables.
#### - CORSFilter
    It is used to avoid CORS block.
#### - Model
    It is used for working with attributes and request parameters.
### Data
#### - @ComponentDao annotation
    This annotation used to instantiating DAO component after deployment.
#### - DatabaseConnection interface, ConnectionFactory, ConnectionPool, DataSource classes
    They are used for getting connection with the database.
## Projects written using this framework
1. [Company API](https://github.com/SergiyKosh/servlet-api)

    This project demonstrates features of my framework.

    Database: PostgreSQL;
    
    Framework: 
    - @RestController, @Service, @PathVariable, @Component, @ComponentDao, @Get,Put,Post,DeleteMapping and @Autowired annotations.
    - Connection pool.

    It's a RESTful API that can do CRUD operations on JSON format. In addition to this API, a client will be created using this framework.
