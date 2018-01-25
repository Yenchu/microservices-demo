package demo.ms.fallback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import com.netflix.hystrix.exception.HystrixTimeoutException;

public class AuthServiceFallbackProvider implements FallbackProvider {
	
	private static final Logger log = LoggerFactory.getLogger(AuthServiceFallbackProvider.class);

	@Override
	public String getRoute() {
		return "auth-service";
	}
	
	@Override
	public ClientHttpResponse fallbackResponse(final Throwable cause) {
		log.error("Fallback for error: {}", cause.getMessage());
		if (cause instanceof HystrixTimeoutException) {
			return response(HttpStatus.GATEWAY_TIMEOUT);
		} else {
			return fallbackResponse();
		}
	}

	@Override
	public ClientHttpResponse fallbackResponse() {
		return response(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ClientHttpResponse response(final HttpStatus status) {
		return new SimpleClientHttpResponse(status) {
			@Override
			public InputStream getBody() throws IOException {
				return new ByteArrayInputStream("Auth Service Unavailabe!".getBytes());
			}
		};
	}
}
