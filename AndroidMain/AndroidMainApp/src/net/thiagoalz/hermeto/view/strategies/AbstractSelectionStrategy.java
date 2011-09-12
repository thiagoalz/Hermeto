package net.thiagoalz.hermeto.view.strategies;

import net.thiagoalz.hermeto.panel.GameManager;
import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.panel.listeners.ISelectionControl;
import net.thiagoalz.hermeto.panel.sequence.Sequencer;
import net.thiagoalz.hermeto.player.IPlayer;

public abstract class AbstractSelectionStrategy implements ISelectionControl {
	private static final String TAG = AbstractSelectionStrategy.class.getCanonicalName();
	
	private GameManager gameManager;
	private Sequencer sequencer;
	
	public AbstractSelectionStrategy(GameManager gameManager) {
		this.gameManager = gameManager;
		this.sequencer = gameManager.getSequencer();
	}
	
	public void notifySelection(IPlayer player, Position position) {
		gameManager.notifySelection(player, position);
	}

	public void notifyDeselection(IPlayer player, Position position) {
		gameManager.notifyDeselection(player, position);
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
