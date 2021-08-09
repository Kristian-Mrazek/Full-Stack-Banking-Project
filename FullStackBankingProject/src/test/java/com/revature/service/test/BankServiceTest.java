package com.revature.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.revature.exceptions.BadInputException;
import com.revature.exceptions.NoSuchAccountException;
import com.revature.exceptions.OverdrawnException;
import com.revature.models.BankAccount;
import com.revature.models.CustomerAccount;
import com.revature.models.EmployeeAccount;
import com.revature.repository.BankDao;
import com.revature.repository.CustomerDao;
import com.revature.service.BankService;
import com.revature.service.BankServiceImpl;

public class BankServiceTest {
	
	BankService bankService;
	
	@Mock
	BankDao bankDao;
	
	@Mock
	CustomerDao customerDao;
	
	@Mock
	CustomerAccount customer;
	
	@Mock
	EmployeeAccount employee;
	
	@Mock
	BankAccount bank1;
	
	@Mock
	BankAccount bank2;
	
	@Mock
	BankAccount bank3;
	
	@Before
	public void setUp() {
		
		bankDao = mock(BankDao.class);
		customerDao = mock(CustomerDao.class);
		customer = mock(CustomerAccount.class);
		bank1 = mock(BankAccount.class);
		bank2 = mock(BankAccount.class);
		bank3 = mock(BankAccount.class);
		
		when(customerDao.selectCustomerAccountByUsername("johnsmith")).thenReturn(customer);
		when(customer.getPassword()).thenReturn("imgeneric7");
		when(customer.getId()).thenReturn(1);
		when(bankDao.selectBankAccountsByCustomer(1)).thenReturn(new ArrayList<BankAccount>(Arrays.asList(bank1, bank2, bank3)));
		
		when(bankDao.selectBankAccountById(1)).thenReturn(bank1);
		when(bankDao.selectBankAccountById(2)).thenReturn(bank2);
		when(bankDao.selectBankAccountById(3)).thenReturn(bank3);
		when(bankDao.selectAllBankAccounts()).thenReturn(new ArrayList<BankAccount>(Arrays.asList(bank1, bank2, bank3)));
		when(bank1.getId()).thenReturn(1);
		when(bank2.getId()).thenReturn(2);
		when(bank3.getId()).thenReturn(3);
		when(bank1.getBalance()).thenReturn(50.0);
		when(bank2.getBalance()).thenReturn(30.0);
		when(bank3.getBalance()).thenReturn(0.0);
		when(bank1.isApproved()).thenReturn(true);
		when(bank2.isApproved()).thenReturn(true);
		when(bank3.isApproved()).thenReturn(false);
		
		bankService = new BankServiceImpl(bankDao, customerDao);
		
	}
	
	@Test
	public void testGetBankAccounts() throws NoSuchAccountException {
		
		assertEquals(bankService.getBankAccounts(), new ArrayList<BankAccount>(Arrays.asList(bank1, bank2, bank3)));
		assertEquals(bankService.getBankAccounts("johnsmith"), new ArrayList<BankAccount>(Arrays.asList(bank1, bank2, bank3)));
		assertThrows(NoSuchAccountException.class, () -> bankService.getBankAccounts("whoami?61"));
		assertThrows(NoSuchAccountException.class, () -> bankService.getBankAccounts("imanemployee"));
		
	}
	
	@Test
	public void testGetUnvalidatedAccounts() {
		
		assertEquals(bankService.getUnvalidatedAccounts(), new ArrayList<BankAccount>(Arrays.asList(bank3)));
		
	}
	
	@Test
	public void testGetBalance() throws NoSuchAccountException {
		
		assertEquals(bankService.getBalance(1), 50.0, 0.001);
		assertEquals(bankService.getBalance(2), 30.0, 0.001);
		assertThrows(NoSuchAccountException.class, () -> bankService.getBalance(4));
		assertThrows(NoSuchAccountException.class, () -> bankService.getBalance(0));
		assertThrows(NoSuchAccountException.class, () -> bankService.getBalance(-10));
		
	}
	
	@Test
	public void testMakeDeposit() throws BadInputException, NoSuchAccountException {
		
		bankService.makeDeposit(1, 50);
		verify(bankDao, times(1)).updateBankAccount(bank1);
		assertThrows(BadInputException.class, () -> bankService.makeDeposit(1, -100));
		assertThrows(BadInputException.class, () -> bankService.makeDeposit(1, -100));
		assertThrows(NoSuchAccountException.class, () -> bankService.makeDeposit(-2, 20));
		assertThrows(NoSuchAccountException.class, () -> bankService.makeDeposit(5, 20));
		
	}
	
	@Test
	public void testMakeWithdrawal() throws OverdrawnException, BadInputException, NoSuchAccountException {
		
		bankService.makeWithdrawal(2, 5);
		verify(bankDao, times(1)).updateBankAccount(bank2);
		assertThrows(BadInputException.class, () -> bankService.makeWithdrawal(1, -50));
		assertThrows(BadInputException.class, () -> bankService.makeWithdrawal(1, 0));
		assertThrows(NoSuchAccountException.class, () -> bankService.makeWithdrawal(-2, 20));
		assertThrows(NoSuchAccountException.class, () -> bankService.makeWithdrawal(5, 20));
		assertThrows(OverdrawnException.class, () -> bankService.makeWithdrawal(1, 80));
		
	}
	
	@Test
	public void testMakeTransfer() throws OverdrawnException, BadInputException, NoSuchAccountException {
		
		bankService.makeTransfer(1, 2, 15);
		verify(bankDao, times(1)).transfer(bank1, bank2, 15);
		assertThrows(BadInputException.class, () -> bankService.makeTransfer(1, 2, -90));
		assertThrows(BadInputException.class, () -> bankService.makeTransfer(2, 1, 0));
		assertThrows(NoSuchAccountException.class, () -> bankService.makeTransfer(-1, 2, 15));
		assertThrows(NoSuchAccountException.class, () -> bankService.makeTransfer(1, 6, 10));
		assertThrows(OverdrawnException.class, () -> bankService.makeTransfer(1, 2, 70));
		
	}
	
	@Test
	public void testApproveAccount() throws NoSuchAccountException {
		
		bankService.approveAccount(3);
		verify(bankDao, times(1)).updateBankAccount(bank3);
		assertThrows(NoSuchAccountException.class, () -> bankService.approveAccount(7));
		assertThrows(NoSuchAccountException.class, () -> bankService.approveAccount(-9));
		
	}
	
	@Test
	public void testRejectAccount() throws NoSuchAccountException {
		
		bankService.rejectAccount(3);
		verify(bankDao, times(1)).deleteBankAccount(bank3);
		assertThrows(NoSuchAccountException.class, () -> bankService.rejectAccount(10));
		assertThrows(NoSuchAccountException.class, () -> bankService.rejectAccount(-8));
		
	}
	

}
