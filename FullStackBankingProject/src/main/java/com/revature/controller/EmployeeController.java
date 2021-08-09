package com.revature.controller;

import io.javalin.http.Context;

public interface EmployeeController {
	
	void employeeLogin(Context ctx);
	
	void employeeLogout(Context ctx);
	
	public void newEmployeeAccount(Context ctx);
	
	public boolean checkUser(Context ctx);

}
