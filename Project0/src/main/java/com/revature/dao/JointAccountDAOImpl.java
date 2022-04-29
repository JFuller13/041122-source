package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.model.Account;
import com.revature.model.Customer;
import com.revature.model.JointAccount;

public class JointAccountDAOImpl implements JointAccountDAO{
	private static final Logger logger = LogManager.getLogger(JointAccountDAOImpl.class);
	private Connection conn = ConnectionManager.getConnection();

	@Override
	//This method will INSERT the JointAccount into the JointAccount database and into the Account database as PENDING
	public void createJointAccount(JointAccount a) {
		conn = ConnectionManager.getConnection();

		try {
			
			//Step 1 Add these accounts to the JointAccount database 
			String query = "INSERT INTO joint_accounts(account_number, primary_owner, co_owner, confirmation_status) values(?,?,?,?)";
			PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
				
			//Set the parameters in the query filter
			for(Customer c: a.getOwners()) {
				statement.setInt(1, a.getAccountNumber());
				statement.setString(2, a.getPrimaryOwner());
				statement.setString(3, c.getUsername());
				statement.setInt(4, 0);
				
				//Insert with this method
				statement.execute();
			}
				
		}catch(SQLException e) {logger.error(e.getLocalizedMessage());}
	}

	@Override
	public void updateStatus(Account a, int choice) {
		conn = ConnectionManager.getConnection();
		
		try {
			
			//STEP1
			String query = "UPDATE joint_accounts SET confirmation_status = ? where account_number =?";
			PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
			
			statement.setInt(1, choice);
			statement.setInt(2, a.getAccountNumber());
			
			statement.execute();
		}catch(SQLException e) {logger.error(e.getLocalizedMessage());}
	}
	
	public boolean existCoOwner(String user, int accNum) {
		conn = ConnectionManager.getConnection();
		
		try {
			String query = "SELECT co_owner FROM joint_accounts WHERE account_number = ?";
			PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
			
			statement.setInt(1, accNum);
			
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				String c = rs.getString("co_owner");
				if(c.equalsIgnoreCase(user)) {
					return true;
				}
			}return false;
		}catch(SQLException e) {logger.error(e.getLocalizedMessage());} return false;
	}

}
