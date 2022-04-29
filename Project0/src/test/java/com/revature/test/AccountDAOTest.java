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

import com.revature.dao.AccountDAOImpl;
import com.revature.dao.CustomerDAOImpl;
import com.revature.dao.JointAccountDAOImpl;
import com.revature.model.Account;
import com.revature.model.Customer;

public class AccountDAOTest {
	
	//connect to the database
	AccountDAOImpl accountTest = new AccountDAOImpl();
	CustomerDAOImpl customerTest = new CustomerDAOImpl();
	JointAccountDAOImpl jointTest = new JointAccountDAOImpl();

	@Test
	public void testGetAllAccounts() {
		
		ArrayList<Account> a = accountTest.getAllAccounts();	
		assertEquals(3, a.size());
		
		
		ArrayList<Account> b  = accountTest.getAllAccounts(customerTest.getCustomer("micjor1"));
		assertEquals(1, b.size());
	}
	
	@Test
	public void testGetAccount() {
		
		Account a = accountTest.getAccount("jimful1", 471385698);
		assertNotNull(a);
		
		Account b = accountTest.getAccount(902153982);
		assertNotNull(b);
		
		Account c = accountTest.getAccount(902151182);
		assertNull(c);
	}
	
	@Test
	public void TestExistAccount() {
		
		boolean t = accountTest.existAccount(471385698);
		boolean f = accountTest.existAccount(471385098);
		assertTrue(t);
		assertFalse(f);
	}
	
	@Ignore
	public void testCreateAccount() {
		
		accountTest.createAccount(customerTest.getCustomer("test01"));
		accountTest.createAccount(customerTest.getCustomer("test01"));
		accountTest.createAccount(customerTest.getCustomer("test01"));

		//Amount depends on what is actually in my database...
		assertEquals(3,accountTest.getAllAccounts(customerTest.getCustomer("test01")).size());
		//assertEquals(3, jointTest.getJointAccountOwners());
	}
	
	@Ignore
	public void testAddAccount() {
		
		//Get the customer I want to add the account to.
		Customer a = customerTest.getCustomer("jimfUl1");
		
		Account acc2 = new Account("saving","Jimmy Fuller",500);
		
		accountTest.addAccount(acc2);
		assertEquals(2,a.getMyAccounts().size());
	}
	
	@Ignore
	public void testDeposit() {
		
		//Check if deposit works ADD $100
		accountTest.deposit(customerTest.getCustomer("jimful1"));
		assertEquals(1000.57, accountTest.getAccount(471385698).getBalance());
		
		//check if deposit works for joint account
		
	}
	
	@Ignore
	public void testWithdraw() {
		//Check the withdraw works TAKE $100
		accountTest.withdraw(customerTest.getCustomer("jimful1"));
		assertEquals(900.57, accountTest.getAccount(471385698).getBalance());
		
		//check if deposit works for joint account
		
	}
	
	@Ignore
	public void testTransfer() {
		//Check if the money moved properly from 1st owner, to second owner.
		accountTest.transfer(customerTest.getCustomer("jimful1"));
		
		//check if transfer works for joint account
		
	}
	
	@Test
	public void testUpdateStatus() {
		//APPROVED
		accountTest.updateStatus(accountTest.getAccount(580949774), 1);
		assertEquals(1, accountTest.getAccount(580949774).getConfirmation());
		
		//DENIED
		accountTest.updateStatus(accountTest.getAccount(580949774), 2);
		assertEquals(2, accountTest.getAccount(580949774).getConfirmation());
		
		//CANCELLED
		accountTest.updateStatus(accountTest.getAccount(580949774), 3);
		assertEquals(3, accountTest.getAccount(580949774).getConfirmation());
		
		//PENDING
		accountTest.updateStatus(accountTest.getAccount(580949774), 0);
		assertEquals(0, accountTest.getAccount(580949774).getConfirmation());
	}
	
	@AfterEach
	public void afterEach() {
		System.out.println();
		System.out.println("<<<<<<<<<<<<<<<<<<<<TEST DONE>>>>>>>>>>>>>>>>>>>>>> NEW TEST IN PROGRESS.....");
	}
}
