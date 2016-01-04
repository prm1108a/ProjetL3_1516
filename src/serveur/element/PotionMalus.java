package serveur.element;

import java.util.HashMap;

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
