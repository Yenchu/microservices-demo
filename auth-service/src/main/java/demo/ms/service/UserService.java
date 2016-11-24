package demo.ms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import demo.ms.domain.User;

@Service
public class UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	private List<User> users;
	
	@PostConstruct
	public void createUsers() {
		users = new ArrayList<>();
		User user = new User("albert");
		users.add(user);
		user = new User("alex");
		users.add(user);
		user = new User("andrew");
		users.add(user);
	}
	
	public List<User> getUsers() {
		return users;
    }

	public User getUser(String username) {
		// just to demo sleuth
		log.debug("Get user {}", username);
		
		if (StringUtils.isEmpty(username)) {
			throw new RuntimeException("The input username is empty!");
		}
		
		Optional<User> userOptional = users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			throw new RuntimeException("Can not find user " + username);
		}
	}
}
