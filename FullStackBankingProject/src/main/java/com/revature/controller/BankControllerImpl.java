package com.revature.controller;

import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exceptions.BadInputException;
import com.revature.exceptions.NoSuchAccountException;
import com.revature.exceptions.OverdrawnException;
import com.revature.models.BankAccount;
import com.revature.repository.BankDaoImpl;
import com.revature.repository.CustomerDaoImpl;
import com.revature.service.BankService;
import com.revature.service.BankServiceImpl;

import io.javalin.http.Context;

public class BankControllerImpl implements BankController {
	
	private BankService bankService = new BankServiceImpl(new BankDaoImpl(), new CustomerDaoImpl());
	
	private static final Logger loggy = Logger.getLogger(BankControllerImpl.class);

	@Override
	public void getBankAccounts(Context ctx) {
		
		ctx.json(bankService.getBankAccounts());
		ctx.status(200);
		
	}
	
	@Override
	public void getUserBankAccounts(Context ctx) {
		
		try {
			ctx.status(200);
			ctx.json(bankService.getBankAccounts(ctx.cookieStore("name")));
		} catch (NoSuchAccountException e) {
			System.out.println("Customer account does not exist.");
			ctx.status(404);
		}
		
	}
	
	@Override
	public void storeUsername(Context ctx) {
		
		String username = ctx.formParam("usernameView");
		
		ctx.redirect("/employee-view-customer.html");
		
		ctx.cookieStore("name", username);
		
	}
	
	@Override
	public void getUserBankAccountsAsEmployee(Context ctx) {
		
		try {
			ctx.status(200);
			ctx.json(bankService.getBankAccounts(ctx.cookieStore("name")));
		} catch (NoSuchAccountException e) {
			ctx.status(404);
		}
		
		ctx.redirect("/employee-view-customer.html");
		
	}
	
	@Override
	public void getUnvalidatedAccounts(Context ctx) {
		
		ctx.json(bankService.getUnvalidatedAccounts());
		ctx.status(200);
		
	}

	@Override
	public void postBankAccount(Context ctx) {

		double balance = Double.parseDouble(ctx.formParam("startingBalance"));
		bankService.createNewBankAccount(balance, ctx.cookieStore("name"));
		
		ctx.status(201);
		ctx.redirect("customer-new-bank-account.html");
		
	}
	
	@Override
	public void deposit(Context ctx) {
		
		ObjectMapper om = new ObjectMapper();
		
		BankAccount account;
		try {
			account = om.readValue(ctx.body(), BankAccount.class);
			int id = account.getId();
			double amount = account.getBalance();
			
			List<BankAccount> listOfAccounts = bankService.getBankAccounts(ctx.cookieStore("name"));
			
			for (BankAccount b: listOfAccounts) {
				if (b.getId() == account.getId()) {
					account = b;
				}
			}
			
			if (account.isApproved()) {
				bankService.makeDeposit(id, amount);
				loggy.info("User '" + account.getCustomerId() + "' deposited $" + amount + " to account " + id + ".");
				ctx.status(200);
				ctx.redirect("customer-deposit.html");
			} else {
				System.out.println("Account has not been approved yet!");
				ctx.status(405);
				ctx.redirect("customer-deposit.html");
			}
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (NoSuchAccountException e) {
			System.out.println("Account does not exist.");
			ctx.status(404);
		} catch (BadInputException e) {
			System.out.println("Input must be a positive number.");
			ctx.status(400);
		}
		
	}
	
	@Override
	public void withdraw(Context ctx) {
		
		ObjectMapper om = new ObjectMapper();
		
		BankAccount account;
		try {
			account = om.readValue(ctx.body(), BankAccount.class);
			int id = account.getId();
			double amount = account.getBalance();
			
			List<BankAccount> listOfAccounts = bankService.getBankAccounts(ctx.cookieStore("name"));
			
			for (BankAccount b: listOfAccounts) {
				if (b.getId() == account.getId()) {
					account = b;
				}
			}
			
			if (account.isApproved()) {
				ctx.status(200);
				ctx.redirect("customer-withdraw.html");
				bankService.makeWithdrawal(id, amount);
				loggy.info("User '" + account.getCustomerId() + "' withdrew $" + amount + " from account " + id + ".");
			} else {
				System.out.println("Account has not been approved yet!");
				ctx.status(405);
				ctx.redirect("customer-withdraw.html");
			}
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (NoSuchAccountException e) {
			System.out.println("Account does not exist.");
			ctx.status(404);
		} catch (BadInputException e) {
			System.out.println("Input must be a positive number.");
			ctx.status(400);
		} catch (OverdrawnException e) {
			System.out.println("Overdrawn!");
			ctx.status(405);
		}
		
	}
	
	@Override
	public void transfer(Context ctx) {
		
		ObjectMapper om = new ObjectMapper();
		
		BankAccount[] accountArray;
		try {
			accountArray = om.readValue(ctx.body(), BankAccount[].class);
			BankAccount account1 = accountArray[0];
			BankAccount account2 = accountArray[1];
			int id1 = account1.getId();
			int id2 = account2.getId();
			double amount = account1.getBalance();
			
			List<BankAccount> listOfAccounts = bankService.getBankAccounts(ctx.cookieStore("name"));
			List<BankAccount> allAccounts = bankService.getBankAccounts();
			
			for (BankAccount b: listOfAccounts) {
				if (b.getId() == account1.getId()) {
					account1 = b;
				}
			}
			
			for (BankAccount b: allAccounts) {	
				if (b.getId() == account2.getId()) {
					account2 = b;
				}
			}
			
			if (account1.isApproved() && account2.isApproved()) {
				ctx.status(200);
				ctx.redirect("customer-transfer.html");
				bankService.makeTransfer(id1, id2, amount);
				loggy.info("User '" + account1.getCustomerId() + "' transferred $" + amount + " from account " + id1 + " to account " + id2 + ".");
			} else {
				System.out.println("At least one of the accounts has not been approved yet!");
				ctx.status(405);
				ctx.redirect("customer-transfer.html");
			}
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (NoSuchAccountException e) {
			System.out.println("At least one of the accounts does not exist.");
			ctx.status(404);
		} catch (BadInputException e) {
			System.out.println("Input must be a positive number.");
			ctx.status(400);
		} catch (OverdrawnException e) {
			System.out.println("Overdrawn!");
			ctx.status(405);
		}
		
	}

	@Override
	public void approveAccount(Context ctx) {

		ObjectMapper om = new ObjectMapper();
		
		BankAccount account;
		try {
			account = om.readValue(ctx.body(), BankAccount.class);
			int id = account.getId();
			
			bankService.approveAccount(id);
			ctx.status(200);
			ctx.redirect("employee-home.html");
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (NoSuchAccountException e) {
			System.out.println("Account does not exist.");
			ctx.status(404);
		}
		
	}
	
	@Override
	public void rejectAccount(Context ctx) {

		ObjectMapper om = new ObjectMapper();
		
		BankAccount account;
		try {
			account = om.readValue(ctx.body(), BankAccount.class);
			int id = account.getId();
			
			bankService.rejectAccount(id);
			ctx.status(200);
			ctx.redirect("employee-home.html");
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (NoSuchAccountException e) {
			System.out.println("Account does not exist.");
			ctx.status(404);
		}
		
	}

}
