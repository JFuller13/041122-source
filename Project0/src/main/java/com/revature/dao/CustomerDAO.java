package com.revature.dao;

import java.util.ArrayList;

import com.revature.model.Customer;

//To organize and know what are the methods for my Customer DAO
public interface CustomerDAO<Customer, String > {

	public ArrayList<Customer> getAllCustomers();
	
	public Customer getCustomer(String username);
	
	public boolean existCustomer(String username);
	
	public void addCustomer(Customer c);
	
	public Customer registerCustomer();
	
	public boolean logIn(String username, String password);
}
