package demo.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import demo.ms.fallback.AccountServiceFallbackProvider;
import demo.ms.fallback.AuthServiceFallbackProvider;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@EnableCircuitBreaker
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
	
	/**
	 * To enable Ribbon and retry support.
	 */
	@LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

	/**
	 * To provide Hystrix fallback.
	 */
	@Bean
	AuthServiceFallbackProvider authServiceFallbackProvider() {
		return new AuthServiceFallbackProvider();
	}

	@Bean
	AccountServiceFallbackProvider accountServiceFallbackProvider() {
		return new AccountServiceFallbackProvider();
	}

	@Controller
	static class WebController {

		@GetMapping(path = { "", "/" })
		public String index() {
			// for demo
			return "redirect:/acct/accounts";
		}
	}
}
