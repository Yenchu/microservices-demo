FROM openjdk:8

VOLUME /tmp
RUN mkdir /msdemo
WORKDIR /msdemo
COPY ./target/monitor-dashboard.jar ./monitor-dashboard.jar
RUN bash -c 'touch monitor-dashboard.jar'

CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "monitor-dashboard.jar"]