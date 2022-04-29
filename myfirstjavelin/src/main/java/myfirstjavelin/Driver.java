package myfirstjavelin;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class Driver {
	public static void main(String[] args) {
		
		//Create a javalin server object and start listening on port 7070
		Javalin app = Javalin.create().start(7070);
		
		//This block sets up a listener for get requests to the path /user
		//and, when a request is detected, return hello! in the the body of 
		// the response
		app.get("/user", 
				ctx -> {
					ctx.result("Hello!"); 
					}
				);
	
		/*Can also break up the definition of the listener/ path and the 
		*code to run once a message is recieved by storing the lamba
		*in a variable (which we do on line 25)
		*/
		app.get("/user2", userHandle);
		
		//A listener is just a path+varb combination
		
		/*We can use path parameters, or variables staored in the path,
		 * to create listeners for resources without hard coding the
		 * resources to look
		 * */
		app.get("/user/{id}", ctx -> {
			
			/*
			 * */
			String id = ctx.pathParam("id");
			
			ctx.result("Your id is " + id);
		});
	}
	
	public static Handler userHandle = ctx -> {
		ctx.result("Hello! Check my method");
	};
	
	

}
