package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.model.Account;
import com.revature.model.BankAdmin;
import com.revature.model.Customer;
import com.revature.model.Employee;

public class EmployeeDAOImpl implements EmployeeDAO{

	private Connection conn = ConnectionManager.getConnection();
	private AccountDAOImpl accountTable = new AccountDAOImpl();
	private CustomerDAOImpl customerTable = new CustomerDAOImpl();
	private static final Logger logger = LogManager.getLogger(EmployeeDAOImpl.class);

	
	public void createEmployee(Employee e){
		conn = ConnectionManager.getConnection();
		
		try {
		String query = "INSERT INTO employees(firstname, lastname, username, e_password, bank_admin)values (?,?,?,?,?)";
		PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
		
		statement.setString(1, e.getFirstName());
		statement.setString(2, e.getLastName());
		statement.setString(3, e.getUsername());
		statement.setString(4, e.getPassword());
		statement.setBoolean(5, false);
		
		statement.execute();
		}catch(SQLException ex) {logger.error(ex.getLocalizedMessage());}
	}
	
	@Override
	public boolean login(String username, String password) {
		//This creates an instance of our getConnection() method from the connection manager file 
		conn = ConnectionManager.getConnection();
		
		try {
			//Step 1
			String query = "SELECT * FROM employees where username = ? and e_password = ?";
			PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
			
			statement.setString(1, username);
			statement.setString(2, password);
			
			ResultSet rs = statement.executeQuery();
			
			if(rs.next()) {return true;}else return false;
			
		}catch(SQLException e) {logger.error(e.getLocalizedMessage());}return false;
	}
	
	public boolean checkAuthority(String username, String password) {
		//This creates an instance of our getConnection() method from the connection manager file 
		conn = ConnectionManager.getConnection();
				
		try {
			//Step 1
			String query = "SELECT bank_admin FROM employees where username = ?";
			PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
					
			statement.setString(1, username);
			
			ResultSet rs = statement.executeQuery();
					
			if(rs.next()) {
				boolean ba = rs.getBoolean("bank_admin");
				return ba;
			}else {return false;}
					
		}catch(SQLException e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			}return false;
	}
	
	public Employee getEmployee(String username) {
		conn = ConnectionManager.getConnection();
		
		try {
			//Step 1
			String query = "SELECT * FROM employees WHERE username = ?";
			PreparedStatement statement = conn.prepareStatement(query);
				
			//set the username parameter in the query filter value (ie the ?)
			statement.setString(1, username.toLowerCase());
			ResultSet rs = statement.executeQuery();
				
			//Step 2
			if(rs.next()) {
				int id = rs.getInt("id_number");
				String firstname = rs.getString("firstname");
				String lastname = rs.getString("lastname");
				String un = rs.getString("username");
				String pw = rs.getString("e_password");
				boolean ba = rs.getBoolean("bank_admin");
				
				Employee e = new Employee(firstname, lastname, un, pw);
				return e;
					
			}else return null;
				
		}catch(SQLException e) {logger.error(e.getLocalizedMessage());}return null;
	}

	@Override
	public void viewCustomerInfo(String c) {
		if(customerTable.existCustomer(c)) {
			System.out.println("Customer name: "+customerTable.getCustomer(c).getFirstname()+" "+customerTable.getCustomer(c).getLastname());
			
			for(Account a: customerTable.getCustomer(c).getMyAccounts()) {
				if(a.getConfirmation() == 1) {System.out.println(a);}
			}
		}else {
			logger.debug("CUSTOMER DOESNT EXIST IN DATABASE");
			logger.error("Customer does not exist in the data base :: Customer: "+c);}		
	}

	@Override
	public void checkPendingAccounts() {
		conn = ConnectionManager.getConnection();
		
		try {
			//Step 1
			String query = "SELECT * FROM accounts WHERE confirmation = 0";
			PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
				
			//set the username parameter in the query filter value (ie the ?)
			//statement.setInt(1, 0);
			ResultSet rs = statement.executeQuery();
			
			//this is the array list i want to hold the accounts in.
			ArrayList<Account> temp = new ArrayList<Account>();
			
			//this loop runs so long as there is another row in rs
			while(rs.next()) {
				int accountNumber = rs.getInt("account_number");
				long routingNumber = rs.getLong("routing_number");
				String accType = rs.getString("account_type");
				double balance = rs.getDouble("balance");
				String primaryOwner = rs.getString("primary_owner");
				int confirmation = rs.getInt("confirmation");
						
				//this adds each Account from the Accounts database to our Account ArrayList<>
				temp.add(new Account(accountNumber,routingNumber,accType,balance,primaryOwner,confirmation));
			}
			
			//Change confirmation status
			System.out.println("Accounts that need to be checked "+temp.size()+"\n");
			for(Account a: temp) {
				System.out.println(a.toString());
				int choice=4;
				while(choice>2 || choice < 1){
					System.out.println("\nMake choice...\n1)Approve\n2)Deny");
					Scanner scan = new Scanner(System.in);
					choice = scan.nextInt();
					}
				accountTable.updateStatus(a,choice);
			}
			
		}catch(SQLException e) {logger.error(e.getLocalizedMessage());}
		
	}

	@Override
	//This method will let the the Bank_admin edit a customers account
	public void editAccounts(String name) {
		Scanner scan = new Scanner(System.in);
		
		try {
			if(customerTable.existCustomer(name)) {
				System.out.println("What do you want to edit on this account?\n1)Withdraw from\n2)Deposit Into\n3)Transfer To Another Account\n");
				int choice = scan.nextInt();
				switch(choice) {
					case 1:
						accountTable.withdraw(customerTable.getCustomer(name));
						break;
					case 2:
						accountTable.deposit(customerTable.getCustomer(name));
						break;
					case 3:
						accountTable.transfer(customerTable.getCustomer(name));
						break;
					default:
						System.out.println("INVALID CHOICE MADE...");
				}
				
			}else {System.out.println("Customer DOESNT EXIST");}
		}catch(Exception e){logger.error(e.getLocalizedMessage());}
		
	}
	
	//This method 'UPDATE' employee in the employees DATABASE
		public void update(Employee e) {
			try {
				conn = ConnectionManager.getConnection();
				
				//Step 1
				String query = "UPDATE employees SET firstname = ?, lastname = ?, e_password = ? WHERE username = ?";
				PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
				
				//Set the parameters in the query filter value
				statement.setString(1, e.getFirstName());
				statement.setString(2, e.getLastName());
				statement.setString(3, e.getPassword());
				statement.setString(4, e.getUsername().toLowerCase());
				
				//Insert with this method
				statement.execute();
				
			}catch(SQLException ex) {
				logger.debug("Failure to add Customer to database:...  "+ e.getUsername());
				logger.error(ex.getLocalizedMessage());
				}
		}
		
		//This method 'DELETE' customer in the customers DATABASE
		public void delete(Employee e) {
			try {
				conn = ConnectionManager.getConnection();
				
				//Step 1
				String query = "DELETE FROM employees WHERE username = ?";
				PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
				
				//Set the parameters in the query filter value
				statement.setString(1, e.getUsername().toLowerCase());
				
				//Insert with this method
				statement.execute();
				
			}catch(SQLException ex) {
				logger.debug("Failure to add Customer to database:...  "+ e.getUsername());
				logger.error(ex.getLocalizedMessage());
				}
			
		}

}
