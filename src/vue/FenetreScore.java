package vue;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import modele.CoupsManager;
import modele.Joueur;
import modele.JoueursManager;

/**
 * <p>Fen&ecirc;tre servant &agrave; afficher les scores</p>
 */
public class FenetreScore extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTabbedPane onglets;
	
	private JPanel panelTotal;
	private ArrayList<JPanel> manches;
	
	private CoupsManager cm;
	private JoueursManager jm;
	/**
	 * <p>Initialise la fenetre des scores</p>
	 * @param nom : Le titre de la fenetre de scores
	 * @see modele.JoueursManager#getNbJoueurs()
	 * @see modele.JoueursManager#getJoueurs()
	 * @see modele.CoupsManager#getNbCoups()
	 */
	public FenetreScore(String nom){
		super("Scores - "+nom);
		cm = CoupsManager.getInstance();
		jm = JoueursManager.getInstance();
		
		onglets = new JTabbedPane(SwingConstants.TOP);
		
		panelTotal = new JPanel();
		panelTotal.setLayout(new GridLayout(jm.getNbJoueurs(),2));
		for(Joueur j : jm.getJoueurs()){
			panelTotal.add(new JLabel(j.getNom()));
			panelTotal.add(new JLabel(""+j.getTotal()));
		}
		onglets.add("Totaux", panelTotal);
		
		manches = new ArrayList<JPanel>();
		for(int i=0;i<cm.getNbCoups();i++){
			manches.add(new JPanel());
			manches.get(i).setLayout(new GridLayout(jm.getNbJoueurs(),2));
			for(Joueur j : jm.getJoueurs()){
				manches.get(i).add(new JLabel(j.getNom()));
				manches.get(i).add(new JLabel(""+j.getScore(i)));
			}
			onglets.add("manche "+(i+1), manches.get(i));
		}
		
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setSize(600,300);
		this.setResizable(false);
		
		this.add(onglets);
		
		this.setLocationRelativeTo(this.getParent());
		this.setVisible(true);
	}
}
