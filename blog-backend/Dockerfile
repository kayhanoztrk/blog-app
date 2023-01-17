FROM openjdk:8-jdk-alpine
WORKDIR /src/uygulamam
COPY /target/blog-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
CMD ["java","-jar","app.jar"]