package com.revature.model;

public class BankAdmin extends Employee{
	final boolean admin = true;
	
	public BankAdmin(String firstName, String lastName, String username, String password) {
		super(firstName, lastName, username, password);
	}
	
	public boolean getAdmin() {return admin;}
	
}
