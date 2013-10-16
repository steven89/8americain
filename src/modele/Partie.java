package modele;

import java.io.Serializable;
/**
 * Partie de 8 americain
 */
public class Partie extends Thread implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//singleton
	private static Partie p = null;
	// params
	private CoupsManager coupManager;
	private JoueursManager joueursManager;
	// donnees
	private int totalCoups=0, totalPoints=0;
	public final static int FACILE = 0;
	public final static int NORMAL = 1;
	public final static int DIFFICILE = 2;
	private String[] niveaux = {"Facile","Normal","Difficile"};
	private int niveau=1; 
	/**
	 * Initialisation des listes de manches et de joueurs
	 */
	private Partie(){
		joueursManager = JoueursManager.getInstance();
		coupManager = CoupsManager.getInstance();
	}
	/**
	 * 
	 * @return instance de {@link Partie}
	 */
	public static Partie getInstance(){
		if(p==null)
			p = new Partie();
		return p;
	}
	/**
	 * Reinitialise la partie
	 */
	public static void reset(){
		p = null;
		CoupsManager.reset();
		JoueursManager.reset();
	}
	/**
	 * Initialise la partie
	 * lance le thread dedi&eacute;
	 */
	public void init(){
		if(!this.isAlive())
			this.start();// démarage de la boucle de jeu(nouveau Thread)
		else
			Updateur.notifyObservers(this, "maj partieExistante");
	}
	
//--------------------------------------------------------------------------------------
//		GET
	/**
	 * @return nombre total de manches a jouer(defini par l'utilisateur)
	 */
	public int getTotalCoups(){
		return totalCoups;
	}
	/**
	 * 
	 * @return nombre total de points pour que la partie se termine (defini par l'utilisateur)
	 */
	public int getTotalPoints(){
		return totalPoints;
	}
	/**
	 * Le score du joueur ayant le score total le plus eleve
	 * utiliser quand la partie se joue au nombre de points
	 * @return score total le plus eleve dans la partie
	 */
	private int getScoreMax(){
		int tmpMax=0;
		for(Joueur j : joueursManager.getJoueurs())
			tmpMax=(tmpMax<j.getTotal())?j.getTotal():tmpMax;
		return tmpMax;	
	}
//--------------------------------------------------------------------------------------
//		paramétrage (SET)
	/**
	 * Parametrage du nombre de joueurs
	 * @param nbReels : nombre de joueurs reels
	 * @param nbCpu : nombre de joueurs virtuels
	 */
	public void setNbJoueurs(String nbReels, String nbCpu){
		if(Validateur.isInt0(nbReels))
			if(Validateur.isInt0(nbCpu))
				joueursManager.addJoueurs(Integer.parseInt(nbReels), Integer.parseInt(nbCpu), niveau);
			else
				Updateur.notifyObservers(this, "get nbJoueursCpu");
		else
			Updateur.notifyObservers(this, "get nbJoueursReels");
	}
	/**
	 * Parametrage du nombre de manches
	 * @param nb : nombre de manches
	 */
	public void setTotalCoups(String nb){
		if(Validateur.isInt(nb)){
			totalCoups = Integer.parseInt(nb);
			Updateur.notifyObservers(this, "maj nbCoups");
		}
		else
			Updateur.notifyObservers(this, "get nbCoups");
	}
	/**
	 * parametrage du score a ne pas depasser (partie au points)
	 * @param nb : nombre de points a ne pas depasser
	 */
	public void setTotalPoints(String nb){
		if(Validateur.isInt(nb)){
			totalPoints = Integer.parseInt(nb);
			Updateur.notifyObservers(this, "maj nbPoints");
		}
		else
			Updateur.notifyObservers(this, "get nbPoints");
	}
	/**
	 * Parametrage du niveau de la partie
	 * @param lvl : index du niveau
	 * utiliser les  constantes FACILE ; NORMAL ; DIFFICILE
	 */
	public void setNiveau(int lvl){
		if(lvl>=0 && lvl<niveaux.length){
			niveau = lvl;
		}
	}
	/**
	 * 
	 * @return index du niveau de difficulte
	 */
	public int getNumNiveau(){
		return niveau;
	}
	/**
	 * 
	 * @return le nom du niveau de difficulte
	 */
	public String getNiveau(){
		return niveaux[niveau];
	}
	/**
	 * Choisir le type de jeu (aux coups/points)
	 * @param type : type de jeu (coups/points)
	 */
	public void chooseTypeJeu(String type){
		if(type.equals("c") || type.equals("coups")){
			Updateur.notifyObservers(this, "get nbCoups");
		}
		else if(type.equals("p") || type.equals("points")){
			Updateur.notifyObservers(this, "get nbPoints");
		}
		else{
			Updateur.notifyObservers(this, "get typeJeu");
		}
	}
//--------------------------------------------------------------------------------------
//	BOUCLE de jeu principale
	/**
	 * Boucle de jeu principale
	 * @see modele.CoupsManager#nouveauCoup()
	 * @see modele.CoupsManager#getNbCoups()
	 */
	public void run(){
		// PARAMETRAGE DE LA PARTIE---------------------------------
		Updateur.notifyObservers(this, "get params");
		waitParams();
		//----------------------------------------------------------
		// BOUCLE DE JEU--------------------------------------------
		Updateur.notifyObservers(this, "maj newPartie");
		do{
			coupManager.nouveauCoup();
		}while((coupManager.getNbCoups()<totalCoups && totalCoups!=0) || (getScoreMax()<totalPoints && totalPoints!=0));
		// FIN DE LA PARTIE-----------------------------------------
		Updateur.notifyObservers(this, "maj finPartie");
	}
	/**
	 * Fait attendre la partie jusqu'a ce que l'utilisateur l'ai parametree
	 */
	private synchronized void waitParams(){
		while(joueursManager.getNbJoueurs()==0 || (totalCoups==0 && totalPoints==0)){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Verifie si les parametres de la partie son definies
	 * @see vue.FenetreParametrage#valider()
	 */
	public synchronized void checkParams(){
		if(!(joueursManager.getNbJoueurs()==0 || (totalCoups==0 && totalPoints==0)))
			notifyAll();
	}
}
