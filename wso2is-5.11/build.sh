#!/bin/bash
NAME=wso2is-5.11
docker build -f docker/Dockerfile \
    -t $NAME \
    docker
