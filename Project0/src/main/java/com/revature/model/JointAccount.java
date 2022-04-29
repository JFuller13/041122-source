package com.revature.model;

import java.util.ArrayList;

public class JointAccount extends Account{
	
	private ArrayList<Customer> owners;

	public JointAccount(String accountType, ArrayList<Customer> owners,double balance) {
		super(accountType, owners.get(0).getUsername(), balance);
		this.owners = owners;
	}
	
	//Get info needed to see the customers for a joint account
	public ArrayList<Customer> getOwners(){return this.owners;}

	@Override
	public String toString() {
		return  "Account [accountNumber=" + super.getAccountNumber() + ", routingNumber=" + super.getRoutingNumber() + ", accountType="
				+ super.getAccountType() + ", balance=" + super.getBalance() + ", primaryOwner=" + super.getPrimaryOwner() + ", " + "owners=" + owners + "]";
	}
	
	
}
