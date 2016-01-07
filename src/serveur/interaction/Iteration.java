package serveur.interaction;

import serveur.element.Caracteristique;
import serveur.element.personnages.Charmeur;
import serveur.element.personnages.Personnage;
import serveur.vuelement.VuePersonnage;

/**
 * Represente une iteration, c'est a dire un deplacement, un ramassage ou un duel.
 *
 */
public class Iteration {
	
	/**
	 * Vue de l'attaquant, c'est-a-dire le personnage faisant l'action.
	 */
	protected VuePersonnage attaquant; 
	
	/**
	 * Cree une iteration d'interaction.
	 * @param personnage personnage
	 */
	public Iteration(VuePersonnage personnage) {
		attaquant = personnage;
	}

	/**
	 * Permet d'ajouter un point de charme au charmeur a chaque iteration
	 * d'une interaction
	 */
	protected void addCharme (){
		Personnage pattaquant = attaquant.getElement();//recuperation du personnage
		if (pattaquant instanceof Charmeur){
			//incremente la caracteristique charme de 1 point
			pattaquant.incrementeCaract(Caracteristique.CHARME, 1);
		}	
	}
	
	/**
	 * Permet de mettre a jour l'attribut freeze du personnage et
	 * d'incrementer le nombre d'iteration pour immobiliser un personnage 
	 * durant 5 iterations.
	 */
	protected boolean checkFreeze(){
		Personnage pattaquant = attaquant.getElement();//recuperation du personnage
		int f = pattaquant.getFreeze();//recuperation de l'attribut freeze du personnage
		//si le personnage est immoblise pendant 5 iterations
		if (f > 0 && f <= 5){
			//on incremente le compteur de freeze (attribut freeze du personnage)
			pattaquant.setFreeze(++f);
			return false;
		}
		//sinon retour au mode deplacement
		else if (f > 5){
			//l'attribut freeze du personnage reprends la valeur -1
			pattaquant.setFreeze(-1);
		}
		return true;
	}
	
	
}

