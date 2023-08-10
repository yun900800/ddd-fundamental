### 如何将springboot打包到docker中

1. 主要包含两个文件一个是dockerfile文件和docker-compose.yml文件

2. 打出来的包有500多M,能否有优化的空间?

### 练习使用curl来发送各类请求
1. post,需要注意的是curl在传递json数据的时候key必须是双引号并且需要转义符;同时如果字符串含有字符和数字,值对应需要转义
```
curl -X POST http://localhost:9000/register 
   -H "Content-Type: application/json"
   -d "{\"userName\": "yun900900", \"password\": "654321"}"
```

```
curl -X POST http://localhost:9000/register -H "Content-Type: application/json" -d "{\"userName\": "900800", \"password\":"654321"}"
```

2. 如何删除重复数据的问题
```
SELECT h.user_name, COUNT(1) FROM user_model h WHERE 1 = 1  GROUP BY h.user_name HAVING(COUNT(1)) > 1;

select * from user_model where user_name in (select user_name from user_model group by user_name having count(user_name) > 1);


DELETE FROM user_model WHERE user_name IN 
  (SELECT t.user_name FROM  
 (SELECT s.user_name from user_model s  group BY s.user_name having COUNT(s.user_name) > 1) t)
 AND id not in
 (SELECT g.id FROM  (select MIN(h.id) AS id from user_model h group BY h.user_name having COUNT(h.user_name) > 1) g);
```
3. 将git设置环境变量GIT_HOME和%GIT_HOME%/bin 以后,可以在windows上使用shell
```
#!/bin/bash
echo @hello
userName="aaa"
function rand(){ 
 min=$1 
 max=$(($2-$min+1)) 
 num=$(($RANDOM+1000000000)) #增加一个10位的数再求余 
 echo $(($num%$max+$min)) 
}
  

echo $userName
curl -X GET http://localhost:9000/messages
for i in {1..1000}
do
    rnd=$(rand 400000 500000)
    userName=admin$rnd
    curl -X POST http://localhost:9000/register -H "Content-Type: application/json" -d "{\"userName\": \"$userName\", \"password\":"$rnd"}"
done
```
` sh bash.sh` 运行就好,以上练习了如何使用变量,如何发送curl发送GET和POST请求,如何定义函数,如何赋值,json组装的时候什么时候需要转义

4. 理解在rabbitMq中如何使用一个队列多个消费者来消费,理解exchange,queue,routingKey它们之间的关系
  一个队列对应多个消费者,一个exchange对应多个queue;以及message Queue和pub_sub之间的怎么应用.
5. 在监听器中存在大数据操作的时候,最好放到线程当中去做,这里数据库有一个bug,显示的数据条数不是很匹配
6. 在测试发送数据的时候,每个连接一个channel发送效果最快(workers=20, throughput=67605),但是比较消耗资源
    其次是单个连接普通IO(workers=20, throughput=22375),最后是单个连接NIO(workers=20, throughput=15979),共享的连接效果最差.
7. 持久化队列创建以后不能修改,只能删除以后重新创建; 错误处理的时候一定要配置成为不循环发送消息
   尤其是使用死信队列和exchange的时候,要不然进不到错误处理的队列;错误处理的一般过程如下
    - 打印错误消息
    - 重试若干次,一直到成功
    - 设置一个阈值,超过这个值以后直接丢弃,同时应该设置x-message-ttl这个header
    - 如果数据不能丢弃,则可以将它们发送到一个park中存到数据库或者发邮件手动处理
8. 理清楚fanOut exchange和 topic exchange的区别(一个用routing key过滤,一个全部发送)

9. docker容器内桥接网络的时候,app访问mysql,rabbitmq的时候最好用容器进行访问,并且使用localhost的时候似乎有问题
    rabbitMQ代码创建队列的时候似乎不能成功,暂时还不知道这是什么原因.容器的运行环境与开发环境是有差异的;注意这里需要区分.
10. 验证在容器中创建队列为啥会出错以及出错的原因?容器名称和服务名称是有差异的,弄清楚差异在哪里

11. 解决maven冲突的办法:
    - 第一步安装maven-helper插件;
    - 打开项目对应的pom文件,看看那些包存在冲突;
    - 如果通过exclude排除那些包后没有问题,这个就算解决了冲突;如果存在问题,那么就要修改代码
    或者采用别的方案:比如hibernate依赖的net-buddy和mockito依赖的net-buddy存在的冲突就不好解决
    这个问题暂时还没有好的方案,其他的问题还算好解决

