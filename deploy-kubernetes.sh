#!/bin/bash

set -e

kubectl create -f kubernetes/env-configmap.yaml

kubectl create -f kubernetes/config-pv.yaml -f kubernetes/config-pvc.yaml

kubectl create -f kubernetes/log-pv.yaml -f kubernetes/log-pvc.yaml

kubectl create -f kubernetes/rabbitmq-deployment.yaml -f kubernetes/rabbitmq-service.yaml

kubectl create -f kubernetes/config-deployment.yaml -f kubernetes/config-service.yaml

kubectl create -f kubernetes/registry-deployment.yaml -f kubernetes/registry-service.yaml

kubectl create -f kubernetes/tracing-deployment.yaml -f kubernetes/tracing-service.yaml

kubectl create -f kubernetes/dashboard-deployment.yaml -f kubernetes/dashboard-service.yaml

kubectl create -f kubernetes/auth-deployment.yaml -f kubernetes/auth-service.yaml

kubectl create -f kubernetes/account-deployment.yaml -f kubernetes/account-service.yaml

kubectl create -f kubernetes/gateway-deployment.yaml -f kubernetes/gateway-service.yaml
