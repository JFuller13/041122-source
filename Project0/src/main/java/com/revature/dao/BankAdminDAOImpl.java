package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.model.Account;
import com.revature.model.BankAdmin;
import com.revature.model.Employee;

public class BankAdminDAOImpl implements BankAdminDAO{
public static final Logger logger = LogManager.getLogger(BankAdminDAOImpl.class);
	Connection conn = ConnectionManager.getConnection();
	
	public BankAdmin getEmployee(String username) {

		conn = ConnectionManager.getConnection();
		
		try {
			//Step 1
			String query = "SELECT * FROM employees WHERE username = ?";
			PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
				
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
				
				BankAdmin badmin = new BankAdmin(firstname, lastname, un, pw);
				return badmin;
					
			}else return null;
				
		}catch(SQLException e) {logger.error(e.getLocalizedMessage());}return null;
	}

}
