package demo.ms.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import demo.ms.vo.User;

@Component
public class AuthServiceRibbonClient {
	
	private static final Logger log = LoggerFactory.getLogger(AuthServiceRibbonClient.class);

    private RestTemplate restTemplate;

	public AuthServiceRibbonClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@HystrixCommand(fallbackMethod = "defaultGetUsers")
	public List<User> getUsers() {
		User[] users = restTemplate.getForObject("http://auth-service/users", User[].class);
		return Arrays.asList(users);
	}
	
	public List<User> defaultGetUsers() {
		log.warn("Fallback method is called for getting users");
        return new ArrayList<User>();
	}

	@HystrixCommand(fallbackMethod = "defaultGetUser")
	public User getUser(String username) {
		return restTemplate.getForObject("http://auth-service/users/{username}", User.class, username);
	}
	
	public User defaultGetUser(String username) {
		log.warn("Fallback method is called for getting user {}", username);
        return new User();
	}

	@HystrixCommand(fallbackMethod = "defaultDeleteUser")
	public void deleteUser(String username) {
		restTemplate.delete("http://auth-service/users/{username}", username);
	}
	
	public void defaultDeleteUser(String username) {
		log.warn("Fallback method is called for deleting user {}", username);
	}
}
