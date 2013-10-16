package vue;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

import modele.*;

/**
 * <p>Fenetre de jeu principale</p>
 */
public class Graphique implements Observer{
	//singleton
	private static Graphique g;
	
	//PARTIE
	private Partie partie;
	private JoueursManager jm;
	private CoupsManager cm;
	
	//fenetre principale
	private JFrame fenetre = null;
	
	// MENU BAR
	private JMenuBar barreMenu;
	private JMenu menuFichier;
	private JMenu menuAide;
	//private JMenuItem itemVersion;
	private JMenuItem itemNewJeu;
	private JMenuItem itemRegles;
	private JMenuItem itemExit;
	
	//fenetre parametrage de partie
	private FenetreParametrage fenetreDemarrer;
	
	private JPanel panelFond;
	private PartiePanel panelPartie;
	
	// boutons 
	private JButton btnOublie;
	private JButton btnCarte;
	/**
	 * singleton
	 * @return l'instance unique de Graphique
	 */
	public static Graphique getInstance(){
		if(g==null)
			g = new Graphique();
		return g;
	}
	
	/**
	 * <p>Constructeur o&ugrave; l'on initialise la fen&ecirc;tre de jeu.</p>
	 */
	private Graphique(){
		Updateur.addObserver(this);
		RessourcesManager.loadRessources();
		//Fenetre principale
		fenetre = new JFrame();
		fenetre.setTitle("8 Americain");
		fenetre.setSize(850,700);
		fenetre.setResizable(false);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panelFond = new JPanel();
		panelFond.setBackground(new Color(0,100,0));
		panelPartie = PartiePanel.getInstance();
		panelFond.add(panelPartie);
		//bouton dire carte
		btnCarte = new JButton("Dire 'Carte'");
		btnCarte.setBackground(new Color(0,90,0));
		btnCarte.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCarte.setPreferredSize(new Dimension(200,30));
		btnCarte.setForeground(Color.WHITE);
		btnCarte.setBorderPainted(false);
		btnCarte.setFocusPainted(false);
		btnCarte.setVisible(false);
		panelFond.add(btnCarte);
		//bouton signaler oublie carte
		btnOublie = new JButton("Signaler oublie de 'Carte'");
		btnOublie.setBackground(new Color(0,90,0));
		btnOublie.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnOublie.setPreferredSize(new Dimension(250,30));
		btnOublie.setForeground(Color.WHITE);
		btnOublie.setBorderPainted(false);
		btnOublie.setFocusPainted(false);
		btnOublie.setVisible(false);
		panelFond.add(btnOublie);
		fenetre.setContentPane(panelFond);
		
		initMenuBar(); 
		initListeners();
		
		fenetre.setLocationRelativeTo(fenetre.getParent());
		fenetre.setVisible(true);
	}
	
	/**
	 * <p>Initialisation de la barre de menu</p> 
	 */
	public void initMenuBar(){
		
		barreMenu = new JMenuBar();
		
		//MENUS
		menuFichier = new JMenu("Fichier");
		menuFichier.setMnemonic(KeyEvent.VK_F);
		menuAide = new JMenu("Aide");
		menuAide.setMnemonic(KeyEvent.VK_A);
		
		//ITEMS
		// fichier
		itemNewJeu = new JMenuItem("Nouveau Jeu");
		itemNewJeu.setMnemonic(KeyEvent.VK_N);
		itemNewJeu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK));
		itemExit = new JMenuItem("Quitter le jeu");
		itemExit.setMnemonic(KeyEvent.VK_Q);
		itemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,InputEvent.CTRL_MASK));
		// aide
		itemRegles = new JMenuItem("Regles du Jeu");
		itemRegles.setMnemonic(KeyEvent.VK_R);
		itemRegles.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,InputEvent.CTRL_MASK));
		
		// AJOUT des ITEMS dans MENUS
		menuFichier.add(itemNewJeu);
		menuFichier.add(itemExit);
		
		menuAide.add(itemRegles);
		//menuAide.add(itemVersion);
		
		//AJOUT des MENUS dans MENUBAR
		barreMenu.add(menuFichier);
		barreMenu.add(menuAide);
		
		//AJOUT MENUBAR dans FENETRE
		fenetre.setJMenuBar(barreMenu);
	}
	
	/**
	 * <p>Initialisation des listeners</p>
	 */
	private void initListeners(){
		
		// ITEM EXIT
		itemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		// ITEM REGLES
		itemRegles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FenetreRegle();
			}
		});
		
		//ITEM NOUVEAU JEU
		itemNewJeu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				partie = Partie.getInstance();
				partie.init();
				jm = JoueursManager.getInstance();
				cm = CoupsManager.getInstance();
			}
		});
		
		// LISTENERS BOUTON
		btnOublie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cm.getCurrentCoup().signalerOubli();
			}
		});
		btnCarte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cm.getCurrentCoup().direCarte();
			}
		});
	}
	// fin initListeners()
	
//	Observer
	/**
	 * <p>recuperation des informations envoyees par le modele</p>
	 * @param obj : Un objet de n'importe quel type
	 * @param msg : Une chaine de caract&egrave;
	 */
	public synchronized void update(Object obj, String msg){
		// PARTIE
		if(obj.getClass().getCanonicalName().equals("modele.Partie")){
			// PATTERNS-------------------
			Pattern patGet = Pattern.compile("get ([A-Za-z]+)");
			Matcher matchGet = patGet.matcher(msg);
			
			Pattern patMaj = Pattern.compile("maj ([A-Za-z]+)");
			Matcher matchMaj = patMaj.matcher(msg);
			
			//----------------------------
			if(matchGet.matches()){
				// le modele a besoin de données
				if(matchGet.group(1).equals("params")){
					fenetreDemarrer = new FenetreParametrage();
				}
				else if(matchGet.group(1).equals("nbJoueursReels")){
					fenetreDemarrer.nbJRinvalide();
				}
				else if(matchGet.group(1).equals("nbJoueursCpu")){
					fenetreDemarrer.nbJVinvalide();
				}
				else if(matchGet.group(1).equals("nbCoups")){
					fenetreDemarrer.showNbCoups();
				}
				else if(matchGet.group(1).equals("nbPoints")){
					fenetreDemarrer.showNbPoints();
				}
			}
			else if(matchMaj.matches()){
				// mise a jour de la vue
				if(matchMaj.group(1).equals("nbCoups")){
				}
				else if(matchMaj.group(1).equals("nbPoints")){
				}
				// debut de la partie
				else if(matchMaj.group(1).equals("newPartie")){
					fenetreDemarrer.setVisible(false);
				}
				// fin de la partie
				else if(matchMaj.group(1).equals("finPartie")){
					partie=null;
					Partie.reset();
					panelPartie.clear();
					panelPartie.majCarteVisible(null);
				}
				else if(matchMaj.group(1).equals("partieExistante")){
					int rep = JOptionPane.showConfirmDialog(fenetre, "Arreter la partie en cours ?", "Nouvelle Partie" , JOptionPane.YES_NO_OPTION);
					if(rep == JOptionPane.YES_OPTION){
						partie = null;
						Partie.reset();
						panelPartie.clear();
						partie = Partie.getInstance();
						partie.init();
					}
				}
			}
		}
		// MANCHE
		else if(obj.getClass().getCanonicalName().equals("modele.Coup")){
			// PATTERNS-------------------
			Pattern patGet = Pattern.compile("get ([A-Za-z]+)");
			Matcher matchGet = patGet.matcher(msg);
			
			Pattern patMaj = Pattern.compile("maj ([A-Za-z]+)");
			Matcher matchMaj = patMaj.matcher(msg);
			
			//----------------------------
			if(matchGet.matches()){
				
			}
			else if(matchMaj.matches()){
				if(matchMaj.group(1).equals("newCoup")){
					panelPartie.initPanel();
					panelPartie.majJoueurs();
				}
				else if(matchMaj.group(1).equals("finCoup")){
					new FenetreScore("fin de la manche "+cm.getNumCurrentCoup());
				}
				else if(matchMaj.group(1).equals("carteVisible")){
					panelPartie.majCarteVisible(cm.getCurrentCoup().getCarteVisible());
					panelPartie.majJoueur(cm.getCurrentCoup().getCurrentJoueurIndex());
					panelPartie.getJoueur(cm.getCurrentCoup().getCurrentJoueurIndex()).setBorder(BorderFactory.createLineBorder(new Color(0,50,0)));
				}
				else if(matchMaj.group(1).equals("joueurPioche") || matchMaj.group(1).equals("joueurCpuPioche")){
					panelPartie.majInfo(cm.getCurrentCoup().getCurrentJoueur().getNom()+" pioche");
					panelPartie.getJoueur(cm.getCurrentCoup().getCurrentJoueurIndex()).maj();
					panelPartie.getJoueur(cm.getCurrentCoup().getCurrentJoueurIndex()).setBorder(BorderFactory.createLineBorder(new Color(0,100,0)));
				}
				else if(matchMaj.group(1).equals("cartePosee")){
					btnCarte.setVisible(false);
					btnOublie.setVisible(false);
					panelPartie.majInfo(cm.getCurrentCoup().getCurrentJoueur().getNom()+" joue "+cm.getCurrentCoup().getCarteVisible());
					panelPartie.getJoueur(cm.getCurrentCoup().getCurrentJoueurIndex()).maj();
					panelPartie.getJoueur(cm.getCurrentCoup().getCurrentJoueurIndex()).setBorder(BorderFactory.createLineBorder(new Color(0,100,0)));
				}
				else if(matchMaj.group(1).equals("paquetRetourne")){
					
				}
				// effet de cartes
				else if(matchMaj.group(1).equals("changementSens")){
					panelPartie.setSens(cm.getCurrentCoup().getSens());
				}
				else if(matchMaj.group(1).equals("fairePasserTour")){
					panelPartie.majInfo(cm.getCurrentCoup().getCurrentJoueur().getNom()+" passe son tour");
				}
				else if(matchMaj.group(1).equals("fairePiocher")){
					panelPartie.getJoueur(cm.getCurrentCoup().getNextJoueurIndex()).maj();
				}
				else if(matchMaj.group(1).equals("fairePiocherMain")){
					panelPartie.getJoueur(cm.getCurrentCoup().getCurrentJoueurIndex()).maj();
					panelPartie.getJoueur(cm.getCurrentCoup().getNextJoueurIndex()).maj();
				}
			}
		}
		//JOUEUR MANAGER
		else if(obj.getClass().getCanonicalName().equals("modele.JoueursManager")){
			if(msg.equals("nbJoueursInvalide"))
				fenetreDemarrer.nbJinvalide();
			else if(msg.equals("nbJoueursReelsInvalide"))
				fenetreDemarrer.nbJRinvalide();
			else if(msg.equals("nbJoueursCpuInvalide"))
				fenetreDemarrer.nbJVinvalide();
		}
		// JOUEUR Reel
		else if(obj.getClass().getCanonicalName().equals("modele.JoueurReel")){
			if(msg.equals("peutJouer")){
				panelPartie.getJoueur(cm.getCurrentCoup().getCurrentJoueurIndex()).setBorder(BorderFactory.createLineBorder(new Color(0,50,0)));
				if(cm.getCurrentCoup().getCurrentJoueur().getMain().size()==2)
					btnCarte.setVisible(true);
				ArrayList<Joueur> tmpList = new ArrayList<Joueur>();
				for(Joueur j : jm.getJoueurs())
					if(j.getMain().size()==1)
						tmpList.add(j);
				if(tmpList.size()!=0)
					for(Joueur j : tmpList)
						if(!j.aDitCarte())
							btnOublie.setVisible(true);
			}
			else if(msg.equals("get couleur")){
				new ColorPicker();
			}
		}
		// EFFETS DE CARTES
		else if(obj.getClass().getInterfaces().length!=0 && obj.getClass().getInterfaces()[0].getCanonicalName().equals("modele.comportements.ComportementCarte")){
				if(msg.equals("couleur")){
					panelPartie.majInfo(cm.getCurrentCoup().getCurrentJoueur()+" choisi la couleur "+cm.getCurrentCoup().getCarteVisible().getComportement().getCouleur()); // retrouver la couleur choisie
				}
		}
		// CARTES EXCEPTIONS
		else if(obj.getClass().getSuperclass().getCanonicalName().equals("modele.exceptions.CarteException")){
			JOptionPane.showMessageDialog(fenetre, msg, "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}
}

