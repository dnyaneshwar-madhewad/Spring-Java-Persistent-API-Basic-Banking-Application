package com.cdac.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.cdac.entity.Account;
import com.cdac.entity.Transaction;

@Component
public class AccountDao extends GenericDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public List<Transaction> findTransactionsByAcno(int acno) {
		return entityManager
				.createQuery("select t from Transaction t where t.account.acno = :no")
				.setParameter("no", acno)
				.getResultList();
	}
	
	public List<Account> findActiveAccounts() {
		return entityManager
				.createQuery("select distinct a from Account a inner join a.transactions t where t.amount > 100")
				.getResultList();
	}

}
