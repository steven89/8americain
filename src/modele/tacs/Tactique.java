package modele.tacs;

import java.io.Serializable;
import java.util.ArrayList;

import modele.Carte;
import modele.Couleurs;
import modele.CoupsManager;
import modele.InfosCpu;

/**
 * Strategie des joueurs virtuels
 */
public abstract class Tactique implements CpuTacs, Serializable {
	private static final long serialVersionUID = 1L;
	
	protected ArrayList<Carte> mainCompatible = new ArrayList<Carte>();
	protected ArrayList<Carte> main = new ArrayList<Carte>();
	protected InfosCpu coup;
	/**
	 * Recupere les cartes compatibles de la main
	 */
	protected void extractCartesCompatibles(){
		coup = CoupsManager.getInstance().getCurrentCoup();
		mainCompatible.clear();
		for(Carte c : main){
			if(coup.getCarteVisible().getComportement().isCompatible(c))
				mainCompatible.add(c);
		}
	}
	/**
	 * Verifie s'il existe une carte normale dans les cartes compatibles
	 * @see #extractCartesCompatibles()
	 * @return true s'il existe une carte normale
	 */
	protected boolean existsCarteN(){
		for(Carte c : mainCompatible)
			if(c.getComportement().getClass().getCanonicalName().equals("modele.comportements.CompN"))
				return true;
		return false;
	}
	/**
	 * Verifie s'il existe un 2 dans les cartes compatibles
	 * @see #extractCartesCompatibles()
	 * @return true s'il existe un 2
	 */
	protected boolean existsCarte2(){
		for(Carte c : mainCompatible)
			if(c.getHauteur()==2)
				return true;
		return false;
	}
	/**
	 * Verifie s'il existe un 7 dans les cartes compatibles
	 * @see #extractCartesCompatibles()
	 * @return true s'il existe un 7
	 */
	protected boolean existsCarte7(){
		for(Carte c : mainCompatible)
			if(c.getHauteur()==7)
				return true;
		return false;
	}
	/**
	 * Verifie s'il existe un 8 dans les cartes compatibles
	 * @see #extractCartesCompatibles()
	 * @return true s'il existe un 8
	 */
	protected boolean existsCarte8(){
		for(Carte c : mainCompatible)
			if(c.getHauteur()==8)
				return true;
		return false;
	}
	/**
	 * Verifie s'il existe un 10 dans les cartes compatibles
	 * @see #extractCartesCompatibles()
	 * @return true s'il existe un 10
	 */
	protected boolean existsCarte10(){
		for(Carte c : mainCompatible)
			if(c.getHauteur()==10)
				return true;
		return false;
	}
	/**
	 * Verifie s'il existe un AS dans les cartes compatibles
	 * @see #extractCartesCompatibles()
	 * @return true s'il existe un AS
	 */
	protected boolean existsCarteAs(){
		for(Carte c : mainCompatible)
			if(c.getHauteur()==1)
				return true;
		return false;
	}
	/**
	 * Verifie s'il existe un Joker dans les cartes compatibles
	 * @see #extractCartesCompatibles()
	 * @return true s'il existe un Joker
	 */
	protected boolean existsCarteJoker(){
		for(Carte c : mainCompatible)
			if(c.getHauteur()==14)
				return true;
		return false;
	}
	/**
	 * Recupere les cartes compatibles & normales
	 */
	protected void extractCarteN(){
		ArrayList<Carte> tmpMain = new ArrayList<Carte>(mainCompatible);
		mainCompatible.clear();
		for(Carte c : tmpMain)
			if(c.getComportement().getClass().getCanonicalName().equals("modele.comportements.CompN"))
				mainCompatible.add(c);
		tmpMain = null;
	}
	/**
	 * Recupere la carte de la mainCompatibles
	 * @param h : la hauteur de la carte a recuperer
	 * @return la carte recuperee
	 */
	protected Carte getCarte(int h){
		for(Carte c : mainCompatible)
			if(c.getHauteur()==h)
				return c;
		return null;
	}
	/**
	 * Recupere la couleur la plus presente dans la main
	 * @return la couleur trouvee
	 */
	protected String maxCouleur(){
		int[] couleurs = new int[Couleurs.nbCouleurs()];
		for(Carte c : main){
			couleurs[Couleurs.getIndexOf(c.getCouleur())]++;
		}
		int max=0;
		int index=0;
		for(int i=0;i<couleurs.length-1;i++){
			if(max<couleurs[i]){
				max = couleurs[i];
				index = i;
			}
		}
			
		return Couleurs.getCouleur(index);
	}
	/**
	 * Recupere la couleur la moins presente dans la main du joueur suivant
	 * @return la couleur trouvee
	 */
	protected String getMinCouleurNextJoueur(){
		int[] couleurs = new int[Couleurs.nbCouleurs()];
		for(Carte c : coup.getNextJoueur().getMain()){
			couleurs[Couleurs.getIndexOf(c.getCouleur())]++;
		}
		int min=100;
		int index=0;
		for(int i=0;i<couleurs.length-1;i++){
			if(min>couleurs[i]){
				min = couleurs[i];
				index = i;
			}
		}
		return Couleurs.getCouleur(index);
	}
}
