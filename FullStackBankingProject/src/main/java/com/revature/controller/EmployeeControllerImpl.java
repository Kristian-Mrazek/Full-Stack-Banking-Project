package com.revature.controller;

import com.revature.exceptions.NoSuchAccountException;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.repository.EmployeeDaoImpl;
import com.revature.service.EmployeeService;
import com.revature.service.EmployeeServiceImpl;

import io.javalin.http.Context;

public class EmployeeControllerImpl implements EmployeeController {
	
	private EmployeeService employeeService = new EmployeeServiceImpl(new EmployeeDaoImpl());
	
	private static final String EMPLOYEE_CODE = "Th1515th33mpl0y33C0D3!";

	@Override
	public void employeeLogin(Context ctx) {
		
		String username = ctx.formParam("username");
		String password = ctx.formParam("password");
		
		try {
			if (employeeService.validateEmployeeLogin(username, password)) {
				
				ctx.status(200);
				ctx.redirect("/employee-home.html");
				
			} else {
				
				ctx.status(507);
				ctx.redirect("/employee-login.html");
				
			}
		} catch (NoSuchAccountException e) {
			
			ctx.status(507);
			ctx.redirect("/employee-login.html");
		}
		
	}
	
	@Override
	public void employeeLogout(Context ctx) {
		ctx.clearCookieStore();
		ctx.redirect("employee-login.html");
	}
	
	@Override
	public void newEmployeeAccount(Context ctx) {
		
		String username = ctx.formParam("newEmployeeUsername");
		String password = ctx.formParam("newEmployeePassword");
		String secretCode = ctx.formParam("secretCode");
		
		try {
			if (secretCode.equals(EMPLOYEE_CODE)) {
				employeeService.createNewEmployeeAccount(username, password);
				ctx.status(201);
				ctx.redirect("employee-login.html");
			} else {
				ctx.status(403);
				ctx.redirect("new-employee.html");
			}
		} catch (UsernameAlreadyExistsException e) {
			ctx.status(405);
			ctx.redirect("new-employee.html");
		}
		
		
	}

	@Override
	public boolean checkUser(Context ctx) {
		
		return employeeService.validateToken(ctx.cookieStore("name"));
		
	}

}
