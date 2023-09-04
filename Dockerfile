#Stage 1
# initialize build and set base image for first stage
FROM maven:3.8.2-eclipse-temurin-17 as stage1
# speed up Maven JVM a bit
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
# set working directory
WORKDIR /opt/release-tracker
# copy just pom.xml
COPY pom.xml .
# go-offline using the pom.xml
RUN mvn dependency:go-offline
# copy your other files
COPY ./src ./src
# compile the source code and package it in a jar file
RUN mvn clean install -Dmaven.test.skip=true
#Stage 2
# set base image for second stage
FROM eclipse-temurin:17
# set deployment directory
WORKDIR /opt/release-tracker
# copy over the built artifact from the maven image
COPY --from=stage1 /opt/release-tracker/target/release-tracker.jar /opt/release-tracker

ENTRYPOINT ["java","-jar","/opt/release-tracker/release-tracker.jar"]

