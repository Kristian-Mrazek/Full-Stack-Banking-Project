package com.revature.service.test;

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
import com.revature.models.EmployeeAccount;
import com.revature.repository.EmployeeDao;
import com.revature.service.EmployeeService;
import com.revature.service.EmployeeServiceImpl;

public class EmployeeServiceTest {
	
	EmployeeService employeeService;
	
	@Mock
	EmployeeDao fakeDao;
	
	@Mock
	EmployeeAccount employee;
	
	@Before
	public void setUp() {
		
		fakeDao = mock(EmployeeDao.class);
		employee = mock(EmployeeAccount.class);
		
		when(fakeDao.selectEmployeeAccountByUsername("imanemployee")).thenReturn(employee);
		when(employee.getPassword()).thenReturn("iworkhard247");
		
		employeeService = new EmployeeServiceImpl(fakeDao);
		
	}
	
	@Test
	public void testValidateEmployeeLogin() throws NoSuchAccountException {
		
		assertTrue(employeeService.validateEmployeeLogin("imanemployee", "iworkhard247"));
		assertFalse(employeeService.validateEmployeeLogin("imanemployee", "imgeneric7"));
		assertThrows(NoSuchAccountException.class, () -> employeeService.validateEmployeeLogin("employee", "iworkhard247"));
		assertThrows(NoSuchAccountException.class, () -> employeeService.validateEmployeeLogin("imanelephant", "1h4v3n01d34!"));
		
	}
	
	@Test
	public void testCreateNewEmployeeAccount() {
		
		
		assertThrows(UsernameAlreadyExistsException.class, () -> employeeService.createNewEmployeeAccount("imanemployee", "differen7"));
		assertThrows(UsernameAlreadyExistsException.class, () -> employeeService.createNewEmployeeAccount("imanemployee", "iworkhard247"));
		
	}
	
}