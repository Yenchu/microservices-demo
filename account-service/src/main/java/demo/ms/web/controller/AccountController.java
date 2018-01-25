package demo.ms.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import demo.ms.domain.Account;
import demo.ms.service.AccountService;

@RestController
public class AccountController {
	
	private static final Logger log = LoggerFactory.getLogger(AccountController.class);

	private AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping("/accounts")
	public List<Account> getAccounts() {
		return accountService.getAccounts();
	}
	
	@GetMapping("/accounts/{name}")
	public Account getAccount(@PathVariable String name) {
		// log is used to demo sleuth: add span and trace IDs to log
		log.debug("Get account {}", name);
		return accountService.getAccount(name);
	}
	
	@PostMapping("/accounts")
	public Account createAccount(@RequestBody Account account) {
		log.info("Create account {}", account.getName());
		return accountService.createAccount(account);
	}

	@PutMapping("/accounts/{name}")
	public Account updateAccount(@PathVariable String name, @RequestBody Account account) {
		log.info("Update account {}", name);
		return accountService.updateAccount(account);
	}

	@DeleteMapping("/accounts/{name}")
	public void deleteAccount(@PathVariable String name) {
		log.info("Delete account {}", name);
		accountService.deleteAccount(name);
	}
}
