package com.revature.model;

import java.util.Random;


// This class will hold everything that you can do in a bank
public class Account {
	
	//Attributes of bank
	private int accountNumber;
	private long routingNumber;
	private String accountType;
	private double balance;
	private String primaryOwner; //FK to link to Customer
	private int confirmation; //
	
	//Only one Constructor because it needs to be specifically defined.
	//Maybe need two...
	public Account() {
		accountNumber = generateAccNumber();
		routingNumber = generateRouNumber();
		 accountType = "TEST";
		 balance=1;
		 primaryOwner= "";
		confirmation=0;
	}
	
	public Account(String accountType, String primaryOwner, double balance){
		this.primaryOwner = primaryOwner;
		this.accountType = accountType;
		this.accountNumber = generateAccNumber();
		this.routingNumber = generateRouNumber();
		this.balance = balance;
		this.confirmation =0;
	}
	
	//For retrieving data from the database
	public Account(int accountNumber,long routingNumber, String accType, double balance, String primaryOwner, int confirmation){
		this.accountNumber = accountNumber;
		this.routingNumber = routingNumber;
		this.accountType = accType;
		this.balance = balance;
		this.primaryOwner = primaryOwner;
		this.confirmation = confirmation;
	}
	
	//Getters methods
	public String getPrimaryOwner() {return this.primaryOwner;}
	public double getBalance() {return this.balance;}
	public String getAccountType() {return this.accountType;}
	public int getAccountNumber() {return this.accountNumber;}
	public long getRoutingNumber() {return this.routingNumber;}
	public int getConfirmation() {return this.confirmation;}
	
	//Setter methods
	public void setOwner(String s1) {this.primaryOwner = s1;}
	public void setBalance(double b1) {this.balance = b1;}
	public void setAccountType(String s1) {this.accountType = s1;}
	public void setConfirmation(int c1) {this.confirmation = c1;}
	
	//Generate a 9-digit number for account number
	private int generateAccNumber() {
		Random rand = new Random();
		//Get an account number between 100,000,000 - 899,999,999
		return 100000000 + rand.nextInt(900000000);
	}
	
	//Generate an 11-Digit number for routing number
	private long generateRouNumber() {
		Random rand = new Random();
		//Get an account number between 10,000,000,000 - 89,999,999,999
		return 10000000000L + rand.nextLong(90000000000L);
	}
		
	//to String method to read the account information.
	@Override
	public String toString() {
		return "Account [accountNumber=" + accountNumber + ", routingNumber=" + routingNumber + ", accountType="
				+ accountType + ", balance=" + balance + ", primaryOwner=" + primaryOwner + "]";
	}

}