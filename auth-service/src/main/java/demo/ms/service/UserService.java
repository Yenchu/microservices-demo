package demo.ms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import demo.ms.domain.User;
import demo.ms.util.IPUtils;

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
		// log is used to demo sleuth: add span and trace IDs to log
		log.debug("Get user {}", username);
		
		if (StringUtils.isEmpty(username)) {
			throw new IllegalArgumentException("The input username is empty!");
		}
		
		Optional<User> userOptional = users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
		if (userOptional.isPresent()) {
			User user =  userOptional.get();
			
			// add service instance info to demo load balancing
			Map<String, String> extra = IPUtils.getHostnameAndAddress();
			user.setExtra(extra);
			return user;
		} else {
			throw new IllegalArgumentException("Can not find user " + username);
		}
	}
	
	public User createUser(User user) {
		users.add(user);
		return user;
	}
}
