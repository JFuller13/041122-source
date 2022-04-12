package com.revature.ex;

public class MyDriver extends Exception{
	MyDriver(){
		super();
		
	}
	
	
	public static void main(String[] args) {
		
		MyDriver d = new MyDriver();
		
		System.out.println(d.toString());

		
	}
}
