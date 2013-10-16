package modele.exceptions;

import modele.Updateur;
/**
 * Carte introuvable Exception
 */
public class CarteIntrouvable extends CarteException {
	private static final long serialVersionUID = 1L;
	/**
	 * @see modele.exceptions.CarteException#notifyException()
	 */
	public void notifyException() {
		Updateur.notifyObservers(this, "Le joueur ne dispose pas de cette carte");
	}
	
}
