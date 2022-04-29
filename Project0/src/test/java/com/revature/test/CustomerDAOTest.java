package com.revature.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.revature.dao.CustomerDAOImpl;
import com.revature.model.Customer;

public class CustomerDAOTest {
	
	//connect to the database
	CustomerDAOImpl customertest = new CustomerDAOImpl();
	
	@Test
	public void testGetAllCustomers() {
		
		//create an array to hold the customers in the database.
		ArrayList<Customer> customers = customertest.getAllCustomers();
		
		//test if there is the right amount of customers in the database
		assertEquals(5, customers.size());
	}
	
	@Test
	public void testGetCustomer() {
		
		Customer a = customertest.getCustomer("jimFUL1");
		assertNotNull(a);
		Customer b = customertest.getCustomer("jimFUL2");
		assertNull(b);

	}
	
	@Test
	public void testExistCustomer() {
		
		boolean t = customertest.existCustomer("jimFul1");
		boolean f = customertest.existCustomer("dhye2");
		
		assertTrue(t);
		assertFalse(f);
	}
	
	@Ignore
	public void testAddCustomer() {
		
		Customer c = new Customer("teST01","pass","Test","Dummy");
		customertest.addCustomer(c);
		
		
		
	}
	
	@Test
	public void testLogin() {
		boolean t = customertest.logIn("jimFUl1", "1234");
		boolean f = customertest.logIn("jimful1", "awsd");
		
		assertTrue(t);
		assertFalse(f);
	}
	
	@Ignore
	public void testRegister() {
		customertest.registerCustomer();
	}
	
	@AfterEach
	public void afterEach() {
		System.out.println();
		System.out.println("<<<<<<<<<<<<<<<<<<<<TEST DONE>>>>>>>>>>>>>>>>>>>>>> NEW TEST IN PROGRESS.....");
	}
	

}
