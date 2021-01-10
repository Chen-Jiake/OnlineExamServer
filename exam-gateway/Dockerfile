FROM openjdk:11
COPY *.jar /exam-gateway.jar
CMD ["--server.port=10011"]
EXPOSE 10011
ENTRYPOINT ["java", "-jar", "/exam-gateway.jar"]
