package com.revature.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.revature.dao.AccountDAOImpl;
import com.revature.dao.CustomerDAOImpl;
import com.revature.dao.JointAccountDAOImpl;
import com.revature.model.Account;
import com.revature.model.Customer;
import com.revature.model.JointAccount;

public class JointAccountDAOTest {
	
	JointAccountDAOImpl jointAccountTable = new JointAccountDAOImpl();
	CustomerDAOImpl customerTest = new CustomerDAOImpl();
	AccountDAOImpl a = new AccountDAOImpl();
	
	@Test
	public void testgetAllJointAccount() {
		ArrayList<Account> jas = a.getAllAccounts(customerTest.getCustomer("test01"));
		
		for(Account acc: jas) {
			System.out.println(acc.toString());
		}
		
		assertEquals(3, jas.size());
				
	}

}
