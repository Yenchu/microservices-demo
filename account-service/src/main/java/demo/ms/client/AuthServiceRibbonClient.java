package demo.ms.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import demo.ms.vo.User;

@Component
public class AuthServiceRibbonClient {
	
	private static final Logger log = LoggerFactory.getLogger(AuthServiceRibbonClient.class);

    private RestTemplate restTemplate;

	@Autowired
	public AuthServiceRibbonClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@HystrixCommand(fallbackMethod = "defaultUser")
	public User getUser(String username) {
		User user = restTemplate.getForObject("http://auth-service/users/{username}", User.class, username);
		return user;
	}
	
	public User defaultUser(String username) {
		log.warn("Fallback method is called for getting user {}", username);
        return null;
	}
}
