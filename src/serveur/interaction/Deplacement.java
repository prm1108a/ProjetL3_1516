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
	
	private Iteration iteration;
	
	/**
	 * Cree un deplacement.
	 * @param personnage personnage voulant se deplacer
	 * @param voisins voisins du personnage
	 */
	public Deplacement(VuePersonnage personnage, HashMap<Integer, Point> voisins) { 
		this.personnage = personnage;
		
		this.iteration = new Iteration (personnage);
		iteration.addCharme();

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
				pvers = fuir (pvers);
			}
	
			// on ne bouge que si l'element existe
			if(pvers != null && iteration.checkFreeze()) {
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
		
		if(dest != null) {
			personnage.setPosition(dest);
		}
	}
	
	/**
	 * Fuit ou pas devant la cible
	 * @param cible (ou se trouve l'element)
	 * @return cible (avec ou sans fuite)
	 */
	public Point fuir(Point direction) {
		Personnage p = personnage.getElement();
		Point pos = personnage.getPosition();
		//System.out.println("cible: (" + direction.x + ", " + direction.y + ")" );
		//System.out.println("pos: (" + pos.x + ", " + pos.y + ")" );
		if (p instanceof Peureux){
			if (direction.x > pos.x)
				direction.x = pos.x - 2;
			else if (direction.x < pos.x)
				direction.x = pos.x + 2;
			else
				direction.x = pos.x;
			if (direction.y > pos.y)
				direction.y = pos.y - 2;
			else if (direction.y < pos.y)
				direction.y = pos.y + 2;
			else
				direction.y = pos.y;
			//System.out.println("cible: (" + direction.x + ", " + direction.y + ")" );
			//System.out.println("pos: (" + pos.x + ", " + pos.y + ")" );
			
		}
		return direction;
	}
	
}

