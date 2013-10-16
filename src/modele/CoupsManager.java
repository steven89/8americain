package modele;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Liste des manches de la partie
 * @see modele.Coup
 */
public class CoupsManager implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//singleton
	private static CoupsManager cc = null;
	// donnees
	private ArrayList<Coup> coups = new ArrayList<Coup>();
	private int currentCoup=0;
	/**
	 * initialisation du compteur de manches
	 */
	private CoupsManager(){
		currentCoup = 0;
	}
	/**
	 * 
	 * @return instance {@link CoupsManager}
	 */
	public static CoupsManager getInstance(){
		if(cc==null)
			cc = new CoupsManager();
		return cc;
	}
	/**
	 * reset de la liste des manches
	 * utiliser pour recommencer une partie
	 */
	public static void reset(){
		cc = null;
	}
	/**
	 * Lancement d'une nouvelle manche
	 */
	public void nouveauCoup(){
		coups.add(new Coup());
		coups.get(currentCoup).lancer();
		currentCoup++;
	}
	/**
	 * 
	 * @return manche actuelle
	 */
	public Coup getCurrentCoup(){
		return coups.get(currentCoup);
	}
	/**
	 * 
	 * @return nombre de manches creees
	 */
	public int getNbCoups(){
		return coups.size();
	}
	/**
	 * 
	 * @return numero de la manche actuelle
	 */
	public int getNumCurrentCoup(){
		return currentCoup+1;
	}
}
