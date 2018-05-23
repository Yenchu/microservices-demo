FROM openjdk:8

VOLUME /tmp
RUN mkdir /msdemo
WORKDIR /msdemo
COPY ./target/turbine-server.jar ./turbine-server.jar
RUN bash -c 'touch turbine-server.jar'

CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "turbine-server.jar"]