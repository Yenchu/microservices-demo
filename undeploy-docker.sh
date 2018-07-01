#!/bin/bash

set -e

docker-compose stop

docker rm monitor-dashboard turbine-server cloud-gateway account-service auth-service service-registry config-server zipkin rabbitmq
