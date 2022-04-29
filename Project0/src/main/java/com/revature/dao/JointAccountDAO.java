package com.revature.dao;

import java.util.ArrayList;

import com.revature.model.Account;
import com.revature.model.Customer;
import com.revature.model.JointAccount;

public interface JointAccountDAO {
	
	//public ArrayList<JointAccount> getAllJointAccounts();
	
	//public JointAccount getJointAccount();
	
	//public ArrayList<Customer> getJointAccountOwners(int accNum);
	
	//public boolean existCoOwner();
	
	public void createJointAccount(JointAccount a); //CHECKED
	
	public void updateStatus(Account a, int choice); //CHECKED
}