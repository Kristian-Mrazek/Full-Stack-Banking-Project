package com.revature.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import com.revature.exceptions.NoSuchAccountException;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.EmployeeAccount;
import com.revature.repository.BankDao;
import com.revature.repository.CustomerDao;
import com.revature.repository.EmployeeDao;
import com.revature.repository.EmployeeDaoImpl;

public class EmployeeServiceImpl implements EmployeeService {
	
	private static byte[] salt = new SecureRandom().getSeed(16);
	
	private EmployeeDao employeeDao;
	
	private Map<String, String> tokenRepo = new HashMap<>();
	
	public EmployeeServiceImpl(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}
	
	/**
	 * Looks for username in database and checks whether the given password is correct
	 * @param	username - the username given to the console by the user
	 * @param	password - the password given to the console by the user
	 * @return	true if password matches password in database, false otherwise
	 * @throws	NoSuchAccountException if account with username cannot be found
	 */
	@Override
	public boolean validateEmployeeLogin(String username, String password) throws NoSuchAccountException {
		
		EmployeeAccount account = employeeDao.selectEmployeeAccountByUsername(username);
		
		if (account == null) {
			throw new NoSuchAccountException();
		}
		
		return (password.equals(account.getPassword()));
		
	}
	
	/**
	 * Creates a new employee account in the database with the given username and password
	 * @param	firstName - the user's first name
	 * @param	lastName - the user's last name
	 * @param	username - the user's created username
	 * @param	password - the user's created password
	 */
	@Override
	public void createNewEmployeeAccount(String username, String password) throws UsernameAlreadyExistsException {
		
		if (employeeDao.selectEmployeeAccountByUsername(username) != null) {
			throw new UsernameAlreadyExistsException();
		}
		
		EmployeeAccount account = new EmployeeAccount(0, username, password);
		
		employeeDao.insertEmployeeAccount(account);
		
	}
	
	@Override
	public String createToken(String username) {
		String token = simpleHash(username);
		tokenRepo.put(token, username);
		
		return token;
	}

	@Override
	public boolean validateToken(String token) {
		
		return tokenRepo.containsKey(token);
	}
	
	private String simpleHash(String username) {
		
		String hash = null;
		MessageDigest md;
		
		try {
			md = MessageDigest.getInstance("SHA-512");
			md.update(salt);
			
			byte[] bytes = md.digest(username.getBytes());
			
			StringBuilder sb = new StringBuilder();
			
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(0));
			}
			
			hash = sb.toString();
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return hash;
		
	}
	
}
