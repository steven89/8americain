package modele;

import java.util.ArrayList;

import vue.Observer;

/**
 * Relais de notification entre le Modele et la Vue
 */
public class Updateur {
	
	private static ArrayList<Observer> uis = new ArrayList<Observer>();
	
	private Updateur(){
		
	}
	/**
	 * Ajoute un observateur
	 * @param obs : objet observant le Modele
	 */
	public static void addObserver(Observer obs){
		uis.add(obs);
	}

	public static void notifyObservers(Object obj, String msg) {
		for(Observer ui : uis){
			ui.update(obj, msg);
		}
	}
}
