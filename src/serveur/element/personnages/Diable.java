package serveur.element.personnages;

import java.util.HashMap;

import serveur.element.Caracteristique;

/**
 * Un diable: un element possedant des caracteristiques et etant capable
 * de jouer une strategie. Cette nouvelle sorte de personnage permet 
 * de modifier les caract�ristiques des potions et les fait passer en 
 * n�gatif.
 */

public class Diable extends Personnage{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Cree un personnage diable.
	 * @param caracts caracteristiques du personnage
	 */
	public Diable(HashMap<Caracteristique, Integer> caracts) {
		super("Diable", "G12", caracts);
	}

}
