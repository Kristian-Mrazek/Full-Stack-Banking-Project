package com.revature.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import com.revature.exceptions.NoSuchAccountException;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.CustomerAccount;
import com.revature.repository.BankDao;
import com.revature.repository.CustomerDao;
import com.revature.repository.CustomerDaoImpl;

public class CustomerServiceImpl implements CustomerService {
	
	private static byte[] salt = new SecureRandom().getSeed(16);
	
	private CustomerDao customerDao;
	
	private Map<String, String> tokenRepo = new HashMap<>();
	
	public CustomerServiceImpl(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	
	/**
	 * Looks for username in database and checks whether the given password is correct
	 * @param	username - the username given to the console by the user
	 * @param	password - the password given to the console by the user
	 * @return	true if password matches password in database, false otherwise
	 * @throws	NoSuchAccountException if account with username cannot be found
	 */
	@Override
	public boolean validateCustomerLogin(String username, String password) throws NoSuchAccountException {
		
		CustomerAccount account = customerDao.selectCustomerAccountByUsername(username);
		
		if (account == null) {
			throw new NoSuchAccountException();
		}
		
		return (password.equals(account.getPassword()));
		
	}
	
	/**
	 * Creates a new customer account in the database with the given username and password as
	 * 	well as the customer's first and last name
	 * @param	firstName - the user's first name
	 * @param	lastName - the user's last name
	 * @param	username - the user's created username
	 * @param	password - the user's created password
	 */
	@Override
	public void createNewCustomerAccount(String username, String password) throws UsernameAlreadyExistsException {
		
		if (customerDao.selectCustomerAccountByUsername(username) != null) {
			throw new UsernameAlreadyExistsException();
		}
		
		CustomerAccount account = new CustomerAccount(0, username, password);
		
		customerDao.insertCustomerAccount(account);
		
	}
	
	/**
	 * Obtains a customer's ID number knowing their username
	 * @param 	username - the username to find the ID for
	 * @return 	the customer's account ID
	 */
	@Override
	public int getCustomerIdFromUsername(String username) throws NoSuchAccountException {
		
		CustomerAccount account = customerDao.selectCustomerAccountByUsername(username);
		
		if (account == null) {
			throw new NoSuchAccountException();
		}
		
		return account.getId();
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
