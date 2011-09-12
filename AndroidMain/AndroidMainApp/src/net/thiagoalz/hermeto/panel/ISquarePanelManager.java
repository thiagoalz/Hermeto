package net.thiagoalz.hermeto.panel;

import net.thiagoalz.hermeto.player.IPlayer;
import net.thiagoalz.hermeto.player.IPlayer.Direction;

/**
 * Panel where the pads are, and where the users play.
 * 
 * @author Gabriel Ozeas de Oliveira
 * @version 0.1
 */
public interface ISquarePanelManager {
	
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
	public boolean move(IPlayer player, Direction direction);
	
	/**
	 * Mark the square where the player is located if the it has not been marked.
	 * 
	 * @param player The player that wants to mark the square under his position.
	 * @return True if the square could be marked, otherwise return false.
	 */
	public boolean mark(IPlayer player);	
}
