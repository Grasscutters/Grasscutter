FROM gradle:7.4.2-jdk8 AS builder
WORKDIR /build
COPY . .

RUN tr -d '\015' <gradlew >gradlew-unix
RUN chmod +x gradlew-unix
RUN ./gradlew-unix jar


FROM lwieske/java-8:jdk-8u202
WORKDIR /app

COPY --from=builder /build/grasscutter.jar grasscutter.jar

# mount resources at runtime plz
COPY Grasscutter-Protos/proto/ ./proto
COPY keys/ ./keys
COPY data/ ./data
COPY keystore.p12 .

ENTRYPOINT ["java", "-jar", "grasscutter.jar"]
