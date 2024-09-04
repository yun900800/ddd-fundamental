# atomikos 基础掌握

1. 依赖包的问题:
    ```maven
        <dependency>
            <groupId>com.atomikos</groupId>
            <artifactId>transactions-spring-boot3-starter</artifactId>
            <version>6.0.0</version>
        </dependency>
    ```
   这个依赖包是否支持springboot2

2. 关于atomikos的[相关文章](https://stackoverflow.com/questions/76837954/spring-boot-3-with-atomikos)
   - Create Beans for JpaVendorAdapter, UserTransaction, TransactionManager and PlatformTransactionManager.
   - Define the ConnectionFactory JMS for XA.
   - Define the Datasource for XA
   - Also define a jta.properties file inside the resources folder of your spring boot application to specify the settings of Atomikos you prefer. All the available properties are described here: Atomikos properties Also they have already default values.

3. activemq 已经分成了好几个版本啦,因此不同的应用对应的版本是不一致的,需要进行区分

4. 分布式事务在jms和jdbc甚至jta中的应用需要学习

