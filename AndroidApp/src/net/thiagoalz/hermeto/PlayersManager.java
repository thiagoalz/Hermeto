package net.thiagoalz.hermeto;

import java.util.List;

/**
 * Manage the users that are playing the game.
 */
public interface PlayersManager {
	/**
	 * The players that are the panel. 
	 * 
	 * @return The list of players. If no player is in the panel, an empty list will be returned.
	 */
	public List<Player> getPlayers();
	
	/**
	 * Add a player to the panel.
	 * 
	 * @param player The player that will be added.
	 */
	public void addPlayer(Player player);
	
	/**
	 * Remove the player in the panel;
	 * 
	 * @param player The player that will be removed.
	 */
	public void removePlayer(Player player);
}
