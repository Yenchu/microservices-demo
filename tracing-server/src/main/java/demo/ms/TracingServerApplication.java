package demo.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;

@SpringBootApplication
@EnableZipkinStreamServer
public class TracingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TracingServerApplication.class, args);
	}
}
