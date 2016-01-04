package serveur.element.potions;

import java.util.HashMap;

import serveur.element.Caracteristique;

public class PotionMalus extends Potion{

	private static final long serialVersionUID = 1L;
	
	private int pointsARetirer = 10;

	public PotionMalus(String nom, String groupe, HashMap<Caracteristique, Integer> caracts) {
		super("Malus", "G12", caracts);
	}
	
	public int getPointsARetirer(){
		return pointsARetirer;
	}

}
