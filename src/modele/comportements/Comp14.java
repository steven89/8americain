package modele.comportements;

import java.io.Serializable;

import modele.Carte;
import modele.CoupsManager;
import modele.EffetCarte;
import modele.Updateur;

/**
 * Comportement des Joker
 */
public class Comp14 implements ComportementCarte, Serializable {
	private static final long serialVersionUID = 1L;
	
	private String couleur;
	private EffetCarte coup = CoupsManager.getInstance().getCurrentCoup();
	/**
	 * @see modele.comportements.ComportementCarte#setCouleur(String)
	 */
	public void setCouleur(String c){
		this.couleur = c;
	}
	/**
	 * @see modele.comportements.ComportementCarte#getCouleur()
	 */
	public String getCouleur() {
		return couleur;
	}
	/**
	 * @see modele.comportements.ComportementCarte#doEffet()
	 */
	public void doEffet() {
		couleur = coup.choixCouleur();
		Updateur.notifyObservers(this, "couleur");
		coup.fairePiocher(5);
	}
	/**
	 * @see modele.comportements.ComportementCarte#isCompatible(Carte)
	 */
	public boolean isCompatible(Carte c) {
		if(c.getHauteur()==18)
			return true;
		if(c.getHauteur()==14 || c.getCouleur().equals(this.couleur))
			return true;
		return false;
	}
	/**
	 * @see modele.comportements.ComportementCarte#setHauteur(int)
	 */
	public void setHauteur(int h) {
		
	}
	
}
