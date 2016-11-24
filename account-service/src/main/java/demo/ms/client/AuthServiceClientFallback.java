package demo.ms.client;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import demo.ms.vo.User;

@Component
public class AuthServiceClientFallback implements AuthServiceClient {
	
	private static final Logger log = LoggerFactory.getLogger(AuthServiceClientFallback.class);

	@Override
	public List<User> getUsers() {
		log.warn("Fallback method is called for getting users");
		return null;
	}

	@Override
	public User getUser(String username) {
		log.warn("Fallback method is called for getting user {}", username);
        return null;
	}

	@Override
	public User createUser(User user) {
		log.warn("Fallback method is called for creating user {}", user.getUsername());
        return null;
	}

	@Override
	public void deleteUser(String username) {
		log.warn("Fallback method is called for deleting user {}", username);
	}
}
