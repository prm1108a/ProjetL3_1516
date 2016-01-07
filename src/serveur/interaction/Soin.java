package serveur.interaction;

import java.rmi.RemoteException;
import java.util.logging.Level;

import serveur.Arene;
import serveur.element.Caracteristique;
import serveur.vuelement.VuePersonnage;
import serveur.vuelement.VuePotion;

/**
 * Represente le soin d'un personnage : il recoit 5 points de vie.
 *
 */

public class Soin extends Interaction<VuePotion>{

	/**
	 * Cree une interaction de soin.
	 * @param arene arene
	 * @param attaquant personnage recevant un soin
	 */
	public Soin(Arene arene, VuePersonnage attaquant) {
		super(arene, attaquant, null);
	}
	
	/**
	 * Realise l'interaction.
	 */
	public void interagit(){
		try{
			arene.incrementeCaractElement(attaquant, Caracteristique.VIE, 5);
		} catch (RemoteException e){
			logs(Level.INFO, "\n Erreur soin : " + e.toString());
		}
		
	}

}
