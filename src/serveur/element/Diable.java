package serveur.element;

import java.util.HashMap;

import serveur.element.personnages.Personnage;

public class Diable extends Personnage{
	private static final long serialVersionUID = 1L;
	
	public Diable(String nom, String groupe, HashMap<Caracteristique, Integer> caracts) {
		super("Diable", "G12", caracts);
	}

}
