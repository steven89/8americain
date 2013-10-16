package modele.tacs;

import java.util.ArrayList;

import modele.Carte;

/**
 * Strategie joueur virtuel niveau NOMRAL
 */
public class Middle extends Tactique implements CpuTacs {
	private static final long serialVersionUID = 1L;

	/**
	 * Choisir la carte a jouer
	 * @return la carte a jouer
	 */
	public Carte choisirCarte(ArrayList<Carte> main) {
		this.main.clear();
		this.main.addAll(main);
		extractCartesCompatibles();
		if(main.size()==2)
			coup.direCarte();
		// choix de la carte
		Carte tmpCarte = null;
		if(existsCarteJoker())
			tmpCarte = getCarte(14);
		else if(existsCarte8())
			tmpCarte = getCarte(8);
		else if(existsCarteAs())
			tmpCarte = getCarte(1);
		else if(existsCarteN()){
			extractCarteN();
			int valeurMax=0;
			for(Carte c : mainCompatible){
				if(valeurMax<c.getValeur()){
					valeurMax=c.getValeur();
					tmpCarte = c;
				}
			}
		}
		else{
			if(existsCarte10())
				tmpCarte = getCarte(10);
			else if(existsCarte2())
				tmpCarte = getCarte(2);
			else if(existsCarte7())
				tmpCarte = getCarte(7);
		}
		return tmpCarte;
	}

	/**
	 * Choisir la couleur
	 * @return nom de la couleur
	 */
	public String choisirCouleur(ArrayList<Carte> main) {
		this.main.clear();
		this.main.addAll(main);
		return maxCouleur();
	}
}
