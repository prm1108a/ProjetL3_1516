package serveur.interaction;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.HashMap;

import serveur.element.personnages.Personnage;
import serveur.element.personnages.Peureux;
import serveur.vuelement.VuePersonnage;
import utilitaires.Calculs;

/**
 * Represente le deplacement d'un personnage.
 *
 */
public class Deplacement {

	/**
	 * Vue du personnage qui veut se deplacer.
	 */
	private VuePersonnage personnage;
	
	/**
	 * References RMI et vues des voisins (calcule au prealable). 
	 */
	private HashMap<Integer, Point> voisins;
	
	/**
	 * Cree un deplacement.
	 * @param personnage personnage voulant se deplacer
	 * @param voisins voisins du personnage
	 */
	public Deplacement(VuePersonnage personnage, HashMap<Integer, Point> voisins) { 
		this.personnage = personnage;

		if (voisins == null) {
			this.voisins = new HashMap<Integer, Point>();
		} else {
			this.voisins = voisins;
		}
	}

	/**
	 * Deplace ce sujet d'une case en direction de l'element dont la reference
	 * est donnee.
	 * Si la reference est la reference de l'element courant, il ne bouge pas ;
	 * si la reference est egale a 0, il erre ;
	 * sinon il va vers le voisin correspondant (s'il existe dans les voisins).
	 * @param refObjectif reference de l'element cible
	 */    
	public void seDirigeVers(int refObjectif) throws RemoteException {
		Point pvers;

		// on ne bouge que si la reference n'est pas la notre
		if (refObjectif != personnage.getRefRMI()) {
			
			// la reference est nulle (en fait, nulle ou negative) : 
			// le personnage erre
			if (refObjectif <= 0) { 
				pvers = Calculs.positionAleatoireArene();
						
			} else { 
				// sinon :
				// la cible devient le point sur lequel se trouve l'element objectif
				pvers = voisins.get(refObjectif);
			}
	
			// on ne bouge que si l'element existe
			if(pvers != null) {
				seDirigeVers(pvers);
			}
		}
	}

	/**
	 * Deplace ce sujet d'une case en direction de la case donnee.
	 * @param objectif case cible
	 * @throws RemoteException
	 */
	public void seDirigeVers(Point objectif) throws RemoteException {
		Point cible = Calculs.restreintPositionArene(objectif); 
		
		// on cherche le point voisin vide
		Point dest = Calculs.meilleurPoint(personnage.getPosition(), cible, voisins);
		dest = fuir (dest);
		
		if(dest != null) {
			personnage.setPosition(dest);
		}
	}
	
	/**
	 * Fuit ou pas devant la cible
	 * @param cible (ou se trouve l'element)
	 * @return cible (avec ou sans fuite)
	 */
	public Point fuir(Point cible) {
		Personnage p = personnage.getElement();
		Point pos = personnage.getPosition();
		if (p instanceof Peureux){
			if (pos.x > cible.x)
				cible.x = cible.x + 2;
			else if (pos.x < cible.x)
				cible.x = cible.x - 2;
			if (pos.y > cible.y)
				cible.y = cible.y + 2;
			else if (pos.y < cible.y)
				cible.y = cible.y - 2;
		}
		return cible;
	}
	
}

