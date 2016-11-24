FROM openjdk:8

VOLUME /tmp
RUN mkdir /msdemo
WORKDIR /msdemo
COPY ./target/config-server.jar ./config-server.jar
RUN bash -c 'touch config-server.jar'

CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "config-server.jar"]