package com.revature.models;

import java.util.List;

public class CustomerAccount {

	private int id;
	private List<BankAccount> listOfAccounts;
	private String username;
	private String password;
	
	public CustomerAccount(int id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<BankAccount> getListOfAccounts() {
		return listOfAccounts;
	}
	
	public void setListOfAccounts(List<BankAccount> listOfAccounts) {
		this.listOfAccounts = listOfAccounts;
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

	@Override
	public String toString() {
		return "CustomerAccount [id=" + id + ", listOfAccounts=" + listOfAccounts + ", username=" + username
				+ ", password=" + password + "]";
	}
	
	
	
}
