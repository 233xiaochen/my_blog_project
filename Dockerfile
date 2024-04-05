FROM openjdk:8u252-jre
COPY *.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
