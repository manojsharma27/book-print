FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
ADD target/book-print-0.0.1-SNAPSHOT.jar /app/book-print.jar
ENTRYPOINT ["java", "-jar", "/app/book-print.jar"]