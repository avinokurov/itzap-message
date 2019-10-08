# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine
COPY target/itzap-message-1.0-SNAPSHOT.jar /opt/lib/
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "/opt/lib/itzap-message-1.0-SNAPSHOT.jar"]
EXPOSE 8025