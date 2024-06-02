#!/usr/bin/env bash
# Cleanup docker environment and build PHP image
docker system prune --volumes -f

docker build -f ./images/go/Dockerfile-GO -t tgl/go-consumer-app:latest .


# Run the compose (optional)
# docker-compose up -d