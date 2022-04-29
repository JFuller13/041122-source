package com.revature.model;

import java.util.ArrayList;

public class Customer {
	//Variables for customer class
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	//This holds the collection of accounts in a arrayList for the customer.
	private ArrayList<Account> myAccounts;
	
	//Constructor classes
	public Customer(String username, String password, String firstname, String lastname){
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.myAccounts = new ArrayList<Account>();
	}
	
	//default constructor
	public Customer() {
		username = "Tester";
		password = "password";
		firstname = "name";
		lastname = "name";
		
	}
	
	//getter methods
	public String getUsername() {return username;}
	public String getPassword() {return password;}
	public String getFirstname() {return firstname;}
	public String getLastname() {return lastname;}
	public ArrayList<Account> getMyAccounts(){return myAccounts;}
	
	//setter methods
	public void setUsername(String user) {this.username = user;}
	public void setPassword(String pass) {this.password = pass;}
	public void setfirstname(String firstname) {this.firstname = firstname;}
	public void addAccount(Account acc) {this.myAccounts.add(acc);}
	public void addAccount(ArrayList<Account> a) {this.myAccounts.addAll(a);}

	@Override
	public String toString() {
		return "Customer [username=" + username + ", password=" + password + ", firstname=" + firstname + ", lastname="
				+ lastname + ", myAccounts=" + myAccounts + "]";
	}

	

}
