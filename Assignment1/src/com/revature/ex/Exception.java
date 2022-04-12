package com.revature.ex;

public class Exception {
	
	int quantity;
	static String name;
	
	Exception(int num, String s){
		quantity = num;
		name = s;
	}
	
	Exception(){
		quantity = 5;
		name = "Library";
		
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name + " Quantity: "+ quantity;
	}
	
}
