# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine
COPY message-impl/target/message-impl-0.0.1-SNAPSHOT.jar /opt/lib/
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "/opt/lib/message-impl-0.0.1-SNAPSHOT.jar"]
EXPOSE 8025