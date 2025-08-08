#FROM openjdk:21-jdk-slim
#WORKDIR /app
#EXPOSE 9000
#COPY target/auth-service-*.jar auth-service.jar
#ENV JAVA_OPTS=""
#ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/auth-service.jar"]

# --- Etapa 1: Compilación ---
# Usa una imagen de Maven con Java 21 para compilar el proyecto
FROM maven:3.9-eclipse-temurin-21-alpine AS build

# Establece el directorio de trabajo
WORKDIR /app

# Copia los archivos de configuración de Maven y descarga dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia el resto del código fuente y compila la aplicación
COPY src ./src
RUN mvn clean package -DskipTests

# --- Etapa 2: Imagen Final ---
# Usa una imagen de Java ligera, solo con el entorno de ejecución
FROM openjdk:21-jdk-slim

# Establece el directorio de trabajo
WORKDIR /app

# Expone el puerto correcto para auth-service
EXPOSE 9000

# Copia únicamente el JAR compilado desde la etapa de 'build'
COPY --from=build /app/target/ms-auth*.jar ms-auth.jar

# El JAVA_OPTS se mantiene igual
ENV JAVA_OPTS=""

# El ENTRYPOINT ejecuta el JAR
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/ms-auth.jar"]