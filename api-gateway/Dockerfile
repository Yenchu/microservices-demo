FROM openjdk:8

VOLUME /tmp
RUN mkdir /msdemo
WORKDIR /msdemo
COPY ./target/api-gateway.jar ./api-gateway.jar
RUN bash -c 'touch api-gateway.jar'

CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "api-gateway.jar"]