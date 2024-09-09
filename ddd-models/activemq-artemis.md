# activemq-artemis docker-compose 配置

1、增加一个activemq docker-compose 配置的文件，[知识来源](https://stackoverflow.com/questions/78677017/mounting-local-broker-xml-to-activemq-artemis-container-but-my-local-file-is-ch)

2. 将文件从docker中cp到宿主机的命令:
    ```shell
      sudo docker cp 910678dfc1dd:/opt/amq/conf/broker.xml ./broker
      sudo docker cp  /home/config/my.cnf  container_id:/etc/mysql/
    ```
   
3. 这里的配置文件broker.xml是空的，应该是镜像的目录文件没有找对