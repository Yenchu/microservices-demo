package demo.ms;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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
	static class WebController implements ErrorController {
		
		private static final Logger log = LoggerFactory.getLogger(WebController.class);
		
		private static final String ERROR_MAPPING = "/error";

	    @Override
	    public String getErrorPath() {
	        return ERROR_MAPPING;
	    }
	    
		@GetMapping({ "", "/" })
		public String index() {
			// for demo
			return "redirect:/acct/accounts";
		}
		
		/**
		 * Using JSON message instead of error page.
		 * @param request
		 * @return
		 */
		@GetMapping(ERROR_MAPPING)
		public ResponseEntity<String> error(HttpServletRequest request) {
			Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
			String errorMsg = (String) request.getAttribute("javax.servlet.error.message");
			log.warn("Got error: {} {}", statusCode, errorMsg);
			
			HttpStatus status = HttpStatus.valueOf(statusCode);
			
			if (StringUtils.isEmpty(errorMsg)) {
				errorMsg = status.name();
			}
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			return new ResponseEntity<String>(errorMsg, headers, status);
		}
	}
}
