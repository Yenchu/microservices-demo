FROM openjdk:8

VOLUME /tmp
RUN mkdir /msdemo
WORKDIR /msdemo
COPY ./target/tracing-server.jar ./tracing-server.jar
RUN bash -c 'touch tracing-server.jar'

CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "tracing-server.jar"]