package net.thiagoalz.hermeto.view.strategies;

import java.util.LinkedHashMap;
import java.util.Map;

import net.thiagoalz.hermeto.audio.InstrumentType;
import net.thiagoalz.hermeto.audio.SoundManager;
import net.thiagoalz.hermeto.panel.GameManager;
import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.panel.listeners.ExecutionEvent;
import net.thiagoalz.hermeto.panel.listeners.IExecutionListener;
import net.thiagoalz.hermeto.player.IPlayer;
import android.util.Log;

public class FreeSelectionStrategy extends AbstractSelectionStrategy {
	private static final String TAG = FreeSelectionStrategy.class.getCanonicalName();
	
	public FreeSelectionStrategy(GameManager gameManager) {
		super(gameManager);
	}
	
	@Override

	public boolean mark(IPlayer player) {
		int column = player.getPosition().getX();
		int row = player.getPosition().getY();
		
		Log.d(TAG, "Starting playing the button at position [" + column + ", " + row + "].");
		synchronized (this) {
			// Put the position in a list to send to the listeners
			Map<Position, InstrumentType> playingPositions = new LinkedHashMap<Position, InstrumentType>();
			playingPositions.put(new Position(column, row), getGameManager().getGameContext().getCurrentInstrumentType());
						
			// Play the sound
			SoundManager.getInstance().playSound(row,getGameManager().getGameContext().getCurrentInstrumentType());
			
			/*
			 * Need to start playing the position.
			 */
			if (playingPositions.size() > 0) {
				ExecutionEvent executionEvent = new ExecutionEvent();
				executionEvent.setPositions(playingPositions);
				for (IExecutionListener listener : getSequencer().getExecutionListeners()) {
					Log.d(TAG, "Telling to the " + listener + " to start playing the group");
					listener.onStartPlayingGroup(executionEvent);
				}
			}
			
			// Wait for the BPM setted.
			try {
				Thread.sleep(getSequencer().getTimeSequence());
			} catch (InterruptedException ie) {	}
			
			/*
			 * Just make the position deselected. 
			 */
			//notifyDeselection(player, player.getPosition());
		}
		return true;
	}

	@Override
	public void cleanAll() {
		// TODO Auto-generated method stub
		
	}

}
