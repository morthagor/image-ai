FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Argumentos de build
ARG JAR_FILE=target/*.jar
ARG ENVIRONMENT=prod

# Variáveis de ambiente
ENV SPRING_PROFILES_ACTIVE=${ENVIRONMENT}

# Copia o arquivo JAR
COPY ${JAR_FILE} app.jar

# Expõe a porta da aplicação
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]