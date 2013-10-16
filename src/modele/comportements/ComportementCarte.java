package modele.comportements;

import modele.Carte;

/**
 * 
 *<p>Interface qui g&egrave;re le comportement des cartes</p> 
 *
 */
public interface ComportementCarte {
	/**
	 * V&eacute;rifie si la carte est compatible avec la carte visible
	 * @param c : une carte
	 * @return : un boolean (true = compatible / false = incompatible)
	 */
	abstract public boolean isCompatible(Carte c);
	/**
	 * <p>Applique l'effet de la carte</p>
	 * 
	 */
	abstract public void doEffet();
	/**
	 * 
	 * <p>D&eacute;finit la couleur de la carte(dans le cas d'un choix de couleur fait par le joueur)</p>
	 * @param c : nom de la couleur
	 */
	abstract public void setCouleur(String c);
	/**
	 * 
	 * <p>D&eacute;finit la hauteur de la carte(dans le cas du compN definit la hauteur de la carte)</p>
	 * @param h : hauteur de la carte
	 */
	abstract public void setHauteur(int h);
	/**
	 * @return <p>la couleur liee au comportement</p>
	 */
	abstract public String getCouleur();
}