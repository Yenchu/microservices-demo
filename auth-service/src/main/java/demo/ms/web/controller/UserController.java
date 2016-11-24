package demo.ms.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.ms.domain.User;
import demo.ms.service.UserService;

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
	public String hello() {
		String msg = welcomeMsg;
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String hostAddress = inetAddress.getHostAddress();
			String hostName = inetAddress.getHostName();
			msg = String.join(" ", msg, hostAddress, "/", hostName);
		} catch (UnknownHostException e) {
			log.error(e.getMessage(), e);
		}
		return msg;
	}
	
	@RequestMapping(path = "/users", method = GET)
	public List<User> getUsers() {
		return userService.getUsers();
	}
	
	@RequestMapping(path = "/users/{username}", method = GET)
	public User getUser(@PathVariable String username) {
		return userService.getUser(username);
	}
}
