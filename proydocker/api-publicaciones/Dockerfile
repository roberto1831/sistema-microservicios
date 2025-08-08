FROM openjdk:21-jdk-slim
WORKDIR /app
EXPOSE 9001
COPY target/api-publicaciones-*.jar api-publicaciones.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/api-publicaciones.jar"]