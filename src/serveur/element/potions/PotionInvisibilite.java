package serveur.element.potions;

import java.util.HashMap;

import serveur.element.Caracteristique;

public class PotionInvisibilite extends Potion{

	private static final long serialVersionUID = 1L;

	public PotionInvisibilite(String nom, String groupe, HashMap<Caracteristique, Integer> caracts) {
		super("Invisibilit�", "G12", caracts);
	}
}
