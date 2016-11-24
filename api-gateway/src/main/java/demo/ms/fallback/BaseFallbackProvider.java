package demo.ms.fallback;

import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.client.ClientHttpResponse;

public abstract class BaseFallbackProvider implements ZuulFallbackProvider {

	@Override
	public ClientHttpResponse fallbackResponse() {
		return new SimpleClientHttpResponse();
	}
}
