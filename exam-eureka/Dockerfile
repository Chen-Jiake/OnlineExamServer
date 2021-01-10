FROM java:8
COPY *.jar /exam-eureka.jar
CMD ["--server.port=10010"]
EXPOSE 10010
ENTRYPOINT ["java", "-jar", "/exam-eureka.jar"]