package net.thiagoalz.hermeto.panel.sequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.panel.listeners.ExecutionEvent;
import net.thiagoalz.hermeto.panel.listeners.ExecutionListener;
import android.util.Log;

/**
 * In the line sequence strategy, the lines in the panel are independent and can be 
 * started when the user select the first square in that line.
 */
public abstract class LineSequenceStrategy implements SequenceStrategy {
	private static final String TAG = LineSequenceStrategy.class.getCanonicalName();
	
	private Map<Integer, LineSequence> lineSequences;
	private Sequencer sequencer;
	
	public LineSequenceStrategy(Sequencer sequencer) {
		this.sequencer = sequencer;
		this.lineSequences = new HashMap<Integer, LineSequence>();
	}

	public Map<Integer, LineSequence> getLineSequences() {
		return lineSequences;
	}

	public void setLineSequences(Map<Integer, LineSequence> lineSequences) {
		this.lineSequences = lineSequences;
	}

	public Sequencer getSequencer() {
		return sequencer;
	}

	public void setSequencer(Sequencer sequencer) {
		this.sequencer = sequencer;
	}

	public void startPlayingSquare(int column, int row) {
		Log.d(TAG, "Starting playing square [" + column + ", " + row + "]");
		List<Position> playingPositions = new ArrayList<Position>();
		playingPositions.add(new Position(column, row));
		if (playingPositions.size() > 0) {
			ExecutionEvent event = new ExecutionEvent();
			event.setPositions(playingPositions);
			for (ExecutionListener listener : sequencer.getExecutionListeners()) {
				listener.onStartPlayingGroup(event);
			}
		}
		
	}

	public void stopPlayingSquare(int column, int row) {
		Log.d(TAG, "Stoping playing square [" + column + ", " + row + "]");
		List<Position> playingPositions = new ArrayList<Position>();
		playingPositions.add(new Position(column, row));
		if (playingPositions.size() > 0) {
			ExecutionEvent event = new ExecutionEvent();
			event.setPositions(playingPositions);
			for (ExecutionListener listener : sequencer.getExecutionListeners()) {
				listener.onStopPlayingGroup(event);
			}
		}
	}
	
	
}
