package com.revature.service;

import com.revature.exceptions.NoSuchAccountException;
import com.revature.exceptions.UsernameAlreadyExistsException;

public interface EmployeeService {
	
	boolean validateEmployeeLogin(String username, String password) throws NoSuchAccountException;
	
	void createNewEmployeeAccount(String username, String password) throws UsernameAlreadyExistsException;
	
	public String createToken(String username);
	
	public boolean validateToken(String token);

}
