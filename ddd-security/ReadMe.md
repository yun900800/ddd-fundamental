# How to use spring security from zero to master?

1. generally speaking, spring security depend on two library:
    ```POM
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    ```
    if we did not set any params in application.yml, when we access any interface,we couldn't access them,
    but if we access interface with username user and password with a string that is generated by springboot,
    we can access this interface.

2. we have two ways to configure our security, the first is configured a bean named userDetailsService,we can create
    many users with many ways, for example, creating user with in memories and create user with JDBC or JPA and so on;
    the second is that create a bean that implement a interface named AuthenticationProvider, and in this interface, we
    could get an Authentication through username and password, this method is used to this scenario that the username and
    password is not accessed in locally; when we create user, and we can authorize some permission to the user,for example
    we can make user admin access all the url, but we only allow  user customer   access  special url which contains string 'customer'
    and this is called Authorization