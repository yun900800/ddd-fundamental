FROM adoptopenjdk/openjdk11:alpine-jre
###以上的镜像是最小的镜像
EXPOSE 8080
ARG JAR_FILE=target/ddd-app-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

#FROM eclipse-temurin:17-jdk-alpine
#VOLUME /tmp
#ARG JAR_FILE
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]