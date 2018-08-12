package com.revature.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.beans.BankAccount;
import com.revature.beans.UserAccount;
import com.revature.util.ConnectionUtil;

public class BankAccountDAOImpl implements BankAccountDAO {

	private static String filename = "connection.properties";
	private static Logger log = Logger.getRootLogger();
	
	@Override
	public List<BankAccount> getBankAccounts() {
		List<BankAccount> bl = new ArrayList<>();
		try(Connection con = ConnectionUtil.getConnectionFromFile(filename)){
			String sql = "SELECT * FROM BANK_ACCOUNT";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()){
				int id = rs.getInt("BANK_ACCOUNT_ID");
				int userAccountId = rs.getInt("USER_ACCOUNT_ID");
				double currentBalance = rs.getDouble("CURRENT_BALANCE");
				bl.add(new BankAccount(id, userAccountId,currentBalance));
				log.info("retrieved bank account with id: " + id);
			}
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return bl;
	}

	@Override
	public BankAccount getBankAccountByBankAccountId(int id) {
		BankAccount b = null;
		PreparedStatement pstmt = null;

		try (Connection con = ConnectionUtil.getConnectionFromFile(filename)) {
			String sql = "SELECT * FROM BANK_ACCOUNT WHERE BANK_ACCOUNT_ID = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				int bankAccountID = rs.getInt("BANK_ACCOUNT_ID");
				int userAccountID = rs.getInt("USER_ACCOUNT_ID");
				double currentBalance = rs.getDouble("CURRENT_BALANCE");
				b = new BankAccount(bankAccountID, userAccountID, currentBalance);
				log.info("retrieved bank account: " + b.toString());
			} else {
				log.warn("no matching user found");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return b;
	}

	@Override
	public boolean saveBankAccount(BankAccount b) {
		if (b == null) {
			log.warn("null bank account entered");
			return false;
		}

		PreparedStatement pstmt = null;

		try (Connection con = ConnectionUtil.getConnectionFromFile(filename)) {
			String sql = "INSERT INTO BANK_ACCOUNT (USER_ACCOUNT_ID) VALUES (?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, b.getUserAccountId());
			if (pstmt.executeUpdate() > 0) {
				log.info("added bank account to db with User Account id:" + b.getUserAccountId());
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}
	

	@Override
	public ArrayList<BankAccount> getAllBankAccountsForUser(int userId) {
		ArrayList<BankAccount> bl = new ArrayList<>();
		try(Connection con = ConnectionUtil.getConnectionFromFile(filename)){
			String sql = "SELECT * FROM BANK_ACCOUNT b"
					+ " INNER JOIN USER_ACCOUNT u ON b.USER_ACCOUNT_ID = u.USER_ACCOUNT_ID"
					+ " WHERE b.USER_ACCOUNT_ID =" + userId;
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()){
				int userAccountId = rs.getInt("USER_ACCOUNT_ID");
				int id = rs.getInt("BANK_ACCOUNT_ID");
				double currentBalance = rs.getDouble("CURRENT_BALANCE");
				bl.add(new BankAccount(id, userAccountId,currentBalance));
				
			}
			log.info("retrieved bank accounts" + bl.toString());
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return bl;
	}

	@Override
	public void updateBalance(int bankId, double balance) {
		
		PreparedStatement pstmt = null;

		try (Connection con = ConnectionUtil.getConnectionFromFile(filename)) {
			String sql = "UPDATE BANK_ACCOUNT SET CURRENT_BALANCE = ? WHERE BANK_ACCOUNT_ID = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(2, bankId);
			pstmt.setDouble(1, balance);
			if (pstmt.executeUpdate() > 0) {
				log.info("Updated Current Balance to db with Bank Account id:" + bankId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return;
		
	}

}
