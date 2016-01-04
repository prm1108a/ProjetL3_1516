package serveur.element.personnages;

import java.util.HashMap;

import serveur.element.Caracteristique;

public class Charmeur extends Personnage{

	private static final long serialVersionUID = 1L;

	public Charmeur(String nom, String groupe, HashMap<Caracteristique, Integer> caracts) {
		super("Charmeur", "G12", caracts);
	}
	
	

}
