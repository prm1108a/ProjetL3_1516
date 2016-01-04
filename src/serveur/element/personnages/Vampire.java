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
public class Vampire extends Personnage {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Cree un personnage vampire avec un nom et un groupe.
	 * @param nom du personnage
	 * @param groupe d'etudiants du personnage
	 * @param caracts caracteristiques du personnage
	 */
	public Vampire(String nom, String groupe, HashMap<Caracteristique, Integer> caracts) {
		super("Vampire", "G12", caracts);
	}
	
}
