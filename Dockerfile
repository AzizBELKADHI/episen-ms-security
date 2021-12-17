FROM openjdk:11
ADD src/main/resources/keys/server.p12 src/main/resources/keys/server.p12
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]