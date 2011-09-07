package net.thiagoalz.hermeto.panel.sequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.thiagoalz.hermeto.audio.SoundManager;
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
	private SoundManager soundManager;
	
	public LineSequenceStrategy(Sequencer sequencer, SoundManager soundManager) {
		this.sequencer = sequencer;
		this.lineSequences = new HashMap<Integer, LineSequence>();
		this.soundManager = soundManager;
	}

	public void startPlayingSquare(int column, int row) {
		Log.d(TAG, "Starting playing square [" + column + ", " + row + "]");
		synchronized (this) {
			List<Position> playingPositions = new ArrayList<Position>();
			Log.d(TAG, "Starting playing the button at position [" + column + ", " + row + "].");
			playingPositions.add(new Position(column, row));
			soundManager.playSound(row);
			if (playingPositions.size() > 0) {
				ExecutionEvent event = new ExecutionEvent();
				event.setPositions(playingPositions);
				for (ExecutionListener listener : sequencer.getExecutionListeners()) {
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
				for (ExecutionListener listener : sequencer.getExecutionListeners()) {
					listener.onStopPlayingGroup(event);
				}
			}
		}
	}
	
	@Override 
	public void cleanUp() {
		soundManager.cleanUp();
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

	public SoundManager getSoundManager() {
		return soundManager;
	}

	public void setSoundManager(SoundManager soundManager) {
		this.soundManager = soundManager;
	}

	
}
