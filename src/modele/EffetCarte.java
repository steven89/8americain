package modele;
/**
 * Effets appliques par les cartes sur la manche
 */
public interface EffetCarte {
	/**
	 * Verifie si l'effet de la carte visible a ete applique
	 * @return true si l'effet a ete applique ; false si l'effet n'est pas encore applique
	 */
	abstract public boolean isEffetApplique();
	/**
	 * Faire piocher des cartes au joueur suivant
	 * @param nb : nombre de cartes a piocher
	 */
	abstract public void fairePiocher(int nb);
	/**
	 * Faire piocher une carte dans la main du joueur actuel au joueur suivant
	 */
	abstract public void fairePiocherMain();
	/**
	 * Faire piocher des cartes au joueur suivant, application d'un cumul si le joueur suivant peut aussi poser un + (AS)
	 * @param nb : nombre de cartes a faire piocher
	 */
	abstract public void fairePiocherCumul(int nb);
	/**
	 * Faire passer le tour du joueur suivant
	 */
	abstract public void fairePasserTour();
	/**
	 * Changer le sens de jeu
	 */
	abstract public void toogleSens();
	/**
	 * Demande au joueur de choisir une couleur
	 * @return la couleur choisie par le joueur
	 */
	abstract public String choixCouleur();
}
