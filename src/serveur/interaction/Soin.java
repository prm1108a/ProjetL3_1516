/**
 * 
 */
package serveur.interaction;

import java.rmi.RemoteException;
import java.util.logging.Level;

import serveur.Arene;
import serveur.element.Caracteristique;
import serveur.vuelement.VuePersonnage;
import serveur.vuelement.VuePotion;

/**
 * @author MarieP
 *
 */
public class Soin extends Interaction<VuePotion>{

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
