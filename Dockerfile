FROM nexus.laowengs.com:8082/jdk/openjdk:17.0.1
WORKDIR /root
COPY target/kafka2db-adapter-es-*.jar /app/kafka2db-adapter-es.jar

CMD ["java", \
     "-XX:InitialRAMPercentage=50.0", "-XX:MaxRAMPercentage=50.0", \
     "-XX:+UseG1GC", "-XX:+HeapDumpOnOutOfMemoryError", \
     "-jar", "/app/kafka2db-adapter-es.jar"]
