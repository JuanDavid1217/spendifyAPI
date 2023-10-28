FROM eclipse-temurin:11
RUN mkdir /opt/app
WORKDIR /opt/app
ARG JAR_FILE=target/Examen1JuanDavidDelgadoMunoz-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "app.jar" ]
