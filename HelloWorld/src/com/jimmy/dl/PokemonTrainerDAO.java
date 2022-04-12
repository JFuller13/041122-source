package com.jimmy.dl;

public class PokemonTrainerDAO {

	//created a method using the PokemonTrainer model
	public static PokemonTrainer addTrainer() {
		
		//This line initializes an instance of the PokemonTrainer model class
		PokemonTrainer pokemonTrainer = new PokemonTrainer();
		
		pokemonTrainer.firstName = "Jimmy";
		pokemonTrainer.lastName = "Fuller";
		pokemonTrainer.age = 27;
		pokemonTrainer.typeFavorite = "plant";
		pokemonTrainer.badges = 2;
		
		System.out.println(pokemonTrainer);
		return pokemonTrainer;
	}
}
