FROM openjdk:8

VOLUME /tmp
RUN mkdir /msdemo
WORKDIR /msdemo
COPY ./target/auth-service.jar ./auth-service.jar
RUN bash -c 'touch auth-service.jar'

CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "auth-service.jar"]