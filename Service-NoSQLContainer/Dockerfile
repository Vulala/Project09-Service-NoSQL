﻿FROM openjdk:15-jdk-alpine as build 
ARG JAR_FILE=target/*.jar 
COPY Project09-Service-NoSQL-1.0.0.jar Service-NoSQL-1.0.0.jar 
ENTRYPOINT ["java","-jar","/Service-NoSQL-1.0.0.jar"]
