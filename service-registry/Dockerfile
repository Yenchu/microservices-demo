FROM openjdk:8

VOLUME /tmp
RUN mkdir /msdemo
WORKDIR /msdemo
COPY ./target/service-registry.jar ./service-registry.jar
RUN bash -c 'touch service-registry.jar'

CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "service-registry.jar"]