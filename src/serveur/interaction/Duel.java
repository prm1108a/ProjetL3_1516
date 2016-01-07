package serveur.interaction;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.logging.Level;

import serveur.Arene;
import serveur.element.Caracteristique;
import serveur.element.personnages.Assassin;
import serveur.element.personnages.Charmeur;
import serveur.element.personnages.Personnage;
import serveur.element.personnages.Vampire;
import serveur.vuelement.VuePersonnage;
import utilitaires.Calculs;
import utilitaires.Constantes;

/**
 * Represente un duel entre deux personnages.
 *
 */
public class Duel extends Interaction<VuePersonnage> {
	
	/**
	 * Cree une interaction de duel.
	 * @param arene arene
	 * @param attaquant attaquant
	 * @param defenseur defenseur
	 */
	public Duel(Arene arene, VuePersonnage attaquant, VuePersonnage defenseur) {
		super(arene, attaquant, defenseur);
	}
	
	@Override
	public void interagit() {
		super.interagit();
		try {
			Personnage pAttaquant = attaquant.getElement();
			int forceAttaquant = pAttaquant.getCaract(Caracteristique.FORCE);
			int perteVie = forceAttaquant;
		
			Point positionEjection = positionEjection(defenseur.getPosition(), attaquant.getPosition(), forceAttaquant);

			// ejection du defenseur
			defenseur.setPosition(positionEjection);

			// degats
			if (perteVie > 0) {
				//on verfie si c'est un charmeur, si oui on effectue sa fonction
				if (charmer(defenseur)) {
					//on verifie si c'est un vampire, si oui on effectue sa fonction
					puiserVie (attaquant, defenseur);
					//on verifie si c'est un assasin, si oui on effectue sa fonction
					assassiner(attaquant, defenseur);
					//appliquer les degats commis lors du duel au defenseur
					arene.incrementeCaractElement(defenseur, Caracteristique.VIE, -perteVie);
				
					logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " colle une beigne ("
						+ perteVie + " points de degats) a " + Constantes.nomRaccourciClient(defenseur));
				}
				else {
					logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " charme " + Constantes.nomRaccourciClient(defenseur));
				}
			}
			
			// initiative
			incrementeInitiative(defenseur);
			decrementeInitiative(attaquant);
			
		} catch (RemoteException e) {
			logs(Level.INFO, "\nErreur lors d'une attaque : " + e.toString());
		}
	}

	/**
	 * Incremente l'initiative du defenseur en cas de succes de l'attaque. 
	 * @param defenseur defenseur
	 * @throws RemoteException
	 */
	private void incrementeInitiative(VuePersonnage defenseur) throws RemoteException {
		arene.incrementeCaractElement(defenseur, Caracteristique.INITIATIVE, 
				Constantes.INCR_DECR_INITIATIVE_DUEL);
	}
	
	/**
	 * Decremente l'initiative de l'attaquant en cas de succes de l'attaque. 
	 * @param attaquant attaquant
	 * @throws RemoteException
	 */
	private void decrementeInitiative(VuePersonnage attaquant) throws RemoteException {
		arene.incrementeCaractElement(attaquant, Caracteristique.INITIATIVE, 
				-Constantes.INCR_DECR_INITIATIVE_DUEL);
	}

	
	/**
	 * Retourne la position ou le defenseur se retrouvera apres ejection.
	 * @param posDefenseur position d'origine du defenseur
	 * @param positionAtt position de l'attaquant
	 * @param forceAtt force de l'attaquant
	 * @return position d'ejection du personnage
	 */
	private Point positionEjection(Point posDefenseur, Point positionAtt, int forceAtt) {
		int distance = forceVersDistance(forceAtt);
		
		// abscisses 
		int dirX = posDefenseur.x - positionAtt.x;
		
		if (dirX > 0) {
			dirX = distance;
		}
		
		if (dirX < 0) {
			dirX = -distance;
		}
		
		// ordonnees
		int dirY = posDefenseur.y - positionAtt.y;
		
		if (dirY > 0) {
			dirY = distance;
		}
		
		if (dirY < 0) {
			dirY = -distance;
		}
		
		int x = posDefenseur.x + dirX;
		int y = posDefenseur.y + dirY;
		
		return Calculs.restreintPositionArene(new Point(x, y));
	}
	
	/**
	 * Calcule la distance a laquelle le defenseur est projete suite a un coup.
	 * @param forceAtt force de l'attaquant
	 * @return distance de projection
	 */
	private int forceVersDistance(int forceAtt) {
		int max = Caracteristique.FORCE.getMax();
		
		int quart = (int) (4 * ((float) (forceAtt - 1) / max)); // -1 pour le cas force = 100
		
		return Constantes.DISTANCE_PROJECTION[quart];
	}
	
	/**
	 * Verifie que le personnage soit un vampire, et lui prend de la vie.
	 * @param attaquant attaquant
	 * @param defenseur defenseur
	 * @throws RemoteException 
	 */
	private void puiserVie (VuePersonnage attaquant, VuePersonnage defenseur) throws RemoteException {
		Personnage pattaquant = attaquant.getElement();//récupération du personnage attaquant
		Personnage pdefenseur = defenseur.getElement();//récupération du personnage defenseur
		if (pattaquant instanceof Vampire){
			int fattaquant = pattaquant.getCaract(Caracteristique.FORCE);//récupération de la force de l'attaquant
			int fdefenseur = pdefenseur.getCaract(Caracteristique.FORCE);//récupération de la force du defenseur
			try {
				logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " je puise ("
						+ fattaquant + " points de vie) a " + Constantes.nomRaccourciClient(defenseur));
				arene.incrementeCaractElement(attaquant, Caracteristique.VIE, fdefenseur);//incrementation de la vie du vampire
			} catch (RemoteException e) {
				logs(Level.INFO, "\nErreur lors d'une attaque : " + e.toString());
			}	
		}
	}
	

	/**
	 * Verifie que le personnage soit un assassin et tue l'adversaire.
	 * @param attaquant attaquant
	 * @param defenseur defenseur 
	 */
	private void assassiner(VuePersonnage attaquant, VuePersonnage defenseur){
		Personnage pattaquant = attaquant.getElement();//récupération du personnage attaquant
		Personnage pdefenseur = defenseur.getElement();//récupération du personnage defenseur
		if (pattaquant instanceof Assassin){
			int fdefenseur = pdefenseur.getCaract(Caracteristique.FORCE);//récupération de la force de l'attaquant
			int fattaquant = pattaquant.getCaract(Caracteristique.FORCE);//récupération de la force du defenseur
			//si la force de l'attaquant est superieure a celle du defenseur 
			if (fattaquant > fdefenseur){
				//on tue le defenseur
				pdefenseur.tue();
				try {
					logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " je tue "
							+ pdefenseur);
					arene.incrementeCaractElement(attaquant, Caracteristique.VIE, fdefenseur);
				} catch (RemoteException e) {
					logs(Level.INFO, "\nErreur lors d'une attaque : " + e.toString());
				}	
			}
		}
	}
	
	/**
	 * Verifie que le personnage soit un charmeur et effectue la fonction du charmeur.
	 * @param defenseur defenseur 
	 */
	private boolean charmer (VuePersonnage defenseur){
		Personnage pattaquant = attaquant.getElement();//récupération du personnage attaquant
		Personnage pdefenseur = defenseur.getElement();//récupération du personnage defenseur
		//si l'attaquant est un charmeur
		if (pattaquant instanceof Charmeur){
			//et que le defenseur est un charmeur
			if (pdefenseur instanceof Charmeur){
				return true;
			}
			//sinon si le defenseur n'est pas un charmeur et que le charmeur a un charme de plus de 20 points
			else if (pdefenseur instanceof Personnage && pattaquant.getCaract(Caracteristique.CHARME) > 20){
				//alors il teleporte le defenseur
				defenseur.setPosition(Calculs.positionAleatoireArene());
				//et la barre de charme de l'attaquant à 0
				pattaquant.incrementeCaract(Caracteristique.CHARME, - pattaquant.getCaract(Caracteristique.CHARME));
				return false;
			}
			else 
				return true;
		}
		else
			return true;
		
	}
	
}
