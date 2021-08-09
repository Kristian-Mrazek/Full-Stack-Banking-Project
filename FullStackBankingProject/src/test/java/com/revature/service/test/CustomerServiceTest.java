package com.revature.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.revature.exceptions.NoSuchAccountException;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.CustomerAccount;
import com.revature.repository.CustomerDao;
import com.revature.service.CustomerService;
import com.revature.service.CustomerServiceImpl;

public class CustomerServiceTest {
	
	CustomerService customerService;
	
	@Mock
	CustomerDao fakeDao;
	
	@Mock
	CustomerAccount customer;
	
	@Before
	public void setUp() {
		
		fakeDao = mock(CustomerDao.class);
		customer = mock(CustomerAccount.class);
		
		when(fakeDao.selectCustomerAccountByUsername("johnsmith")).thenReturn(customer);
		when(customer.getPassword()).thenReturn("imgeneric7");
		when(customer.getId()).thenReturn(1);
		
		customerService = new CustomerServiceImpl(fakeDao);
		
	}
	
	@Test
	public void testValidateCustomerLogin() throws NoSuchAccountException {
		
		assertTrue(customerService.validateCustomerLogin("johnsmith", "imgeneric7"));
		assertFalse(customerService.validateCustomerLogin("johnsmith", "imgeneric"));
		assertThrows(NoSuchAccountException.class, () -> customerService.validateCustomerLogin("jonsmith", "imgeneric7"));
		assertThrows(NoSuchAccountException.class, () -> customerService.validateCustomerLogin("will", "bigbadbi11"));
		
	}
	
	@Test
	public void testCreateNewCustomerAccount() throws UsernameAlreadyExistsException {
		
		assertThrows(UsernameAlreadyExistsException.class, () -> customerService.createNewCustomerAccount("johnsmith", "differen7"));
		assertThrows(UsernameAlreadyExistsException.class, () -> customerService.createNewCustomerAccount("johnsmith", "imgeneric7"));
	
	}
	
	@Test
	public void testGetCustomerIdFromUsername() throws NoSuchAccountException {
		
		assertEquals(customerService.getCustomerIdFromUsername("johnsmith"), 1);
		assertThrows(NoSuchAccountException.class, () -> customerService.getCustomerIdFromUsername("idontexist"));
		assertThrows(NoSuchAccountException.class, () -> customerService.getCustomerIdFromUsername(""));
		assertThrows(NoSuchAccountException.class, () -> customerService.getCustomerIdFromUsername("?????"));
		
	}

}
