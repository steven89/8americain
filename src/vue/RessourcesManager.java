package vue;

import java.util.HashMap;

import javax.swing.ImageIcon;

import modele.Carte;
import modele.Couleurs;

/**
 * liste des images utilisees pour le jeu
 */
public class RessourcesManager {
	
	private static HashMap<String, ImageIcon> ressources = new HashMap<String, ImageIcon>();
	
	private RessourcesManager(){
		
	}
	/**
	 * Charge toutes les images
	 */
	public static void loadRessources(){
		ressources.put("sens0", new ImageIcon(RessourcesManager.class.getResource("/vue/ressources/sens0.png")));
		ressources.put("sens1", new ImageIcon(RessourcesManager.class.getResource("/vue/ressources/sens1.png")));
		ressources.put("flipside", new ImageIcon(RessourcesManager.class.getResource("/vue/ressources/cartes/flipside.png")));
		for(int i=Couleurs.CARREAUX;i<=Couleurs.PIC;i++){
			ressources.put(Couleurs.getCouleur(i), new ImageIcon(RessourcesManager.class.getResource("/vue/ressources/cartes/"+Couleurs.getCouleur(i)+"/0.png")));
			for(int j=Carte.AS;j<=Carte.ROI;j++)
				ressources.put(j+""+Couleurs.getCouleur(i), new ImageIcon(RessourcesManager.class.getResource("/vue/ressources/cartes/"+Couleurs.getCouleur(i)+"/"+j+".png")));
		}
			for(int i=0;i<2;i++)
			ressources.put("14joker", new ImageIcon(RessourcesManager.class.getResource("/vue/ressources/cartes/joker/14.png")));
		
	}
	/**
	 * 
	 * @param key : cle de l'image a recuperer
	 * @return l'image associee a la cle
	 */
	public static ImageIcon getImage(String key){
		return ressources.get(key);
	}
}
