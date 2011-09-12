package net.thiagoalz.hermeto.view.strategies;

import net.thiagoalz.hermeto.panel.GameManager;
import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.panel.listeners.SelectionControl;
import net.thiagoalz.hermeto.panel.listeners.SelectionEvent;
import net.thiagoalz.hermeto.panel.listeners.SelectionListener;
import net.thiagoalz.hermeto.panel.sequence.Sequencer;
import net.thiagoalz.hermeto.player.Player;
import android.util.Log;

public abstract class AbstractSelectionStrategy implements SelectionControl {
	private static final String TAG = AbstractSelectionStrategy.class.getCanonicalName();
	
	private GameManager gameManager;
	private Sequencer sequencer;
	
	public AbstractSelectionStrategy(GameManager gameManager) {
		this.gameManager = gameManager;
		this.sequencer = gameManager.getSequencer();
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
			Log.d(TAG, "Informing the " + listener + " about deselection.");
			listener.onDeselected(event);
		}
	}
	
	public GameManager getGameManager() {
		return gameManager;
	}

	public void setGameManager(GameManager gameManager) {
		this.gameManager = gameManager;
	}

	public Sequencer getSequencer() {
		return sequencer;
	}

	public void setSequencer(Sequencer sequencer) {
		this.sequencer = sequencer;
	}
}
