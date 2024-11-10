# Usar una imagen base de OpenJDK 17 para Java
FROM openjdk:21-jdk

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR de la aplicación Spring Boot al contenedor
COPY build/libs/apicalendario-0.0.1-SNAPSHOT.jar /app/apicalendario-0.0.1-SNAPSHOT.jar

# Exponer el puerto 8080 en el contenedor
EXPOSE 8080

# Definir el comando que se ejecutará cuando se inicie el contenedor
ENTRYPOINT ["java", "-jar", "/app/apicalendario-0.0.1-SNAPSHOT.jar"]
