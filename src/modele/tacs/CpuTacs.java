package modele.tacs;

import java.util.ArrayList;

import modele.Carte;
/**
 * Comportement des joueurs virtuels
 */
public interface CpuTacs {
	/**
	 * Choisi la carte a jouer dans la main du joueur
	 * @param main : liste des cartes de la main du joueur
	 * @return La carte a jouer
	 */
	abstract public Carte choisirCarte(ArrayList<Carte> main);
	/**
	 * Choisi la couleur a donner lorsque l'on pose un 8 ou un joker
	 * @param main : liste des cartes de la main du joueur
	 * @return La couleur choisie
	 */
	abstract public String choisirCouleur(ArrayList<Carte> main);
}
