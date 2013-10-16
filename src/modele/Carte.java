package modele;

import java.io.Serializable;

import modele.comportements.ComportementCarte;

/**
 * <p>Creer une carte</p>
 */
public class Carte implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int hauteur, id, valeur;
	private String couleur;
	private ComportementCarte comportement;
	
	private static int nbCartes = 0;
	
	public final static int AS = 1;
	public final static int DEUX = 2;
	public final static int TROIS = 3;
	public final static int QUATRE = 4;
	public final static int CINQ = 5;
	public final static int SIX = 6;
	public final static int SEPT = 7;
	public final static int HUIT = 8;
	public final static int NEUF = 9;
	public final static int DIX = 10;
	public final static int VALET = 11;
	public final static int DAME = 12;
	public final static int ROI = 13;
	public final static int JOKER = 14;
	/**
	 * Initialise la carte (id + hauteur + valeur + couleur + comportement)
	 * @param h : hauteur de la carte
	 * @param c : couleur de la carte
	 * @see modele.Couleurs#getCouleur(int)
	 * @see modele.comportements.ComportementCarte
	 */
	public Carte(int h, int c){
		this.hauteur = h;
		this.id = nbCartes;
		nbCartes++;
		this.couleur = Couleurs.getCouleur(c);
		// comportement
		try {
			switch(hauteur){
			case 1 :
			case 2 :
			case 7 :
			case 8 :
			case 10 :
			case 14 :
				this.comportement = (ComportementCarte) Class.forName("modele.comportements.Comp"+this.hauteur).newInstance();
				this.comportement.setCouleur(couleur);
			break;
			
			default :
				this.comportement = (ComportementCarte) Class.forName("modele.comportements.CompN").newInstance();
				this.comportement.setHauteur(hauteur);
				this.comportement.setCouleur(couleur);
			break;
			}
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// valeur
		switch(hauteur){
		case 1 :
			valeur = 20;
			break;
		case 8 :
			valeur = 32;
			break;
		case 11 :
		case 12 :
		case 13 :
			valeur = 10;
			break;
		case 14 :
			valeur = 50;
			break;
		default :
			valeur = hauteur;
			break;
		}
	}
	/**
	 * 
	 * @return la couleur de la carte
	 * @see modele.Couleurs
	 */
	public String getCouleur(){
		return this.couleur;
	}
	/**
	 * 
	 * @return la hauteur de la carte
	 */
	public int getHauteur(){
		return this.hauteur;
	}
	/**
	 * 
	 * @return la valeur de la carte (nombre de points)
	 */
	public int getValeur(){
		return valeur;
	}
	/**
	 * 
	 * @return identifiant unique de la carte
	 */
	public int getId(){
		return id;
	}
	/**
	 * 
	 * @return comportements de la carte
	 * @see modele.comportements.ComportementCarte
	 */
	public ComportementCarte getComportement(){
		return this.comportement;
	}
	
	public String toString(){
		if(hauteur==14)
			return couleur;
		else
			return hauteur+couleur;
	}
}
