FROM openjdk:11
ADD target/canopyonebackenddev.jar canopyonebackenddev
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "canopyonebackenddev.jar"]