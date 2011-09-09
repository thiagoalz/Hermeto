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
		int rows = gameContext.getDimensions()[1];
		for (int i = 0; i < rows; i++) {
			List<Position> positions = gameContext.getRowMarkedSquares(rows);
			if (positions.size() > 0) {
				Position lastSquare = positions.get(positions.size() - 1);
				LineSequence lineSequence = new LineSequence(this);
				lineSequence.setPlayingLine(lastSquare.getX());
				lineSequence.schedule(lastSquare.getX(), getSequencer().getTimeSequence());
			}
		}		
	}

	@Override
	public void stop() {
		for (int i = 0; i < getLineSequences().size(); i++) {
			LineSequence lineSequence = getLineSequences().get(i);
			lineSequence.unschedule();
			lineSequence.resetToStart();
		}
	}

	@Override
	public void pause() {
		for (int i = 0; i < getLineSequences().size(); i++) {
			LineSequence lineSequence = getLineSequences().get(i);
			lineSequence.unschedule();
		}
	}

	@Override
	public void onSelected(SelectionEvent event) {
		Position position = event.getPosition();
		LineSequence lineSequence = null;
		if (getLineSequences().containsKey(position.getX())) {
			synchronized(this) {
				Log.d(TAG, "The line sequence #" + position.getX() + " already exists, retrieving it.");
				lineSequence = getLineSequences().get(position.getX());
				lineSequence.unschedule();
			}
		} else {
			Log.d(TAG, "Creating line sequence at #" + position.getX() + ".");
			lineSequence = new LineSequence(this);
			lineSequence.setPlayingLine(position.getX());
			getLineSequences().put(position.getX(), lineSequence);
		}
		if (getSequencer().isPlaying()) {
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
