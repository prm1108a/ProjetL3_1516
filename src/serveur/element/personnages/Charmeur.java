package serveur.element.personnages;

import java.util.HashMap;

import serveur.element.Caracteristique;

/**
 * Un charmeur : un element possedant des caracteristiques et etant capable
 * de jouer une strategie. Cette nouvelle sorte de personnage gagne à chaque 
 * itération d'interaction (duel, deplacement ou ramassage) un point de charme.
 * Lorsqu'il a plu de 20 points de charme le duel n’a aucun effet, le charme 
 * téléporte et sa barre charme revient à 0. Sinon, il fait le duel normalement. 
 * Si 2 charmeurs se battent en duel, alors on prend en compte leurs points de charme.
 */

public class Charmeur extends Personnage{

	private static final long serialVersionUID = 1L;

	/**
	 * Cree un personnage charmeur.
	 * @param caracts caracteristiques du personnage
	 */
	public Charmeur(HashMap<Caracteristique, Integer> caracts) {
		super("Charmeur", "G12", caracts);
	}
	
	

}
