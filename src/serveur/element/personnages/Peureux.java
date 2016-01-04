/**
 * 
 */
package serveur.element.personnages;

import java.util.HashMap;

import serveur.element.Caracteristique;

/**
 * Un vampire: un element possedant des caracteristiques et etant capable
 * de jouer une strategie.
 * 
 */
public class Peureux extends Personnage {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Cree un personnage peureux
	 * @param nom du personnage
	 * @param groupe d'etudiants du personnage
	 * @param caracts caracteristiques du personnage
	 */
	public Peureux(String nom, String groupe, HashMap<Caracteristique, Integer> caracts) {
		super("Peureux", "G12", caracts);
	}
	
}
