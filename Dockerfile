########## DOCKER WITH MAVEN COMPILE

FROM maven:3.8-openjdk-17-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM eclipse-temurin:17-jre-alpine
COPY --from=build /home/app/target/userservices-0.0.1-SNAPSHOT.jar /usr/local/lib/userservices-0.0.1-SNAPSHOT.jar

#EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/userservices-0.0.1-SNAPSHOT.jar"]



######### NO MAVEN
# Fetching latest version of Java
#FROM openjdk:latest
#FROM openjdk:17-alpine
#FROM eclipse-temurin:17-alpine
#FROM eclipse-temurin:17-jre-alpine

# Setting up work directory
#WORKDIR /usr/app

# Create a user group 'xyzgroup'
#RUN addgroup -S xyzgroup

# Create a user 'appuser' under 'xyzgroup'
#RUN adduser -S -D -h /usr/app appuser xyzgroup

# Chown all the files to the app user.
#RUN chown -R appuser:xyzgroup /usr/app

# Switch to 'appuser'
#USER appuser 

# Copy the jar file into our app
#COPY ./target/*.jar /usr/app

# Exposing port 8080
#EXPOSE 8080

# Starting the application
#CMD ["java", "-jar", "baype-0.0.1-SNAPSHOT.jar"]