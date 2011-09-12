package net.thiagoalz.hermeto.panel.listeners;

public interface IPlayerListener {
	public void onPlayerMove(MoveEvent event);
	public void onPlayerConnect(ConnectEvent event);
	public void onPlayerDisconnect(ConnectEvent event);
}
