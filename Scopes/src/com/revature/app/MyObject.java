package com.revature.app;

public class MyObject {
	
	//Static fields belong to the class and not any instance.
	//So any change to a static field is reflected by all instances of the class.
	static String name = "My Object";
	
	//Any non-static fields will carry data only for each individual instance.
	public int number = 0;
	
	public void printName() {
		System.out.println(name + ": " + number);
	}
	
	public void growNumber() {
		
		//Any variables declared at the top level of a method are in METHOD scope - they belong to each
		//unique invocation of the method,
		int multiplier = 2;
		
		if(multiplier > 1) {
			
			//This variable is in BLOCK scope, and cannot be used outside of this
			int y = multiplier*2;
			
			number += y;
		}
	}
	
	//Regarding Var args
	//in order to use the below, args must first be set
	public void testArray(String[] args) {
		for(String s : args) {
			System.out.println(s);
		}
	}
	
	public void testVarArgs(String... args) {
		for(String s : args) {
			System.out.println(s);
		}
	}

}