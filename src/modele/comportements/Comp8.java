package modele.comportements;

import java.io.Serializable;

import modele.Carte;
import modele.CoupsManager;
import modele.EffetCarte;
import modele.Updateur;

/**
 * Comportement des 8
 */
public class Comp8 implements ComportementCarte, Serializable {
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
	public String getCouleur(){
		return couleur;
	}
	/**
	 * @see modele.comportements.ComportementCarte#doEffet()
	 */
	public void doEffet() {
		couleur = coup.choixCouleur();
		Updateur.notifyObservers(this, "couleur");
	}
	/**
	 * @see modele.comportements.ComportementCarte#isCompatible(Carte)
	 */
	public boolean isCompatible(Carte c) {
		if(c.getHauteur()==14)
			return true;
		if(c.getHauteur()==8 || c.getCouleur().equals(this.couleur))
			return true;
		return false;
	}
	/**
	 * @see modele.comportements.ComportementCarte#setHauteur(int)
	 */
	public void setHauteur(int h) {
		
	}
}
