package net.thiagoalz.hermeto.panel.listeners;

import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.player.Player;

public class ConnectEvent {
	private Player player;
	private Position position;

	public ConnectEvent(Player player, Position position) {
		super();
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
