package com.revature.controller;

import io.javalin.http.Context;

public interface CustomerController {
	
	public void customerLogin(Context ctx);
	
	public void customerLogout(Context ctx);
	
	public void newCustomerAccount(Context ctx);
	
	public boolean checkUser(Context ctx);

}
