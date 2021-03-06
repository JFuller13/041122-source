package com.revature.controller;

import com.revature.dao.CustomerDAOImpl;
import com.revature.model.Customer;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class CustomerController {
	
	CustomerDAOImpl dao;
	
	public CustomerController(Javalin app) {
		
		dao = new CustomerDAOImpl();
		
		app.get("/customers/{username}", getHandler);
		app.post("/customers", postHandler);
		app.put("/customers/{username}", putHandler);
		app.delete("/customers/{username}", deleteHandler);
		
		
		//TESTING FORM on post...
		//app.post("/signup", postHandler2);
		app.put("/editcustomer/{username}", putHandler2);
	}
	
	public Handler getHandler = ctx -> {
		
		//Get the path's customer you are trying to request
		String customerUsername = ctx.pathParam("username");
		
		//Get the customer based on the username
		Customer customer = dao.getCustomer(customerUsername);
		
		/*Turns the given Java object into a JSON format,
		 * which is the text format understood by almost everybody and
		 * can be used in any system running any technology
		 * think of JSON as a universal data transfer format
		 * 
		 * */
		ctx.json(customer);
	};
	
	//This is the 'create' command
	public Handler postHandler = ctx -> {
		
		//Turn the body of the request from JSON into a real java object
		Customer customer = ctx.bodyAsClass(Customer.class);
		
		dao.addCustomer(customer);;
		
		//AS best practice have status codes
		ctx.status(205);
	};
	
	//This is the 'UPDATE' command
	public Handler putHandler = ctx -> {
		Customer customer = ctx.bodyAsClass(Customer.class);
		
		dao.update(customer);
		
		ctx.status(210);
	};
	
	//This is the 'DELETE' command
	public Handler deleteHandler = ctx-> {
		Customer customer = ctx.bodyAsClass(Customer.class);
		
		dao.delete(customer);
		
		ctx.status(200);
	};
	
	//This is the 'create' command, but in a Form
	public Handler postHandler2 = ctx -> {
		Customer c = new Customer();
		
		c.setUsername(ctx.formParam("username"));
		c.setPassword(ctx.formParam("password"));
		c.setfirstname(ctx.formParam("firstname"));
		c.setLastname(ctx.formParam("lastname"));
		
		CustomerDAOImpl dao = new CustomerDAOImpl();
		dao.addCustomer(c);
		
		ctx.status(205);
	};
	
	//This is the 'UPDATE' command in a form
	public Handler putHandler2 = ctx -> {
		Customer customer = ctx.bodyAsClass(Customer.class);
			
		dao.update(customer);
			
		ctx.status(210);
	};

}
