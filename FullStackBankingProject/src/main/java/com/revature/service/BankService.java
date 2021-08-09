package com.revature.service;

import java.util.List;

import com.revature.exceptions.BadInputException;
import com.revature.exceptions.NoSuchAccountException;
import com.revature.exceptions.OverdrawnException;
import com.revature.models.BankAccount;

public interface BankService {
	
	void createNewBankAccount(double balance, String username);
	
	List<BankAccount> getBankAccounts();
	List<BankAccount> getBankAccounts(String username) throws NoSuchAccountException;
	List<BankAccount> getUnvalidatedAccounts();
	
	double getBalance(int id) throws NoSuchAccountException;
	void makeDeposit(int id, double amount) throws BadInputException, NoSuchAccountException;
	void makeWithdrawal(int id, double amount) throws OverdrawnException, BadInputException, NoSuchAccountException;
	void makeTransfer(int id1, int id2, double amount) throws OverdrawnException, BadInputException, NoSuchAccountException;
	
	void approveAccount(int id) throws NoSuchAccountException;
	void rejectAccount(int id) throws NoSuchAccountException;

}
