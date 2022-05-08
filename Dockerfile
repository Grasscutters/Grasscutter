# Create a multi-stage container build
FROM eclipse-temurin:17 as build

# Build application
WORKDIR /grasscutter
COPY . .
RUN chmod +x gradlew && ./gradlew jar

# Define base image
FROM eclipse-temurin:17-jre

# Application deployment
WORKDIR /grasscutter
COPY --from=build /grasscutter/grasscutter*.jar grasscutter.jar
COPY data data
COPY keys keys
COPY keystore.p12 keystore.p12
RUN echo '{"DatabaseUrl":"mongodb://mongo:27017","GameServer":{"DispatchServerDatabaseUrl":"mongodb://mongo:27017"}}' > config.json
CMD ["java", "-jar", "/grasscutter/grasscutter.jar"]