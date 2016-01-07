package serveur.element.potions;

import java.util.HashMap;

/**
 * Une potion t�l�portation : un �l�ment qui permet de t�l�porter
 * le personnage qui ramasse cet �l�ment al�atoirement sur l'ar�ne
 */

import serveur.element.Caracteristique;

/**
 * Une potion teleportation : un element qui permet de teleporter
 * un personnage.
 */

public class PotionTeleportation extends Potion{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur d'une potion teleportation avec ses 
	 * caracteristiques (ajoutees lorsqu'un Personnage ramasse cette potion).
	 * @param caracts caracteristiques de la potion
	 */
	public PotionTeleportation( HashMap<Caracteristique, Integer> caracts) {
		super("Teleportation", "G12", caracts);
	}

}
