# Microservices Demo
Using Spring Cloud to develop microservices and deploy to Kubernetes.

* Externalized configuration: Spring Cloud Config
* Service registry and discovery: Eureka
* Client side load balancing: Ribbon, Feign
* API gateway: Zuul
* Circuit breaker: Hystrix, Turbine
* Distributed tracing: Sleuth, Zipkin

## Deployment
* `microservices-demo/build-images.sh` is used to build docker images.
  It tags the images with `192.168.99.100:5000` prefix for uploading them to the registry server on `192.168.99.100`.
  Update them to the correct address if your registry server' address is not `192.168.99.100:5000`.
  If you don't deploy to Kubernetes, you can remove those prefix.
* To deploy to Kubernetes, this demo uses `Minikube`.

There are three ways to deploy this demo application.
### Local Deployment
* You need to provide RabbitMQ service.
* Edit `config-server/src/main/resources/application.properties`:
  * Make sure `RABBITMQ_SERVICE_HOST`, `RABBITMQ_SERVICE_PORT`, `RABBITMQ_SERVICE_USERNAME` and  `RABBITMQ_SERVICE_PASSWORD`
    match your RabbitMQ configuration.
  * Set `APP_CONFIG_DIR` and `APP_LOGGINH_DIR` to your correct file path.
* Run `java -jar _service-jar-file_` to start each service, config-server should be started first, service-registry next, then other services.

### Docker Deployment
* Download rabbitmq-management image.
  Tag the image with `192.168.99.100:5000/rabbitmq:management` if you don't remove the `192.168.99.100:5000` prefix in `build-images.sh`.
* Edit `microservices-demo/start-containers.sh`, set `APP_CONFIG_DIR` and `APP_LOGGINH_DIR` to your correct file path.
* Run the script `microservices-demo/build-images.sh` to build docker images.
* Run the script `microservices-demo/start-containers.sh` to start containers.
* Run the script `microservices-demo/rm-containers.sh` if you want to stop and remove the containers.

### Kubernetes Deployment
* Download rabbitmq-management image.
  Tag the image with `192.168.99.100:5000/rabbitmq:management`.
* Edit `microservices-demo/kubernetes/config-pv.yaml`, set `spec.hostPath.path` to your correct file path.
  Edit `microservices-demo/kubernetes/log-pv.yaml`, set `spec.hostPath.path` to your correct file path.
* Run the script `microservices-demo/build-images.sh` to build docker images.
* Run the script `microservices-demo/push-images.sh` to upload docker images to registry server.
* Run the script `microservices-demo/deploy-kubernetes.sh` to deploy to Kubernetes.
* Run the script `microservices-demo/undeploy-kubernetes.sh` if you want to stop and remove the containers.
