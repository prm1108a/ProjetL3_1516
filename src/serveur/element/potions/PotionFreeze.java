package serveur.element.potions;

import java.util.HashMap;

import serveur.element.Caracteristique;

/**
 * Une potion freeze : un element qui permet d'immobiliser 
 * le personnage qui rammasse cet élément 
 */

public class PotionFreeze extends Potion{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur d'une potion freeze avec ses 
	 * caracteristiques (ajoutees lorsqu'un Personnage ramasse cette potion).
	 * @param caracts caracteristiques de la potion
	 */
	public PotionFreeze(HashMap<Caracteristique, Integer> caracts) {
		super("Freeze", "G12", caracts);
	}
}
