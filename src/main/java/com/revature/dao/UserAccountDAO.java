package com.revature.dao;

import java.util.List;

import com.revature.beans.UserAccount;

public interface UserAccountDAO {

	public List<UserAccount> getUsers();
	public UserAccount getUserAccountById(int id);
	public boolean saveUserAccount(UserAccount u);
	
}
