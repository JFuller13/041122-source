package com.revature.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.revature.dao.BankAdminDAOImpl;
import com.revature.model.BankAdmin;

public class BankAdminTest {
	
	BankAdminDAOImpl baTest = new BankAdminDAOImpl();
	
	@Test
	public void testGetEmplyee() {
		BankAdmin bob = baTest.getEmployee("carsmi1");
		
		assertNotNull(bob);
	}

}
