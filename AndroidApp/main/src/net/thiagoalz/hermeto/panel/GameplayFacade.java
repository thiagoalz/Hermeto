package net.thiagoalz.hermeto.panel;

/**
 *  Facade with all the functions that an external player can call.
 */
public interface GameplayFacade {
	/**
	 * Move the player in the expected direction.
	 * 
	 * @param playerID The unique id of the player.
	 * @param direction The direction where the player will be moved
	 * @return True indicates that the user was moved, otherwise return false.
	 */
	public boolean move(String playerID, String direction);
	
	/**
	 * Mark the square under the player location.
	 * 
	 * @param playerID The unique identifier of the player.
	 * @return True indicates that the square was marked successfully. Otherwise return false.
	 */
	public boolean mark(String playerID);
	
	/**
	 * Connect player to start using the application. 
	 * @return
	 */
	public String connectPlayer();

}
