#FROM maven:3.6.1-jdk-11-slim AS build
#RUN mkdir -p /workspace
#WORKDIR /workspace
#COPY pom.xml /workspace
#COPY src /workspace/src
#RUN mvn -f pom.xml clean package

FROM openjdk:13-alpine
#COPY --from=build /workspace/target/*.jar app.jar
VOLUME /workspace

ADD "target/dao-microservice-0.0.1-SNAPSHOT.jar" app.jar

EXPOSE 9193
ENTRYPOINT ["java","-jar","app.jar"]
