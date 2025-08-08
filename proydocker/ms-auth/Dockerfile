FROM openjdk:21-jdk-slim
WORKDIR /app
EXPOSE 9000
COPY target/ms-auth-*.jar ms-auth.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/ms-auth.jar"]