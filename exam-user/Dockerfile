FROM openjdk:11
COPY *.jar /exam-user.jar
CMD ["--server.port=10013"]
EXPOSE 10013
ENTRYPOINT ["java", "-jar", "/exam-user.jar"]
