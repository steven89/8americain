package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Liste des joueurs de la partie
 * @see modele.Joueur
 */
public class JoueursManager implements Serializable {
	private static final long serialVersionUID = 1L;

	//singleton
	private static JoueursManager jm = null;
	//donnees
	private ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
	private int nbJoueursReels=0, nbJoueursCpu=0;
	
	private JoueursManager(){
		
	}
	/**
	 * 
	 * @return instance de {@link JoueursManager}
	 */
	public static JoueursManager getInstance(){
		if(jm==null)
			jm = new JoueursManager();
		return jm;
	}
	/**
	 * reset de la liste des joueurs
	 * utiliser pour recommencer une partie
	 */
	public static void reset(){
		jm = null;
	}

//-------------------------------------------------------------
//		GET
	/**
	 * R&eacute;cup&egrave;re une liste de joueurs
	 * @return la liste de joueurs
	 */
	public ArrayList<Joueur> getJoueurs(){
		return joueurs;
	}
	/**
	 * Recuperer un joueur a partir de son index
	 * @param i : index du joueur
	 * @return le joueur a l'index i
	 */
	public Joueur getJoueur(int i){
		return joueurs.get(i);
	}
	/**
	 * 
	 * @return nombre de joueurs dans la partie
	 */
	public int getNbJoueurs(){
		return joueurs.size();
	}
	/**
	 * 
	 * @return nombre de joueurs reels de la partie
	 */
	public int getNbJoueursReels(){
		return nbJoueursReels;
	}
	/**
	 * 
	 * @return nombre de joueurs virtuels de la partie
	 */
	public int getNbJoueursCpu(){
		return nbJoueursCpu;
	}
	
//-------------------------------------------------------------
//		Fonctions
	/**
	 * Ajoute les joueurs
	 * @param reel : nombre de joueurs reels a ajouter
	 * @param cpu : nombre de joueurs virtuels a ajouter
	 * @param lvlCpu : niveau des joueurs virtuels (=niveau de la partie)
	 * @see modele.Partie
	 */
	public void addJoueurs(int reel, int cpu, int lvlCpu){
		if(reel+cpu>=2 && reel+cpu<=8)// 2 joueurs min et 8joueurs au max
			if(reel>=0 && reel<=8)
				if(cpu>=0 && cpu<=8){
					if(reel!=0)
						addJoueursReels(reel);
					if(cpu!=0)
						addJoueursCpu(cpu, lvlCpu);
				}
				else
					Updateur.notifyObservers(this, "nbJoueursCpuInvalide");
			else
				Updateur.notifyObservers(this, "nbJoueursReelsInvalide");
		else
			Updateur.notifyObservers(this, "nbJoueursInvalide");
	}
	/**
	 * Ajoute les joueurs reels
	 * @param nb : nombre de joueurs reels
	 */
	private void addJoueursReels(int nb){
		for(int i=0;i<nb;i++){
			joueurs.add(new JoueurReel("Joueur"+(nbJoueursReels+1)));
			nbJoueursReels++;
		}
	}
	/**
	 * Ajoute les joueurs virtuels
	 * @param nb : nombre de joueurs virtuels
	 * @param lvl : niveau des joueurs virtuels (=niveau de la partie)
	 * @see modele.Partie
	 */
	private void addJoueursCpu(int nb, int lvl){
		for(int i=0;i<nb;i++){
			joueurs.add(new JoueurCpu("Cpu"+(nbJoueursCpu+1),lvl));
			nbJoueursCpu++;
		}
	}
	/**
	 * Melange la liste des joueurs
	 * utiliser au debut de chaque manche
	 */
	public void shuffle(){
		Collections.shuffle(joueurs);
	}
	/**
	 * reset les mains des joueurs
	 */
	public void resetMains(){
		for(Joueur j : joueurs){
			j.resetMain();
		}
	}
}
