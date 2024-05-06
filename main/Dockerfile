FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

COPY build/libs/main-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

CMD ["java", "-jar", "main-0.0.1-SNAPSHOT.jar"]