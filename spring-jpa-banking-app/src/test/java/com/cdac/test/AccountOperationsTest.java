package com.cdac.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cdac.entity.Account;
import com.cdac.entity.Transaction;
import com.cdac.service.AccountService;

public class AccountOperationsTest {

	@Test
	public void openAccountInTheBank() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("my-spring-config.xml");
		
		AccountService accServ = (AccountService) ctx.getBean("accountService");

		Account acc = new Account();
		acc.setName("Dnyaneshwar Madhewad");
		acc.setType("Savings");
		acc.setBalance(10000);
		accServ.openAccount(acc);
	}
	
	@Test
	public void withdraw() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("my-spring-config.xml");
		
		AccountService accServ = (AccountService) ctx.getBean("accountService");
		accServ.withdraw(1, 500);
	}
	
	@Test
	public void deposit() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("my-spring-config.xml");
		
		AccountService accServ = (AccountService) ctx.getBean("accountService");
		accServ.deposit(2, 500);
	}
	
	@Test
	public void transfer() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("my-spring-config.xml");
		
		AccountService accServ = (AccountService) ctx.getBean("accountService");
		accServ.transfer(1, 2, 500);
	}
	
	@Test
	public void whatsMyBalance() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("my-spring-config.xml");
		
		AccountService accServ = (AccountService) ctx.getBean("accountService");
		System.out.println(accServ.balanceCheck(2));
	}

	@Test
	public void fetchTransactions() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("my-spring-config.xml");
		
		AccountService accServ = (AccountService) ctx.getBean("accountService");
		List<Transaction> transactions = accServ.fetchTransactions(2);
		for(Transaction t : transactions)
			System.out.println(t.getAmount() + " " + t.getDateAndTime() + " " + t.getType());
	}

	@Test
	public void fetchActiveAccounts() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("my-spring-config.xml");
		
		AccountService accServ = (AccountService) ctx.getBean("accountService");
		List<Account> accounts = accServ.fetchActiveAccounts();
		for(Account acc : accounts)
			System.out.println(acc.getName() + " " + acc.getBalance());
	}
}
