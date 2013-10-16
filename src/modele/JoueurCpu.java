package modele;

import modele.tacs.CpuTacs;
import modele.tacs.High;
import modele.tacs.Middle;
import modele.tacs.Noob;

public class JoueurCpu extends Joueur {
	private static final long serialVersionUID = 1L;
	
	private CpuTacs tac;
	/**
	 * Initialise le nom et le comportement du joueur
	 * @param nom : le nom du joueur
	 * @param lvl : le niveau du joueur (niveau de la partie)
	 * @see modele.Partie
	 */
	public JoueurCpu(String nom, int lvl){
		this.nom = nom;
		switch(lvl){
		case 0 :
			tac = new Noob();
			break;
		case 2 :
			tac = new High();
			break;
		default :
			tac = new Middle();
			break;
		}
	}
	
	/**
	 * @see modele.Joueur#jouer()
	 */
	public Carte jouer() {
		aDitCarte(false);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return tac.choisirCarte(main);
	}

	/**
	 * @see modele.Joueur#joue(String)
	 */
	public void joue(String c) {
		
	}
	
	/**
	 * @see modele.Joueur#choisirCouleur()
	 */
	public String choisirCouleur(){
		return tac.choisirCouleur(main);
	}

	/**
	 * @see modele.Joueur#choixCouleur(String)
	 */
	public void choixCouleur(String c) {
		
	}
}
