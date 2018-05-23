package demo.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.stream.EnableTurbineStream;

@SpringBootApplication
@EnableTurbineStream
public class TurbineServerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(TurbineServerApplication.class, args);
	}
}
