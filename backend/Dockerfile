# Usar una imagen base con Java 11
FROM eclipse-temurin:11-jre-alpine


# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR empaquetado por Maven al contenedor
# El nombre del JAR puede variar, usamos un comodín
COPY target/Hidrored-*.jar app.jar

# Exponer el puerto en el que corre la aplicación Spring Boot
EXPOSE 8080

# Comando para ejecutar la aplicación cuando se inicie el contenedor
ENTRYPOINT ["java","-jar","/app/app.jar"]
