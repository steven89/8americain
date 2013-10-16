package vue;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import modele.CoupsManager;
import modele.Joueur;
import modele.JoueursManager;
import modele.Partie;
import modele.Updateur;

/**
 * <p>Interface utilisateur en mode terminal</p>
 * @deprecated <p>Classe non maitenue depuis l'implementation de l'interface graphique<br/>Utiliser {@link Graphique}</p>
 */
public class Terminal implements Observer {
	
	private static Terminal t = null;
	private Partie partie;
	private JoueursManager jm;
	private CoupsManager cm;
	
	private Scanner scan = new Scanner(System.in);

	private Terminal(){
		System.out.println("+----------------------------------------------------+");
		System.out.println("|                 Jeu de 8 americain                 |");
		System.out.println("|	               ______________                |");
		System.out.println("|       ______        |   ________   |               |");
		System.out.println("|      /  /\\  \\       |  |        |  |               |");
		System.out.println("|     |  |  |  |      |  |________|  |   _    _      |");
		System.out.println("|      \\  \\/  /       |   ________   |  | \\  / |     |");
		System.out.println("|      /  /\\  \\       |  |        |  |  |  \\/  |     |");
		System.out.println("|     |  |  |  |      |  |        |  |  |      |     |");
		System.out.println("|      \\__\\/__/       |__|        |__|  |      |     |");
		System.out.println("|                                                    |");
		System.out.println("|            Philippe Pierre & Salaun Steven         |");
		System.out.println("+----------------------------------------------------+");
	}
	
	public void init(){
		Updateur.addObserver(this);
		partie = Partie.getInstance();
		partie.init();
		jm = JoueursManager.getInstance();
		cm = CoupsManager.getInstance();
	}
	
	public static Terminal getInstance(){
		if(t==null)
			t = new Terminal();
		return t;
	}
//--------------------------------------------------------------------------------------
//		Observer
	public synchronized void update(Object obj, String msg){
		// PARTIE
		if(obj.getClass().getCanonicalName().equals("Modele.Partie")){
			// PATTERNS-------------------
			Pattern patGet = Pattern.compile("get ([A-Za-z]+)");
			Matcher matchGet = patGet.matcher(msg);
			
			Pattern patMaj = Pattern.compile("maj ([A-Za-z]+)");
			Matcher matchMaj = patMaj.matcher(msg);
			
			//----------------------------
			if(matchGet.matches()){
				// le modele a besoin de données
				if(matchGet.group(1).equals("params")){
					System.out.print("Difficulte de la partie (f|n|d) :");
					String dif = scan.next();
					if(dif.equals("f"))
						partie.setNiveau(Partie.FACILE);
					else if(dif.equals("d"))
						partie.setNiveau(Partie.DIFFICILE);
					else
						partie.setNiveau(Partie.NORMAL);
					System.out.print("Nombre de joueurs Reels : ");
					String nbReels = scan.next();
					System.out.print("Nombre de joueurs virtuels : ");
					String nbCpu = scan.next();
					partie.setNbJoueurs(nbReels, nbCpu);
					System.out.print("Type de jeu : ");
					partie.chooseTypeJeu(scan.next());
				}
				else if(matchGet.group(1).equals("typeJeu")){
					System.out.print("Type de jeu : ");
					partie.chooseTypeJeu(scan.next());
				}
				else if(matchGet.group(1).equals("nbCoups")){
					System.out.print("Nombre de coups : ");
					partie.setTotalCoups(scan.next());
				}
				else if(matchGet.group(1).equals("nbPoints")){
					System.out.print("Nombre de points : ");
					partie.setTotalPoints(scan.next());
				}
			}
			else if(matchMaj.matches()){
				// mise a jour de la vue
				if(matchMaj.group(1).equals("nbCoups")){
					System.out.println("La parte se joue en "+partie.getTotalCoups()+" coups !");
				}
				else if(matchMaj.group(1).equals("nbPoints")){
					System.out.println("La partie se joue en "+partie.getTotalPoints()+" points !");
				}
				// debut de la partie
				else if(matchMaj.group(1).equals("newPartie")){
					System.out.println("Debut de la partie :");
				}
				// fi de la partie
				else if(matchMaj.group(1).equals("finPartie")){
					System.out.println("fin de la partie");
				}
			}
		}
		// MANCHE
		else if(obj.getClass().getCanonicalName().equals("Modele.Coup")){
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
					System.out.println("/////////////////////////////////////////////");
					System.out.println("Debut de la manche n° "+cm.getNumCurrentCoup());
					System.out.println("Ordre de jeu :");
					for(Joueur j : jm.getJoueurs()){
						System.out.println("\t"+j);
					}
				}
				else if(matchMaj.group(1).equals("finCoup")){
					System.out.println("Fin de la manche n° "+cm.getNumCurrentCoup());
					System.out.println("/////////////////////////////////////////////");
				}
				else if(matchMaj.group(1).equals("carteVisible")){
					System.out.println("$ Tour de "+cm.getCurrentCoup().getCurrentJoueur()+ "-----------------------------------");
					System.out.print("$ Carte visible : "+cm.getCurrentCoup().getCarteVisible());
					if(cm.getCurrentCoup().getCarteVisible().getHauteur()==8 || cm.getCurrentCoup().getCarteVisible().getHauteur()==14)
						System.out.println("("+cm.getCurrentCoup().getCarteVisible().getComportement().getCouleur()+")");
					else
						System.out.println();
				}
				else if(matchMaj.group(1).equals("joueurPioche")){
					System.out.println("# "+cm.getCurrentCoup().getCurrentJoueur()+" pioche");
				}
				else if(matchMaj.group(1).equals("joueurCpuPioche")){
					System.out.println("# "+cm.getCurrentCoup().getCurrentJoueur()+" pioche");
				}
				else if(matchMaj.group(1).equals("cartePosee")){
					System.out.println("$ "+cm.getCurrentCoup().getCurrentJoueur()+" pose : "+cm.getCurrentCoup().getCarteVisible());
				}
				else if(matchMaj.group(1).equals("paquetRetourne")){
					System.out.println("$ pile de carte retournee");
				}
				// effet de cartes
				else if(matchMaj.group(1).equals("changementSens")){
					System.out.println("# changement du sens de jeu");
				}
				else if(matchMaj.group(1).equals("fairePasserTour")){
					System.out.println("# "+cm.getCurrentCoup().getCurrentJoueur()+" passe son tour");
				}
				else if(matchMaj.group(1).equals("fairePiocher")){
					System.out.println("# "+cm.getCurrentCoup().getNextJoueur()+" pioche "+cm.getCurrentCoup().getNbCartesPiochees()+" cartes");
				}
				else if(matchMaj.group(1).equals("fairePiocherMain")){
					System.out.println("# "+cm.getCurrentCoup().getNextJoueur()+" pioche une carte dans la main de "+cm.getCurrentCoup().getCurrentJoueur());
				}
			}
		}
		// JOUEUR Reel
		else if(obj.getClass().getCanonicalName().equals("Modele.JoueurReel")){
			if(msg.equals("peutJouer")){
				System.out.println("# main de "+cm.getCurrentCoup().getCurrentJoueur()+" : "+cm.getCurrentCoup().getCurrentJoueur().getMain());
				System.out.print("Jouer la carte : ");
				cm.getCurrentCoup().getCurrentJoueur().joue(scan.next());
			}
			else if(msg.equals("get couleur")){
				System.out.print("Choisir la couleur : ");
				cm.getCurrentCoup().getCurrentJoueur().choixCouleur(scan.next());
			}
		}
		// JOUEUR Cpu
		else if(obj.getClass().getCanonicalName().equals("Modele.JoueurCpu")){
			if(msg.equals("test")){
				System.out.println("# "+cm.getCurrentCoup().getCurrentJoueur()+" try");
			}
		}
		// EFFETS DE CARTES
		else if(obj.getClass().getInterfaces().length!=0 && obj.getClass().getInterfaces()[0].getCanonicalName().equals("Modele.ComportementCarte")){
				if(msg.equals("couleur")){
					System.out.println(cm.getCurrentCoup().getCurrentJoueur()+" choisi la couleur "+cm.getCurrentCoup().getCarteVisible().getComportement().getCouleur()); // retrouver la couleur choisie
				}
		}
		// CARTES EXCEPTIONS
		else if(obj.getClass().getSuperclass().getCanonicalName().equals("Modele.Exceptions.CarteException")){
			System.out.println("/!\\"+msg+"/!\\");
		}
	}
}
