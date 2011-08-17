package net.thiagoalz.hermeto;

/**
 * User that is is active and playing in the game.
 */
public interface Player {
	/**
	 * Directions that the player can go.
	 */
	public enum Direction {
		LEFT, RIGHT, TOP, DOWN;
	}

	/**
	 * Move the player the especified direction.
	 * 
	 * @param direction
	 *            The direction where the player need to be moved.
	 * @return True if the player could be moved. Otherwise return false.
	 */
	public boolean move(Direction direction);

	/**
	 * Mark where the player is located.
	 * 
	 * @return True if the space could be marked, otherwise return false.
	 */
	public boolean mark();

	/**
	 * Get the position of the player in the panel.
	 * 
	 * @return An {@code Position} instance containing the x, y coordinates related with
	 *         the position of the player.
	 */
	public Position getPosition();
	
	/**
	 * Retrieve the ID of the player.
	 * 
	 * @return The unique identifier of the player. 
	 */
	public String getName();
	
	/**
	 * Retrieve the ID of the player.
	 * 
	 * @return The unique identifier of the player. 
	 */
	public String getId();
}
