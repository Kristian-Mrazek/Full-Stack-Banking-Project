package com.revature;

import com.revature.controller.BankController;
import com.revature.controller.BankControllerImpl;
import com.revature.controller.CustomerController;
import com.revature.controller.CustomerControllerImpl;
import com.revature.controller.EmployeeController;
import com.revature.controller.EmployeeControllerImpl;

import io.javalin.Javalin;

public class MainDriver {
	
	private static final String CUSTOMER_PATH = "/customer";
	private static final CustomerController customerController = new CustomerControllerImpl();
	
	private static final String EMPLOYEE_PATH = "/employee";
	private static final EmployeeController employeeController = new EmployeeControllerImpl();
	
	private static final String BANK_PATH = "/bank";
	private static final BankController bankController = new BankControllerImpl();
	
	
	public static void main(String[] args) {
		
		Javalin app = Javalin.create(
					config -> {
						config.addStaticFiles("/public");
					}
				).start(9000);
		
		
		app.post(CUSTOMER_PATH, ctx -> customerController.customerLogin(ctx));
		app.post(EMPLOYEE_PATH, ctx -> employeeController.employeeLogin(ctx));
		app.post("/new-customer", ctx -> customerController.newCustomerAccount(ctx));
		app.post("/new-employee", ctx -> employeeController.newEmployeeAccount(ctx));
		app.post(BANK_PATH, ctx -> bankController.postBankAccount(ctx));
		app.get(BANK_PATH, ctx -> bankController.getUserBankAccounts(ctx));
		app.put("/deposit", ctx -> bankController.deposit(ctx));
		app.put("/withdraw", ctx -> bankController.withdraw(ctx));
		app.put("/transfer", ctx -> bankController.transfer(ctx));
		app.get("/validate", ctx -> bankController.getUnvalidatedAccounts(ctx));
		app.put("/validate", ctx -> bankController.approveAccount(ctx));
		app.delete("/validate", ctx -> bankController.rejectAccount(ctx));
		app.post("/view-accounts", ctx -> bankController.storeUsername(ctx));
		app.get("/view-accounts", ctx -> bankController.getUserBankAccounts(ctx));
		app.get("/customer-logout", ctx -> customerController.customerLogout(ctx));
		app.get("/employee-logout", ctx -> employeeController.employeeLogout(ctx));
		
	}

}