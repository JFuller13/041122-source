package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.model.Customer;


public class CustomerDAOImpl implements CustomerDAO<Customer, String> {
	
	private static final Logger logger = LogManager.getLogger(CustomerDAOImpl.class);

	
	//This creates an instance of our getConnection() method from the connection manager file 
	private Connection conn = ConnectionManager.getConnection();
		
	//this method returns all customers from the database
	public ArrayList<Customer> getAllCustomers(){
		try {
			conn = ConnectionManager.getConnection();
			
			//This creates a new statement called statement that uses a connection to the database
			Statement statement = conn.createStatement();
				
			//This is the SQL statement i want to execute.
			ResultSet rs = statement.executeQuery("Select * From customers");
				
			//this is our array list for storing our results
			ArrayList<Customer> customers = new ArrayList<Customer>();
				
			//this loop runs so long as there is another row in rs
			while(rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("c_password");
				String firstname = rs.getString("first_name");
				String lastname = rs.getString("last_name");
					
				//load customer information
				Customer c = new Customer(username, password,firstname,lastname);
				AccountDAOImpl test = new AccountDAOImpl();
				c.addAccount(test.getAllAccounts(c));
				
				//this adds each customer to our customer list(array)
				customers.add(c);
			}return customers;
		}catch(SQLException e) {
			logger.debug("Failure to retrieve from database...");
			logger.error(e.getLocalizedMessage());}return null;
	}

	@Override
	//This method will be used to GET ONE customer from the database
	public Customer getCustomer(String username) {
		try {
			conn = ConnectionManager.getConnection();
			
			//Step 1
			String query = "SELECT * FROM customers WHERE username = ?";
			PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
				
			//set the username parameter in the query filter value (ie the ?)
			statement.setString(1, username.toLowerCase());
			ResultSet rs = statement.executeQuery();
				
			//Step 2
			if(rs.next()) {
				String un = rs.getString("username");
				String pw = rs.getString("c_password");
				String fn = rs.getString("first_name");
				String ln = rs.getString("last_name");
				
				Customer c = new Customer(un,pw,fn,ln);
				
				//ADD the accounts to the customer's ArrayList<Account>
				AccountDAOImpl test = new AccountDAOImpl();
				c.addAccount(test.getAllAccounts(c));
				
				return c;
					
			}else return null;
				
		}catch(SQLException e) {
			logger.debug("Failure to find Customer: "+ username);
			logger.error(e.getLocalizedMessage());}return null;		
	}

	@Override
	//This method will see if the customer exist in the database
	public boolean existCustomer(String username) {
		try {
			conn = ConnectionManager.getConnection();
			
			//Step 1
			String query = "SELECT * FROM customers WHERE username = ?";
			PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
			
			//Set the username parameter in the query filter value
			statement.setString(1, username.toLowerCase());
			ResultSet rs = statement.executeQuery();
			
			//Step 2
			if(rs.next()){return true;}else return false;
			
		}catch(SQLException e) {
			logger.debug("Failure to find Customer.... ");
			logger.error(e.getLocalizedMessage());}return false;
	}

	//This method will be used to CREATE a new customer based on info provided and return that user.
	public Customer registerCustomer() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Create your username: ");
		String username = scan.nextLine();
		
		if(this.existCustomer(username.toLowerCase())) {
			System.out.println("USER ALREADY EXIST! TRY AGAIN");
		}else {
			System.out.println("Create your password");
			String password = scan.nextLine();
			System.out.println("Enter your first name: ");
			String firstname = scan.nextLine();
			System.out.println("Enter your Last name: ");
			String lastname = scan.nextLine();
			
			Customer c = new Customer(username,password,firstname,lastname);
			this.addCustomer(c);
			System.out.println("welcome, " + c.getFirstname());
			c= this.getCustomer(username);
			return c;
		}
		return null;
	}
	
	@Override
	//This method will INSERT the customer into the Customers database
	public void addCustomer(Customer c) {
		try {
			conn = ConnectionManager.getConnection();
			
			//Step 1
			String query = "INSERT INTO customers(username,c_password,first_name,last_name) values(?,?,?,?)";
			PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
			
			//Set the parameters in the query filter value
			statement.setString(1, c.getUsername().toLowerCase());
			statement.setString(2, c.getPassword());
			statement.setString(3, c.getFirstname());
			statement.setString(4, c.getLastname());
			
			//Insert with this method
			statement.execute();
			
		}catch(SQLException e) {
			logger.debug("Failure to add Customer to database:...  "+ c.getUsername());
			logger.error(e.getLocalizedMessage());
			}
		
	}

	//This method will be used to log into the Bank console.
	public boolean logIn(String username, String password) {
		try {
			conn = ConnectionManager.getConnection();
			
			//Step 1
			String query = "SELECT * FROM customers where username = ? and c_password = ?";
			PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
			
			statement.setString(1, username.toLowerCase());
			statement.setString(2, password);
			
			ResultSet rs = statement.executeQuery();
			
			if(rs.next()) {return true;}else return false;
			
		}catch(SQLException e) {
			logger.debug("Failure at Login:... "+ username);
			logger.error(e.getLocalizedMessage());}return false;
	}	
	
	//This method 'UPDATE' customer in the customers DATABASE
	public void update(Customer c) {
		try {
			conn = ConnectionManager.getConnection();
			
			//Step 1
			String query = "UPDATE customers SET first_name = ?, last_name = ?, c_password = ? WHERE username = ?";
			PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
			
			//Set the parameters in the query filter value
			statement.setString(1, c.getFirstname());
			statement.setString(2, c.getLastname());
			statement.setString(3, c.getPassword());
			statement.setString(4, c.getUsername().toLowerCase());
			
			//Insert with this method
			statement.execute();
			
		}catch(SQLException e) {
			logger.debug("Failure to add Customer to database:...  "+ c.getUsername());
			logger.error(e.getLocalizedMessage());
			}
	}
	
	//This method 'DELETE' customer in the customers DATABASE
	public void delete(Customer c) {
		try {
			conn = ConnectionManager.getConnection();
			
			//Step 1
			String query = "DELETE FROM customers WHERE username = ?";
			PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
			
			//Set the parameters in the query filter value
			statement.setString(1, c.getUsername().toLowerCase());
			
			//Insert with this method
			statement.execute();
			
		}catch(SQLException e) {
			logger.debug("Failure to add Customer to database:...  "+ c.getUsername());
			logger.error(e.getLocalizedMessage());
			}
		
	}
}
