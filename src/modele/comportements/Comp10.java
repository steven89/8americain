package modele.comportements;

import java.io.Serializable;

import modele.Carte;
import modele.CoupsManager;
import modele.EffetCarte;

/**
 * Comportement des 10
 */
public class Comp10 implements ComportementCarte, Serializable {
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
	 * @see modele.comportements.ComportementCarte#doEffet()
	 */
	public void doEffet() {
		coup.toogleSens();
	}
	/**
	 * @see modele.comportements.ComportementCarte#isCompatible(Carte)
	 */
	public boolean isCompatible(Carte c) {
		if(c.getHauteur()==8 || c.getHauteur()==14)
			return true;
		if(c.getCouleur().equals(this.couleur) || c.getHauteur()==10)
			return true;
		return false;
	}
	/**
	 * @see modele.comportements.ComportementCarte#setHauteur(int)
	 */
	public void setHauteur(int h) {
		
	}
	/**
	 * @see modele.comportements.ComportementCarte#getCouleur()
	 */
	public String getCouleur() {
		return null;
	}

}
