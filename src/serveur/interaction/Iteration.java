package serveur.interaction;

import serveur.element.Caracteristique;
import serveur.element.personnages.Charmeur;
import serveur.element.personnages.Personnage;
import serveur.vuelement.VuePersonnage;

/**
 *
 */
public class Iteration {
	
	/**
	 * Vue de l'attaquant, c'est-a-dire le personnage faisant l'action.
	 */
	protected VuePersonnage attaquant; 
	
	public Iteration(VuePersonnage personnage) {
		attaquant = personnage;
	}

	/**
	 * Charme le personnage
	 */
	protected void addCharme (){
		Personnage pattaquant = attaquant.getElement();
		if (pattaquant instanceof Charmeur){
			pattaquant.incrementeCaract(Caracteristique.CHARME, 1);
		}	
	}
	
	protected boolean checkFreeze(){
		Personnage pattaquant = attaquant.getElement();
		int f = pattaquant.getFreeze();
		if (f > 0 && f <= 5){
			pattaquant.setFreeze(++f);
			System.out.println("f = " + f);
			return false;
		}
		else if (f > 5){
			pattaquant.setFreeze(-1);
			System.out.println("f > 5 ");
		}
		return true;
	}
	
	
}

