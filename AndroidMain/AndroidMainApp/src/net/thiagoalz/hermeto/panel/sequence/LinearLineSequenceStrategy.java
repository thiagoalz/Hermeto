package net.thiagoalz.hermeto.panel.sequence;

import java.util.List;

import android.util.Log;

import net.thiagoalz.hermeto.audio.SoundManager;
import net.thiagoalz.hermeto.panel.GameContext;
import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.panel.listeners.SelectionEvent;
import net.thiagoalz.hermeto.panel.listeners.SelectionListener;


/**
 * The linear line sequence strategy will make the the lines play until the 
 * last square and back to the first square, like a circular list.  
 */
public class LinearLineSequenceStrategy extends LineSequenceStrategy implements SelectionListener {
	private static final String TAG = LinearLineSequenceStrategy.class.getCanonicalName();
	
	
	public LinearLineSequenceStrategy(Sequencer sequencer, SoundManager soundManager) {
		super(sequencer, soundManager);
	}
	
	
	@Override
	public void start() {
		GameContext gameContext = getSequencer().getGameManager().getGameContext();
		int columns = gameContext.getDimensions()[0];
		
		LineSequence lineSequence = null;
		for (int i = 0; i < columns; i++) {
			List<Position> positions = gameContext.getColumnMarkedSquares(i);
			
			// If the column has some square selected
			if (positions.size() > 0) {
				// If that column does not have a LineSequence yet, create one.
				if (!(getLineSequences().containsKey(i))) {
					lineSequence = new LineSequence(this, i);
					getLineSequences().put(lineSequence.getPlayingLine(), lineSequence);
					Log.d(TAG, "Adding new line sequence for column #" + lineSequence.getPlayingLine() + ": " + getLineSequences().get(i));
				} else {
					lineSequence = getLineSequences().get(i);
				}
				Position lastSquare = positions.get(positions.size() - 1);
				Log.d(TAG, "Starting line sequene at [" + lastSquare.getX() + ", " + lastSquare.getY() + "]");
				// Schedule the lineSequence to execute until the last marked square
				lineSequence.schedule(lastSquare.getY(), getSequencer().getTimeSequence());
			}
			
		}		
	}

	@Override
	public void stop() {
		for (Integer lineSequenceColumn : getLineSequences().keySet()) {
			LineSequence lineSequence = getLineSequences().get(lineSequenceColumn);
			if (lineSequence != null) {
				lineSequence.unschedule();
				lineSequence.resetToStart();
			}
		}
	}

	@Override
	public void pause() {
		Log.d(TAG, getLineSequences().size() + " line sequences to be paused.");
		for (Integer lineSequenceColumn : getLineSequences().keySet()) {
			LineSequence lineSequence = getLineSequences().get(lineSequenceColumn);
			if (lineSequence != null) {
				lineSequence.unschedule();
			}
			
		}
	}

	@Override
	public void onSelected(SelectionEvent event) {
		if (getSequencer().isPlaying()) {
			Position position = event.getPosition();
			LineSequence lineSequence = null;
			if (getLineSequences().containsKey(position.getX())) {
				synchronized(this) {
					Log.d(TAG, "The line sequence #" + position.getX() + " already exists, retrieving it.");
					lineSequence = getLineSequences().get(position.getX());
					if (lineSequence != null) {
						lineSequence.unschedule();
					}
				}
			} else {
				Log.d(TAG, "Creating line sequence at #" + position.getX() + ".");
				lineSequence = new LineSequence(this, position.getX());
				getLineSequences().put(lineSequence.getPlayingLine(), lineSequence);
			}
			lineSequence.schedule(position.getY(), getSequencer().getTimeSequence());
		}		
	}

	@Override
	public void onDeselected(SelectionEvent event) {
		Position position = event.getPosition();
		LineSequence lineSequence = getLineSequences().get(position.getX());
		if (lineSequence != null) {
			synchronized(this) {
				lineSequence.unschedule();
				getLineSequences().remove(position.getX());
			}
		} else {
			Log.d(TAG, "The line sequence #" + position.getX() + " doesn't exist.");
		}
	}
}
