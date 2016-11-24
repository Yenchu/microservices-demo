#!/bin/bash

set -e

kubectl delete deploy,svc api-gateway
kubectl delete deploy,svc account-service
kubectl delete deploy,svc auth-service
kubectl delete deploy,svc monitor-dashboard
kubectl delete deploy,svc tracing-server
kubectl delete deploy,svc service-registry
kubectl delete deploy,svc config-server

kubectl delete deploy,svc rabbitmq

kubectl delete pvc config-claim log-claim
kubectl delete pv config-pv log-pv

kubectl delete cm env-config
