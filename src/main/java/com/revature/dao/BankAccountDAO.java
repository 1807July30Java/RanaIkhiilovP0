package com.revature.dao;

import java.util.ArrayList;
import java.util.List;

import com.revature.beans.BankAccount;

public interface BankAccountDAO {

	public List<BankAccount> getBankAccounts();
	public BankAccount getBankAccountByBankAccountId(int id);
	public boolean saveBankAccount(BankAccount u);
	public ArrayList<BankAccount> getAllBankAccountsForUser(int userID);
	public void updateBalance(int bankId, double balance);
	boolean deleteBankAccount(int userID);
	
}
