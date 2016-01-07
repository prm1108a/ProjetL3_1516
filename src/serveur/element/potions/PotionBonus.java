package serveur.element.potions;

import java.util.HashMap;

import serveur.element.Caracteristique;

public class PotionBonus extends Potion{

	private static final long serialVersionUID = 1L;

	public PotionBonus(HashMap<Caracteristique, Integer> caracts) {
		super("Bonus", "G12", caracts);
	}
}
