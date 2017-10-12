package demo.ms.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.ms.client.AuthServiceClient;
import demo.ms.client.AuthServiceRibbonClient;
import demo.ms.domain.Account;
import demo.ms.vo.User;

@Service
public class AccountService {

	private static final Logger log = LoggerFactory.getLogger(AccountService.class);
	
	private AuthServiceClient authClient;
	
	private AuthServiceRibbonClient authServiceRibbonClient;
	
	private Map<String, Account> accounts;

	@Autowired
	public AccountService(AuthServiceClient authClient, AuthServiceRibbonClient authServiceRibbonClient) {
		this.authClient = authClient;
		this.authServiceRibbonClient = authServiceRibbonClient;
	}

	@PostConstruct
	public void createAccounts() {
		accounts = new LinkedHashMap<>();
		Account account = new Account("albert");
		accounts.put(account.getName(), account);
		account = new Account("alex");
		accounts.put(account.getName(), account);
		account = new Account("andrew");
		accounts.put(account.getName(), account);
	}
	
	public List<Account> getAccounts() {
		// to demo ribbon
		authServiceRibbonClient.getUsers();
		return new ArrayList<Account>(accounts.values());
	}
	
	public Account getAccount(String name) {
		// log is used to demo sleuth: add span and trace IDs to log
		log.debug("Get account {}", name);
		
		User user = authClient.getUser(name);
		if (user == null || "unknown".equals(user.getUsername())) {
			throw new IllegalArgumentException("Can not find user " + name);
		}
		
		Account account = accounts.get(name);
		if (account != null) {
			// to show which user-service instance is connected
			Map<String, String> extraInfo = user.getExtra();
			account.setExtra(extraInfo);
		} else {
			throw new IllegalArgumentException("Can not find account " + name);
		}
		return account;
	}
	
	public Account createAccount(Account newAccount) {
		// to demo feign
		authClient.createUser(new User(newAccount.getName()));
		
		accounts.put(newAccount.getName(), newAccount);
		log.info("Create account {}", newAccount.getName());
		return newAccount;
	}
	
	public Account updateAccount(Account account) {
		accounts.put(account.getName(), account);
		log.info("Update account {}", account.getName());
		return account;
	}
	
	public void deleteAccount(String name) {
		// to demo hystrix fallback
		authClient.deleteUser(name);
	}
}
