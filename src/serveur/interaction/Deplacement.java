package serveur.interaction;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.HashMap;

import serveur.element.personnages.Personnage;
import serveur.element.personnages.Peureux;
import serveur.vuelement.VuePersonnage;
import utilitaires.Calculs;
import utilitaires.Constantes;

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
				//trouver l'arene pour faire estPotionRef
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
		Personnage p = personnage.getElement();//récupération du personnage 
		Point pos = personnage.getPosition();//récupération de la position du personnage
		Point newPoint = new Point();
		Point distance = Calculs.distanceXY(pos, direction); 
		int distCheb = Calculs.distanceChebyshev(pos, direction);
		//vérifie que ça soit un peureux
		if (p instanceof Peureux){
			// si le voisin à fuir se trouve en haut a gauche du peureux alors il se deplace 
			// dans la direction opposée
			if (direction.x < pos.x && direction.y < pos.y){
				newPoint.setLocation(pos.x + 1, pos.y + 1);
			}
			// sinon si le voisin à fuir se trouve en bas a gauche du peureux alors il se deplace 
			// dans la direction opposée
			else if (direction.x < pos.x && direction.y > pos.y){
				newPoint.setLocation(pos.x + 1, pos.y - 1);
			}
			// sinon si le voisins à fuir se trouve en haut a droite du peureux alors il se deplace 
			// dans la direction opposée
			else if (direction.x > pos.x && direction.y < pos.y){
				newPoint.setLocation(pos.x - 1, pos.y + 1);
			}
			// sinon si le voisins à fuir se trouve en bas a droite du peureux alors il se deplace 
			// dans la direction opposée
			else if (direction.x > pos.x && direction.y > pos.y){
				newPoint.setLocation(pos.x - 1, pos.y - 1);
			}
			//si le peureux se retrouve dans un coin
			// alors il doit situer ses voisins pour pouvoir repartir dans l'autre sens 
			// sans les rencontrer 
			
			//si il est dans le coin haut gauche
			else if (pos.x == Constantes.XMIN_ARENE && pos.y == Constantes.YMIN_ARENE){
				if (distCheb == distance.y){
					newPoint.setLocation(pos.x, pos.y + 1);
				}
				else newPoint.setLocation(pos.x + 1, pos.y);
			}
			//si il est dans le coin bas gauche
			else if (pos.x == Constantes.XMIN_ARENE && pos.y == Constantes.YMAX_ARENE){
				if (distCheb == distance.y){
					newPoint.setLocation(pos.x, pos.y - 1);
				}
				else newPoint.setLocation(pos.x + 1, pos.y);
			}
			//si il est dans le coin haut droite
			else if (pos.x == Constantes.XMAX_ARENE && pos.y == Constantes.YMIN_ARENE){
				if (distCheb == distance.y){
					newPoint.setLocation(pos.x, pos.y + 1);
				}
				else newPoint.setLocation(pos.x - 1, pos.y);
			}
			//si il est dans le coin bas droite
			else if (pos.x == Constantes.XMAX_ARENE && pos.y == Constantes.YMAX_ARENE){
				if (distCheb == distance.y){
					newPoint.setLocation(pos.x, pos.y - 1);
				}
				else newPoint.setLocation(pos.x - 1, pos.y);
			}
			return newPoint;	
		}
		else {
			return direction;
		}
	}
}	
