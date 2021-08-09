package com.revature.repository;

import com.revature.models.CustomerAccount;

public interface CustomerDao {
	
	// Create
	boolean insertCustomerAccount(CustomerAccount account);
	
	// Read
	CustomerAccount selectCustomerAccountByUsername(String username);
		
	// Update
	void updateCustomerAccount(CustomerAccount account);
		
	// Delete
	void deleteCustomerAccount(CustomerAccount account);

}
