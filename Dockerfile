# Builder
FROM gradle:8.6-jdk17-alpine as builder

WORKDIR /app
COPY ./ /app/

RUN gradle jar --no-daemon

# Fetch Data
FROM bitnami/git:2.43.0-debian-11-r1 as data

ARG DATA_REPOSITORY=https://gitlab.com/YuukiPS/GC-Resources.git
ARG DATA_BRANCH=4.0

WORKDIR /app

RUN git clone --branch ${DATA_BRANCH} --depth 1 ${DATA_REPOSITORY}

# Result Container
FROM amazoncorretto:17-alpine

RUN apt-get update && apt-get install unzip

WORKDIR /app

# Copy built assets
COPY --from=builder /app/grasscutter-1.7.4.jar /app/grasscutter.jar
COPY --from=builder /app/keystore.p12 /app/keystore.p12

# Copy the resources
COPY --from=data /app/GC-Resources/Resources /app/resources/

# Copy startup files
COPY ./entrypoint.sh /app/

CMD [ "sh", "/app/entrypoint.sh" ]

EXPOSE 80 443 8888 22102
