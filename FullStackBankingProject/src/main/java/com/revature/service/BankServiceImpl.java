package com.revature.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.exceptions.BadInputException;
import com.revature.exceptions.NoSuchAccountException;
import com.revature.exceptions.OverdrawnException;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.BankAccount;
import com.revature.models.CustomerAccount;
import com.revature.models.EmployeeAccount;
import com.revature.repository.BankDao;
import com.revature.repository.BankDaoImpl;
import com.revature.repository.CustomerDao;
import com.revature.repository.CustomerDaoImpl;

public class BankServiceImpl implements BankService {
	
	private BankDao bankDao;
	private CustomerDao customerDao;
	
	public BankServiceImpl(BankDao bankDao, CustomerDao customerDao) {
		this.bankDao = bankDao;
		this.customerDao = customerDao;
	}
	
	/**
	 * Creates a new bank account in the database with the given balance and customer ID
	 * @param	balance - the starting balance for the account specified by user
	 * @param	customerID - the user's unique identification key
	 */
	@Override
	public void createNewBankAccount(double balance, String username) {
		
		CustomerAccount customer = customerDao.selectCustomerAccountByUsername(username);
		
		BankAccount account = new BankAccount(0, balance, false, customer.getId());
		
		bankDao.insertBankAccount(account);
		
	}
	
	/**
	 * Obtains a list of IDs for all the bank accounts in the bank
	 * @return 	a list of IDs for all the bank accounts in the bank
	 */
	@Override
	public List<BankAccount> getBankAccounts() {
		
		return bankDao.selectAllBankAccounts();
		
	}

	/**
	 * Obtains a list of IDs for all the bank accounts associated with a given user
	 * @param	username - the username of the user to find the accounts for
	 * @return 	a list of IDs for all the bank accounts associated with the username
	 */
	@Override
	public List<BankAccount> getBankAccounts(String username) throws NoSuchAccountException {
		
		CustomerAccount cAccount = customerDao.selectCustomerAccountByUsername(username);
		
		if (cAccount == null) {
			throw new NoSuchAccountException();
		}
		
		int id = cAccount.getId();
		
		return bankDao.selectBankAccountsByCustomer(id);
	}
	
	/**
	 * Returns a list of account IDs for accounts that have not been approved
	 * 	by an employee only.
	 * @return	a list of account IDs for the unvalidated accounts
	 */
	@Override
	public List<BankAccount> getUnvalidatedAccounts() {
		
		List<BankAccount> accountList = new ArrayList<>();
		
		for (BankAccount b: bankDao.selectAllBankAccounts()) {
			if (!b.isApproved()) {
				accountList.add(b);
			}
		}
		
		return accountList;
	}

	/**
	 * Finds the account with the given ID number and returns its balance
	 * @param	id - the ID number of the account to search for
	 * @return	the account balance
	 * @throws 	NoSuchAccountException if account does not exist
	 */
	@Override
	public double getBalance(int id) throws NoSuchAccountException {
		
		BankAccount account = bankDao.selectBankAccountById(id);
		
		if (account == null) {
			throw new NoSuchAccountException();
		}
		
		return account.getBalance();
	}

	/**
	 * Deposits the given amount into the bank account with the given ID number
	 * @param	id - the ID of the bank account to deposit to
	 * @param	amount - the amount of money to be deposited
	 * @throws	BadInputException if amount is zero or negative
	 * @throws	NoSuchAccountException if account does not exist
	 */
	@Override
	public void makeDeposit(int id, double amount) throws BadInputException, NoSuchAccountException {

		if (amount <= 0) {
			throw new BadInputException();
		}
		
		BankAccount account = bankDao.selectBankAccountById(id);
		
		if (account == null) {
			throw new NoSuchAccountException();
		}
		
		account.setBalance(account.getBalance() + amount);
		
		bankDao.updateBankAccount(account);
		
	}

	/**
	 * Withdraws the given amount from the bank account with the given ID number
	 * @param	id - the ID of the bank account to withdraw from
	 * @param	amount - the amount of money to be withdrawn
	 * @throws	OverdrawnException if amount is greater than balance in account
	 * @throws	BadInputException if amount is zero or negative
	 * @throws	NoSuchAccountException if account does not exist
	 */
	@Override
	public void makeWithdrawal(int id, double amount) throws OverdrawnException, BadInputException, NoSuchAccountException {
		
		if (amount <= 0) {
			throw new BadInputException();
		}
		
		BankAccount account = bankDao.selectBankAccountById(id);
		if (account == null) {
			throw new NoSuchAccountException();
		}
		double balance = account.getBalance();
		
		if (amount > balance) {
			throw new OverdrawnException();
		}
		
		account.setBalance(balance - amount);
		
		bankDao.updateBankAccount(account);
		
	}
	
	/**
	 * Transfers the given amount of money from one account to another.
	 * @param	id1 - the ID of the account to transfer from
	 * @param 	id2 - the ID of the account to transfer to
	 * @param 	amount - the amount of money to transfer
	 * @throws	OverdrawnException if amount is greater than balance in first account
	 * @throws	BadInputException if amount is zero or negative
	 * @throws	NoSuchAccountException if either account does not exist
	 */
	@Override
	public void makeTransfer(int id1, int id2, double amount) throws OverdrawnException, BadInputException, NoSuchAccountException {
		if (amount <= 0) {
			throw new BadInputException();
		}
		
		BankAccount account1 = bankDao.selectBankAccountById(id1);
		BankAccount account2 = bankDao.selectBankAccountById(id2);
		
		if (account1 == null || account2 == null) {
			throw new NoSuchAccountException();
		}
		double balance = account1.getBalance();
		
		if (amount > balance) {
			throw new OverdrawnException();
		}
		
		bankDao.transfer(account1, account2, amount);
	}

	/**
	 * Approves the account with the given ID, allowing the customer to use it.
	 * @param	id - the ID of the account to be approved
	 * @throws	NoSuchAccountException if account does not exist
	 */
	@Override
	public void approveAccount(int id) throws NoSuchAccountException {
		
		BankAccount account = bankDao.selectBankAccountById(id);
		
		if (account == null) {
			throw new NoSuchAccountException();
		}
		
		account.setApproved(true);
		
		bankDao.updateBankAccount(account);
		
	}

	/**
	 * Rejects the account with the given ID, removing it from the system.
	 * @param	id - the ID of the account to be rejected
	 * @throws	NoSuchAccountException if account does not exist
	 */
	@Override
	public void rejectAccount(int id) throws NoSuchAccountException {
		
		BankAccount account = bankDao.selectBankAccountById(id);
		
		if (account == null) {
			throw new NoSuchAccountException();
		}
		
		bankDao.deleteBankAccount(account);
		
	}
	
	

}
