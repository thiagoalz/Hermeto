package net.thiagoalz.hermeto.panel.sequence.strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.thiagoalz.hermeto.audio.SoundManager;
import net.thiagoalz.hermeto.panel.GameContext;
import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.panel.listeners.ExecutionEvent;
import net.thiagoalz.hermeto.panel.listeners.ExecutionListener;
import net.thiagoalz.hermeto.panel.listeners.SelectionEvent;
import net.thiagoalz.hermeto.panel.listeners.SelectionListener;
import net.thiagoalz.hermeto.panel.sequence.Sequencer;
import android.util.Log;

/**
 * In the line sequence strategy, the lines in the panel are independent and can be 
 * started when the user select the first square in that line.
 */
public class LineSequenceStrategy extends AbstractSequenceStrategy implements SelectionListener {
	private static final String TAG = LineSequenceStrategy.class.getCanonicalName();
	
	/**
	 * All the line sequences that will be played in the sequencer.
	 */
	private Map<Integer, LineSequence> lineSequences;
		
	/**
	 * The behave type that the each line sequence will have.
	 */
	private PositionBehavior positionBehavior;

	public LineSequenceStrategy(Sequencer sequencer, SoundManager soundManager) {
		super(sequencer, soundManager);
		this.lineSequences = new HashMap<Integer, LineSequence>();
		this.positionBehavior = PositionBehavior.REPEAT;
	}
	
	@Override
	public void start() {
		GameContext gameContext = getSequencer().getGameManager().getGameContext();
		int columns = gameContext.getDimensions()[0];
		
		LineSequence lineSequence = null;
		for (int i = 0; i < columns; i++) {
			List<Position> positions = getColumnMarkedSquares(i);
			
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
				Positioner positioner = createPositioner(lastSquare.getY());
	
				// Schedule the lineSequence to execute until the last marked square
				lineSequence.schedule(positioner, getSequencer().getTimeSequence());
			}
		}		
	}
	
	private Positioner createPositioner(int maxPosition) {
		switch(positionBehavior) {
			case REPEAT:
				return new RepeatPositioner(maxPosition);
			case BOUNCE:
				return new BouncePositioner(maxPosition);
			default:
				return new RepeatPositioner(maxPosition);
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

	public void startPlayingSquare(int column, int row) {
		Log.d(TAG, "Starting playing the button at position [" + column + ", " + row + "].");
		synchronized (this) {
			// Put the position in a list to send to the listeners
			List<Position> playingPositions = new ArrayList<Position>();
			playingPositions.add(new Position(column, row));
			// Play the sound
			getSoundManager().playSound(row);
			
			// Send the event with the position to be played to the listeners.
			if (playingPositions.size() > 0) {
				ExecutionEvent event = new ExecutionEvent();
				event.setPositions(playingPositions);
				for (ExecutionListener listener : getSequencer().getExecutionListeners()) {
					listener.onStartPlayingGroup(event);
				}
			}
		}
		
	}

	public void stopPlayingSquare(int column, int row) {
		Log.d(TAG, "Stoping playing square [" + column + ", " + row + "]");
		synchronized (this) {
			List<Position> playingPositions = new ArrayList<Position>();
			playingPositions.add(new Position(column, row));
			if (playingPositions.size() > 0) {
				ExecutionEvent event = new ExecutionEvent();
				event.setPositions(playingPositions);
				for (ExecutionListener listener : getSequencer().getExecutionListeners()) {
					listener.onStopPlayingGroup(event);
				}
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
			Positioner positioner = createPositioner(position.getY());
			lineSequence.schedule(positioner, getSequencer().getTimeSequence());
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
	
	@Override 
	public void cleanUp() {
		//soundManager.cleanUp();
		
	}
	
	public Map<Integer, LineSequence> getLineSequences() {
		return lineSequences;
	}

	public void setLineSequences(Map<Integer, LineSequence> lineSequences) {
		this.lineSequences = lineSequences;
	}

	public PositionBehavior getPositionBehavior() {
		return positionBehavior;
	}

	public void setPositionBehavior(PositionBehavior positionBehavior) {
		this.positionBehavior = positionBehavior;
	}
}
