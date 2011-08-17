package net.thiagoalz.hermeto.player;

import java.util.Map;

/**
 * Manage the users that are playing the game.
 * 
 * @author Gabriel Ozeas de Oliveira
 * @version 0.1
 */
public interface PlayersManager {
	/**
	 * The players that are the panel. 
	 * 
	 * @return The list of players. If no player is in the panel, an empty list will be returned.
	 */
	public Map<String, Player> getPlayers();
	
	/**
	 * Add a player to the panel.
	 * 
	 * @param player The player that will be added.
	 */
	public Player connectPlayer();
	
	/**
	 * Remove the player in the panel;
	 * 
	 * @param player The player that will be removed.
	 */
	public void disconnectPlayer(Player player);
	
	/**
	 * Retrieve the player using the ID. 
	 * 
	 * @param playerID The unique identifier of the player.
	 * @return The player corresponding to the ID passed.
	 */
	public Player getPlayer(String playerID);
}
