
### Requirements
- Jdk 18
- Gradle 7.4.1
- Lombok IDE configuration (if needed)

### How to run
- $**_./gradlew bootRun_** run command in the root of project to start web server.
- $**_./gradlew clean test_** run command in the root of project to run the tests.

### Dockerize application
- **./gradlew build** build project in the root.
- **docker build -t takehometask/ratepay .** build docker image.
- **docker run -p 8080:8080 takehometask/ratepay** run docker image.

### My two cents as feedback
- of course in this task I avoided soo many things, those immediately coming in my mind, might be logging,validation,error handling, schema definition, localization, virtualization and more depending on request, I have knowledge how to provide them, but in terms of simplicity I just tried to show how I approach to the problem. 