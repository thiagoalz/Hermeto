package net.thiagoalz.hermeto.panel.listeners;

import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.player.IPlayer;

public class MoveEvent {
	private IPlayer player;
	private Position oldPosition;
	private Position newPosition;
	
	public MoveEvent(IPlayer player, Position oldPosition, Position newPosition) {
		super();
		this.player = player;
		this.oldPosition = oldPosition;
		this.newPosition = newPosition;
	}
	public IPlayer getPlayer() {
		return player;
	}
	public Position getOldPosition() {
		return oldPosition;
	}
	public Position getNewPosition() {
		return newPosition;
	}
	
	
}
