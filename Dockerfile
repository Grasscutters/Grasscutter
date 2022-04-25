FROM gradle:7.4.2-jdk8 AS builder
WORKDIR /build
COPY . .

RUN tr -d '\015' <gradlew >gradlew-unix && \
    chmod +x gradlew-unix && \
    ./gradlew-unix jar


FROM lwieske/java-8:server-jre-8u202-slim
WORKDIR /app

COPY --from=builder /build/grasscutter.jar grasscutter.jar

# mount these folder/file at runtime please
# resources/ data/ config.json keystore.p12
COPY Grasscutter-Protos/proto/ ./proto
COPY keys/ ./keys

ENTRYPOINT ["java", "-jar", "grasscutter.jar"]
