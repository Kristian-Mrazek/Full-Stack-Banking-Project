package com.revature.repository;

import java.util.List;

import com.revature.models.BankAccount;
import com.revature.models.CustomerAccount;
import com.revature.models.EmployeeAccount;

public interface BankDao {

	// Create
	int insertBankAccount(BankAccount account);
	
	// Read
	BankAccount selectBankAccountById(int id);
	List<BankAccount> selectBankAccountsByCustomer(int customerId);
	List<BankAccount> selectAllBankAccounts();
	
	// Update
	void updateBankAccount(BankAccount account);
	
	// Delete
	void deleteBankAccount(BankAccount account);
	
	// Custom Stored Procedure
	void transfer(BankAccount sender, BankAccount receiver, double amount);
	
}
