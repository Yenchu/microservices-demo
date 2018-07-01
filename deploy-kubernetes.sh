#!/bin/bash

set -e

kubectl create -f kubernetes/config-secret.yaml

kubectl create -f kubernetes/env-configmap.yaml

kubectl create -f kubernetes/config-pv.yaml

kubectl create -f kubernetes/log-pv.yaml

kubectl create -f kubernetes/rabbitmq.yaml

kubectl create -f kubernetes/zipkin.yaml

kubectl create -f kubernetes/config-server.yaml

kubectl create -f kubernetes/service-registry.yaml

kubectl create -f kubernetes/turbine-server.yaml

kubectl create -f kubernetes/monitor-dashboard.yaml

kubectl create -f kubernetes/auth-service.yaml

kubectl create -f kubernetes/account-service.yaml

kubectl create -f kubernetes/cloud-gateway.yaml
