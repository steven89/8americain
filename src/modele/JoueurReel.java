package modele;

import modele.exceptions.CarteException;

public class JoueurReel extends Joueur {
	private static final long serialVersionUID = 1L;
	
	private Carte aJouer;
	private String couleurChoisie;
	private boolean peutJouer = false, peutChoisirCouleur=false;
	/**
	 * Initialise le nom du joueur
	 * @param nom
	 */
	public JoueurReel(String nom){
		this.nom = nom;
	}

	/**
	 * @see modele.Joueur#jouer()
	 */
	public synchronized Carte jouer() {
		peutJouer = true;
		Updateur.notifyObservers(this, "peutJouer");
		aDitCarte(false);
		while(aJouer==null)
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		Carte tmpCarte = aJouer;
		aJouer = null;
		peutJouer = false;
		return tmpCarte;
	}

	/**
	 * @see modele.Joueur#joue(String)
	 */
	public synchronized void joue(String c) {
		if(peutJouer)
			try {
				aJouer = getCarte(c);
				notifyAll();
			} catch (CarteException e) {
				e.notifyException();
				Updateur.notifyObservers(this, "peutJouer");
			}
		
	}
	
	/**
	 * @see modele.Joueur#choisirCouleur()
	 */
	public synchronized String choisirCouleur() {
		peutChoisirCouleur = true;
		Updateur.notifyObservers(this, "get couleur");
		while(couleurChoisie==null)
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		String tmpCouleur = couleurChoisie;
		couleurChoisie=null;
		return tmpCouleur;
	}

	/**
	 * @see modele.Joueur#choixCouleur(String)
	 */
	public synchronized void choixCouleur(String c) {
		if(peutChoisirCouleur){
			if(Validateur.isCouleur(c)){
				couleurChoisie=c;
				notifyAll();
			}
			else
				Updateur.notifyObservers(this, "get couleur");
		}
	}
	
	
}
