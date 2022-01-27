package com.cdac.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.dao.AccountDao;
import com.cdac.dao.GenericDao;
import com.cdac.entity.Account;
import com.cdac.entity.Transaction;
import com.cdac.exception.AccountServiceException;

@Component
public class AccountService {

	@Autowired
	private GenericDao genericDao;
	
	@Autowired
	private AccountDao accountDao;
	
	@Transactional
	public void openAccount(Account acc) {
		if(acc.getBalance() < 5000)
			throw new AccountServiceException("Sorry, cannot open account. Minimum 5000 Rs required for the same!");
		else {
			//GenericDao dao = new GenericDao();
			genericDao.save(acc);
			//send an email/sms confirmation to the customer - code to trigger the same will be here
			//emailService.sendEmailForNewAccount(..);
		}
	}
	
	@Transactional
	public void withdraw(int acno, double amount) {
		Account acc = genericDao.findByPK(Account.class, acno);
		if(acc.getBalance() < amount)
			throw new AccountServiceException("Insufficient Balance!");
		else {
			acc.setBalance(acc.getBalance() - amount);
			genericDao.save(acc);
			
			Transaction tx = new Transaction();
			tx.setDateAndTime(LocalDateTime.now());
			tx.setAmount(amount);
			tx.setType("Withdraw");
			tx.setAccount(acc);
			genericDao.save(tx);
		}
	}
	
	@Transactional
	public void deposit(int acno, double amount) {
		Account acc = genericDao.findByPK(Account.class, acno);
		acc.setBalance(acc.getBalance() + amount);
		genericDao.save(acc);
			
		Transaction tx = new Transaction();
		tx.setDateAndTime(LocalDateTime.now());
		tx.setAmount(amount);
		tx.setType("Deposit");
		tx.setAccount(acc);
		genericDao.save(tx);
	}
	
	@Transactional
	public void transfer(int fromAcno, int toAcno, double amount) {
		//withdraw(fromAcno, amount);
		//deposit(toAcno, amount);
		Account fromAcc = genericDao.findByPK(Account.class, fromAcno);
		Account toAcc = genericDao.findByPK(Account.class, toAcno);
		if(fromAcc.getBalance() < amount)
			throw new AccountServiceException("Insufficient Balance!");
		else {
			fromAcc.setBalance(fromAcc.getBalance() - amount);
			toAcc.setBalance(toAcc.getBalance() + amount);

			genericDao.save(fromAcc);
			genericDao.save(toAcc);
			
			Transaction tx1 = new Transaction();
			tx1.setDateAndTime(LocalDateTime.now());
			tx1.setAmount(amount);
			tx1.setType("FundTransfer");
			tx1.setAccount(fromAcc);

			Transaction tx2 = new Transaction();
			tx2.setDateAndTime(LocalDateTime.now());
			tx2.setAmount(amount);
			tx2.setType("FundReceive");
			tx2.setAccount(toAcc);
			
			genericDao.save(tx1);
			genericDao.save(tx2);
		}
	}
	
	public double balanceCheck(int acno) {
		Account acc = genericDao.findByPK(Account.class, acno);
		return acc.getBalance();
	}
	
	public List<Transaction> fetchTransactions(int acno) {
		return accountDao.findTransactionsByAcno(acno);
	}
	
	public List<Account> fetchActiveAccounts() {
		return accountDao.findActiveAccounts();
	}
}
