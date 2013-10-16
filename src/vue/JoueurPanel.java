package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.Carte;
import modele.Joueur;

/**
 * <p>Representation graphique d'un joueur</p>
 */
public class JoueurPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private ArrayList<Carte> main;
	
	private Color bgColor;
	
	private boolean isReel=false;
	
	private Joueur joueur;
	
	private JPanel panelNom;
	private JLabel labelNom;
	private JPanel panelMain;
	/**
	 * @param j : un joueur
	 * @see modele.Joueur
	 */
	public JoueurPanel(Joueur j){
		joueur = j;
		isReel = (joueur.getClass().getCanonicalName().equals("modele.JoueurReel"))?true:false;
		bgColor = new Color(0,100,0);
		this.setBackground(bgColor);
		this.setLayout(new BorderLayout());
		labelNom = new JLabel(j.getNom());
		labelNom.setForeground(Color.WHITE);
		labelNom.setHorizontalAlignment(JLabel.CENTER);
		panelNom = new JPanel();
		panelNom.setPreferredSize(new Dimension(JoueurPanel.WIDTH,20));
		panelNom.setBackground(bgColor);
		panelNom.add(labelNom);
		panelMain = new JPanel();
		panelMain.setBackground(bgColor);
		this.add(panelNom, BorderLayout.NORTH);
		this.add(panelMain, BorderLayout.CENTER);
		this.setBorder(BorderFactory.createLineBorder(new Color(0,100,0)));
		main = new ArrayList<Carte>();
	}
	/**
	 * @return le joueur
	 */
	public Joueur getJoueur(){
		return joueur;
	}
	/**
	 * <p>Mise &agrave; jour de la main affich&eacute;e lors d'une partie</p>
	 * @see CarteGraphique
	 */
	public void maj(){
		main.clear();
		main.addAll(joueur.getMain());
		panelMain.removeAll();
		chooseLayout();
		for(Carte c : main){
			if(isReel){
				CarteGraphique cg = new CarteGraphique(c, true);
				panelMain.add(cg, FlowLayout.LEFT);
				cg.addMouseListener(new CarteClicked());
			}
			else
				panelMain.add(new CarteGraphique(c, false), FlowLayout.LEFT);
		}
		panelMain.updateUI();
	}
	/**
	 * <p>Permet de choisir le layout du panel en fonction du nombre de cartes dans la main</p>
	 */
	private void chooseLayout(){
		if(main.size()<=8)
			panelMain.setLayout(new FlowLayout());
		else if(main.size()>10)
			panelMain.setLayout(new GridLayout(2, (int)Math.ceil(main.size()/2)));
		else
			panelMain.setLayout(new GridLayout(1, main.size()));
			
	}
	/**
	 * <p>Classe interne qui implemente l'interface MouseListener pour g&eacute;rer le click sur les cartes</p>
	 */
	public class CarteClicked implements MouseListener{
		/**
		 * <p>Lorsque qu'utilisateur clique sur une carte, appel la m&eacute;thode joue()</p>
		 * @param e : evenement de la souris
		 * @see modele.JoueurReel#joue(String)
		 */
		public void mouseClicked(MouseEvent e) {
			joueur.joue(((CarteGraphique)e.getSource()).toString());
		}

		public void mouseEntered(MouseEvent e) {
			
		}

		public void mouseExited(MouseEvent e) {
			
		}

		public void mousePressed(MouseEvent e) {
			
		}

		public void mouseReleased(MouseEvent e) {
			
		}
		
	}
}
