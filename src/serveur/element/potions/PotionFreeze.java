package serveur.element.potions;

import java.util.HashMap;

import serveur.element.Caracteristique;

public class PotionFreeze extends Potion{

	private static final long serialVersionUID = 1L;

	public PotionFreeze(String nom, String groupe, HashMap<Caracteristique, Integer> caracts) {
		super("Freeze", "G12", caracts);
	}
}
