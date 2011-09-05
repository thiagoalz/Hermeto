package net.thiagoalz.hermeto.panel.sequence;

import java.util.List;
import java.util.Timer;

import net.thiagoalz.hermeto.panel.GameContext;
import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.panel.listeners.ExecutionEvent;
import net.thiagoalz.hermeto.panel.listeners.ExecutionListener;
import android.util.Log;

/**
 * The group sequence strategy make the lines play together.
 */
public abstract class GroupSequenceStrategy implements SequenceStrategy {
	private static final String TAG = GroupSequenceStrategy.class.getCanonicalName();
	
	private Timer timer;
	private Sequencer sequencer;
	private GameContext context;
	
	public GroupSequenceStrategy(Sequencer sequencer) {
		this.sequencer = sequencer;
		this.context = sequencer.getGameManager().getGameContext();
	}
	
	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	
	public Sequencer getSequencer() {
		return sequencer;
	}

	public void setSequencer(Sequencer sequencer) {
		this.sequencer = sequencer;
	}

	protected synchronized void startPlayingGroup(int group) {
		Log.d(TAG, "Starting playing group " + group);
		List<Position> playingPositions = context.getColumnMarkedSquares(group);
		if (playingPositions.size() > 0) {
			ExecutionEvent event = new ExecutionEvent();
			event.setPositions(playingPositions);
			for (ExecutionListener listener : sequencer.getExecutionListeners()) {
				listener.onStartPlayingGroup(event);
			}
		}
	}

	protected synchronized void stopPlayingGroup(int group) {
		Log.d(TAG, "Stoping playing group " + group);
		List<Position> playingPositions = context.getColumnMarkedSquares(group);
		if (playingPositions.size() > 0) {
			ExecutionEvent event = new ExecutionEvent();
			event.setPositions(playingPositions);
			for (ExecutionListener listener : sequencer.getExecutionListeners()) {
				listener.onStopPlayingGroup(event);
			}
		}
	}
}
