package vue;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import modele.JoueursManager;
import modele.Partie;


/**
 *<p>D&eacute;finit la fenetre de param&eacute;trages d'une nouvelle partie</p>
 */
public class FenetreParametrage extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Partie partie;
	
	private JPanel panelJR;
	private JPanel panelJV;
	private JPanel panelChoixJeu;
	private JPanel panelNbChoixJeu;
	private JPanel panelCoups;
	private JPanel panelPoints;
	private JPanel panelNiveau;
	
	private JComboBox listNbJR;
	private JComboBox listNbJV;
	private JTextField textNbCoups;
	private JTextField textNbPoints;
	
	private JLabel labelNbJR;
	private JLabel labelNbJV;
	private JLabel labelNbCoups;
	private JLabel labelNbPoints;
	
	private JRadioButton radioChoixJeuCoups;
	private JRadioButton radioChoixJeuPoints;
    private ButtonGroup groupChoixJeu = new ButtonGroup();
    
    private JRadioButton radioFacile;
    private JRadioButton radioNormal;
    private JRadioButton radioDifficile;
    private ButtonGroup groupChoixNiveau = new ButtonGroup();
	
	private JButton btnValider;
	
	private JoueursManager jm = JoueursManager.getInstance();
	
	/**
	 * <p>Constructeur</p>
	 * <p>Initialisation des &eacute;l&eacute;ments graphiques</p>
	 */
	public FenetreParametrage(){
		super();
		
		partie = Partie.getInstance();
		
		this.setTitle("Creer une partie !");
		this.setSize(new Dimension(400, 220));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setLayout(new GridLayout(6, 1));
		
		// panels de choix du nombre de joueurs
		panelJR = new JPanel();
		
		labelNbJR = new JLabel("Nombre de Joueurs reels (0-8) : ");
		String[] tabJ = {"0","1","2","3","4","5","6","7","8"};
		listNbJR = new JComboBox(tabJ);

		
		panelJR.add(labelNbJR);
		//panelJR.add(textNbJR);
		panelJR.add(listNbJR);
		
		panelJV = new JPanel();
		
		labelNbJV = new JLabel("Nombre de Joueurs virtuel (0-8) : ");
		listNbJV = new JComboBox(tabJ);
		
		panelJV.add(labelNbJV);
		panelJV.add(listNbJV);
		
		// panel choix du niveau
		panelNiveau = new JPanel();
		
		radioFacile = new JRadioButton("Facile");
		radioNormal = new JRadioButton("Normal");
		radioNormal.setSelected(true);
		radioDifficile = new JRadioButton("Difficile");
		
		groupChoixNiveau.add(radioFacile);
		groupChoixNiveau.add(radioNormal);
		groupChoixNiveau.add(radioDifficile);
		
		panelNiveau.add(radioFacile);
		panelNiveau.add(radioNormal);
		panelNiveau.add(radioDifficile);
		
		// panel de choix du type de jeu
		panelChoixJeu = new JPanel();
		
		radioChoixJeuCoups = new JRadioButton("Aux Coups");
		radioChoixJeuPoints = new JRadioButton("Aux Points");
		
		groupChoixJeu.add(radioChoixJeuCoups);
		groupChoixJeu.add(radioChoixJeuPoints);
		
		panelChoixJeu.add(radioChoixJeuCoups);
		panelChoixJeu.add(radioChoixJeuPoints);
		
		// panel Nb coups/points
		panelNbChoixJeu = new JPanel();
		
		panelCoups = new JPanel();
		panelPoints = new JPanel();
		
		panelCoups.setVisible(false);
		panelPoints.setVisible(false);
		
		labelNbCoups = new JLabel("Nombre de manches (1+) : ");
		labelNbPoints = new JLabel("Nombre de Points (1+) : ");
		
		textNbCoups = new JTextField();
		textNbPoints = new JTextField();
		
		textNbCoups.setPreferredSize(new Dimension(30,20));
		textNbPoints.setPreferredSize(new Dimension(30,20));
		
		panelCoups.add(labelNbCoups);
		panelPoints.add(labelNbPoints);
		
		panelCoups.add(textNbCoups);
		panelPoints.add(textNbPoints);
		
		panelNbChoixJeu.add(panelCoups);
		panelNbChoixJeu.add(panelPoints);
		
		radioChoixJeuCoups.setSelected(true);
		panelCoups.setVisible(true);
		
		initRadioListeners();
		
		// bouton valider
		btnValider = new JButton("Valider !");
		
		initValiderListener();
		
		this.getContentPane().add(panelJR);
		this.getContentPane().add(panelJV);
		this.getContentPane().add(panelNiveau);
		this.getContentPane().add(panelChoixJeu);
		this.getContentPane().add(panelNbChoixJeu);
		this.getContentPane().add(btnValider);
		
		this.setLocationRelativeTo(this.getParent());
		this.setVisible(true);
	}
	
	/**
	 * <p>Ajoute un listener aux boutons radio coups et points</p>
	 * @see modele.Partie#chooseTypeJeu(String)
	 */
	public void initRadioListeners(){
		// choix jeu aux coups
		radioChoixJeuCoups.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					partie.chooseTypeJeu("c");
			}
		});
		// choix jeu aux points
		radioChoixJeuPoints.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					partie.chooseTypeJeu("p");
			}
		});
	}
	/**
	 * <p>Initalisation du listener pour le boutton Valider</p>
	 * @see #valider()
	 */
	public void initValiderListener(){
		btnValider.addActionListener (new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				valider();
			}
		});
	}
	/**
	 * <p>On affiche le panel qui contient le nombre de coups &agrave; choisir et on masque le panel qui contient le nombre de points</p>
	 */
	public void showNbCoups(){
		panelCoups.setVisible(true);
		panelPoints.setVisible(false);
	}
	/**
	 * <p>On affiche le panel qui contient le nombre de points &agrave; choisir et on masque le panel qu contient le nombre de coups</p>
	 */
	public void showNbPoints(){
		panelPoints.setVisible(true);
		panelCoups.setVisible(false);
	}
	/**
	 * <p>Affiche une erreur si le nombre de joueurs est invalide</p>
	 */
	public void nbJinvalide(){
		JOptionPane.showMessageDialog(this, "nombre de Joueurs invalide");
	}
	/**
	 * <p>Affiche une erreur si le nombre de joueurs r&eacute;els est invalide</p>
	 * 
	 */
	public void nbJRinvalide(){
		JOptionPane.showMessageDialog(this, "nombre de JR invalide ("+jm.getNbJoueursReels()+")");
	}
	/**
	 * <p>Affiche une erreur si le nombre de joueurs virtuels est invalide</p>
	 */
	public void nbJVinvalide(){
		JOptionPane.showMessageDialog(this, "nombre de JV invalide ("+jm.getNbJoueursCpu()+")");
	}
	/**
	 * <p>R&eacute;cup&eacute;ration des param&eagrave;tres entr&eacute; dans la fenetre de param&eacute;trage</p>
	 * <p>Envoie des parametres a la partie</p>
	 * @see modele.Partie#setNbJoueurs(String, String)
	 * @see modele.Partie#setTotalCoups(String)
	 * @see modele.Partie#setTotalPoints(String)
	 * @see modele.Partie#checkParams()
	 */
	public synchronized void valider(){
		//recuperation des parametres
		String nbJR = (String) listNbJR.getSelectedItem();
		String nbJV = (String) listNbJV.getSelectedItem();
		
		String nbCoups = textNbCoups.getText();
		String nbPoints = textNbPoints.getText();
		
		if(radioFacile.isSelected())
			partie.setNiveau(Partie.FACILE);
		else if(radioDifficile.isSelected())
			partie.setNiveau(Partie.DIFFICILE);
		else
			partie.setNiveau(Partie.NORMAL);
		
		partie.setNbJoueurs(nbJR, nbJV);
		
		if(panelCoups.isVisible())
			partie.setTotalCoups(nbCoups);
		else if(panelPoints.isVisible())
			partie.setTotalPoints(nbPoints);
		
		partie.checkParams();
	}
}
