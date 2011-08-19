package net.thiagoalz.hermeto.panel;

import net.thiagoalz.hermeto.player.Player;

/**
 * Event that is dispatched when the player mark a 
 * square in he panel.
 */
public class SelectionEvent {
	private Player player;
	private Position position;
	
	public SelectionEvent() {}
	public SelectionEvent(Player player, Position position) {
		this.player = player;
		this.position = position;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Position getPosition() {
		return position;
	}
}
