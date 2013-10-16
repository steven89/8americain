package modele;
/**
 * Validation des donnes saisies par l'utilisateur
 */
public class Validateur {
	
	private Validateur(){
		
	}
//--------------------------------------------------------------------------------------
//		Validation des entrées utilisateurs
	/**
	 * Verifie si  le type de jeu est valide
	 * @param str : type de jeu choisi
	 * @return true si le type de jeu est valide ; false si le type de jeu est invalide
	 */
	public static boolean isTypeJeuValide(String str){
		if(str.equals("c") || str.equals("coups") || str.equals("p") || str.equals("points"))
			return true;
		else 
			return false;
	}
	/**
	 * Verifie si la chaine est numerique>0
	 * @param str : la chaine saisie
	 * @return true si la chaine est numerique ; false si la chaine n'est pas numerique
	 */
	public static boolean isInt(String str){
		try{  
			if(Integer.parseInt(str)>0)
				return true;
		} catch(Exception e){  
		      return false;  
		}
		return false;
	}
	/**
	 * Verifie si la chaine est numerique>=0
	 * @param str : la chaine saisie
	 * @return true si la chaine est numerique ; false si la chaine n'est pas numerique
	 */
	public static boolean isInt0(String str){
		try{  
			if(Integer.parseInt(str)>=0)
				return true;
		} catch(Exception e){  
		      return false;  
		}
		return false;
	}
	/**
	 * Verifie si la couleur est valide
	 * @param c : le nom de la couleur
	 * @return true si la couleur est valide ; false si la couleur n'est pas valide
	 */
	public static boolean isCouleur(String c){
		if(Couleurs.exists(c))
			return true;
		return false;
	}
}
