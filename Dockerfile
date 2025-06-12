# Etapa 1: build da aplicação com Maven
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copia os arquivos do projeto
COPY pom.xml .
COPY src ./src

# Gera o JAR (skip tests para build mais rápido)
RUN mvn clean package -DskipTests

# Etapa 2: imagem final para rodar a aplicação
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copia o JAR gerado na etapa anterior
COPY --from=builder /app/target/gestao-financeira-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta padrão
EXPOSE 8080

# Comando de entrada
ENTRYPOINT ["java", "-jar", "app.jar"]
