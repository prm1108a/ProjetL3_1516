package client;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.HashMap;

import client.controle.Console;
import serveur.IArene;
import serveur.element.Caracteristique;
import serveur.element.Element;
import serveur.element.potions.Potion;
import utilitaires.Calculs;
import utilitaires.Constantes;

public class Strategies {
	
	/**
	 * Console permettant d'ajouter une phrase et de recuperer le serveur 
	 * (l'arene).
	 */
	protected Console console;
	
	// TODO etablir une strategie afin d'evoluer dans l'arene de combat
		// une proposition de strategie (simple) est donnee ci-dessous
		/** 
		 * Decrit la strategie.
		 * Les methodes pour evoluer dans le jeu doivent etre les methodes RMI
		 * de Arene et de ConsolePersonnage. 
		 * @param voisins element voisins de cet element (elements qu'il voit)
		 * @throws RemoteException
		 */
		public void executeStrategie(HashMap<Integer, Point> voisins) throws RemoteException {
			// arene
			IArene arene = console.getArene();
			
			// reference RMI de l'element courant
			int refRMI = 0;
			
			// position de l'element courant
			Point position = null;
			
			try {
				refRMI = console.getRefRMI();
				position = arene.getPosition(refRMI);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
			if (voisins.isEmpty()) { // je n'ai pas de voisins, j'erre
				console.setPhrase("J'erre...");
				if (console.getPersonnage().getCaract(Caracteristique.VIE) < 100)
						arene.soigne(refRMI);
				else 
					arene.deplace(refRMI, 0); 
				
			} else {
				int refCible = Calculs.chercheElementProche(position, voisins);
				int distPlusProche = Calculs.distanceChebyshev(position, arene.getPosition(refCible));

				Element elemPlusProche = arene.elementFromRef(refCible);

				if(distPlusProche <= Constantes.DISTANCE_MIN_INTERACTION) { // si suffisamment proches
					// j'interagis directement
					if(elemPlusProche instanceof Potion) { // potion
						// ramassage
						console.setPhrase("Je ramasse une potion");
						arene.ramassePotion(refRMI, refCible);

					} else { // personnage
						// duel
						console.setPhrase("Je fais un duel avec " + elemPlusProche.getNom());
						arene.lanceAttaque(refRMI, refCible);
					}
					
				} else { // si voisins, mais plus eloignes
					// je vais vers le plus proche
					console.setPhrase("Je vais vers mon voisin " + elemPlusProche.getNom());
					arene.deplace(refRMI, refCible);
				}
			}
		}
}
