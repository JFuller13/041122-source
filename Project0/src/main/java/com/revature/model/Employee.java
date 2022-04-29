package com.revature.model;

public class Employee {
	private int idNumber;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	
	//Constructors
	public Employee(String firstName, String lastName, String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
	}
	
	public Employee() {
		idNumber = 1;
		firstName = "FIRST";
		lastName = "LAST";
		username = "UNAME";
		password = "PASS";
	}
	
	//getter methods
	public int getIdNumber() {return idNumber;}
	public String getFirstName() {return firstName;}
	public String getLastName() {return lastName;}
	public String getUsername() {return username;}
	public String getPassword() {return password;}
	
	//setter methods
	public void setIdNumber(int num) {this.idNumber = num;}
	public void setFirstName(String firstName) {this.firstName = firstName;}
	public void setLastName(String lastName) {this.lastName = lastName;}
	public void setUsername(String username) {this.username = username;}
	public void setPassword(String password) {this.password = password;}

	@Override
	public String toString() {
		return "Employee [idNumber=" + idNumber + ", firstName=" + firstName + ", lastName=" + lastName + ", username="
				+ username + ", password=" + password + "]";
	}
	
	
	
}
