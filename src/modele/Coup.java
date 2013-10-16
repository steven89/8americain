package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Manche du jeu
 */
public class Coup implements EffetCarte, InfosCpu, Serializable {
	private static final long serialVersionUID = 1L;
	
	private JoueursManager joueursManager;
	// CARTES
	private ArrayList<Carte> talon = new ArrayList<Carte>(); // liste des cartes à piocher
	private ArrayList<Carte> pile = new ArrayList<Carte>(); // liste des cartes posées par les joueurs
	private Carte bergere;
	
	private int currentJoueur=0, nbCartesPiochees=0, cumul=0;
	private boolean sens = true; // sens du jeu true = + ; false = -;
	
	private boolean effetApplique=true;// init a true, pour la verification de la compatibilité de l'AS (si la bergere est un AS)
	/**
	 * @see modele.JoueursManager
	 */
	public Coup(){
		joueursManager = JoueursManager.getInstance();
		joueursManager.shuffle();
	}
	
	
	/**
	 * Initialise le talon avec 2 jeu de cartes
	 */
	private void initTalon(){
		// init paquet de carte
		for(int t=0;t<2;t++){// 2 paquets
			for(int i=Couleurs.CARREAUX;i<=Couleurs.PIC;i++)
				for(int j=Carte.AS;j<=Carte.ROI;j++)
					talon.add(new Carte(j,i));
			for(int i=0;i<2;i++) // 2 joker
				talon.add(new Carte(Carte.JOKER,Couleurs.JOKER));
		}
		// mélange
		melangerCartes();
		// distribution
		distribuerCartes();
		// init bergère
		do{
			pile.add(talon.get(0));
			bergere = pile.get(0);
			talon.remove(0);
		} while(bergere.getHauteur()==14);
	}
	/**
	 * Melanger les cartes du talon
	 */
	private void melangerCartes(){
		Collections.shuffle(talon);
		Collections.shuffle(talon);
		Collections.shuffle(talon);
	}
	/**
	 * Distrubuer les cartes aux joueurs
	 */
	private void distribuerCartes(){
		joueursManager.resetMains();
		// a changer 5 -> 8
		for(int i=0;i<8;i++){
			for(Joueur j : joueursManager.getJoueurs()){
				j.addCarte(talon.get(0));
				talon.remove(0);
			}
		}
	}
	/**
	 * 
	 * @return la bergere (premiere carte de la manche a etre posee dans la pile)
	 */
	public Carte getBergere(){
		return bergere;
	}
	/**
	 * @return la carte actuellement en haut de la pile
	 */
	public Carte getCarteVisible(){
		return pile.get(0);
	}
	/**
	 * 
	 * @return le nombre de carte piochees
	 */
	public int getNbCartesPiochees(){
		return nbCartesPiochees;
	}
	/**
	 * 
	 * @return le sens actuel du jeu
	 */
	public boolean getSens(){
		return sens;
	}
	/**
	 * Piocher une carte dans le talon
	 * @return la carte qui a ete piochee
	 */
	public Carte piocher(){
		Carte tmpCarte;// carte retournee
		try{
			tmpCarte = talon.get(0);
		} catch(IndexOutOfBoundsException e){
			// retournement du packet
			Carte tmpDelete;// sauvegarde de la carte visible
			tmpDelete = pile.get(0);
			pile.remove(0);// suppression de la carte visible
			talon = pile;
			pile.clear();
			pile.add(tmpDelete);// remise de la carte visible dans la pile
			// mélange
			melangerCartes();
			// reuperation de la carte
			tmpCarte = talon.get(0);
		}
		talon.remove(0);
		return tmpCarte;
	}
	/**
	 * Poser une carte sur la pile
	 * @param c la carte a poser
	 */
	public void poserCarte(Carte c){
		pile.add(0, c);
	}
	/**
	 * Verifie si un joueur n'a plus de carte
	 * @return true si au moins un joueur n'a aucune carte dans sa main ; false si tous les joueurs ont des cartes dans leur main
	 */
	public boolean joueurHasNoCarte(){
		for(Joueur j : joueursManager.getJoueurs()){
			if(j.main.size()==0)
				return true;
		}
		return false;
	}
	
//---------------------------------------------------------------------------------------------
	/**
	 * Boucle de jeu de la manche
	 * @see modele.JoueursManager#getJoueur(int)
	 * @see modele.JoueursManager#getJoueurs()
	 * @see modele.Joueur#updateScores()
	 * @see modele.Joueur#getMain()
	 * 
	 */
	public void lancer(){
		// DEBUT DE LA MANCHE
		initTalon();
		Updateur.notifyObservers(this, "maj newCoup");
		do{
			// debut du tour de jeu
			Carte carteJouee;
			Updateur.notifyObservers(this, "maj carteVisible");
			do{
				// le joueur joue sa carte
				if(hasCarteCompatible(getCurrentJoueur().getMain()) && !getCurrentJoueur().doitPasser()){
						carteJouee = getCurrentJoueur().jouer();
				}
				else{
					carteJouee=null;
					break;
				}
			} while(!getCarteVisible().getComportement().isCompatible(carteJouee));// verification de la validité de la carte
			
			if(carteJouee==null){
				// pas de carte jouee = joueur pioche
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(getCurrentJoueur().doitPasser()){
					getCurrentJoueur().addCarte(piocher());
					getCurrentJoueur().doitPasser(false);
				}
				getCurrentJoueur().addCarte(piocher());
				if(getCurrentJoueur().getClass().getCanonicalName().equals("Modele.JoueurReel"))
					Updateur.notifyObservers(this, "maj joueurPioche");
				else
					Updateur.notifyObservers(this, "maj joueurCpuPioche");
			}
			else{
				poserCarte(carteJouee);
				getCurrentJoueur().carteValidee(carteJouee);
				Updateur.notifyObservers(this, "maj cartePosee");
				effetApplique = false;
			}
			// application de l'effet de la carte posee
			if(getCarteVisible().getId()!=getBergere().getId() && !effetApplique && !joueurHasNoCarte()){// si la carte visible n'est pas la bergere & l'effet n'a pas encore ete applique
				// voir pour effets cumulables de l'as
				getCarteVisible().getComportement().doEffet();
			}
			nextJoueur();
			//fin du tour de jeu
		}while(!joueurHasNoCarte());
		// FIN DE LA MANCHE
		// calcul des scores
		for(Joueur j : joueursManager.getJoueurs()){
			j.updateScores();
		}
		Updateur.notifyObservers(this, "maj finCoup");
	}
	/**
	 * 
	 * @return le joueur courant
	 */
	public Joueur getCurrentJoueur(){
		return joueursManager.getJoueur(currentJoueur);
	}
	/**
	 * 
	 * @return index du joueur courant
	 */
	public int getCurrentJoueurIndex(){
		return currentJoueur;
	}
	/**
	 * Fait passer l'index du joueur courant(currentJoueur) au joueur suivant
	 */
	private void nextJoueur(){
		if(sens){
			currentJoueur++;
			if(currentJoueur>=joueursManager.getNbJoueurs())
				currentJoueur=0;
		}
		else{
			currentJoueur--;
			if(currentJoueur<0)
				currentJoueur=joueursManager.getNbJoueurs()-1;
		}
	}
	/**
	 * @return le joueur suivant
	 */
	public Joueur getNextJoueur(){
		int next = currentJoueur;
		if(sens){
			next++;
			if(next>=joueursManager.getNbJoueurs())
				next=0;
		}
		else{
			next--;
			if(next<0)
				next=joueursManager.getNbJoueurs()-1;
		}
		return joueursManager.getJoueur(next);
	}
	/**
	 * 
	 * @return index du joueur suivant
	 */
	public int getNextJoueurIndex(){
		int next = currentJoueur;
		if(sens){
			next++;
			if(next>=joueursManager.getNbJoueurs())
				next=0;
		}
		else{
			next--;
			if(next<0)
				next=joueursManager.getNbJoueurs()-1;
		}
		return next;
	}
	/**
	 * Verifie s'il existe une carte compatible avec la carte visible dans la liste (main d'un joueur)
	 * @param cartes liste des cartes de la main d'un joueur
	 * @return true si il existe un carte compatible dans la liste ; false si aucune carte n'est compatible
	 */
	public boolean hasCarteCompatible(ArrayList<Carte> cartes){
		for(Carte c : cartes){
			if(getCarteVisible().getComportement().isCompatible(c))
				return true;
		}
		return false;
	}
	/**
	 * Verifie s'il existe un AS dans la liste (main d'un joueur)
	 * @param cartes liste des cartes de la mian d'un joueur
	 * @return true s'il exite un AS dans la liste ; false s'il n'existe pas d'AS dans la liste
	 */
	public boolean hasAs(ArrayList<Carte> cartes){
		for(Carte c : cartes){
			if(c.getHauteur()==1)
				return true;
		}
		return false;
	}
	/**
	 * Le joueur courant annonce qu'il ne lui reste qu'une carte (a annocer avant de poser la carte)
	 */
	public void direCarte(){
		getCurrentJoueur().aDitCarte(true);
	}
	/**
	 * Signaler qu'un joueur a oublier de dire Carte alors qu'il ne lui reste qu'une carte dans la main
	 */
	public void signalerOubli(){
		ArrayList<Joueur> tmpList = new ArrayList<Joueur>();
		for(Joueur j : joueursManager.getJoueurs())
			if(j.getMain().size()==1)
				tmpList.add(j);
		if(tmpList.size()!=0)
			for(Joueur j : tmpList)
				if(!j.aDitCarte())
					j.doitPasser(true);
	}
//---------------------------------------------------------------------
//		EFFET CARTE
	/**
	 * @see modele.EffetCarte#isEffetApplique()
	 */
	public boolean isEffetApplique(){
		return effetApplique;
	}
	/**
	 * @see modele.EffetCarte#toogleSens()
	 */
	public void toogleSens(){
		sens=(sens)?false:true;
		Updateur.notifyObservers(this, "maj changementSens");
		effetApplique = true;
	}
	/**
	 * @see modele.EffetCarte#fairePasserTour()
	 */
	public void fairePasserTour(){
		nextJoueur();
		Updateur.notifyObservers(this, "maj fairePasserTour");
		effetApplique = true;
	}
	/**
	 * @see modele.EffetCarte#fairePiocher(int)
	 */
	public void fairePiocher(int nb){
		nbCartesPiochees = nb;
		for(int i=0;i<nb;i++){
			getNextJoueur().addCarte(piocher());
		}
		Updateur.notifyObservers(this, "maj fairePiocher");
		nbCartesPiochees = 0;
		fairePasserTour();
	}
	/**
	 * @see modele.EffetCarte#fairePiocherMain()
	 */
	public void fairePiocherMain(){
		Random rand = new Random();
		getNextJoueur().addCarte(getCurrentJoueur().piocherCarte(rand.nextInt(getCurrentJoueur().getMain().size())));
		Updateur.notifyObservers(this, "maj fairePiocherMain");
		fairePasserTour();
	}
	/**
	 * @see modele.EffetCarte#fairePiocherCumul(int)
	 */
	public void fairePiocherCumul(int nb){
		cumul += nb;
		if(!hasAs(getNextJoueur().getMain())){
			nbCartesPiochees = cumul;
			for(int i=0;i<cumul;i++){
				getNextJoueur().addCarte(piocher());
			}
			Updateur.notifyObservers(this, "maj fairePiocher");
			nbCartesPiochees = 0;
			cumul = 0;
			fairePasserTour();
			effetApplique = true;
		}	
	}
	/**
	 * @see modele.EffetCarte#choixCouleur()
	 */
	public String choixCouleur(){
		effetApplique = true;
		return getCurrentJoueur().choisirCouleur();
	}
}
