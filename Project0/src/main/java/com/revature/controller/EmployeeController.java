package com.revature.controller;

import com.revature.dao.EmployeeDAOImpl;
import com.revature.model.Employee;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class EmployeeController {
	EmployeeDAOImpl dao;

	public EmployeeController(Javalin app) {
		
		dao = new EmployeeDAOImpl();
		
		app.get("/employees/{username}", getHandler);
		app.post("/employees", postHandler);
		app.put("/employees/{username}", putHandler);
		app.delete("/employees/{username}", deleteHandler);
	}
	
	// this is the 'GET' command
	public Handler getHandler = ctx -> {
		
		//Get the path's username
		String eUsername = ctx.pathParam("username");
		
		//Get the user based on the username
		Employee e = dao.getEmployee(eUsername);
		
		/*Turns the given Java object into a JSON format,
		 * which is the text format understood by almost everybody and
		 * can be used in any system running any technology
		 * think of JSON as a universal data transfer format
		 * 
		 * */
		ctx.json(e);
		
	};
	
	//This is the 'create' command
	public Handler postHandler = ctx -> {
		
		//Turn the body of the request from JSON into a real java object
		Employee employee = ctx.bodyAsClass(Employee.class);
		
		dao.createEmployee(employee);
		
		//AS best practice have status codes
		ctx.status(201);
	};
	
	public Handler putHandler = ctx -> {
		Employee e = ctx.bodyAsClass(Employee.class);
		
		dao.update(e);
	};
	
	public Handler deleteHandler = ctx-> {
		Employee user = ctx.bodyAsClass(Employee.class);
		
		dao.delete(user);
		
		ctx.status(200);
	};

}
