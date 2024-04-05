FROM openjdk:8u131-jre-alpine
COPY *.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
