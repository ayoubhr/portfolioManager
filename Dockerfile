FROM adoptopenjdk/openjdk11
MAINTAINER ayrr
COPY target/portmanager-0.0.1.jar portmanager-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/portmanager-0.0.1.jar"]