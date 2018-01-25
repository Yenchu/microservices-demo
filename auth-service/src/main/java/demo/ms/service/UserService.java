package demo.ms.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import demo.ms.domain.User;
import demo.ms.util.IPUtils;

@Service
public class UserService {

	private List<User> users;
	
	@PostConstruct
	public void createUsers() {
		users = Stream.of(
					new User("albert"), 
					new User("alex"), 
					new User("andrew"))
				.collect(Collectors.toList());
	}
	
	public List<User> getUsers() {
		return users;
    }

	public User getUser(String username) {
		return users.stream()
				.filter(user -> user.getUsername().equals(username))
				.findFirst()
				.map(this::addExtraInfo)
				.orElseThrow(() -> new IllegalArgumentException("Can not find user " + username));
	}
	
	private User addExtraInfo(User user) {
		// add service instance info to demo load balancing
		Map<String, String> extra = IPUtils.getHostnameAndAddress();
		user.setExtra(extra);
		return user;
	}
	
	public User createUser(User user) {
		users.add(user);
		return user;
	}
}
