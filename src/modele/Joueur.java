package modele;

import java.io.Serializable;
import java.util.ArrayList;

import modele.exceptions.CarteIntrouvable;

/**
 * Joueur de la partie
 */
public abstract class Joueur implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected ArrayList<Carte> main = new ArrayList<Carte>();
	protected String nom="";
	
	private ArrayList<Integer> scores = new ArrayList<Integer>();
	private int total=0;
	/**
	 * Demande au joueur de jouer une carte de sa main
	 * @return la carte jouee
	 */
	abstract public Carte jouer();
	/**
	 * Fait jouer la carte de nom c 
	 * @param c : nom de la carte a jouer
	 * @see modele.Carte#toString()
	 */
	abstract public void joue(String c);
	/**
	 * Demande au joueur de choisir une couleur
	 * @return le nom de la couleur choisie
	 */
	abstract public String choisirCouleur();
	/**
	 * Fait choisir la couleur au joueur
	 * @param c : nom de la couleur a choisir
	 */
	abstract public void choixCouleur(String c);
	
	private boolean aDitCarte = false;
	private boolean doitPasser = false;
	/**
	 * Verifie si le joueur a annonce qu'il ne lui reste qu'une carte
	 * @return true si le joueur a fait l'annonce ; false si le joueur n'a pas fait l'annonce
	 */
	public boolean aDitCarte(){
		return aDitCarte;
	}
	/**
	 * Modifie l'etat de l'annonce (dire carte)
	 * @param b : etat de l'annoce
	 */
	public void aDitCarte(boolean b){
		aDitCarte = b;
	}
	/**
	 * Verifie si le joueur doit passer son tour
	 * @return true si le joueur doit passer ; false si le joueur ne peut jouer
	 */
	public boolean doitPasser(){
		return doitPasser;
	}
	/**
	 * Modifie la possibilite de jouer
	 * @param b : etat de la possibilite de jouer
	 */
	public void doitPasser(boolean b){
		doitPasser = b;
	}
	/**
	 * 
	 * @return la liste des cartes dans la main du joueur
	 */
	public ArrayList<Carte> getMain(){
		return this.main;
	}
	/**
	 * Ajoute une carte dans la main du joueur
	 */
	public void addCarte(Carte c){
		this.main.add(c);
	}
	/**
	 * 
	 * @return le nom du joueur
	 */
	public String getNom(){
		return nom;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return nom;
	}
	/**
	 * 
	 * @return la main du joueur sous forme de chaine de caractere
	 */
	public String mainToString(){
		String m="";
		for(Carte c : main){
			m+=c.toString()+"   ";
		}
		return m;
	}
	/**
	 * 
	 * @param nom : le nom de la carte 
	 * @see modele.Carte#toString()
	 * @return la carte correspondant au nom
	 * @throws CarteIntrouvable : si le nom de la carte ne correspond a	aucune carte de la main du joueur
	 */
	protected Carte getCarte(String nom) throws CarteIntrouvable{
		for(Carte c : main){
			if(c.toString().equals(nom))
				return c;
		}
		throw new CarteIntrouvable();
	}
	/**
	 * Piocher une carte dans la main du joueur
	 * @param i : index de la carte a piocher
	 * @return la carte piochee
	 */
	public Carte piocherCarte(int i){
		Carte tmpCarte = main.get(i);
		main.remove(i);
		return tmpCarte;
	}
	
	/**
	 * La carte que le joueur a poser a ete validee par la manche, on la supprime de sa main
	 */
	public void carteValidee(Carte c){
		for(int i=0;i<main.size();i++){
			if(main.get(i).equals(c))
				main.remove(i);
		}
	}
	/**
	 * Calcul du score du joueur dans la manche actuelle
	 * @return le score du joueur
	 */
	private int calculScores(){
		int tmpScore=0;
		if(main.size()!=0)
			for(Carte c : main){
				tmpScore += c.getValeur();
			}
		return tmpScore;
	}
	/**
	 * Met a jour les scores du joueur : ajout du score de la manche dans la liste des scores + maj du score total
	 */
	public void updateScores(){
		scores.add(calculScores());
		total += calculScores();
	}
	/**
	 * 
	 * @return le score total du joueur
	 */
	public int getTotal(){
		return total;
	}
	/**
	 * 
	 * @return la liste des scores pour chaque manche
	 */
	public ArrayList<Integer> getScores(){
		return scores;
	}
	/**
	 * 
	 * @param i : index de la manche
	 * @return le score du joueur pour la manche i
	 */
	public int getScore(int i){
		return scores.get(i);
	}
	/**
	 * Supprime toutes les cartes de la main du joueur (en fin de manche)
	 */
	public void resetMain(){
		main = new ArrayList<Carte>();
	}
}