FROM maven:4.0.0-rc-4-amazoncorretto AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8888
ENTRYPOINT ["java","-jar","app.jar"]