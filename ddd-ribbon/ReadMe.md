# ribbon基础知识学习

1. 如果不用eureka注册中心,可以在本地配置需要访问的的服务器地址:
    ```yaml
    users:
      ribbon:
        listOfServers: http://localhost:9000,http://localhost:9001,http://localhost:9002
   ```
2. 可以给restTemplate配置拦截器，比如给request添加header等?一个疑问,ribbon能处理response吗?

3. restTemplate的response与springMVC的response不是同一个