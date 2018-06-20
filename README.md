# Microservices Demo
Using Spring Cloud to develop microservices application and deploy to Kubernetes.

* Externalized configuration: Spring Cloud Config
* Service registry and discovery: Eureka
* Client side load balancing: Ribbon, Feign
* API gateway: Zuul
* Circuit breaker: Hystrix, Turbine
* Distributed tracing: Sleuth, Zipkin

## Deployment
* `build-images.sh` is used to build docker images.
  It tags the images with `192.168.99.100:5000` prefix for uploading them to a local registry server on `192.168.99.100`.
  Update to the correct address if your registry server's address is not `192.168.99.100:5000`.
  If you don't deploy to Kubernetes, you can remove those prefix.
* `push-images.sh` is used to push docker images to the registry server.
* `deploy-docker.sh` is used to deploy the demo app to docker using `docker-compose.yml`.
* `undeploy-docker.sh` is used to undeploy the demo app in docker.
* `deploy-kubernetes.sh` is used to deploy the demo app to kubernetes.
  To deploy to Kubernetes, this demo uses `Minikube`.
* `undeploy-kubernetes.sh` is used to undeploy the demo app in kubernetes.

There are three ways to deploy this demo application.
### Local Deployment
* You need to provide RabbitMQ and Zipkin services.
  - RabbitMQ:
    
    Please see https://www.rabbitmq.com/download.html
    If using docker, you can use the following command:
    ```
    docker run -d -p 5672:5672 -p 15672:15672 -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=amqp --name rabbitmq rabbitmq:management
    ```
    
  - Zipkin:
    
    Please see https://github.com/openzipkin/zipkin
    ```
    # Download Zipkin server
    curl -sSL https://zipkin.io/quickstart.sh | bash -s
    # Start Zipkin server
    RABBIT_ADDRESSES=localhost RABBIT_USER=admin RABBIT_PASSWORD=amqp java -jar zipkin.jar
    ```
    
* Edit `config-server/src/main/resources/application.properties`:
  * Make sure `RABBITMQ_SERVICE_HOST`, `RABBITMQ_SERVICE_PORT`, `RABBITMQ_SERVICE_USERNAME` and  `RABBITMQ_SERVICE_PASSWORD`
    match your RabbitMQ configuration.
  * Set `APP_CONFIG_DIR` and `APP_LOGGINH_DIR` to your correct file path.
* Use `java -jar` to start each service, config-server should be started first, service-registry next, then other services.
* Access services with its URL, eg:
```
api-gateway:  http://localhost:8080/
auth-service:  http://localhost:9000/
account-service:  http://localhost:9010/
service-registry: http://localhost:8761/
config-server:  http://localhost:8888/
turbine-server:  http://localhost:8989/
monitor-dashboard:  http://localhost:7979/
zipkin:  http://localhost:9411/
```

### Docker Deployment
* Download rabbitmq:management image.
  Tag the image with `192.168.99.100:5000/rabbitmq:management` if you use `192.168.99.100:5000` prefix in `build-images.sh`.
* Download openzipkin/zipkin image.
  Tag the image with `192.168.99.100:5000/zipkin` if you use `192.168.99.100:5000` prefix in `build-images.sh`.
* Edit `microservices-demo/deploy-docker.sh`, set `APP_CONFIG_DIR` and `APP_LOGGINH_DIR` to your correct file path.
* Run the script `microservices-demo/build-images.sh` to build docker images.
* Run the script `microservices-demo/deploy-docker.sh` to start containers.
* Run the script `microservices-demo/undeploy-docker.sh` if you want to stop and remove the containers.
* Access a service with its URL (same as Local Deployment)

### Kubernetes Deployment (v1.10.0)
* Download rabbitmq:management image.
  Tag the image with `192.168.99.100:5000/rabbitmq:management`.
* Download openzipkin/zipkin image.
  Tag the image with `192.168.99.100:5000/zipkin`.
* Edit `microservices-demo/kubernetes/config-pv.yaml`, set `spec.hostPath.path` to your correct file path.
  Edit `microservices-demo/kubernetes/log-pv.yaml`, set `spec.hostPath.path` to your correct file path.
* Run the script `microservices-demo/build-images.sh` to build docker images.
* Run the script `microservices-demo/push-images.sh` to upload docker images to registry server.
* Run the script `microservices-demo/deploy-kubernetes.sh` to deploy to Kubernetes.
* Run the script `microservices-demo/undeploy-kubernetes.sh` if you want to stop and remove the containers.
* Access services using command `minikube service SERVICE_NAME` eg: 
```
minikube service api-gateway
minikube service auth-service
minikube service account-service
minikube service service-registry
minikube service config-server
minikube service monitor-dashboard
minikube service zipkin
```
