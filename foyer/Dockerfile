FROM maven:3.8.4-openjdk-17
WORKDIR /foyer
EXPOSE 8089
ADD target/4TWIN4-BugBusters-Foyer.jar foyer-3.0.0.jar
ENTRYPOINT ["java","-jar","/foyer-3.0.0.jar"]

