package serveur.element.potions;

import java.util.HashMap;

import serveur.element.Caracteristique;

/**
 * Une potion bonus : un element qui permet de multiplier les caractéristiques 
 * d'un personnage à chaque incrémentation
 */

public class PotionBonus extends Potion{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur d'une potion bonus avec ses 
	 * caracteristiques (ajoutees lorsqu'un Personnage ramasse cette potion).
	 * @param caracts caracteristiques de la potion
	 */
	public PotionBonus(HashMap<Caracteristique, Integer> caracts) {
		super("Bonus", "G12", caracts);
	}
}
