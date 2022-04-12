package com.revature.app;

public class Driver {
	public static void main(String[] args) {
		
		//Use the below to declare a new array of 10 numbers
		int[] listOfNumbers = new int[10];
		// [1,2,3,4,5,6,7,8,9,10]
		//Arrays are a fixed length; If you need a bigger array you have to make a new one.
		
		//The below sets the first element of listOfNumbers to 15
		listOfNumbers[0] = 15; //Arrays are zero-indexed, Start at 0 and go up.
		
		//Sets the values for every element in listOfNumbers.
		for(int i = 0; i < listOfNumbers.length; i++) {
			listOfNumbers[i] = i;
		}
		
		//This is called an enhanced for loop. it runs its code for every element in the given data structure
		for(int element: listOfNumbers) {
			Driver.doubleValueandPrint(element, element);
		}
		
	}
	
	//This method multiples the given by by 2 and then prints it
	public static void doubleValueandPrint(int value, int multiplier) {
		System.out.println(value * multiplier);
	}
	
	//This is method overloading. Java determines which method you use based on the parameters we pass.
	public static void doubleValueandPrint(int value) {
		System.out.println(value * 2);
	}

}
