package modele.comportements;

import java.io.Serializable;

import modele.Carte;

/**
 * Comportement par defaut
 */
public class CompN implements ComportementCarte, Serializable {
	private static final long serialVersionUID = 1L;
	
	private String couleur;
	private int hauteur;
	/**
	 * @see modele.comportements.ComportementCarte#doEffet()
	 */
	public void doEffet() {
		
	}
	/**
	 * @see modele.comportements.ComportementCarte#isCompatible(Carte)
	 */
	public boolean isCompatible(Carte c) {
		if(c.getHauteur()==8 || c.getHauteur()==14)
			return true;
		if(c.getHauteur()==hauteur || c.getCouleur().equals(couleur))
			return true;
		return false;
	}
	/**
	 * @see modele.comportements.ComportementCarte#setCouleur(String)
	 */
	public void setCouleur(String c) {
		couleur = c;
	}
	/**
	 * @see modele.comportements.ComportementCarte#setHauteur(int)
	 */
	public void setHauteur(int h) {
		hauteur = h;
	}
	/**
	 * @see modele.comportements.ComportementCarte#getCouleur()
	 */
	public String getCouleur() {
		return null;
	}

}
