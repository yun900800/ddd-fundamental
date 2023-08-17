# ribbon基础知识学习

1. 如果不用eureka注册中心,可以在本地配置需要访问的的服务器地址:
    ```yaml
    users:
      ribbon:
        listOfServers: http://localhost:9000,http://localhost:9001,http://localhost:9002
   ```
2. 可以给restTemplate配置拦截器，比如给request添加header等?一个疑问,ribbon能处理response吗?

3. restTemplate的response与springMVC的response不是同一个

4. 什么情况下可能配置多个restTemplate?如何处理response的转化?

5. 集中式负载均衡和客户端负载均衡有啥区别?

6. ribbon的组件主要有哪些呢?
   1. ServerList,主要是服务实例配置列表,可以从配置文件获取,也可以从配置中心获取
   2. ServerListFilter 服务过滤组件,用于在某些场景下过滤一些不需要的服务实例
   3. ServerListUpdater 主要用于ribbon更新本地的serverList列表
   4. LoadBalancer 主要用于负载均衡的时候使用IRule规则选择一个服务器
   5. IRule 定义各种负载策略的接口
   6. IPing 用于向配置中心发送心跳
   为了方便记忆,可以这样定义:存储组件,过滤组件,更新组件,是否可用,选择