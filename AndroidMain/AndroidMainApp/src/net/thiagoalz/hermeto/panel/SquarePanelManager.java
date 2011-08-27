package net.thiagoalz.hermeto.panel;

import net.thiagoalz.hermeto.player.Player;
import net.thiagoalz.hermeto.player.Player.Direction;

/**
 * Panel where the pads are, and where the users play.
 * 
 * @author Gabriel Ozeas de Oliveira
 * @version 0.1
 */
public interface SquarePanelManager {
	
	/**
	 * Move the player to the direction, both taken as parameters. The move
	 * action is performed in just one unit. For example, when the players
	 * requests to move to the left, it panel will move him just on square to
	 * left.
	 * 
	 * @param player The player that will be moved
	 * @param direction The direction where the player will be moved.
	 * @return True if the player could be moved, otherwise return false.
	 */
	public boolean move(Player player, Direction direction);
	
	/**
	 * Mark the square where the player is located if the it has not been marked.
	 * 
	 * @param player The player that wants to mark the square under his position.
	 * @return True if the square could be marked, otherwise return false.
	 */
	public boolean mark(Player player);
	
	/**
	 * Retrieve all the position of the marked squares in the panel.
	 * @return The array of position of the marked squares.
	 */
	public Position[] getMarkedSquares();	
	
	
	/**
	 * Retrieve if the game is playing.
	 * 
	 * @return True indicates if the game is playing, otherwise return false.
	 */
	public boolean isPlaying();
	
	public void start();
	
	public void stop();
	
	public void pause();
	
	public void reset();
	
}
