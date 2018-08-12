package com.revature.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.beans.UserAccount;
import com.revature.util.ConnectionUtil;

public class UserAccountDAOImpl implements UserAccountDAO{

	private static String filename = "connection.properties";
	private static Logger log = Logger.getRootLogger();
	
	@Override
	public List<UserAccount> getUsers() {
		List<UserAccount> ul = new ArrayList<>();
		try(Connection con = ConnectionUtil.getConnectionFromFile(filename)){
			String sql = "SELECT * FROM USER_ACCOUNT";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()){
				int id = rs.getInt("USER_ACCOUNT_ID");
				String username = rs.getString("USERNAME");
				String password = rs.getString("PASSWORD");
				int userSuper = rs.getInt("USER_ACCOUNT_SUPER");
				ul.add(new UserAccount(id, username,password,userSuper));
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return ul;
	}

	@Override
	public UserAccount getUserAccountById(int id) {
		UserAccount u = null;
		PreparedStatement pstmt = null;

		try (Connection con = ConnectionUtil.getConnectionFromFile(filename)) {

			// use a prepared statement
			String sql = "SELECT * FROM USER_ACCOUNT WHERE USER_ACCOUNT_ID = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			// do something with result
			if (rs.next()) {
				int userAccountID = rs.getInt("USER_ACCOUNT_ID");
				String username = rs.getString("USERNAME");
				String password = rs.getString("PASSWORD");
				int userAccountSuper = rs.getInt("USER_ACCOUNT_SUPER");
				u = new UserAccount(id, username, password, userAccountSuper);
				log.info("retrieved user with id "+id);
			} else {
				log.warn("no matching user found");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return u;
	}

	@Override
	public boolean saveUserAccount(UserAccount u) {
		if (u == null) {
			log.warn("null user entered");
			return false;
		}

		PreparedStatement pstmt = null;

		try (Connection con = ConnectionUtil.getConnectionFromFile(filename)) {
			String sql = "INSERT INTO USER_ACCOUNT (USERNAME, PASSWORD) VALUES (?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, u.getUsername());
			pstmt.setString(2, u.getPassword());
			if (pstmt.executeUpdate() > 0) {
				log.info("added user to db with username: " + u.getUsername() + " and password: " + u.getPassword());
				return true;
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("Username already exists");
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		System.out.println("Please enter valid inputs");
		return false;
	}

	@Override
	public boolean isExistingUser(UserAccount u) {
		
		PreparedStatement pstmt = null;
		try (Connection con = ConnectionUtil.getConnectionFromFile(filename)) {

			// use a prepared statement
			String sql = "SELECT * FROM USER_ACCOUNT WHERE USERNAME = ? AND PASSWORD = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, u.getUsername().trim());
			pstmt.setString(2, u.getPassword().trim());
			
			ResultSet rs = pstmt.executeQuery();

			// do something with result
			if (rs.next()) {
				int id = rs.getInt("USER_ACCOUNT_ID");
				u.setId(id);
				u.setUserSuper(rs.getInt("USER_ACCOUNT_SUPER"));
				log.info("Retrieved user with id "+id);
				
				return true;
			} else {
				log.warn("No matching user found");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

}
