package net.thiagoalz.hermeto.panel.listeners;

import net.thiagoalz.hermeto.player.Player;

/**
 * Controls the selection of the positions in the game.
 */
public interface SelectionControl {
	public boolean mark(Player player);
	public void cleanAll();
}
