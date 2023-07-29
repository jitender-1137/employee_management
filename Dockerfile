FROM openjdk:17
EXPOSE 8085
ADD target/*.jar em-docker.jar
ENTRYPOINT ["java","-jar","/em-docker.jar"]