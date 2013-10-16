package vue;
/**
 * <p>Interface Observer</p>
 */
public interface Observer {
	/**
	 * @param obj : tout les types d'objets
	 * @param msg : chaine de caract&egrave;re
	 */
	public void update(Object obj, String msg);
}
