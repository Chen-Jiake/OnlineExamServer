FROM openjdk:11
COPY *.jar /exam-test.jar
CMD ["--server.port=10015"]
EXPOSE 10015
ENTRYPOINT ["java", "-jar", "/exam-test.jar"]
