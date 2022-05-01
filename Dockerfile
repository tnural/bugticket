FROM amazoncorretto:18-alpine
COPY build/libs/ratepaytakehometask-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]