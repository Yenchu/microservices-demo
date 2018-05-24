package demo.ms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

@SpringBootApplication
@EnableHystrixDashboard
public class MonitorDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitorDashboardApplication.class, args);
	}
	
	@Bean
	@LoadBalanced
	public WebClient.Builder loadBalancedWebClientBuilder() {
		return WebClient.builder();
	}
	
	@Controller
	static class WebController {
		
		@Autowired
	    private WebClient.Builder webClientBuilder;
		
		@Value("${server.port}")
		private Integer serverPort;
		
		@Value("${turbine.aggregator.clusterConfig}")
		private String clusterName;

		@GetMapping("/turbine-stream")
	    public Flux<ServerSentEvent> turbineStream() {
	        return webClientBuilder
					.build()
					.get()
					.uri("http://turbine-server/turbine.stream?cluster=" + clusterName)
					.retrieve()
					.bodyToFlux(ServerSentEvent.class);
	    }
		
		@GetMapping({"", "/"})
		public String index() {
			return "redirect:/hystrix/monitor?stream=http://localhost:" + serverPort + "/turbine-stream";
		}
	}
}