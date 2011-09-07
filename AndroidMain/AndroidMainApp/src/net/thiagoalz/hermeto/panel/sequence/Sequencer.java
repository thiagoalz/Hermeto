package net.thiagoalz.hermeto.panel.sequence;

import java.util.ArrayList;
import java.util.List;

import net.thiagoalz.hermeto.panel.GameManager;
import net.thiagoalz.hermeto.panel.listeners.ExecutionControl;
import net.thiagoalz.hermeto.panel.listeners.ExecutionEvent;
import net.thiagoalz.hermeto.panel.listeners.ExecutionListener;
import android.util.Log;

/**
 * The sequencer is responsible for execute the sequence action on the game 
 * like start, stop, reset the music.
 *  
 * @see GameManager#GameManager
 */
public class Sequencer implements ExecutionControl {
	private static final String TAG = Sequencer.class.getCanonicalName();
	private static final int DEFAULT_TIME_SEQUENCE = 200;
	
	private GameManager gameManager;
	private List<ExecutionListener> executionListeners;
	private int timeSequence = DEFAULT_TIME_SEQUENCE;
	
	private SequenceStrategy sequenceStrategy;
	
	public Sequencer(GameManager gameManager) {
		this(gameManager, null);
	}
	
	public Sequencer(GameManager gameManager, SequenceStrategy sequenceStrategy) {
		this.gameManager = gameManager;
		this.executionListeners = new ArrayList<ExecutionListener>();
		this.executionListeners.add(gameManager);
		this.sequenceStrategy = sequenceStrategy;
	}
	
	@Override
	public boolean isPlaying() {
		boolean state = gameManager.getGameContext().isPlaying();
		Log.d(TAG, "The game is playing? " + state);
		return state;
	}

	@Override
	public synchronized void start() {
		if (sequenceStrategy == null) {
			throw new IllegalStateException("No SequenceStrategy was set to the Sequencer.");
		}
		sequenceStrategy.start();
		ExecutionEvent event = new ExecutionEvent();
		for (ExecutionListener listener : executionListeners) {
			listener.onStart(event);
		}
	}

	@Override
	public synchronized void stop() {
		if (sequenceStrategy == null) {
			throw new IllegalStateException("No SequenceStrategy was set to the Sequencer.");
		}
		Log.d(TAG, "Sequencer delegating the stop action to the sequence strategy");
		sequenceStrategy.stop();
		ExecutionEvent event = new ExecutionEvent();
		for (ExecutionListener listener : executionListeners) {
			listener.onStop(event);
		}
	}

	@Override
	public synchronized void pause() {
		if (sequenceStrategy == null) {
			throw new IllegalStateException("No SequenceStrategy was set to the Sequencer.");
		}
		sequenceStrategy.pause();
		ExecutionEvent event = new ExecutionEvent();
		for (ExecutionListener listener : executionListeners) {
			listener.onPause(event);
		}
	}

	@Override
	public synchronized void reset() {
		if (sequenceStrategy == null) {
			throw new IllegalStateException("No SequenceStrategy was set to the Sequencer.");
		}
		Log.d(TAG, "Reseting the sequencer.");
		sequenceStrategy.stop();
		ExecutionEvent event = new ExecutionEvent();
		for (ExecutionListener listener : executionListeners) {
			listener.onReset(event);
		}
	}

	@Override
	public void updateBPM(int bpm) {
		// TODO Auto-generated method stub
	}
	
	public void removeExecutionListener(ExecutionListener executionListener) {
		this.executionListeners.remove(executionListener);
	}
	
	public void addExecutionListener(ExecutionListener executionListener) {
		this.executionListeners.add(executionListener);
	}

	public GameManager getGameManager() {
		return gameManager;
	}

	public void setGameManager(GameManager gameManager) {
		this.gameManager = gameManager;
	}

	public int getTimeSequence() {
		return timeSequence;
	}

	public void setTimeSequence(int timeSequence) {
		this.timeSequence = timeSequence;
	}

	public List<ExecutionListener> getExecutionListeners() {
		return executionListeners;
	}

	public void setExecutionListeners(List<ExecutionListener> executionListeners) {
		this.executionListeners = executionListeners;
	}

	public SequenceStrategy getSequenceStrategy() {
		return sequenceStrategy;
	}

	public void setSequenceStrategy(SequenceStrategy sequenceStrategy) {
		if (sequenceStrategy != null)
			sequenceStrategy.stop();
		this.sequenceStrategy = sequenceStrategy;
		if (sequenceStrategy != null && isPlaying()) 
			sequenceStrategy.start();
	}
}
