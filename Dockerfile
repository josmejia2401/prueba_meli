FROM openjdk:11
VOLUME /tmp
EXPOSE 8081
ARG JAR_FILE=target/spring-boot-crud.jar
ADD ${JAR_FILE} app.jar
RUN sh -c 'touch /app.jar'
RUN ls -S
ENTRYPOINT ["java","-jar","./app.jar"]