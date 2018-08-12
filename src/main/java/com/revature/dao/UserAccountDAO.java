package com.revature.dao;

import java.util.List;

import com.revature.beans.UserAccount;

public interface UserAccountDAO {

	public List<UserAccount> getUsers();
	public UserAccount getUserAccountById(int id);
	public boolean saveUserAccount(UserAccount u);
	public boolean isExistingUser(UserAccount u);
	
	//Super User functionality
	public boolean deleteUser(int userID);
	public void createUser();
	public void viewAllUsers();
	public boolean updateUsername(String previousUserName, String newUserName);
	public boolean updatePassword(String userName, String previousPassword, String newPassword);
	public boolean makeSuperUser(int userAccId);
}
