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
## Modules
### Core
#### - Annotations @Component and @Service
    These annotations are used over classes for instantiate singletones (beans) after deployment. 
    There is no different between them. @Service annotation was created for convenience while writing code and uses over classes that doing business logic.
#### - Context class
    This class created for getting beans
#### - ContextListener
    In this class beans are initialized, after that dependencies are injected
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
## Projects which will be written using this framework
Link to a project that will be demonstrate framework features will be here :)
