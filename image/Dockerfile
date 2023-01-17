FROM openjdk:17-jdk-slim

RUN apt update && \
    apt install -y unzip wget && \
    mkdir /grasscutter && cd /grasscutter && \
    wget -O grasscutter.jar https://github.com/Grasscutters/Grasscutter/releases/download/v1.4.3/grasscutter-1.4.3.jar && \
    wget https://raw.githubusercontent.com/Grasscutters/Grasscutter/development/keystore.p12 && \
    wget -P /grasscutter/resources https://git.crepe.moe/grasscutters/Grasscutter_Resources/-/archive/main/Grasscutter_Resources-main.zip && \
    unzip -d /grasscutter/resources /grasscutter/resources/Grasscutter_Resources-main.zip && rm /grasscutter/resources/Grasscutter_Resources-main.zip && \
    unzip -d /grasscutter/resources /grasscutter/resources/Grasscutter_Resources-main/Grasscutter_Resources-3.3.zip && rm -rf /grasscutter/resources/Grasscutter_Resources-main && \
    mv /grasscutter/resources/Grasscutter_Resources-3.3/Resources/* /grasscutter/resources/ && rm -rf /grasscutter/resources/Grasscutter_Resources-3.3 && \
    apt remove -y unzip wget

EXPOSE 443
EXPOSE 80
EXPOSE 8888
EXPOSE 22102

WORKDIR /grasscutter

ENTRYPOINT [ "java", "-jar", "grasscutter.jar" ]
