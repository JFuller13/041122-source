package com.revature.ex;

import java.util.Scanner;

public class Driver {
	public static void main(String[] args) {
		
		//this is an example of a try catch block
		//The try block executes a line of code for the sake of testing for errors
		try {
			//block of code we want to try
			int[] partyPokemon = {1,2,3,4,5,6};
			System.out.println(partyPokemon[4]);
		}
		
		//the exception we want to catch in this case an exception and assign it to variable e
		catch(Exception e) {
			System.out.println("a party can one have 6 pokemon.");
		}
		
		//the finally statement executes a line of code after the try/catch blocks, no matter what the results of the try/catch was
		finally {
			System.out.println("the finally statement has run successfuly");
		}
		
		//This is out new scanner called userObj for getting user input
		Scanner userObj = new Scanner(System.in);
		//This prompts the user for an input
		System.out.println("How many pokemon are in your party?");
		int party = userObj.nextInt();
		myException.checkPartySize(party);
		
		
		int myInput;
		try {
			System.out.println("What is your favorite number?");
			myInput = userObj.nextInt();
		}catch(Exception e) {
			System.out.println("The data entered is invalid. please enter a number.");
		}

	}
}
