package com.revature.beans;

public class UserAccount {

	
	public UserAccount() {
		super();
	}
	public UserAccount(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.userSuper = 0;
	}
	public UserAccount(String username, String password, int userSuper) {
		super();
		this.username = username;
		this.password = password;
		this.userSuper = userSuper;
	}
	public UserAccount(int id, String username, String password, int userSuper) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.userSuper = userSuper;
	}
	private int id;
	private String username;
	private String password;
	private int userSuper;
	
	@Override
	public String toString() {
		return "UserAccount [id=" + id + ", username=" + username + ", password=" + password + ", userSuper="
				+ userSuper + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + userSuper;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAccount other = (UserAccount) obj;
		if (id != other.id)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userSuper != other.userSuper)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getUserSuper() {
		return userSuper;
	}
	public void setUserSuper(int userSuper) {
		this.userSuper = userSuper;
	}
	
	
}
