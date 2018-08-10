package com.revature.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.revature.beans.BankAccount;
import com.revature.beans.UserAccount;
import com.revature.dao.BankAccountDAO;
import com.revature.dao.BankAccountDAOImpl;
import com.revature.dao.UserAccountDAO;
import com.revature.dao.UserAccountDAOImpl;
import com.revature.util.ConnectionUtil;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			Connection con = ConnectionUtil.getConnectionFromFile("connection.properties");
			System.out.println(con.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		BankAccountDAO cd = new BankAccountDAOImpl();
		
//		List<BankAccount> caves = cd.getBankAccounts();
//		for (BankAccount c : caves) {
//			System.out.println(c);
//		}
		
		//System.out.println(cd.getBankAccountByBankAccountId(1));
		
		//cd.saveBankAccount(new BankAccount(22, 500));
		cd.getAllBankAccountsForUser(22);
	}

}
