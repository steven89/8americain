package vue;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
/**
 * <p>Contient la structure de la fenetre d'aide avec les r&egrave;gles du jeu à l'int&eacute;rieur</p>
 */
public class FenetreRegle extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JTabbedPane onglets;
	private JPanel panelEffetCarte;
	private JPanel panelReglesG;
	
	private ArrayList<JLabel> labelsRG;
	private ArrayList<JLabel> labelsEffetCarte;
	/**
	 * Creation d'une fen&ecirc;tre pour afficher les r&egrave; du jeu
	 */
	public FenetreRegle(){
		super("Regles du jeu de 8 americain");
		// reglages fenetre
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setSize(800,400);
		this.setResizable(false);
		// creation des onglets
		onglets = new JTabbedPane(SwingConstants.TOP);
		
		// onglet regles generales
		panelReglesG = new JPanel();
		labelsRG = new ArrayList<JLabel>();
		labelsRG.add(new JLabel("Le jeu permet de joueur selon de types : aux nombres points ou aux nombres de manches"));
		labelsRG.add(new JLabel("Pour se debarrasser de ses cartes, il faut que la carte que l'on souhaite poser soit de la meme couleur ou de la meme hauteur que la carte sur la carte visible"));
		labelsRG.add(new JLabel("La partie se termine lorsque un joueur a atteint le nombre limite de point ou que le nombre de manches a ete effectuee"));
		labelsRG.add(new JLabel("Le gagnant est celui qui possede le moins de points a la fin de la partie"));
		panelReglesG.setLayout(new GridLayout(labelsRG.size(),1));
	    for(JLabel l : labelsRG)
	    	panelReglesG.add(l);
	    
	    JScrollPane scrollPaneRG = new JScrollPane(panelReglesG);
		
		// onglet effets des cartes
		panelEffetCarte = new JPanel();
		labelsEffetCarte = new ArrayList<JLabel>();
		labelsEffetCarte.add(new JLabel("Carte 2 : Le joueur suivant pioche 2 cartes et passe son tour"));
		labelsEffetCarte.add(new JLabel("Carte 7 : Le joueur suivant pioche une carte dans la main du joueur qui pose la carte et passe son tour"));
		labelsEffetCarte.add(new JLabel("Carte 8 : Change la couleur du jeu"));
		labelsEffetCarte.add(new JLabel("Carte 10 : Change le sens de jeu"));
		labelsEffetCarte.add(new JLabel("Joker : Change la couleur du jeu, le joueur suivant pioche 5 cartes et passe son tour"));
		panelEffetCarte.setLayout(new GridLayout(labelsEffetCarte.size(),1));
		for(JLabel l : labelsEffetCarte)
			panelEffetCarte.add(l);
		
	    JScrollPane scrollPaneEffetCarte = new JScrollPane(panelEffetCarte);
	    
	    onglets.add("Regles Generales",scrollPaneRG);
		onglets.add("Effet des cartes",scrollPaneEffetCarte);
		
		onglets.setVisible(true);
		
		this.add(onglets);
		
		this.setLocationRelativeTo(this.getParent());
		this.setVisible(true);
	}
}
