package demo.ms.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.ms.domain.User;
import demo.ms.service.UserService;
import demo.ms.util.IPUtils;

@RefreshScope
@RestController
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	// to demo @RefreshScope
	private String welcomeMsg;

	private UserService userService;

	@Autowired
	public UserController(UserService userService, @Value("${welcome.msg}") String welcomeMsg) {
		this.userService = userService;
		this.welcomeMsg = welcomeMsg;
	}
	
	@RequestMapping(path = {"", "/"}, method = GET)
	public Map<String, String> hello() {
		// add service instance info to show which service instance is refreshed
		Map<String, String> info = IPUtils.getHostnameAndAddress();
		info.put("welcomeMessage", welcomeMsg);
		return info;
	}
	
	@RequestMapping(path = "/users", method = GET)
	public List<User> getUsers() {
		return userService.getUsers();
	}
	
	@RequestMapping(path = "/users/{username}", method = GET)
	public User getUser(@PathVariable String username) {
		// log is used to demo sleuth: add span and trace IDs to log
		log.debug("Get user {}", username);
		return userService.getUser(username);
	}
	
	@RequestMapping(path = "/users", method = POST)
	public User createUser(@RequestBody User user) {
		log.info("Create user {}", user);
		return userService.createUser(user);
	}
}
