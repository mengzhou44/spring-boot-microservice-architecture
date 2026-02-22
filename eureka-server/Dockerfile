FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app
COPY pom.xml .
COPY config-server config-server
COPY eureka-server eureka-server
COPY api-gateway api-gateway
COPY user-service user-service
COPY order-service order-service

RUN apt-get update -q && apt-get install -y -q maven && \
    cd eureka-server && mvn package -DskipTests -q

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/eureka-server/target/eureka-server-1.0.0-SNAPSHOT.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]
