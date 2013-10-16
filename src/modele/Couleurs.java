package modele;
/**
 * Liste des couleurs disponibles dans le jeu
 */
public class Couleurs {
	
	public final static int CARREAUX = 0;
	public final static int COEUR = 1;
	public final static int TREFLE = 2;
	public final static int PIC = 3;
	public final static int JOKER = 4;
	
	private static String[] couleurs = {"carreaux","coeur","trefle","pic","joker"};
	
	private Couleurs(){
		
	}
	/**
	 * @param i : index de la couleur (utiliser les constantes)
	 * @return le nom de la couleur
	 */
	public static String getCouleur(int i){
		if(i<couleurs.length && i>=0)
			return couleurs[i];
		return null;
	}
	/**
	 * Recuperer l'index de la couleur avec son nom
	 * @param c le nom de la couleur
	 * @return index de la couleur
	 */
	public static int getIndexOf(String c){
		for(int i=0;i<couleurs.length;i++)
			if(couleurs[i].equals(c))
				return i;
		return 0;
	}
	/**
	 * Verifie l'existance de la couleur
	 * @param c nom de la couleur
	 * @return true si la couleur existe ; false si la couleur n'existe pas
	 */
	public static boolean exists(String c){
		// couleurs disponibles au choix (8 ou joker)
		for(int i=0; i<couleurs.length-1; i++)
			if(couleurs[i].equals(c))
				return true;
		return false;
	}
	/**
	 * 
	 * @return le nombre de couleurs disponibles
	 */
	public static int nbCouleurs(){
		return couleurs.length;
	}
}
