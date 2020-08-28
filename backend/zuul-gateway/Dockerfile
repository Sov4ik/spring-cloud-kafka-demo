FROM openjdk:13-alpine

VOLUME /workspace

ADD "target/api-gateway-0.0.1-SNAPSHOT.jar" app.jar

EXPOSE 9193
ENTRYPOINT ["java","-jar","app.jar"]
