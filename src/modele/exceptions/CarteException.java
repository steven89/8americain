package modele.exceptions;
/**
 * Exceptions de cartes
 */
abstract public class CarteException extends Exception {
	private static final long serialVersionUID = 1L;
	/**
	 * notifie l'exception a la vue
	 */
	public abstract void notifyException();
}
