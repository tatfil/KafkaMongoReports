FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

#proper line, doesn't work
#COPY build/libs/main-0.0.1-SNAPSHOT.jar /app/main-0.0.1-SNAPSHOT.jar
COPY main-0.0.1-SNAPSHOT.jar /app/main-0.0.1-SNAPSHOT.jar

EXPOSE 8080
CMD ["java", "-jar", "main-0.0.1-SNAPSHOT.jar"]