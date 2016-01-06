package serveur.interaction;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.logging.Level;

import serveur.Arene;
import serveur.element.Caracteristique;
import serveur.element.personnages.Diable;
import serveur.element.potions.PotionFreeze;
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
				
				if (!(attaquant.getElement() instanceof Diable)){
					for(Caracteristique c : valeursPotion.keySet()) {
						arene.incrementeCaractElement(attaquant, c, valeursPotion.get(c));
					}
				}
				if (!(attaquant.getElement() instanceof Diable)){
					logs(Level.INFO, "Potion bue !");
				}
				else if (defenseur.getElement() instanceof PotionMalus){
					logs(Level.INFO, "c'est deja une potion malus elle n'est pas remplacee !");
				}
				else logs(Level.INFO, "Potion remplacee !");
				// test si mort
				if(!attaquant.getElement().estVivant()) {
					arene.setPhrase(attaquant.getRefRMI(), "Je me suis empoisonne, je meurs ");
					logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " vient de boire un poison... Mort >_<");
				}
				if (attaquant.getElement() instanceof Diable){
					remplacerPotion();
				}
				else arene.ejectePotion(defenseur.getRefRMI());
				
				if (defenseur.getElement() instanceof PotionTeleportation){
					attaquant.setPosition(Calculs.positionAleatoireArene());
				}
				
				if (defenseur.getElement() instanceof PotionFreeze){
					attaquant.getElement().setFreeze(1);
				}
				
				if (defenseur.getElement() instanceof PotionMalus){
					if (!(attaquant.getElement() instanceof Diable)){
						attaquant.getElement().incrementeCaract(Caracteristique.VIE, -10);
					}
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
		// caracteristiques de la potion
		HashMap<Caracteristique, Integer> caractsPotion = new HashMap<Caracteristique, Integer>();
		int cvie = defenseur.getElement().getCaract(Caracteristique.VIE);
		int cforce = defenseur.getElement().getCaract(Caracteristique.FORCE);
		int cinit = defenseur.getElement().getCaract(Caracteristique.INITIATIVE);
		caractsPotion.put(Caracteristique.VIE, cvie>0?-cvie:cvie);
		caractsPotion.put(Caracteristique.FORCE, cforce>0?-cforce:cforce);
		caractsPotion.put(Caracteristique.INITIATIVE, cinit>0?-cinit:cinit);
		caractsPotion.put(Caracteristique.CHARME, 0);
		defenseur.getElement().setCaracts(caractsPotion);
		attaquant.setPosition(Calculs.positionAleatoireArene());
	}
}
