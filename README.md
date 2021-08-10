# Online Banking System

## Project Description

This is browser-based bank application that allows the user to perform both customer and employee functionalities. It interacts with a PostgreSQL database, which stores information
on bank, customer, and employee accounts. Customers can apply for new bank accounts and make transactions while employees can perform administrative actions such as validating new
accounts and viewing the transaction log.

## Technologies Used

•	Java 8

•	PostgreSQL 10

•	HTML 5 with CSS

•	JavaScript

•	Spring Tool Suite

•	Javalin

•	Maven and Maven dependencies (Apache Log4J, JUnit, Mockito, and Javalin)

•	Bootstrap

## Features

•	The ability to log in as either an employee or customer, as well as the ability to create a new employee or customer account.

•	The ability to apply for a new bank account as a customer.

•	The ability to view the balance of an existing account as a customer.

•	The ability to perform deposits, withdrawals, and money transfers as a customer.

• The ability to approve and reject pending bank accounts as an employee.

•	The ability to view a list of a customer's accounts as an employee.

•	The ability to view a log of all transactions since the bank's inception as an employee.

•	Modern, Bootstrap-based UI with a navbar for easy navigation.

•	Protection against invalid transactions (non-positive amount or withdrawal resulting in a negative balance).

## Getting Started

To begin with, download the project and open it in Spring Tool Suite. Ensure that all the needed internal dependencies are installed (this does not include the Maven dependencies,
though it includes Maven itself).

It is important to generate the needed database using DBeaver or another PostgreSQL tool. Use the following statement to do this:

```
create table employee_accounts (
	employee_id serial primary key,
	user_name varchar(30) unique not null,
	pass_word varchar(30) not null

);

create table customer_accounts (
	customer_id serial primary key,
	user_name varchar(30) unique not null,
	pass_word varchar(30) not null

);

create table bank (
	account_id serial primary key,
	balance decimal not null,
	is_approved boolean not null,
	customer_id int,
	foreign key(customer_id) references customer_accounts(customer_id)

);

insert into bank (balance, is_approved, customer_id) values (10.0, true, 1) returning account_id;

create or replace function transfer(sender_id int, receiver_id int, amount decimal) returns void
	language sql 
	as $$
		update bank set balance = balance - amount where account_id = sender_id;
		update bank set balance = balance + amount where account_id = receiver_id;
	$$;
```

The code can then be run. Go to the URL that Javalin specifies in the console.

## Usage

You will start at the customer login screen. Here, you can log in as a customer if you have an existing account, create a new customer account with a username or password, or go
to the employee login screen instead. The employee login screen has options to log in with an existing employee account, create a new employee account (which requires you to
know a secret code), or return to the customer login.

If you log in as a customer, you will see a view of your existing accounts if you have any. If not, you have to use the navbar at the top of the page to create a new account with
a starting balance. After this, you must wait until the account is approved. You can then perform deposits, withdrawals, and transfers, specifying the account(s) involved as well
as the amount of money in the transaction.

In employee mode, there are three main capabilities. When you log in, you will see a list of pending accounts. You can approve or reject the accounts by entering the ID number
in the form and then clicking the respective button. On the View Customer Accounts page, enter a customer username and press Submit to view the list of accounts and their
respective balances. On the transaction log page, simply select the log file and the application will print the contents on the page.
