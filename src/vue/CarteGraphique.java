package vue;

import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JLabel;

import modele.Carte;

/**
 * 
 * <p>Affiche les cartes sous forme d'images</p>
 */
public class CarteGraphique extends JLabel {
	private static final long serialVersionUID = 1L;
	
	private Carte carte;
	
	/**
	 * 
	 * <p>Creation de la carte</p>
	 * @param c : Une carte
	 * @param isClickable : Definit si l'on peut cliquer ou non sur la carte
	 * @see modele.Carte
	 */
	public CarteGraphique(Carte c, boolean isClickable){
		super();
		carte = c;
		if(c!=null && isClickable){
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			this.setIcon(RessourcesManager.getImage(carte.getHauteur()+""+carte.getCouleur()));
		}
		else
			this.setIcon(RessourcesManager.getImage("flipside"));
		this.setPreferredSize(new Dimension(48,76));
	}
	
	/**
	 * 
	 * <p>Changer la carte &agrave; afficher</p>
	 * @param c : une carte
	 * @see modele.Carte
	 */
	public void setCarte(Carte c){
		carte = c;
		if(carte==null)
			this.setIcon(RessourcesManager.getImage("flipside"));
		else if(carte.getHauteur()==8 || carte.getHauteur()==14)
			this.setIcon(RessourcesManager.getImage(carte.getComportement().getCouleur()));
		else
			this.setIcon(RessourcesManager.getImage(carte.getHauteur()+""+carte.getCouleur()));
	}
	
	public String toString(){
		return carte.toString();
	}
}
