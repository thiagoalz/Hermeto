package net.thiagoalz.hermeto.panel.listeners;

import net.thiagoalz.hermeto.player.IPlayer;

/**
 * Controls the selection of the positions in the game.
 */
public interface ISelectionControl {
	public boolean mark(IPlayer player);
	public void cleanAll();
}
