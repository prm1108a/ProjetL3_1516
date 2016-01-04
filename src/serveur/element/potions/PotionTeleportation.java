package serveur.element.potions;

import java.util.HashMap;

import serveur.element.Caracteristique;

public class PotionTeleportation extends Potion{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PotionTeleportation(String nom, String groupe, HashMap<Caracteristique, Integer> caracts) {
		super("T�l�portation", "G12", caracts);
	}

}
