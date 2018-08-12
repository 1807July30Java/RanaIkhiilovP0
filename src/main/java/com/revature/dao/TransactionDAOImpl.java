package com.revature.dao;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.beans.BankAccount;
import com.revature.beans.Transaction;
import com.revature.util.ConnectionUtil;

public class TransactionDAOImpl implements TransactionDAO {
	
	private static String filename = "connection.properties";
	private static Logger log = Logger.getRootLogger();
	
	@Override
	public ArrayList<Transaction> accountTransactionHistory(int bankId) {
		ArrayList<Transaction> tl = new ArrayList<>();
		try(Connection con = ConnectionUtil.getConnectionFromFile(filename)){
			String sql = "SELECT * FROM TRANSACTION t"
					+ " INNER JOIN BANK_ACCOUNT b ON t.BANK_ACCOUNT_ID = b.BANK_ACCOUNT_ID"
					+ " WHERE t.BANK_ACCOUNT_ID =" + bankId;
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()){
				int transactiontId = rs.getInt("TRANSACTION_ID");
				Date date = rs.getDate("TRANSACTION_DATE");
				double previousBalance = rs.getDouble("PREVIOUS_BALANCE");
				double updatedBalance = rs.getDouble("UPDATED_BALANCE");
				double value = rs.getDouble("TRANSACTION_VALUE");
				String type = rs.getString("TRANSACTION_TYPE");
				tl.add(new Transaction(transactiontId, date, previousBalance, updatedBalance,  value, type));
				
			}
			log.info("Retrieved transactions " + tl.toString());
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return tl;
	}

	@Override
	public boolean saveTransaction(Transaction t) {
		if (t == null) {
			log.warn("NULL transaction entered");
			return false;
		}

		PreparedStatement pstmt = null;

		try (Connection con = ConnectionUtil.getConnectionFromFile(filename)) {
			String sql = "INSERT INTO TRANSACTION (BANK_ACCOUNT_ID, TRANSACTION_DATE, PREVIOUS_BALANCE, UPDATED_BALANCE, TRANSACTION_VALUE, TRANSACTION_TYPE) VALUES (?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, t.getBankAccountId());
			pstmt.setDate(2,java.sql.Date.valueOf(java.time.LocalDate.now()));
			pstmt.setDouble(3, t.getPreviousBalance());
			pstmt.setDouble(4, t.getNewBalance());
			pstmt.setDouble(5, t.getValue());
			pstmt.setString(6, t.getType());
			if (pstmt.executeUpdate() > 0) {
				log.info("added transaction to db with bank Account id:" + t.getBankAccountId());
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
	public void withdraw(Transaction t) {
		
		CallableStatement cs = null;
		
		try(Connection con = ConnectionUtil.getConnectionFromFile(filename)){
			
			String sql = "{call SP_WITHDRAW(?,?)}";
			cs = con.prepareCall(sql);
			cs.setInt(1, t.getBankAccountId());
			cs.setDouble(2, t.getValue());
			log.info("Successful withdrawal of value " + t.getValue() + " from bank account id: " + t.getBankAccountId() );
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deposit(Transaction t) {
		
		CallableStatement cs = null;
		
		try(Connection con = ConnectionUtil.getConnectionFromFile(filename)){
			
			String sql = "{call SP_DEPOSIT(?,?)}";
			cs = con.prepareCall(sql);
			cs.setInt(1, t.getBankAccountId());
			cs.setDouble(2, t.getValue());
			log.info("Successful deposit of value " + t.getValue() + " from bank account id: " + t.getBankAccountId() );
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public boolean deleteTransactions(int bankAccountID) {
		PreparedStatement pstmt = null;
		try (Connection con = ConnectionUtil.getConnectionFromFile(filename)) {
			
			String sql = "DELETE FROM TRANSACTION WHERE BANK_ACCOUNT_ID = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bankAccountID);
			
			if (pstmt.executeUpdate() > 0) {
				log.info("Deleted all transactions for bank account with id "+ bankAccountID);
				return true;
			} else {
				log.info("No transactions found for bank account with id "+ bankAccountID);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

}
