package serveur.element.personnages;

import java.util.HashMap;

import serveur.element.Caracteristique;

/**
 * Un assassin: un element possedant des caracteristiques et etant capable
 * de jouer une strategie. Cette nouvelle sorte de personnage permet,
 * lors d’un duel, si sa force est supérieure à celle de son adversaire 
 * alors il le tue son adversaire.
 */

public class Assassin extends Personnage{

	private static final long serialVersionUID = 1L;

	/**
	 * Cree un personnage assassin.
	 * @param caracts caracteristiques du personnage
	 */
	public Assassin(HashMap<Caracteristique, Integer> caracts) {
		super("Assassin", "G12", caracts);
	}

}
