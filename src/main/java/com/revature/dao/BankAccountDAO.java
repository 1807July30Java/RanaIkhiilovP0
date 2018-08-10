package com.revature.dao;

import java.util.List;

import com.revature.beans.BankAccount;

public interface BankAccountDAO {

	public List<BankAccount> getBankAccounts();
	public BankAccount getBankAccountByBankAccountId(int id);
	public boolean saveBankAccount(BankAccount u);
	public List<BankAccount> getAllBankAccountsForUser(int userID);
	
}
