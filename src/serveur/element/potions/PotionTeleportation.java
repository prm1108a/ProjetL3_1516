package serveur.element.potions;

import java.util.HashMap;

import serveur.element.Caracteristique;

public class PotionTeleportation extends Potion{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PotionTeleportation( HashMap<Caracteristique, Integer> caracts) {
		super("Teleportation", "G12", caracts);
	}

}
