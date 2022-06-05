FROM adoptopenjdk/openjdk11
MAINTAINER ayrr
COPY target/portm-0.0.1.jar portm-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/portm-0.0.1.jar"]