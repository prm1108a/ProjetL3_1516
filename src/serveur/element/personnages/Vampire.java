package serveur.element.personnages;

import java.util.HashMap;

import serveur.element.Caracteristique;

/**
 * Un vampire: un element possedant des caracteristiques et etant capable
 * de jouer une strategie. Cette nouvelle sorte de personnage permet, 
 * lors d'un duel, de bénéficier des points qu'il retire à son adversaire 
 * (quand il gagne).
 * 
 */
public class Vampire extends Personnage {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Cree un personnage vampire.
	 * @param caracts caracteristiques du personnage
	 */
	public Vampire(HashMap<Caracteristique, Integer> caracts) {
		super("Vampire", "G12", caracts);
	}
	
}
