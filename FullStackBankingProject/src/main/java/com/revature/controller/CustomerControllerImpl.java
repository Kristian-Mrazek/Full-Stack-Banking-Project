package com.revature.controller;

import com.revature.exceptions.NoSuchAccountException;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.repository.CustomerDaoImpl;
import com.revature.service.CustomerService;
import com.revature.service.CustomerServiceImpl;

import io.javalin.http.Context;

public class CustomerControllerImpl implements CustomerController {
	
	private CustomerService customerService = new CustomerServiceImpl(new CustomerDaoImpl());

	@Override
	public void customerLogin(Context ctx) {
		
		String username = ctx.formParam("username");
		String password = ctx.formParam("password");
		
		try {
			if (customerService.validateCustomerLogin(username, password)) {
			
				ctx.status(200);
				ctx.cookieStore("name", username);
				ctx.redirect("/customer-home.html");
			
			} else {
				
				ctx.status(507);
				ctx.redirect("/customer-login.html");
				
			}
			
		} catch (NoSuchAccountException e) {
			ctx.status(200);
			ctx.redirect("/customer-login.html");
		}
		
	}
	
	@Override
	public void customerLogout(Context ctx) {
		ctx.clearCookieStore();
		ctx.redirect("customer-login.html");
	}
	
	@Override
	public void newCustomerAccount(Context ctx) {
		
		String username = ctx.formParam("newCustomerUsername");
		String password = ctx.formParam("newCustomerPassword");
		
		System.out.println(username);
		System.out.println(password);
		
		try {
			customerService.createNewCustomerAccount(username, password);
			ctx.status(201);
			ctx.redirect("customer-login.html");
		} catch (UsernameAlreadyExistsException e) {
			ctx.status(405);
			ctx.redirect("customer-new-bank-account.html");
		}
		
		
	}

	@Override
	public boolean checkUser(Context ctx) {
		
		return customerService.validateToken(ctx.cookieStore("name"));
		
	}

}
