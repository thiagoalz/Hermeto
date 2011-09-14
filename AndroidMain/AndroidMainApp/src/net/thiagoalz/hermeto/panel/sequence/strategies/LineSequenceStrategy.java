package net.thiagoalz.hermeto.panel.sequence.strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.thiagoalz.hermeto.audio.InstrumentType;
import net.thiagoalz.hermeto.audio.SoundManager;
import net.thiagoalz.hermeto.panel.GameManager;
import net.thiagoalz.hermeto.panel.IGameContext;
import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.panel.listeners.ExecutionEvent;
import net.thiagoalz.hermeto.panel.listeners.IExecutionListener;
import net.thiagoalz.hermeto.panel.listeners.ISelectionListener;
import net.thiagoalz.hermeto.panel.listeners.SelectionEvent;
import net.thiagoalz.hermeto.panel.sequence.Sequencer;
import net.thiagoalz.hermeto.panel.sequence.positioner.BouncePositioner;
import net.thiagoalz.hermeto.panel.sequence.positioner.Positioner;
import net.thiagoalz.hermeto.panel.sequence.positioner.RepeatPositioner;
import net.thiagoalz.hermeto.player.IPlayer;
import android.util.Log;

/**
 * In the line sequence strategy, the lines in the panel are independent and can be 
 * started when the user select the first square in that line.
 */
public class LineSequenceStrategy extends AbstractSequenceStrategy implements ISelectionListener {
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
		IGameContext gameContext = getSequencer().getGameManager().getGameContext();
		int columns = gameContext.getDimensions()[0];
		
		LineSequence lineSequence = null;
		for (int i = 0; i < columns; i++) {
			Map<Position, InstrumentType> positions = getColumnMarkedSquares(i);
			
			// Get a list with all the retrieve positions from the column
			List<Position> orderedPositions = new ArrayList<Position>(positions.keySet());
			
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
				Position lastSquare = orderedPositions.get(orderedPositions.size() - 1);
				Positioner positioner = createPositioner(lastSquare.getY());
	
				// Schedule the lineSequence to execute until the last marked square
				lineSequence.schedule(positioner, getSequencer().getTimeSequence(), gameContext.getCurrentInstrumentType());
			}
		}		
	}
	
	private Positioner createPositioner(int maxPosition) {
		switch(positionBehavior) {
			case BOUNCE:
				return new BouncePositioner(maxPosition);
			case REPEAT:
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

	public void startPlayingSquare(int column, int row, InstrumentType instrumentType) {
		Log.d(TAG, "Starting playing the button at position [" + column + ", " + row + "].");
		synchronized (this) {
			// Put the position in a list to send to the listeners
			Map<Position, InstrumentType> playingPositions = new LinkedHashMap<Position, InstrumentType>();
			playingPositions.put(new Position(column, row), instrumentType);
			// Play the sound
			getSoundManager().playSound(row, instrumentType);
			
			// Send the event with the position to be played to the listeners.
			if (playingPositions.size() > 0) {
				ExecutionEvent event = new ExecutionEvent();
				event.setPositions(playingPositions);
				for (IExecutionListener listener : getSequencer().getExecutionListeners()) {
					listener.onStartPlayingGroup(event);
				}
			}
		}
		
	}

	public void stopPlayingSquare(int column, int row, InstrumentType instrumentType) {
		Log.d(TAG, "Stoping playing square [" + column + ", " + row + "]");
		synchronized (this) {
			Map<Position, InstrumentType> playingPositions = new LinkedHashMap<Position, InstrumentType>();
			playingPositions.put(new Position(column, row), instrumentType);
			if (playingPositions.size() > 0) {
				ExecutionEvent event = new ExecutionEvent();
				event.setPositions(playingPositions);
				for (IExecutionListener listener : getSequencer().getExecutionListeners()) {
					listener.onStopPlayingGroup(event);
				}
			}
		}
	}
	
	@Override
	public void onSelected(SelectionEvent event) {
		IGameContext gameContext = getSequencer().getGameManager().getGameContext();
		
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
			lineSequence.schedule(positioner, getSequencer().getTimeSequence(), gameContext.getCurrentInstrumentType());
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
	public void reset() {
		for (Position position : getMarkedSquares().keySet()) {
			deselectLine(null, position);
		}
		setMarkedSquares(new LinkedHashMap<Position, InstrumentType>());
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
	
	
	/////////////////////////////
	protected void notifyDeselection(IPlayer player, Position position) {
		GameManager.getInstance().notifyDeselection(player, position);
	}
	
	private void deselectLine(IPlayer player, Position selectedPosition) {
		for(int y=0; y<=selectedPosition.getY();y++){
			Position pos=new Position(selectedPosition.getX(),y);
			notifyDeselection(player, pos);
		}
	}
}
