FROM openjdk:11
COPY *.jar /exam-auth.jar
CMD ["--server.port=10012"]
EXPOSE 10012
ENTRYPOINT ["java", "-jar", "/exam-auth.jar"]
