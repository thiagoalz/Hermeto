package net.thiagoalz.hermeto.panel.listeners;

import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.player.IPlayer;

/**
 * Event that is dispatched when the player mark a 
 * square in he panel.
 */
public class SelectionEvent {
	private IPlayer player;
	private Position position;
	
	public SelectionEvent() {}
	public SelectionEvent(IPlayer player, Position position) {
		this.player = player;
		this.position = position;
	}
	
	public IPlayer getPlayer() {
		return player;
	}
	
	public Position getPosition() {
		return position;
	}
}
