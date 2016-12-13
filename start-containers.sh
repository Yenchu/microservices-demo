#!/bin/bash

set -e

export APP_CONFIG_DIR=/Users/yenchu/git/microservices-demo/config-server/src/main/resources/shared
export APP_LOGGING_DIR=/Users/yenchu/log/microservices-demo

docker-compose up -d
