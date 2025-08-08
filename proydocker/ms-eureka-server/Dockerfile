FROM openjdk:21-jdk-slim
WORKDIR /app
EXPOSE 8761
COPY target/ms-eureka-server-*.jar ms-eureka-server.jar

# JAVA_OPTS vacío por defecto para poder pasar opciones desde docker-compose
ENV JAVA_OPTS=""

# Usamos sh -c para que $JAVA_OPTS sea expandido
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/ms-eureka-server.jar"]
