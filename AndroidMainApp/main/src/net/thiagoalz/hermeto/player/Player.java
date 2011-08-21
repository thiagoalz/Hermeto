package net.thiagoalz.hermeto.player;

import net.thiagoalz.hermeto.panel.Position;

/**
 * User that is is active and playing in the game.
 * 
 * @author Gabriel Ozeas de Oliveira
 * @version 0.1
 */
public interface Player {
	/**
	 * Directions that the player can go.
	 */
	public enum Direction {
		LEFT("left"), RIGHT("right"), TOP("top"), DOWN("down");
		
		private String value;
		
		private Direction(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * Move the player the specified direction.
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
	 * Set the position of the player.
	 * 
	 * @param position Position of the player;
	 */
	public void setPosition(Position position);
	
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
