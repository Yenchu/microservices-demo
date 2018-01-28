package demo.ms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import demo.ms.client.AuthServiceClient;
import demo.ms.client.AuthServiceRibbonClient;
import demo.ms.domain.Account;
import demo.ms.vo.User;

@Service
public class AccountService {
	
	private static final Logger log = LoggerFactory.getLogger(AccountService.class);

	private AuthServiceClient authServiceClient;
	
	private AuthServiceRibbonClient authServiceRibbonClient;
	
	private Map<String, Account> accounts;

	public AccountService(AuthServiceClient authServiceClient, AuthServiceRibbonClient authServiceRibbonClient) {
		this.authServiceClient = authServiceClient;
		this.authServiceRibbonClient = authServiceRibbonClient;
	}

	@PostConstruct
	public void createAccounts() {
		accounts = Stream.of(
					new Account("albert"), 
					new Account("alex"), 
					new Account("andrew"))
				.collect(Collectors.toMap(Account::getName, Function.identity()));
	}
	
	public List<Account> getAccounts() {
		// to demo ribbon
		authServiceRibbonClient.getUsers();
		return new ArrayList<Account>(accounts.values());
	}
	
	public Account getAccount(String name) {
		// to demo feign
		User user = authServiceClient.getUser(name);
		
		return Optional.ofNullable(accounts.get(name))
			.map(account -> addUserInfo(account, user))
			.orElseGet(() -> {
				log.info("Can not find account {}", name);
				return new Account();
			});
	}
	
	private Account addUserInfo(Account account, User user) {
		if (!StringUtils.isEmpty(user.getUsername())) {
			// to show which user-service instance is connected
			Map<String, String> extraInfo = user.getExtra();
			account.setExtra(extraInfo);
		}
		return account;
	}
	
	public Account createAccount(Account newAccount) {
		// to demo feign
		authServiceClient.createUser(new User(newAccount.getName()));
		
		accounts.put(newAccount.getName(), newAccount);
		return newAccount;
	}
	
	public Account updateAccount(Account account) {
		accounts.put(account.getName(), account);
		return account;
	}
	
	public void deleteAccount(String name) {
		// to demo ribbon with hystrix fallback
		authServiceRibbonClient.deleteUser(name);
	}
}
