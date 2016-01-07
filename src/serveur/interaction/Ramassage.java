package serveur.interaction;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.logging.Level;

import serveur.Arene;
import serveur.element.Caracteristique;
import serveur.element.personnages.Diable;
import serveur.element.potions.PotionFreeze;
import serveur.element.potions.PotionBonus;
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
				//si c'est un diable qui prend la potion on change la potion sans la supprimer
				if (attaquant.getElement() instanceof Diable){
					changePotion();
				}
				//sinon on la supprime
				else arene.ejectePotion(defenseur.getRefRMI());
				//si on ramasse une potion de teleportation alors on deplace le personage 
				//a une position aleatoire sur l'arene
				if (defenseur.getElement() instanceof PotionTeleportation){
					attaquant.setPosition(Calculs.positionAleatoireArene());
				}
				//si on ramasse une potion freeze on fait passer l'attribut 
				// du personnage à 1 pour dire que ce personnage doit être immobilisé
				if (defenseur.getElement() instanceof PotionFreeze){
					attaquant.getElement().setFreeze(1);
				}
				//si on ramasse une potion bonus alors on augmente l'attribut bonus 
				//udu personnage de 10 points
				if (defenseur.getElement() instanceof PotionBonus){
						attaquant.getElement().setBonus(attaquant.getElement().getBonus() + 10);
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
	 * Permet de changer les caracteristiques de la potion ramassée 
	 * en les passant en négatif
	 * @throws RemoteException
	 */
	public void changePotion() throws RemoteException {
		//création de nouvelles caractéristiques
		HashMap<Caracteristique, Integer> caractsPotion = new HashMap<Caracteristique, Integer>();
		int cvie = defenseur.getElement().getCaract(Caracteristique.VIE);
		int cforce = defenseur.getElement().getCaract(Caracteristique.FORCE);
		int cinit = defenseur.getElement().getCaract(Caracteristique.INITIATIVE);
		//passage des valeurs en negatif
		caractsPotion.put(Caracteristique.VIE, cvie>0?-cvie:cvie);
		caractsPotion.put(Caracteristique.FORCE, cforce>0?-cforce:cforce);
		caractsPotion.put(Caracteristique.INITIATIVE, cinit>0?-cinit:cinit);
		//le charme n'évolue pas
		caractsPotion.put(Caracteristique.CHARME, 0);
		defenseur.getElement().setCaracts(caractsPotion);
		//application des modifications
		attaquant.setPosition(Calculs.positionAleatoireArene());
	}
}
