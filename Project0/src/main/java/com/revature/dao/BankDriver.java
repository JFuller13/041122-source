package com.revature.dao;

import java.util.ArrayList;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.controller.AccountController;
import com.revature.controller.CustomerController;
import com.revature.controller.EmployeeController;
import com.revature.model.Account;
import com.revature.model.BankAdmin;
import com.revature.model.Customer;
import com.revature.model.Employee;
import com.revature.model.JointAccount;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class BankDriver {
	
	//THESE ARE GOING TO BE MY CORE OBJECTS FOR MY PROGRAM
	static boolean terminate = false;
	static CustomerDAOImpl customerTable = new CustomerDAOImpl();
	static AccountDAOImpl accountTable = new AccountDAOImpl();
	static JointAccountDAOImpl jointAccountTable = new JointAccountDAOImpl();
	static EmployeeDAOImpl employeeTable = new EmployeeDAOImpl();
	static BankAdminDAOImpl adminTable = new BankAdminDAOImpl();
	static Customer currentUser;
	static Employee employee;
	static BankAdmin manager;
	private static final Logger logger = LogManager.getLogger(BankDriver.class);

	
	public static void main(String[] args) {
		
		Javalin app = Javalin.create(config -> {config.addStaticFiles("/html",Location.CLASSPATH);}).start(7070);
		//Javalin app = Javalin.create().start(7070);
		CustomerController  customerController = new CustomerController(app);
		EmployeeController employeeController = new EmployeeController(app);
		AccountController accController = new AccountController(app);
		
		System.out.println("Welcome to Fuller Banking Inc.\nChoose one of the options below.");		
		Scanner scan;
		String username;
		String password;
		
		while(!terminate) {
			
			//Prompt the login in menu
			int choice=9;
			int menuChoice=9;
			
			do {
				try {
					System.out.println("1)Login\n2)Register\n0)Quit");
					scan = new Scanner(System.in);
					choice = scan.nextInt();
					if(choice >= 0 && choice < 3) {
						if(choice == 0) {terminate = true;break;}
						else {
							if(choice == 1) {
								System.out.println("Enter your Username:");
								username = scan.next();
								System.out.println("Enter your password");
								password = scan.next();
								
								if(customerTable.logIn(username, password)) {
									currentUser = customerTable.getCustomer(username);
									System.out.println("welcome, " + currentUser.getFirstname());
									menuChoice=3; //which menu to display
									break;
								}else {
									if(employeeTable.login(username, password)) {
										if(employeeTable.checkAuthority(username,password)==false) {
											employee = employeeTable.getEmployee(username);
											System.out.println("Welcome employee: " + employee.getFirstName());
											menuChoice=1; //which menu to display
											break;
										}else {
											manager = adminTable.getEmployee(username);
											System.out.println("Welcome administrator: " + manager.getFirstName());
											menuChoice=2; //which menu to display
											break;
										}
										
									}else {System.out.println("WRONG USERNAME OR WRONG PASSWORD!!! TRY AGAIN\n");}
								}
							}
							if(choice == 2) {
								currentUser = customerTable.registerCustomer();
								menuChoice=3;
								break;
							}
						}
					}
					System.out.println("USE THE COMMANDS PROVIDED...\n");
				}catch(Exception e) {
					logger.error(e.getLocalizedMessage());
					logger.debug("Failure at Login / Register..");}
			}while(true);
			
			//END PROGRAM
			if(terminate) {break;}
			
			//INSIDE APP
			//Employee Menu
			if(menuChoice ==1) {
				System.out.println("Employee menu display...");
				do {
					try {
						System.out.println("What action do you want to perform\n1)View Customer Information\n2)Approve/Deny Accounts\n3)Logout");
						scan = new Scanner(System.in);
						choice = scan.nextInt();
						
						switch(choice) {
							case 1:
								System.out.println("What is the name of the customer that you want to view?");
								String viewing = scan.next();
								employeeTable.viewCustomerInfo(viewing);
								break;
							case 2:
								employeeTable.checkPendingAccounts();
								break;
							case 3:
								System.out.println("YOU HAVE LOGGED OUT GOOD BYE!");
								break;
							default :
								System.out.println("YOU DIDNT MAKE THE RIGHT CHOICE");	
						}
						if(choice==3) {break;}
						
					}catch(Exception e) {
						logger.debug("INVALID INPUT TRY AGAIN...");
						logger.error(e.getLocalizedMessage());}					
				}while(true);
			}
			//Manager Menu
			if(menuChoice ==2) {
				System.out.println("Manager menu display...");
				do {
					//NEED TO FIX THIS>>>>>>>
					try {
						System.out.println("What action do you want to perform\n1)View Customer Information\n2)Approve/Deny Accounts\n3)Edit Accounts\n4)Cancel Accounts\n5)Logout");
						scan = new Scanner(System.in);
						choice = scan.nextInt();
						
						switch(choice) {
							case 1:
								System.out.println("What is the name of the customer that you want to view?");
								String viewing = scan.next();
								employeeTable.viewCustomerInfo(viewing);
								break;
							case 2:
								employeeTable.checkPendingAccounts();
								break;
							case 3:
								System.out.println("Which customer's account do you want to edit?");
								String editing = scan.next();
								employeeTable.editAccounts(editing);
								break;
							case 4:
								System.out.println("Which account do you want to cancel?");
								ArrayList<Account> allAccounts = accountTable.getAllAccounts();
								for(Account a: allAccounts) {
									if(a.getConfirmation() != 3) {
										System.out.println(a.toString());}
									}
								int cancelThis = scan.nextInt();
								accountTable.updateStatus(accountTable.getAccount(cancelThis), 3);
								System.out.println("Account Cancellaction in progress...\nFINISHED!\nAccount number ["+accountTable.getAccount(cancelThis)+"] has been cancelled...");
								break;
							case 5:
								System.out.println("YOU HAVE LOGGED OUT GOOD BYE!");
								break;
							default :
								System.out.println("YOU DIDNT MAKE THE RIGHT CHOICE");	
						}
						if(choice==5) {break;}
						
					}catch(Exception e) {
						logger.debug("INVALID INPUT TRY AGAIN...");
						logger.error(e.getLocalizedMessage());}
				}while(true);
			}
			//Customer Menu
			if(menuChoice ==3) {
			do {
				currentUser = customerTable.getCustomer(currentUser.getUsername());
				try {
					System.out.println("Choose what transaction you want to perform...\n1)Open New Account\n2)Deposit\n3)Withdraw\n4)Transfer\n5)Display Accounts\n6)Logout");
					scan = new Scanner(System.in);
					choice = scan.nextInt();
					switch(choice) {
					case 1:
						accountTable.createAccount(currentUser);
						break;
					case 2:
						accountTable.deposit(currentUser);
						break;
					case 3:
						accountTable.withdraw(currentUser);
						break;
					case 4:
						accountTable.transfer(currentUser);
						break;
					case 5:
						System.out.println("Displaying your acounts...");
						for(Account a: currentUser.getMyAccounts()) {
							if(a.getConfirmation() == 1) {System.out.println(a.toString());}
						}
						break;
					case 6:
						System.out.println("YOU HAVE LOGGED OUT, GOOD BYE!");
						break;
					default:
						System.out.println("YOU DIDNT MAKE THE RIGHT CHOICE...");
				}
				if(choice == 6) {break;}
				}catch(Exception e) {
					logger.debug("Invalid input please use the commands provided");
					logger.error(e.getLocalizedMessage());}
			}while(true);
			}
			
			//AFTER session DONE
			currentUser=null;
			employee=null;
			manager=null;
		}
	}

}