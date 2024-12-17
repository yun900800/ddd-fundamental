1、在使用镜像插件打包镜像以后,使用一下命令来启动容器
    docker run --net=host -p 8080:8080 ddd-factory:from-jib