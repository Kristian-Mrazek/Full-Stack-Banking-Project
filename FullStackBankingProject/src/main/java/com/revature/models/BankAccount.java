package com.revature.models;

import java.util.Objects;

public class BankAccount {
	
	private int id;
	private double balance;
	private boolean isApproved;
	private int customerId;
	
	public BankAccount() {
		super();
	}
	
	public BankAccount(int id, double balance, boolean isApproved, int customerId) {
		super();
		this.id = id;
		this.balance = balance;
		this.isApproved = isApproved;
		this.customerId = customerId;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(balance, customerId, id, isApproved);
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
		return Double.doubleToLongBits(balance) == Double.doubleToLongBits(other.balance)
				&& customerId == other.customerId && id == other.id && isApproved == other.isApproved;
	}

	@Override
	public String toString() {
		return "BankAccount [id=" + id + ", balance=" + balance + ", isApproved=" + isApproved + ", customerId="
				+ customerId + "]";
	}
	
	
	
	

}
