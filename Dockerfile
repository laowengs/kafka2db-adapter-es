FROM nexus.laowengs.com:8082/jdk/openjdk:17.0.1
WORKDIR /root
COPY target/kafka2db-adapter-es-*.jar /app/kafka2db-adapter-es.jar
COPY apm /app/apm

CMD ["java", \
     "-XX:InitialRAMPercentage=50.0", "-XX:MaxRAMPercentage=50.0", \
     "-XX:+UseG1GC", "-XX:+HeapDumpOnOutOfMemoryError", \
     "-javaagent:/Users/laoweng/data/code/laowengs/kafka2db-adapter-es/src/main/resources/apm/skywalking-agent.jar -DSW_AGENT_NAME=kafka2db-adapter-es -DSW_AGENT_COLLECTOR_BACKEND_SERVICES=192.168.3.21:11800 ",\
     "-jar", "/app/kafka2db-adapter-es.jar"]
