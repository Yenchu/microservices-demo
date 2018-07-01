package demo.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@SpringBootApplication
public class CloudGatewayApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(CloudGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // shortcut for index page
                // it may be better to use a UI microservice
                .route("index", p -> p.path("/").or().path("/index.html")
                        .filters(f -> f.setPath("/static/index.html"))
                        .uri("lb://cloud-gateway"))
                // set larger order to put this route to lower priority, so it wouldn't override other routes
                .route("not_found", p -> p.order(1000).path("/static/**").negate()
                        .filters(f -> f.setPath("/not-found"))
                        .uri("lb://cloud-gateway"))
                .build();
    }

    @RestController
    static class WebController {

        @GetMapping("/not-found")
        public Mono<String> notFound() {
            return Mono.just("Not found!");
        }

        @GetMapping("/auth-service-fallback")
        public Mono<String> authServiceFallback() {
            return Mono.just("Auth Service Unavailabe!");
        }

        @GetMapping("/account-service-fallback")
        public Mono<String> accountServiceFallback() {
            return Mono.just("Account Service Unavailabe!");
        }
    }
}
