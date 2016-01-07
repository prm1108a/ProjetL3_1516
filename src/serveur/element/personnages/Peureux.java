package serveur.element.personnages;

import java.util.HashMap;

import serveur.element.Caracteristique;

/**
 * Un peureux: un element possedant des caracteristiques et etant capable
 * de jouer une strategie. Cette nouvelle sorte de personnage permet 
 * de fuir tous ses voisins, m�me les potions car il existe un personnage 
 * Diable qui modifie les caract�ristiques des potions pour les rendre plus 
 * n�fastes.
 */

public class Peureux extends Personnage {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Cree un personnage peureux.
	 * @param caracts caracteristiques du personnage
	 */
	public Peureux(HashMap<Caracteristique, Integer> caracts) {
		super("Peureux", "G12", caracts);
	}
	
}
