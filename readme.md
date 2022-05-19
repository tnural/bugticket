
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
