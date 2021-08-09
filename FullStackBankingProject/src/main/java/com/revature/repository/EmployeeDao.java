package com.revature.repository;

import com.revature.models.EmployeeAccount;

public interface EmployeeDao {
	
	// Create
	boolean insertEmployeeAccount(EmployeeAccount account);
		
	// Read
	EmployeeAccount selectEmployeeAccountByUsername(String username);

}
