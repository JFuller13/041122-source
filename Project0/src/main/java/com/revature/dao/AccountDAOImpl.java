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

import com.revature.model.Account;
import com.revature.model.Customer;
import com.revature.model.JointAccount;

public class AccountDAOImpl implements AccountDAO {
	
	private static final Logger logger = LogManager.getLogger(AccountDAOImpl.class);

	//This creates an instance of our getConnection() method from the connection manager file 
	private Connection conn = ConnectionManager.getConnection();
	
	//this method returns all accounts from the database
	public ArrayList<Account> getAllAccounts(){
		try {
			conn = ConnectionManager.getConnection();
			
			//This creates a new statement called statement that uses a connection to the Accounts database
			Statement statement = conn.createStatement();
					
			//This is the sql statement i want to execute.
			ResultSet rs = statement.executeQuery("Select * From accounts");
					
			//this is our arraylist for storing our results
			ArrayList<Account> accounts = new ArrayList<Account>();
					
			//this loop runs so long as there is another row in rs
			while(rs.next()) {
				int accountNumber = rs.getInt("account_number");
				long routingNumber = rs.getLong("routing_number");
				String accType = rs.getString("account_type");
				double balance = rs.getDouble("balance");
				String primaryOwner = rs.getString("primary_owner");
				int confirmation = rs.getInt("confirmation");
						
				//this adds each Account from the Accounts database to our Account ArrayList<>
				accounts.add(new Account(accountNumber,routingNumber,accType,balance,primaryOwner,confirmation));
			}return accounts;
		}catch(SQLException e) {
			logger.debug("FAILURE TO GRAB ALL ACCOUNTS FROM THE DATABASE...");
			logger.error(e.getLocalizedMessage());
		}return null;

	}
	
	//This method returns all of the account for a specific Customer
	//RETURNING TOO MANY OF THE SAME ACCOUNT FOR JOINT ACCOUNTS
	public ArrayList<Account> getAllAccounts(Customer c){
		conn = ConnectionManager.getConnection();

		
		try {
			//STEP1: Create a new  PreparedStatement that uses a connection to the database
			String query ="Select * FROM accounts WHERE primary_owner = ?";
			PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
			
			//set the parameters for the statement, then execute query
			statement.setString(1, c.getUsername().toLowerCase());
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
			
			
			//STEP 2: FIND THE JOINT ACCOUNTS AND ADD THEM IF THEY EXIST
			String query2 = "select account_number from joint_accounts where co_owner = ?";
			PreparedStatement statement2 = conn.prepareStatement(query2);
			  
			statement2.setString(1, c.getUsername()); 
			ResultSet rs2 = statement2.executeQuery();
			  
			while(rs2.next()) { 
				int acc = rs2.getInt("account_number"); 
				Account found = this.getAccount(acc);
			  
				if(temp.contains(found) == false) { 
					temp.add(found); 
				} 
			}
			 	
			return temp;
			
		}catch(SQLException e) {
			logger.debug("FAILURE WITH RETRIEVING ALL ACCOUNTS FOR CUSTOMER:..."+ c.getUsername());
			logger.error(e.getLocalizedMessage());
			}return null;}

	@Override
	//This method returns a specific account from a specific customer's database
	public Account getAccount(String primaryOwner, int accountNumber) {
		conn = ConnectionManager.getConnection();

		try {
			//Step 1 create a prepared statement 
			String query = "SELECT * FROM Accounts WHERE primary_owner = ? AND account_number = ?";
			PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
			
			//set the user_id parameter in the query filter value (ie the ?) and set the account_number value
			statement.setString(1, primaryOwner.toLowerCase());
			statement.setInt(2, accountNumber);
			ResultSet rs = statement.executeQuery();
			
			//Step 2
			if(rs.next()) {
				int accountNum = rs.getInt("account_number");
				long routingNum = rs.getLong("routing_number");
				String accType = rs.getString("account_type");
				double balance = rs.getDouble("balance");
				String po = rs.getString("primary_owner");
				int confirmation = rs.getInt("confirmation");
			
				Account a = new Account(accountNum, routingNum, accType, balance, po,confirmation);
				return a;
				
			}else return null;	
		}catch(SQLException e) {
			logger.debug("failure getting specific account for Customer:... "+primaryOwner);
			logger.error(e.getLocalizedMessage());}return null;
	}
	
	//This method returns a specific account
	public Account getAccount(int accountNumber) {
		conn = ConnectionManager.getConnection();

		try {
			//Step 1 create a prepared statement 
			String query = "SELECT * FROM Accounts WHERE account_number = ?";
			PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
			
			//set the user_id parameter in the query filter value (ie the ?) and set the account_number value
			statement.setInt(1, accountNumber);
			ResultSet rs = statement.executeQuery();
			
			//Step 2
			if(rs.next()) {
				int accountNum = rs.getInt("account_number");
				long routingNum = rs.getLong("routing_number");
				String accType = rs.getString("account_type");
				double balance = rs.getDouble("balance");
				String po = rs.getString("primary_owner");
				int confirmation = rs.getInt("confirmation");
			
				Account a = new Account(accountNum, routingNum, accType, balance, po,confirmation);
				return a;
				
			}else return null;	
		}catch(SQLException e) {
			logger.debug("Could not retrieve account: Account Number ["+accountNumber+"]");
			logger.error(e.getLocalizedMessage());}return null;
	}

	@Override
	public boolean existAccount(int accountNumber) {
		conn = ConnectionManager.getConnection();

		try {
			//Step 1
			String query = "SELECT * FROM Accounts WHERE account_number = ?";
			PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
			
			//Set the username parameter in the query filter value
			statement.setInt(1, accountNumber);
			ResultSet rs = statement.executeQuery();
			
			//Step 2
			if(rs.next()){return true;}else return false;
			
		}catch(SQLException e) {
			logger.debug("FAILURE AT existAccount() Account Number ["+accountNumber+"]");
			logger.error(e.getLocalizedMessage());}
		return false;
	}
	
	//This method will be used to create a new account for Customer
	public void createAccount(Customer c) {
		conn = ConnectionManager.getConnection();

		Scanner scan = new Scanner(System.in);
		try {
			System.out.println("What type of account to you want to open?\n1)checking\n2)saving\n3)joint");
			int choice = scan.nextInt();
			
			//Checking Account
			if(choice==1) {
				System.out.println("How much do you want to deposit into your checking account starting balance?");
				double amount = scan.nextDouble();
				Account acc;
				
				while(true) {
					acc = new Account("checking",c.getUsername(),Double.parseDouble(String.format("%.2f", amount)));
					if(!this.existAccount(acc.getAccountNumber())) {
						break;
					}
				}
				this.addAccount(acc);
				System.out.println("Checking account created, Waiting on approval...");
				
			}else {
				//Saving account
				if(choice==2) {
					System.out.println("How much do you want to deposit into your saving account starting balance?");
					double amount = scan.nextDouble();
					Account acc = new Account("saving",c.getUsername(),amount);
					
					while(true) {
						acc = new Account("saving",c.getUsername(),Double.parseDouble(String.format("%.2f", amount)));
						if(!this.existAccount(acc.getAccountNumber())) {
							break;
						}
					}
					this.addAccount(acc);
					System.out.println("Saving account created, Waiting on approval...");
				}
				if(choice ==3) {
					//Joint Account
					ArrayList<Customer> applicants = new ArrayList<Customer>();
					applicants.add(c);
					int ppl;
					do {
						System.out.println("How many people will be using this account? MAX: 3 INDIVIDUALS");
						ppl = scan.nextInt();
						if(ppl < 4 && ppl > 1) {break;}
					}while(true);
					
					CustomerDAOImpl customerTable = new CustomerDAOImpl();
					while(ppl !=1) {
						System.out.println("CO-owners please enter your information..");
						System.out.println("Enter your username:");
						String username = scan.next();
						if(customerTable.existCustomer(username)) {
							applicants.add(customerTable.getCustomer(username));
						}else {
							customerTable.registerCustomer();
						}
						ppl--;
					}
					System.out.println("How much do you want to deposit into your joint account starting balance?");
					double amount = scan.nextDouble();
					
					JointAccount acc = new JointAccount("joint",applicants,Double.parseDouble(String.format("%.2f", amount)));
					this.addAccount(acc);
					JointAccountDAOImpl joint = new JointAccountDAOImpl();
					joint.createJointAccount(acc);
					System.out.println("Joint Account created, Waiting on approval...");
				}
			}
		}catch(Exception e) {
			logger.error(e.getLocalizedMessage());
			logger.debug("INVALID INPUT TRY AGAIN...");}
		
	}

	@Override
	//This method INSERT the account into the Account database as PENDING
	public void addAccount(Account a) {
		conn = ConnectionManager.getConnection();

		try {
			//Step 1
			String query = "INSERT INTO Accounts(account_number, routing_number, account_type, balance, primary_owner,confirmation) values(?,?,?,?,?,?)";
			PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
				
			//Set the parameters in the query filter value
			statement.setInt(1, a.getAccountNumber());
			statement.setLong(2, a.getRoutingNumber());
			statement.setString(3, a.getAccountType());
			statement.setDouble(4, a.getBalance());
			statement.setString(5, a.getPrimaryOwner());
			statement.setInt(6, a.getConfirmation());
				
			//Insert with this method
			statement.execute();
				
		}catch(SQLException e) {
			logger.debug("FAILURE ADDING ACCOUNT account number ["+a.getAccountNumber()+"]");
			logger.error(e.getLocalizedMessage());}		
	}
	
	//This method will be used to UPDATE the accounts balance in the database
	public void updateAccount(double amount, int accountNumber) {
		conn = ConnectionManager.getConnection();

		try {
			//Step 1
			String query = "UPDATE accounts SET balance =? WHERE account_number = ?";
			PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
			
			//set the parameters int the query filter value
			statement.setDouble(1, Double.parseDouble(String.format("%.2f", amount)));
			statement.setInt(2, accountNumber);
			
			//Insert with this method
			statement.execute();
		}catch(SQLException e) {
			logger.debug("FAILURE UPDATING ACCOUNT BALANCE... ["+accountNumber+"]");
			logger.error(e.getLocalizedMessage());}
	}
	
	
	
	//This method will get the info needed from the Customer
	public void deposit(Customer c) {
		conn = ConnectionManager.getConnection();

		try {
			for(Account a: c.getMyAccounts()) {
				if(a.getConfirmation() == 1) {
					System.out.println(a.toString());
				}
			}
			int account;
			double amount;
			double oldBalance;
			
			do {
				System.out.println("Which account do you want to deposit into?");
				Scanner scan = new Scanner(System.in);
				account = scan.nextInt();
				Account temp = this.getAccount(account);
				oldBalance = temp.getBalance();
				
			
				System.out.println("How much do you want to deposit?");
				amount = scan.nextDouble();
				amount = Double.parseDouble(String.format("%.2f", amount));
				if(amount > 0) {
					amount += temp.getBalance();
				}else {System.out.println("NO NEGATIVE VALUES..\n");}
			}while(account <100000000 || amount < 0);
			
			this.updateAccount(amount, account);
			logger.info(this.getAccount(account)+" has been updated to "+amount+" from "+oldBalance+"\n");
			
		}catch(Exception e) {
			logger.error(e.getLocalizedMessage());;
			logger.debug("INVALID INPUT...");}
	}
	
	//This method will get the info needed from the customer to perform a WITHDRAW
	public void withdraw(Customer c) {
		conn = ConnectionManager.getConnection();

		try {
			for(Account a: c.getMyAccounts()) {
				if(a.getConfirmation() == 1) {
					System.out.println(a.toString());
				}
			}
			int account;
			double amount;
			double oldBalance;
			
			do {
				System.out.println("Which account do you want to withdraw from?");
				Scanner scan = new Scanner(System.in);
				account = scan.nextInt();
				System.out.println("How much do you want to withdraw?");
				amount = scan.nextDouble();
				amount = Double.parseDouble(String.format("%.2f", amount));
				
				Account temp = this.getAccount(account);
				oldBalance = temp.getBalance();
				
				//CHECK if account is a joint and if the customer is a coOwner
				if(temp.getAccountType().equalsIgnoreCase("joint") && temp.getConfirmation() == 1) {
					JointAccountDAOImpl jaTable = new JointAccountDAOImpl();
					
					if(jaTable.existCoOwner(c.getUsername(), temp.getAccountNumber())) {
						System.out.println("You are a co_owner..\n");
						amount = temp.getBalance() - amount;		
					}else {
						amount = temp.getBalance();
						logger.info("FAILED: SORRY YOU DON'T HAVE ACCESS TO THIS ACCOUNT...");}
				}else {
	
					//This is a normal account
					if(temp.getPrimaryOwner().equalsIgnoreCase(c.getUsername()) && temp.getConfirmation() == 1){
							amount = temp.getBalance() - amount;	
					}else {
						amount = temp.getBalance();
						logger.info("FAILED: You CAN NOT withdraw from this account...");}
				}
			}while(account <100000000 || amount < 0);
			
			this.updateAccount(amount, account);
			logger.info(this.getAccount(account)+" has been updated to "+amount+" from "+oldBalance+"\n");
		}catch(Exception e) {
			logger.error(e.getLocalizedMessage());;
			logger.debug("INVALID INPUT.....");}
	}
	
	//This method will get the info needed from the customer to perform a TRANSFER
	public void transfer(Customer c) {
		conn = ConnectionManager.getConnection();

		try {
			for(Account a: c.getMyAccounts()) {
				if(a.getConfirmation() == 1) {
					System.out.println(a.toString());
				}
			}
			int account;
			double amount;
			
			do {
				System.out.println("Which account do you want to transfer from?");
				Scanner scan = new Scanner(System.in);
				account = scan.nextInt();
				Account sender = this.getAccount(account);
				
				//CHECK if account is a joint and if the customer is a coOwner
				if(sender.getAccountType().equalsIgnoreCase("joint") && sender.getConfirmation() == 1) {
					JointAccountDAOImpl jaTable = new JointAccountDAOImpl();
					
					if(jaTable.existCoOwner(c.getUsername(), sender.getAccountNumber())) {
						System.out.println("You are a co_owner..\n");
							
					}else {
						
						logger.info("FAILED: SORRY YOU DON'T HAVE ACCESS TO THIS ACCOUNT...");
						break;}
				}else {
	
					//This is a normal account
					if(sender.getPrimaryOwner().equalsIgnoreCase(c.getUsername()) && sender.getConfirmation() == 1){
							System.out.println("You are the primary owner...\n");
					}else {
				
						logger.info("FAILED: You CAN NOT withdraw from this account...");
						break;
						}
				}
				
				System.out.println("Which account do you want to transfer to?");
				account = scan.nextInt();
				Account receiver = this.getAccount(account);
				
				if(sender.getAccountNumber() == receiver.getAccountNumber()) {logger.info(c.getUsername()+" TRIED TO USE THE SAME ACCOUNT...\n");break;}
				if(sender.getConfirmation() != 1 || receiver.getConfirmation() != 1) {logger.info("ONE OF THE ACCOUNTS CHOSEN IS EITHER PENDING, DENIED OR CANCELED...\n");break;}
				
				System.out.println("How much do you want to transfer?");
				amount = scan.nextDouble();
				amount = Double.parseDouble(String.format("%.2f", amount));
				
				//Adjust balance for sender
				double senderOldBalance=sender.getBalance();
				double receiverOldBalance = receiver.getBalance();
				double senderAmount = sender.getBalance() - amount;
				double receiverAmount = receiver.getBalance() + amount;
				
				if(senderAmount >0) {
					this.updateAccount(senderAmount, sender.getAccountNumber());
					this.updateAccount(receiverAmount, receiver.getAccountNumber());
					
					logger.info("Account number ["+sender.getAccountNumber()+"] has been updated to "+senderAmount+" from "+senderOldBalance+"\n");
					logger.info("Account number ["+receiver.getAccountNumber()+"] has been updated to "+receiverAmount+" from "+receiverOldBalance+"\n");
				}else {
					logger.info("sender: "+c.getFirstname()+" "+c.getLastname()+" does not have enough to transfer.");
				}
			}while(account <100000000 || amount < 0);
			
		}catch(Exception e) {
			logger.error(e.getLocalizedMessage());;
			logger.debug("INVALID INPUT...");}
	}

	//This method will UPDATE the confirmation status of the account
	public void updateStatus(Account a, int choice) {
		conn = ConnectionManager.getConnection();

		try {
			//Step 1
			String query = "UPDATE accounts SET confirmation =? WHERE account_number = ?";
			PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
			
			//set the parameters in the query filter value
			statement.setInt(1, choice);
			statement.setInt(2, a.getAccountNumber());
			
			//Insert with this method
			statement.execute();
			
			if(a.getAccountType().equalsIgnoreCase("joint")) {
				JointAccountDAOImpl jointAcc = new JointAccountDAOImpl();
				jointAcc.updateStatus(a, choice);
			}
		}catch(SQLException e) {
			logger.debug("FAILURE AT updateStatus... account number ["+a.getAccountNumber()+"]");
			logger.error(e.getLocalizedMessage());}
		
	}
	
	//This method 'DELETE' customer in the customers DATABASE
	public void delete(Account a) {
		try {
			conn = ConnectionManager.getConnection();
			
			//Step 1
			String query = "DELETE FROM accounts WHERE account_number = ?";
			PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
			
			//Set the parameters in the query filter value
			statement.setInt(1, a.getAccountNumber());
			
			//Insert with this method
			statement.execute();
			
		}catch(SQLException ex) {
			logger.debug("Failure to delete account from database:...  Account number: ["+ a.getAccountNumber()+"]");
			logger.error(ex.getLocalizedMessage());
			}
		
	}
		
}
