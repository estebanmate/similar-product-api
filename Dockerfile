FROM amazoncorretto:17-alpine-jdk
COPY ./target/simpro-0.0.1-SNAPSHOT.jar simpro-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","simpro-0.0.1-SNAPSHOT.jar"]
EXPOSE 5000