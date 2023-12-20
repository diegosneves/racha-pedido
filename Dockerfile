# Use a imagem oficial do OpenJDK 17 como a imagem base
FROM openjdk:17-jdk-alpine

# Define o diretório de trabalho no contêiner
WORKDIR /app

# Copia o arquivo pom.xml para o diretório de trabalho
COPY pom.xml .

# Copia o código-fonte para o diretório de trabalho
COPY . .

# Instala o Maven
RUN apk --no-cache add maven

# Compila o código-fonte usando o Maven
RUN mvn package -DskipTests

# Expõe a porta 8080 (ou a porta que a sua aplicação Java está configurada para usar)
EXPOSE 8080

# Comando para iniciar a aplicação quando o contêiner for iniciado
CMD ["java", "-jar", "target/racha-pedido.jar"]