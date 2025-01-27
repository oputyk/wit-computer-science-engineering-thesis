FROM eclipse-temurin:17-jdk-alpine

WORKDIR /opt/app

COPY ./ ./

RUN ./gradlew bootJar

ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005","-jar","./build/libs/greathealth-0.0.1-SNAPSHOT.jar"]
