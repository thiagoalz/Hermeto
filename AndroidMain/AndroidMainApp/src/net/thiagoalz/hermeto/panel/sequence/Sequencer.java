package net.thiagoalz.hermeto.panel.sequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.thiagoalz.hermeto.audio.SoundManager;
import net.thiagoalz.hermeto.panel.GameManager;
import net.thiagoalz.hermeto.panel.listeners.ExecutionControl;
import net.thiagoalz.hermeto.panel.listeners.ExecutionEvent;
import net.thiagoalz.hermeto.panel.listeners.ExecutionListener;
import net.thiagoalz.hermeto.panel.sequence.strategies.FreeSequenceStrategy;
import net.thiagoalz.hermeto.panel.sequence.strategies.GroupSequenceStrategy;
import net.thiagoalz.hermeto.panel.sequence.strategies.LineSequenceStrategy;
import net.thiagoalz.hermeto.panel.sequence.strategies.SequenceStrategy;
import net.thiagoalz.hermeto.panel.sequence.strategies.SequenceStrategy.SequenceStrategyType;
import android.util.Log;

/**
 * The sequencer is responsible for execute the sequence action on the game 
 * like start, stop, reset the music.
 *  
 * @see GameManager#GameManager
 */
public class Sequencer implements ExecutionControl {
	private static final String TAG = Sequencer.class.getCanonicalName();
		
	/**
	 * Default sequence time used by the sequencer.
	 */
	private static final int DEFAULT_TIME_SEQUENCE = 200;
	
	/**
	 * The manager responsible to control the game
	 */
	private GameManager gameManager;
	
	/**
	 * The list of listeners that are observing the execution 
	 * events like play, pause and stop.
	 */
	private List<ExecutionListener> executionListeners;
	
	/**
	 * The sequence time that the sequence is using.
	 */
	private int timeSequence = DEFAULT_TIME_SEQUENCE;
	
	/**
	 * The strategy that the sequence are using.
	 */
	private SequenceStrategy currentSequenceStrategy;
	
	/**
	 * The sequence strategies used in the sequencer
	 */
	private Map<SequenceStrategyType, SequenceStrategy> sequenceStrategies;
	
	private SoundManager soundManager;
	
	public Sequencer(GameManager gameManager) {
		this(gameManager, null);
	}
	
	public Sequencer(GameManager gameManager, SequenceStrategyType type) {
		this.gameManager = gameManager;
		this.soundManager = SoundManager.getInstance();
		this.executionListeners = new ArrayList<ExecutionListener>();
		this.executionListeners.add(gameManager);
		this.sequenceStrategies = new HashMap<SequenceStrategyType, SequenceStrategy>();
		this.currentSequenceStrategy = type != null ? getSequenceStrategy(type) : getSequenceStrategy(SequenceStrategyType.GROUP);
	}
	
	@Override
	public boolean isPlaying() {
		boolean state = gameManager.getGameContext().isPlaying();
		Log.d(TAG, "The game is playing? " + state);
		return state;
	}

	@Override
	public synchronized void start() {
		for (SequenceStrategyType sequenceStrategyType : sequenceStrategies.keySet()) {
			SequenceStrategy strategy = sequenceStrategies.get(sequenceStrategyType);
			strategy.start();
		}
		ExecutionEvent event = new ExecutionEvent();
		for (ExecutionListener listener : executionListeners) {
			listener.onStart(event);
		}
	}

	@Override
	public synchronized void stop() {
		for (SequenceStrategyType sequenceStrategyType : sequenceStrategies.keySet()) {
			SequenceStrategy strategy = sequenceStrategies.get(sequenceStrategyType);
			strategy.stop();
		}
		Log.d(TAG, "Sequencer delegating the stop action to the sequence strategy");
		currentSequenceStrategy.stop();
		ExecutionEvent event = new ExecutionEvent();
		for (ExecutionListener listener : executionListeners) {
			listener.onStop(event);
		}
	}

	@Override
	public synchronized void pause() {
		for (SequenceStrategyType sequenceStrategyType : sequenceStrategies.keySet()) {
			SequenceStrategy strategy = sequenceStrategies.get(sequenceStrategyType);
			strategy.pause();
		}
		ExecutionEvent event = new ExecutionEvent();
		for (ExecutionListener listener : executionListeners) {
			listener.onPause(event);
		}
	}

	@Override
	public synchronized void reset() {
		for (SequenceStrategyType sequenceStrategyType : sequenceStrategies.keySet()) {
			SequenceStrategy strategy = sequenceStrategies.get(sequenceStrategyType);
			strategy.stop();
		}
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

	public SequenceStrategy getCurrentSequenceStrategy() {
		return currentSequenceStrategy;
	}

	public void setCurrentSequenceStrategy(SequenceStrategyType sequenceStrategyType) {
		currentSequenceStrategy = getSequenceStrategy(sequenceStrategyType);
	}
	
	public SequenceStrategy getSequenceStrategy(SequenceStrategyType type) {
		SequenceStrategy strategy = sequenceStrategies.get(type);
		if (strategy == null) {
			strategy = createNewStrategy(type);
			sequenceStrategies.put(type, strategy);
		}
		return strategy;
	}
	
	private SequenceStrategy createNewStrategy(SequenceStrategyType type) {
		switch (type) {
			case GROUP:
				return new GroupSequenceStrategy(this, soundManager);
			case LINE:
				return new LineSequenceStrategy(this, soundManager);
			case FREE:
				return new FreeSequenceStrategy(this, soundManager);
			default:
				return new GroupSequenceStrategy(this, soundManager);
		}
	}
	
	
	
}
