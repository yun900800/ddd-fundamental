docker network ls

docker inspect network1 network2
主要查看网络的名字,id,属于哪个网段,以及加入了多少容器

查看子网段
docker inspect -f '{{range .IPAM.Config}}{{.Subnet}}{{end}}' network1

查看容器
docker ps --format 'table {{.ID}}\t{{.Names}}'
488f5db94671   networks-test1-1
f2cd4e817289   networks-test2-1
1e630a998d45   networks-test3-1

查看容器对应的网络
docker inspect f2cd4e817289 --format '{{.NetworkSettings.Networks.network1.IPAddress}}'
docker inspect 1e630a998d45 --format '{{.NetworkSettings.Networks.network2.IPAddress}}'

注意这里的容器networks-test2-1实际上在两个网络中,因此有两个ip地址

打印容器中的hosts文件
docker exec 488f5db94671 cat /etc/hosts

进入docker容器中执行命令
docker exec -it 488f5db94671 /bin/bash

curl test2.8080 可以返回
curl test3.8080 网路不通

将容器添加到网络中 将容器3添加到network1后,容器1与容器3在同一个网络,因此可以访问
docker network connect --alias test3 network1 1e630a998d45
