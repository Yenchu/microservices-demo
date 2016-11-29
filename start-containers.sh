#!/bin/bash

set -e

export APP_CONFIG_DIR=/Users/yenchu/git/microservices-demo/config-server/src/main/resources/shared
export APP_LOGGINH_DIR=/Users/yenchu/workspace/log

docker-compose up -d
