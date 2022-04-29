package com.revature.dao;

import java.util.ArrayList;

import com.revature.model.Account;
import com.revature.model.Customer;

public interface AccountDAO {
	
	public ArrayList<Account> getAllAccounts(); //CHECKED
	
	public ArrayList<Account> getAllAccounts(Customer c); //FIX FOR JOINT ACCOUNT
	
	public Account getAccount(String userId, int accountNumber); //CHECKED
	
	public Account getAccount(int accountNumber); //CHECKED
	
	public boolean existAccount(int accountNumber); //CHECKED
	
	public void createAccount(Customer c); //CHECKED
	
	public void addAccount(Account a); //tested with createAccount(Customer c)
  
	public void deposit(Customer c);

	public void withdraw(Customer c);
	
	public void transfer(Customer c);
	
	public void updateStatus(Account a, int choice); //CHECKED
}