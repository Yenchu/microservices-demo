FROM openjdk:8

VOLUME /tmp
RUN mkdir /msdemo
WORKDIR /msdemo
COPY ./target/account-service.jar ./account-service.jar
RUN bash -c 'touch account-service.jar'

CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "account-service.jar"]