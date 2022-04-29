package com.revature.dao;

import com.revature.model.Employee;

public interface EmployeeDAO {
	public boolean login(String username, String password); //CHECKED
	
	public Employee getEmployee(String username); //CHECKED
	
	public boolean checkAuthority(String username, String password); //CHECKED
	
	public void viewCustomerInfo(String c); //CHECKED
	
	public void checkPendingAccounts(); //CHECKED
	
	public void editAccounts(String name); //CHECKED	
}
