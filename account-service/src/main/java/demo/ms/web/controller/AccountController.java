package demo.ms.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.ms.domain.Account;
import demo.ms.service.AccountService;
import demo.ms.vo.AccountDetail;

@RestController
public class AccountController {
	
	private static final Logger log = LoggerFactory.getLogger(AccountController.class);

	private AccountService accountService;

	@Autowired
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@RequestMapping(path = "/accounts/{name}", method = GET)
	public AccountDetail getAccountDetail(@PathVariable String name) {
		// just to demo sleuth and hystrix
		log.debug("Get account detail {}", name);
		return accountService.getAccountDetail(name);
	}

	@RequestMapping(path = "/accounts", method = RequestMethod.GET)
	public List<Account> getAccounts() {
		return accountService.getAccounts();
	}
	
	@RequestMapping(path = "/accounts", method = POST)
	public Account createAccount(@RequestBody Account account) {
		return accountService.createAccount(account);
	}

	@RequestMapping(path = "/accounts/{name}", method = PUT)
	public Account updateAccount(@PathVariable String name, @RequestBody Account account) {
		return accountService.updateAccount(account);
	}

	@RequestMapping(path = "/accounts/{name}", method = DELETE)
	public void deleteAccount(@PathVariable String name) {
		accountService.deleteAccount(name);
	}
}
