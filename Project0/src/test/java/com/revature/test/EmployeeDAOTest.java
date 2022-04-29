package com.revature.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import com.revature.dao.EmployeeDAOImpl;
import com.revature.model.Account;
import com.revature.model.Customer;
import com.revature.model.Employee;
import com.revature.model.JointAccount;

public class EmployeeDAOTest {
	
	EmployeeDAOImpl employeeTest = new EmployeeDAOImpl();
	
	@Test
	public void testLogin() {
			boolean t = employeeTest.login("carsmi1", "hdyet4");
			boolean f = employeeTest.login("jimful1", "1234");
			
			assertTrue(t);
			assertFalse(f);
		
	}
	
	@Test
	public void testCheckAuthority() {
		boolean t = employeeTest.checkAuthority("carsmi1", "hdyet4");
		boolean f = employeeTest.checkAuthority("bobjon1", "mrfixit2020");
		
		assertTrue(t);
		assertFalse(f);
		
		
	}

}
