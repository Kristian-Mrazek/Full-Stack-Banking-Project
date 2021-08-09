package com.revature.service;

import com.revature.exceptions.NoSuchAccountException;
import com.revature.exceptions.UsernameAlreadyExistsException;

public interface CustomerService {
	
	boolean validateCustomerLogin(String username, String password) throws NoSuchAccountException;
	
	void createNewCustomerAccount(String username, String password) throws UsernameAlreadyExistsException;
	
	int getCustomerIdFromUsername(String username) throws NoSuchAccountException;
	
	public String createToken(String username);
	
	public boolean validateToken(String token);

}
