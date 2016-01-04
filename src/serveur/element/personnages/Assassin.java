package serveur.element.personnages;

import java.util.HashMap;

import serveur.element.Caracteristique;

public class Assassin extends Personnage{

	private static final long serialVersionUID = 1L;

	public Assassin(String nom, String groupe, HashMap<Caracteristique, Integer> caracts) {
		super("Assassin", "G12", caracts);
	}

}
