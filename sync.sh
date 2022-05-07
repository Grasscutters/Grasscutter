#!/bin/bash
if [ "$1" = "get" ];then
 git pull https://github.com/Grasscutters/Grasscutter.git development
 #git pull https://github.com/Akka0/Grasscutter.git tower
fi

if [ "$1" = "b" ];then
 ./gradlew jar
fi

if [ "$1" = "c" ];then
 ./gradlew clean
fi

if [ "$1" = "s" ];then
 java -jar grasscutter-1.0.3-dev.jar
fi