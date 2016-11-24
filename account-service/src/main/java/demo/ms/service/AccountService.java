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
import demo.ms.domain.Account;
import demo.ms.vo.AccountDetail;
import demo.ms.vo.User;

@Service
public class AccountService {

	private static final Logger log = LoggerFactory.getLogger(AccountService.class);
	
	private AuthServiceClient authClient;
	
	private Map<String, Account> accounts;

	@Autowired
	public AccountService(AuthServiceClient authClient) {
		this.authClient = authClient;
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
	
	public AccountDetail getAccountDetail(String name) {
		Account account = getAccount(name);
		
		// just to demo hystrix
		User user = authClient.getUser(name);
		
		AccountDetail detail = new AccountDetail();
		detail.setAccount(account);
		detail.setUser(user);
		return detail;
	}
	
	public List<Account> getAccounts() {
		return new ArrayList<Account>(accounts.values());
	}
	
	public Account getAccount(String name) {
		// just to demo sleuth
		log.debug("Get account {}", name);
		
		Account account = accounts.get(name);
		if (account == null) {
			log.warn("Account {} is not found!", name);
		}
		return account;
	}
	
	public Account createAccount(Account newAccount) {
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
		// just to demo hystrix fallback
		authClient.deleteUser(name);
	}
}
