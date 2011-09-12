package net.thiagoalz.hermeto.panel.listeners;

import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.player.IPlayer;

public class ConnectEvent {
	private IPlayer player;
	private Position position;

	public ConnectEvent(IPlayer player, Position position) {
		super();
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
