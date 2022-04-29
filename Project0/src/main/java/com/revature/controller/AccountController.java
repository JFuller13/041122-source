package com.revature.controller;

import com.revature.dao.AccountDAOImpl;
import com.revature.model.Account;

import io.javalin.Javalin;
import io.javalin.http.Handler;


public class AccountController {
	AccountDAOImpl dao;

	public AccountController(Javalin app) {
		
		dao = new AccountDAOImpl();
		
		app.get("/accounts/{accountNumber}", getHandler);
		app.post("/accounts", postHandler);
		app.put("/accounts/{accountNumber}", putHandler);
		app.delete("/accounts/{accountNumber}", deleteHandler);
		
	}
	
	// this is the 'GET' command
	public Handler getHandler = ctx -> {
		
		//Get the path's identifier f
		int accountNumber = Integer.parseInt(ctx.pathParam("accountNumber"));
		
		//Get the account based on the accountNumber
		Account user = dao.getAccount(accountNumber);
		
		/*Turns the given Java object into a JSON format,
		 * which is the text format understood by almost everybody and
		 * can be used in any system running any technology
		 * think of JSON as a universal data transfer format
		 * 
		 * */
		ctx.json(user);	
	};
	
	//This is the 'CREATE' command
	public Handler postHandler = ctx -> {
		
		//Turn the body of the request from JSON into a real java object
		Account a = ctx.bodyAsClass(Account.class);
		
		dao.addAccount(a);
		
		//AS best practice have status codes
		ctx.status(201);
	};
	
	//This is the 'UPDATE' command
	public Handler putHandler = ctx -> {
		Account a = ctx.bodyAsClass(Account.class);
		
		dao.updateAccount(0,a.getAccountNumber());
	};
	
	//////////////////// FIX THIS //////////////////
	
	//This is the 'DELETE' command
	public Handler deleteHandler = ctx-> {
		Account account = ctx.bodyAsClass(Account.class);
		
		dao.delete(account);
		
		ctx.status(200);
	};

}
