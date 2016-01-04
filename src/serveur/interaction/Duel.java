package serveur.interaction;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.logging.Level;

import serveur.Arene;
import serveur.element.Caracteristique;
import serveur.element.personnages.Assassin;
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
		try {
			Personnage pAttaquant = attaquant.getElement();
			int forceAttaquant = pAttaquant.getCaract(Caracteristique.FORCE);
			int perteVie = forceAttaquant;
		
			Point positionEjection = positionEjection(defenseur.getPosition(), attaquant.getPosition(), forceAttaquant);

			// ejection du defenseur
			defenseur.setPosition(positionEjection);

			// degats
			if (perteVie > 0) {
				puiserVie (attaquant, defenseur);
				assassiner(attaquant, defenseur);
				arene.incrementeCaractElement(defenseur, Caracteristique.VIE, -perteVie);
				
				logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " colle une beigne ("
						+ perteVie + " points de degats) a " + Constantes.nomRaccourciClient(defenseur));
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
	 * VÃ©rifie que le personnage soit un vampire, et lui prend de la vie.
	 * @param attaquant attaquant
	 * @param defenseur defenseur
	 * @throws RemoteException 
	 */
	private void puiserVie (VuePersonnage attaquant, VuePersonnage defenseur) throws RemoteException {
		Personnage pattaquant = attaquant.getElement();
		if (pattaquant instanceof Vampire){
			int fattaquant = pattaquant.getCaract(Caracteristique.FORCE);
			try {
				logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " je puise ("
						+ fattaquant + " points de vie) a " + Constantes.nomRaccourciClient(defenseur));
				arene.incrementeCaractElement(attaquant, Caracteristique.VIE, fattaquant);
			} catch (RemoteException e) {
				logs(Level.INFO, "\nErreur lors d'une attaque : " + e.toString());
			}	
		}
	}
	/**
	 * Vérifie que le personnage soit un assassin et tue l'adversaire.
	 * @param attaquant attaquant
	 * @param defenseur defenseur 
	 */
	private void assasSiner(VuePersonnage attaquant, VuePersonnage defenseur){
		Personnage pattaquant = attaquant.getElement();
		Personnage pdefenseur = defenseur.getElement();
		if (pattaquant instanceof Assassin){
			int fdefenseur = pdefenseur.getCaract(Caracteristique.FORCE);
			int fattaquant = pattaquant.getCaract(Caracteristique.FORCE);
			if (fattaquant > fdefenseur){
				pdefenseur.tue();
			}
		}
	}
}
