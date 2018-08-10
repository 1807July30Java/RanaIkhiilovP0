package com.revature.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Transaction {
	public Transaction() {
		super();
	}
	
	public Transaction(int bankAccountId, String date, double previousBalance, double newBalance, double value,
			String type) {
		super();
		this.bankAccountId = bankAccountId;
		this.date = date;
		this.previousBalance = previousBalance;
		this.newBalance = newBalance;
		this.value = value;
		this.type = type;
	}
	public Transaction(int id, int bankAccountId, String date, double previousBalance, double newBalance, double value,
			String type) {
		super();
		this.id = id;
		this.bankAccountId = bankAccountId;
		this.date = date;
		this.previousBalance = previousBalance;
		this.newBalance = newBalance;
		this.value = value;
		this.type = type;
	}
	
	private int id;
	private int bankAccountId;
	private String date;
	private double previousBalance;
	private double newBalance;
	private double value;
	private String type;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bankAccountId;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(newBalance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(previousBalance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Transaction other = (Transaction) obj;
		if (bankAccountId != other.bankAccountId)
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(newBalance) != Double.doubleToLongBits(other.newBalance))
			return false;
		if (Double.doubleToLongBits(previousBalance) != Double.doubleToLongBits(other.previousBalance))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
			return false;
		return true;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBankAccountId() {
		return bankAccountId;
	}
	public void setBankAccountId(int bankAccountId) {
		this.bankAccountId = bankAccountId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getPreviousBalance() {
		return previousBalance;
	}
	public void setPreviousBalance(double previousBalance) {
		this.previousBalance = previousBalance;
	}
	public double getNewBalance() {
		return newBalance;
	}
	public void setNewBalance(double newBalance) {
		this.newBalance = newBalance;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
