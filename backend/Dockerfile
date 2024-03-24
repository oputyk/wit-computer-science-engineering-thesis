FROM eclipse-temurin:17-jdk-alpine

WORKDIR /opt/app

COPY ./ ./

RUN ./gradlew bootJar

ENTRYPOINT ["java","-jar","./build/libs/greathealth-0.0.1-SNAPSHOT.jar"]
