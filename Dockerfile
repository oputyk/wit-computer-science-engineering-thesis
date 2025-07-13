FROM node:alpine as frontendbuild

WORKDIR /usr/src/app

COPY ./frontend /usr/src/app

RUN npm install -g @angular/cli

RUN npm install

RUN npm run build


FROM eclipse-temurin:17-jdk-alpine

WORKDIR /opt/app

COPY ./backend/ ./

COPY --from=frontendbuild /usr/src/app/dist/greathealth/browser/ /opt/app/src/main/resources/static/

ENV WEB_PORT 80

RUN ./gradlew bootJar

ENTRYPOINT ["java","-jar","./build/libs/greathealth-0.0.1-SNAPSHOT.jar"]
