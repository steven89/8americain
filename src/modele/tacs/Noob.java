package modele.tacs;

import java.util.ArrayList;
import java.util.Random;

import modele.Carte;
import modele.Couleurs;

/**
 * Strategie joueur virtuel niveau FACILE
 */
public class Noob extends Tactique {
	private static final long serialVersionUID = 1L;

	/**
	 * Choisir la carte a jouer
	 * @return la carte a jouer
	 */
	public Carte choisirCarte(ArrayList<Carte> main) {
		this.main.clear();
		this.main.addAll(main);
		extractCartesCompatibles();
		// choix de la carte
		Carte tmpCarte = null;
		if(existsCarteN()){
			extractCarteN();
			int valeurMin=100;
			for(Carte c : mainCompatible){
				if(valeurMin>c.getValeur()){
					valeurMin=c.getValeur();
					tmpCarte = c;
				}
			}
		}
		else{
			if(existsCarte10())
				tmpCarte = getCarte(10);
			else if(existsCarte2())
				tmpCarte = getCarte(2);
			else if(existsCarte8())
				tmpCarte = getCarte(8);
			else if(existsCarte7())
				tmpCarte = getCarte(7);
			else if(existsCarteAs())
				tmpCarte = getCarte(1);
			else if(existsCarteJoker())
				tmpCarte = getCarte(14);
		}
		return tmpCarte;
	}

	/**
	 * Choisir la couleur
	 * @return nom de la couleur
	 */
	public String choisirCouleur(ArrayList<Carte> main) {
		Random rand = new Random();
		return Couleurs.getCouleur(rand.nextInt(Couleurs.nbCouleurs()-1));//-1 pour ne pas prendre en compte le joker
	}
}
