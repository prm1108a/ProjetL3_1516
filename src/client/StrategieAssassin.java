package client;

import java.awt.Point;
import java.util.HashMap;

import client.controle.Console;
import logger.LoggerProjet;
import serveur.element.Caracteristique;
import serveur.element.personnages.Assassin;

public class StrategieAssassin extends Strategies{
	/**
	 * Cree un personnage, la console associe et sa strategie.
	 * @param ipArene ip de communication avec l'arene
	 * @param port port de communication avec l'arene
	 * @param ipConsole ip de la console du personnage
	 * @param nom nom du personnage
	 * @param groupe groupe d'etudiants du personnage
	 * @param nbTours nombre de tours pour ce personnage (si negatif, illimite)
	 * @param position position initiale du personnage dans l'arene
	 * @param logger gestionnaire de log
	 */
	public StrategieAssassin(String ipArene, int port, String ipConsole, 
			String nom, String groupe, HashMap<Caracteristique, Integer> caracts,
			int nbTours, Point position, LoggerProjet logger) {
		logger.info("Lanceur", "Creation de la console...");
		
		try {
			console = new Console(ipArene, port, ipConsole, this, 
					new Assassin(nom, groupe, caracts), 
					nbTours, position, logger);
			logger.info("Lanceur", "Creation de la console reussie");
			
		} catch (Exception e) {
			logger.info("Assassin", "Erreur lors de la creation de la console : \n" + e.toString());
			e.printStackTrace();
		}
	}
}
