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
3. @Controller and @RestController annotations. Difference between them in the data mapping. @TestController mapps data as jsp page, @RestController - as JSON;
4. @GetMapping, @PutMapping, @PostMapping, @DeleteMapping and @OptionsMapping annotations for handling http-requests;
5. @Component, @Service, @ComponentDao for instantiate objects before application initialization;
6. @Autowired for injecting beans;
7. Connection pool;
8. Auto-creation tables in the database;
## Projects which will be written using this framework
Link to a project that will be demonstrate framework features will be here :)
