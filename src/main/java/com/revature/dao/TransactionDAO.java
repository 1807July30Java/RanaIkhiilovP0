package com.revature.dao;

import java.util.ArrayList;

import com.revature.beans.Transaction;

public interface TransactionDAO {
	public ArrayList<Transaction> accountTransactionHistory(int bankId);
	public boolean saveTransaction(Transaction t);
}
