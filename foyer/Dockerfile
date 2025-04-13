FROM openjdk:17-jdk-slim
WORKDIR /app
EXPOSE 8083
COPY target/4twin4-bugbusters-foyer.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

