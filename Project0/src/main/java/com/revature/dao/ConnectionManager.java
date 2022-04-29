package com.revature.dao;

import java.sql.Connection;
import java.sql.DriverManager;

//This is the class that will be used to make a connection to the database
public class ConnectionManager {
	
	//lowercase connection is the NAME, Connection with a capital 'C' is the DATATYPE
	private static Connection connection;
	
	//This is our connection data
	/*	type  ''jdbc:postgreql://'' then copy and paste the server from elephantsql 
	 * in your profile. and then add 5432 at the end, then and your user & default database
	 */
	private static String connectionString = "jdbc:postgresql://salt.db.elephantsql.com:5432/wrgsmvho",
			username = "wrgsmvho",
			password = "zMHsCbkdCDMTmKre3nOqfq2NgYQA_rQ2";
	
	public static Connection getConnection() {
		//this will not compile without being in a try catch block
		try {
			if(connection == null || connection.isClosed()) {
				Class.forName("org.postgresql.Driver");
			
				//this says to use the DriveManager to make sure there is a suitable driver to make the connection string word
				//it will call the postgre driver top set up the output/input io stream to pass the data between the application and the database.
				connection = DriverManager.getConnection(connectionString, username,password);
			}
			return connection;
		}catch(Exception e) {}return null;
	}
}
