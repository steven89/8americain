package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.Carte;
import modele.Joueur;
import modele.JoueursManager;

/** 
 * Met en place l'espace du jeu
 */
public class PartiePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private static PartiePanel pp = null;
	
	private ArrayList<JoueurPanel> joueurs;
	private JoueursManager jm;
	
	private JPanel panelTop;
	private JPanel panelBottom;
	private JPanel panelLeft;
	private JPanel panelRight;
	private JPanel panelCenter;
	
	//elements du panel centre
	private CarteGraphique carteVisible;
	private JLabel sensJeu;
	private JLabel infosJeu;
	
	private Color bgColor;
	/**
	 * Initialisation des composants SWING
	 * 
	 */
	private PartiePanel(){
		joueurs = new ArrayList<JoueurPanel>();
		bgColor = new Color(0,100,0);
		
		this.setBackground(bgColor);
		this.setPreferredSize(new Dimension(800,600));
		
		panelTop = new JPanel();
		panelBottom = new JPanel();
		panelLeft = new JPanel();
		panelRight = new JPanel();
		panelCenter = new JPanel();
		
		panelTop.setPreferredSize(new Dimension(600,190));
		panelBottom.setPreferredSize(new Dimension(600,190));
		panelLeft.setPreferredSize(new Dimension(265,300));
		panelRight.setPreferredSize(new Dimension(265,300));
		
		panelTop.setBackground(bgColor);
		panelBottom.setBackground(bgColor);
		panelLeft.setBackground(bgColor);
		panelRight.setBackground(bgColor);
		panelCenter.setBackground(bgColor);
		
		panelCenter.setLayout(new BorderLayout());
		
		infosJeu = new JLabel("Nouvelle partie");
		infosJeu.setHorizontalAlignment(JLabel.CENTER);
		infosJeu.setForeground(Color.WHITE);
		
		this.setLayout(new BorderLayout());
		
		this.add(panelTop, BorderLayout.NORTH);
		this.add(panelBottom, BorderLayout.SOUTH);
		this.add(panelLeft, BorderLayout.WEST);
		this.add(panelRight, BorderLayout.EAST);
		this.add(panelCenter, BorderLayout.CENTER);
	}
	/**
	 * @return un objet PartiePanel
	 */
	public static PartiePanel getInstance(){
		if(pp==null)
			pp = new PartiePanel();
		return pp;
	}
	/**
	 * Initialise l'espace de jeu en fonction du nombre de joueurs
	 * @see modele.JoueursManager#getNbJoueurs()
	 * @see modele.JoueursManager#getJoueurs()
	 */
	public void initPanel(){
		// remise a zero
		clear();
		// appele a chaque manche
		jm = JoueursManager.getInstance();
		// panel centre : infos de partie (sens de jeu, infos txt, carte visible)
		panelCenter.setBackground(new Color(0,110,0));
		sensJeu = new JLabel(RessourcesManager.getImage("sens1"));
		panelCenter.add(sensJeu, BorderLayout.NORTH);
		infosJeu.setText("Nouvelle manche");
		panelCenter.add(infosJeu, BorderLayout.SOUTH);
		carteVisible = new CarteGraphique(null , false);
		carteVisible.setHorizontalAlignment(JLabel.CENTER);
		panelCenter.add(carteVisible, BorderLayout.CENTER);
		// PANEL TOP
		if(jm.getNbJoueurs()<=4){
			panelTop.setLayout(new GridLayout(1,1,5,5));
		}
		else if(jm.getNbJoueurs()>4 && jm.getNbJoueurs()<=6){
			panelTop.setLayout(new GridLayout(1,2,5,5));
		}
		else{
			panelTop.setLayout(new GridLayout(1,3,5,5));
		}
		panelTop.setBackground(bgColor);
		
		// PANEL BOTTOM
		if(jm.getNbJoueurs()<=3){
			panelBottom.setLayout(new GridLayout(1,1,5,5));
		}
		else if(jm.getNbJoueurs()>5 && jm.getNbJoueurs()<=7){
			panelBottom.setLayout(new GridLayout(1,2,5,5));
		}
		else{
			panelBottom.setLayout(new GridLayout(1,3,5,5));
		}
		
		// PANEL RIGHT
		panelRight.setLayout(new GridLayout(1,1,5,5));
		// PANEL LEFT
		panelLeft.setLayout(new GridLayout(1,1,5,5));
		
		for(Joueur j : jm.getJoueurs()){
			joueurs.add(new JoueurPanel(j));
		}
		
		//positionnement des panels de joueurs
		switch(jm.getNbJoueurs()){
		case 2 :
			panelTop.add(joueurs.get(0));
			panelBottom.add(joueurs.get(1));
			break;
		case 3 :
			panelTop.add(joueurs.get(0));
			panelRight.add(joueurs.get(1));
			panelBottom.add(joueurs.get(2));
			break;
		case 4 :
			panelTop.add(joueurs.get(0));
			panelRight.add(joueurs.get(1));
			panelBottom.add(joueurs.get(2));
			panelLeft.add(joueurs.get(3));
			break;
		case 5 :
			panelTop.add(joueurs.get(0));
			panelTop.add(joueurs.get(1));
			panelRight.add(joueurs.get(2));
			panelBottom.add(joueurs.get(3));
			panelLeft.add(joueurs.get(4));
			break;
		case 6 :
			panelTop.add(joueurs.get(0));
			panelTop.add(joueurs.get(1));
			panelRight.add(joueurs.get(2));
			panelBottom.add(joueurs.get(4));
			panelBottom.add(joueurs.get(3));
			panelLeft.add(joueurs.get(5));
			break;
		case 7 :
			panelTop.add(joueurs.get(0));
			panelTop.add(joueurs.get(1));
			panelTop.add(joueurs.get(2));
			panelRight.add(joueurs.get(3));
			panelBottom.add(joueurs.get(5));
			panelBottom.add(joueurs.get(4));
			panelLeft.add(joueurs.get(6));
			break;
		case 8 :
			panelTop.add(joueurs.get(0));
			panelTop.add(joueurs.get(1));
			panelTop.add(joueurs.get(2));
			panelRight.add(joueurs.get(3));
			panelBottom.add(joueurs.get(6));
			panelBottom.add(joueurs.get(5));
			panelBottom.add(joueurs.get(4));
			panelLeft.add(joueurs.get(7));
			break;
		}
		this.updateUI();
	}
	/**
	 * Efface le contenu de l'espace de jeu
	 */
	public void clear(){
		panelTop.removeAll();
		panelBottom.removeAll();
		panelLeft.removeAll();
		panelRight.removeAll();
		panelCenter.removeAll();
		panelCenter.setBackground(bgColor);
		joueurs.clear();
		
		updateUI();
	}
	/**
	 * Met a jour un joueur
	 * @param i :  index du joueur a mettre a jour
	 */
	public void majJoueur(int i){
		joueurs.get(i).maj();
	}
	/**
	 * Met a jour les joueurs
	 */
	public void majJoueurs(){
		for(int i=0;i<jm.getNbJoueurs();i++){
			joueurs.get(i).maj();
		}
	}
	/**
	 * Met &agrave; jour la carte visible
	 * @param c : la carte qui est visible
	 */
	public void majCarteVisible(Carte c){
		carteVisible.setCarte(c);
	}
	/**
	 * Met a jour le texte d'info (en dessous de la carte visible)
	 * @param s : le nouveau message
	 */
	public void majInfo(String s){
		infosJeu.setText(s);
	}
	/**
	 * @param i : index du joueur
	 * @return le Joueur(graphique de type JoueurPanel)
	 */
	public JoueurPanel getJoueur(int i){
		return joueurs.get(i);
	}
	/**
	 * Met a jour le sens de rotation de la partie
	 * @param sens : true = de gauche &agrave; droite, false = de droite &agrave; gauche
	 */
	public void setSens(boolean sens){
		if(sens)
			sensJeu.setIcon(RessourcesManager.getImage("sens1"));
		else
			sensJeu.setIcon(RessourcesManager.getImage("sens0"));
	}
}
