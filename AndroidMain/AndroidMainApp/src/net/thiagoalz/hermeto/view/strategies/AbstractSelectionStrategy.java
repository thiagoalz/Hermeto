package net.thiagoalz.hermeto.view.strategies;

import net.thiagoalz.hermeto.panel.GameManager;
import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.panel.listeners.SelectionControl;
import net.thiagoalz.hermeto.panel.listeners.SelectionEvent;
import net.thiagoalz.hermeto.panel.listeners.SelectionListener;
import net.thiagoalz.hermeto.player.Player;

public abstract class AbstractSelectionStrategy implements SelectionControl {
	private GameManager gameManager;
	
	public AbstractSelectionStrategy(GameManager gameManager) {
		this.gameManager = gameManager;
	}
	
	public void notifySelection(Player player, Position position) {
		SelectionEvent event = new SelectionEvent(player, position);
		for (SelectionListener listener : gameManager.getSelectionListeners()) {
			listener.onSelected(event);
		}
	}

	public void notifyDeselection(Player player, Position position) {
		SelectionEvent event = new SelectionEvent(player, position);
		for (SelectionListener listener : gameManager.getSelectionListeners()) {
			listener.onDeselected(event);
		}
	}
	
	@Override
	public void cleanAll() {
		// Deselect all the markedSquares and stop playing.
		for (Position position : gameManager.getGameContext().getMarkedSquares()) {
			notifyDeselection(null, position);
		}
	}

	public GameManager getGameManager() {
		return gameManager;
	}

	public void setGameManager(GameManager gameManager) {
		this.gameManager = gameManager;
	}
}
