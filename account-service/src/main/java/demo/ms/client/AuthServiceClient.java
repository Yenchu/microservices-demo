package demo.ms.client;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.ms.vo.User;

@FeignClient(name = "auth-service", fallback = AuthServiceClientFallback.class)
public interface AuthServiceClient {
	
	@RequestMapping(path = "/users", method = GET)
	List<User> getUsers();
	
	@RequestMapping(path = "/users/{username}", method = GET)
	User getUser(@PathVariable("username") String username);
	
	// createUser and deleteUser are used to demo circuit breaker fallback
	@RequestMapping(path = "/users", method = POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	User createUser(User user);
	
	@RequestMapping(path = "/users/{username}", method = DELETE)
	void deleteUser(@PathVariable("username") String username);
	
}