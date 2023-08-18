# Eureka注册中心集群步骤

1. 新建三个配置文件application-peer1.yml;application-peer2.yml;application-peer3.yml
2. 定义spring.profiles的属性为分别的peer1,peer2,peer3;启动的时候通过传递参数不同来选择不通过的配置文件
3. 运行java执行代码
    ```shell
   java -jar ./target/ddd-eureka-server-1.0-SNAPSHOT.jar --spring.profiles.active=peer1 --server.port=6001
   ```
4. 用一个start.bat来执行a.bat,b.bat,c.bat;
5. 需要注意的是,修改host文件,做本地域名映射
   ```shell
    10.0.0.118 peer1
    10.0.0.118 peer2
    10.0.0.118 peer3
   ```
