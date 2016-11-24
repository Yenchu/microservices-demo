package demo.ms.fallback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.client.ClientHttpResponse;

public class AccountServiceFallbackProvider implements ZuulFallbackProvider {

	@Override
	public String getRoute() {
		return "account-service";
	}
	
	@Override
	public ClientHttpResponse fallbackResponse() {
		return new SimpleClientHttpResponse() {
			
			@Override
		    public InputStream getBody() throws IOException {
		        return new ByteArrayInputStream("Account Service Unavailabe!".getBytes());
		    }
		};
	}
}
