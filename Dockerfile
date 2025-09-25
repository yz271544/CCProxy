FROM alpine/java:b08-jre

COPY target/CCProxy-1.0.jar /opt/CP/CP.jar
COPY java/cn/studio/cc/config/app.properties /opt/CP/config/app.properties

ENTRYPOINT ["java", "-Dccproxy.config=/opt/CP/config/app.properties", "-jar", "/opt/CP/CP.jar"]