package net.thiagoalz.hermeto.panel.sequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.thiagoalz.hermeto.audio.SoundManager;
import net.thiagoalz.hermeto.panel.GameManager;
import net.thiagoalz.hermeto.panel.listeners.IExecutionControl;
import net.thiagoalz.hermeto.panel.listeners.ExecutionEvent;
import net.thiagoalz.hermeto.panel.listeners.IExecutionListener;
import net.thiagoalz.hermeto.panel.sequence.strategies.FreeSequenceStrategy;
import net.thiagoalz.hermeto.panel.sequence.strategies.GroupSequenceStrategy;
import net.thiagoalz.hermeto.panel.sequence.strategies.ISequenceStrategy.PositionBehavior;
import net.thiagoalz.hermeto.panel.sequence.strategies.LineSequenceStrategy;
import net.thiagoalz.hermeto.panel.sequence.strategies.ISequenceStrategy;
import net.thiagoalz.hermeto.panel.sequence.strategies.ISequenceStrategy.SequenceStrategyType;
import android.util.Log;

/**
 * The sequencer is responsible for execute the sequence action on the game 
 * like start, stop, reset the music.
 *  
 * @see GameManager#GameManager
 */
public class Sequencer implements IExecutionControl {
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
	private List<IExecutionListener> executionListeners;
	
	/**
	 * The sequence time that the sequence is using.
	 */
	private int timeSequence = DEFAULT_TIME_SEQUENCE;
	
	/**
	 * The strategy that the sequence are using.
	 */
	private ISequenceStrategy currentSequenceStrategy;
	
	/**
	 * The sequence strategies used in the sequencer
	 */
	private Map<SequenceStrategyType, ISequenceStrategy> sequenceStrategies;
	
	private SoundManager soundManager;
	
	public Sequencer(GameManager gameManager) {
		this(gameManager, null);
	}
	
	public Sequencer(GameManager gameManager, SequenceStrategyType type) {
		this.gameManager = gameManager;
		this.soundManager = SoundManager.getInstance();
		this.executionListeners = new ArrayList<IExecutionListener>();
		this.executionListeners.add(gameManager);
		this.sequenceStrategies = new HashMap<SequenceStrategyType, ISequenceStrategy>();
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
			ISequenceStrategy strategy = sequenceStrategies.get(sequenceStrategyType);
			strategy.start();
		}
		ExecutionEvent event = new ExecutionEvent();
		for (IExecutionListener listener : executionListeners) {
			listener.onStart(event);
		}
	}

	@Override
	public synchronized void stop() {
		for (SequenceStrategyType sequenceStrategyType : sequenceStrategies.keySet()) {
			ISequenceStrategy strategy = sequenceStrategies.get(sequenceStrategyType);
			strategy.stop();
		}
		Log.d(TAG, "Sequencer delegating the stop action to the sequence strategy");
		currentSequenceStrategy.stop();
		ExecutionEvent event = new ExecutionEvent();
		for (IExecutionListener listener : executionListeners) {
			listener.onStop(event);
		}
	}

	@Override
	public synchronized void pause() {
		for (SequenceStrategyType sequenceStrategyType : sequenceStrategies.keySet()) {
			ISequenceStrategy strategy = sequenceStrategies.get(sequenceStrategyType);
			strategy.pause();
		}
		ExecutionEvent event = new ExecutionEvent();
		for (IExecutionListener listener : executionListeners) {
			listener.onPause(event);
		}
	}

	@Override
	public synchronized void reset() {
		for (SequenceStrategyType sequenceStrategyType : sequenceStrategies.keySet()) {
			ISequenceStrategy strategy = sequenceStrategies.get(sequenceStrategyType);
			strategy.stop();
		}
		ExecutionEvent event = new ExecutionEvent();
		for (IExecutionListener listener : executionListeners) {
			listener.onReset(event);
		}
	}

	@Override
	public void updateBPM(int bpm) {
		// TODO Auto-generated method stub
	}
	
	public void removeExecutionListener(IExecutionListener executionListener) {
		this.executionListeners.remove(executionListener);
	}
	
	public void addExecutionListener(IExecutionListener executionListener) {
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

	public List<IExecutionListener> getExecutionListeners() {
		return executionListeners;
	}

	public void setExecutionListeners(List<IExecutionListener> executionListeners) {
		this.executionListeners = executionListeners;
	}

	public ISequenceStrategy getCurrentSequenceStrategy() {
		return currentSequenceStrategy;
	}

	public void setCurrentSequenceStrategy(SequenceStrategyType sequenceStrategyType) {
		currentSequenceStrategy = getSequenceStrategy(sequenceStrategyType);
	}
	
	public ISequenceStrategy getSequenceStrategy(SequenceStrategyType type) {
		ISequenceStrategy strategy = sequenceStrategies.get(type);
		if (strategy == null) {
			strategy = createNewStrategy(type);
			sequenceStrategies.put(type, strategy);
		}
		return strategy;
	}
	
	private ISequenceStrategy createNewStrategy(SequenceStrategyType type) {
		switch (type) {
			case LINE:
				return new LineSequenceStrategy(this, soundManager);
			case FREE:
				return new FreeSequenceStrategy(this, soundManager);
			case GROUP:
			default:
				return new GroupSequenceStrategy(this, soundManager);
		}
	}

	public void setPositionBehavior(PositionBehavior behavior) {
		for (SequenceStrategyType sequenceStrategyType : sequenceStrategies.keySet()) {
			ISequenceStrategy strategy = sequenceStrategies.get(sequenceStrategyType);
			strategy.setPositionBehavior(behavior);
		}
	}
	
	
	
}
