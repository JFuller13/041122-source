package hello;

import com.jimmy.dl.PokemonTrainerDAO;

public class helloMain {
	
	int x;
	
	//This is a constructor that creates an instance of the helloMain class.
	public helloMain() {
		x=5;
	}

	//We created a method that is static with a void return type called myMethod.
	static void myMethod() {
		System.out.println("My cat is fluffy");
	}

	public static void main(String[] args) {
		
		//We are calling out method.
		PokemonTrainerDAO.addTrainer();
	}
	
}