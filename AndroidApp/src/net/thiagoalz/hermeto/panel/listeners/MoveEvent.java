package net.thiagoalz.hermeto.panel.listeners;

import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.player.Player;

public class MoveEvent {
	private Player player;
	private Position oldPosition;
	private Position newPosition;
	
	public MoveEvent(Player player, Position oldPosition, Position newPosition) {
		super();
		this.player = player;
		this.oldPosition = oldPosition;
		this.newPosition = newPosition;
	}
	public Player getPlayer() {
		return player;
	}
	public Position getOldPosition() {
		return oldPosition;
	}
	public Position getNewPosition() {
		return newPosition;
	}
	
	
}
