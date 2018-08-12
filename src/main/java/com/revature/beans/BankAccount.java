package com.revature.beans;

public class BankAccount {
	
	private int id;
	private int userAccountId;
	private double currentBalance;
	
	public BankAccount() {
		super();
	}
	
	public BankAccount(int userAccountId) {
		super();
		this.userAccountId = userAccountId;
	}
	
	public BankAccount(int userAccountId, double currentBalance) {
		super();
		this.userAccountId = userAccountId;
		this.currentBalance = currentBalance;
	}
	
	public BankAccount(int id, int userAccountId, double currentBalance) {
		super();
		this.id = id;
		this.userAccountId = userAccountId;
		this.currentBalance = currentBalance;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserAccountId() {
		return userAccountId;
	}
	public void setUserAccountId(int userAccountId) {
		this.userAccountId = userAccountId;
	}
	public double getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(currentBalance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		result = prime * result + userAccountId;
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
		BankAccount other = (BankAccount) obj;
		if (Double.doubleToLongBits(currentBalance) != Double.doubleToLongBits(other.currentBalance))
			return false;
		if (id != other.id)
			return false;
		if (userAccountId != other.userAccountId)
			return false;
		return true;
	}
	@Override
	public String toString() {
		String baInfo = id + "\t\t" + currentBalance;
		return baInfo;
	}
}
