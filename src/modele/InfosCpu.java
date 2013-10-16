package modele;
/**
 * Infos sur la manche necessaire aux comportements des cpu
 */
public interface InfosCpu {
	/**
	 * 
	 * @return La carte visible
	 */
	abstract public Carte getCarteVisible();
	/**
	 * 
	 * @return Le joueur suivant
	 */
	abstract public Joueur getNextJoueur();
	/**
	 * Annoncer qu'il ne reste qu'une carte dans la main
	 */
	abstract public void direCarte();
	/**
	 * Annoncer qu'un joueur a oublier de dire Carte
	 */
	abstract public void signalerOubli();
}
