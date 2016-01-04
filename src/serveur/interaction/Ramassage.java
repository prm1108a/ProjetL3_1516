package serveur.interaction;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.logging.Level;

import serveur.Arene;
import serveur.element.Caracteristique;
import serveur.element.personnages.Diable;
import serveur.element.potions.PotionInvisibilite;
import serveur.element.potions.PotionMalus;
import serveur.element.potions.PotionTeleportation;
import serveur.vuelement.VuePersonnage;
import serveur.vuelement.VuePotion;
import utilitaires.Calculs;
import utilitaires.Constantes;

/**
 * Represente le ramassage d'une potion par un personnage.
 *
 */
public class Ramassage extends Interaction<VuePotion> {

	/**
	 * Cree une interaction de ramassage.
	 * @param arene arene
	 * @param ramasseur personnage ramassant la potion
	 * @param potion potion a ramasser
	 */
	public Ramassage(Arene arene, VuePersonnage ramasseur, VuePotion potion) {
		super(arene, ramasseur, potion);
	}

	@Override
	public void interagit() {
		try {
			logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " essaye de rammasser " + 
					Constantes.nomRaccourciClient(defenseur));
			
			// si le personnage est vivant
			if(attaquant.getElement().estVivant()) {

				// caracteristiques de la potion
				HashMap<Caracteristique, Integer> valeursPotion = defenseur.getElement().getCaracts();
				
				for(Caracteristique c : valeursPotion.keySet()) {
					arene.incrementeCaractElement(attaquant, c, valeursPotion.get(c));
				}
				
				logs(Level.INFO, "Potion bue !");
				
				// test si mort
				if(!attaquant.getElement().estVivant()) {
					arene.setPhrase(attaquant.getRefRMI(), "Je me suis empoisonne, je meurs ");
					logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " vient de boire un poison... Mort >_<");
				}

				//remplacement de la potion par une potion malus si c'est un diable
				if(attaquant.getElement() instanceof Diable){
					remplacerPotion();
				}
				else{// suppression de la potion
					arene.ejectePotion(defenseur.getRefRMI());
				}
				
				if (defenseur.getElement() instanceof PotionTeleportation){
					attaquant.setPosition(Calculs.positionAleatoireArene());
				}
				
				if (defenseur.getElement() instanceof PotionInvisibilite){
					//attaquant.getElement()
				}
				
				if (defenseur.getElement() instanceof PotionMalus){
					attaquant.getElement().incrementeCaract(Caracteristique.VIE, -10);
				}
				
			} else {
				logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " ou " + 
						Constantes.nomRaccourciClient(defenseur) + " est deja mort... Rien ne se passe");
			}
		} catch (RemoteException e) {
			logs(Level.INFO, "\nErreur lors d'un ramassage : " + e.toString());
		}
	}
	
	/**
	 * Permet de remplacer la potion ramassée par une potion malus
	 * @throws RemoteException
	 */
	public void remplacerPotion() throws RemoteException {
		Point ref = defenseur.getPosition();
		arene.ejectePotion(defenseur.getRefRMI());
		arene.ajoutePotion(new PotionMalus("Malus", "G12", new HashMap<Caracteristique,Integer>()), ref);
	}
}
