#!/bin/bash

set -e

docker push 192.168.99.100:5000/config-server
docker push 192.168.99.100:5000/service-registry
docker push 192.168.99.100:5000/monitor-dashboard
docker push 192.168.99.100:5000/auth-service
docker push 192.168.99.100:5000/account-service
docker push 192.168.99.100:5000/api-gateway
