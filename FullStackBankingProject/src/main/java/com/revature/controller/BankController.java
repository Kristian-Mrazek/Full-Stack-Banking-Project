package com.revature.controller;

import io.javalin.http.Context;

public interface BankController {
	
	void getBankAccounts(Context ctx);
	
	void getUserBankAccounts(Context ctx);
	
	void storeUsername(Context ctx);
	
	void getUserBankAccountsAsEmployee(Context ctx);
	
	void getUnvalidatedAccounts(Context ctx);
	
	void postBankAccount(Context ctx);
	
	void deposit(Context ctx);
	
	void withdraw(Context ctx);
	
	void transfer(Context ctx);
	
	void approveAccount(Context ctx);
	
	void rejectAccount(Context ctx);

}
