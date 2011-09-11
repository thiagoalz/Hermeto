package net.thiagoalz.hermeto.panel.sequence.strategies;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import net.thiagoalz.hermeto.audio.SoundManager;
import net.thiagoalz.hermeto.panel.GameContext;
import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.panel.listeners.ExecutionEvent;
import net.thiagoalz.hermeto.panel.listeners.ExecutionListener;
import net.thiagoalz.hermeto.panel.sequence.Sequencer;
import android.util.Log;

/**
 * The group sequence strategy make the lines play together.
 */
public class GroupSequenceStrategy implements SequenceStrategy {
	private static final String TAG = GroupSequenceStrategy.class.getCanonicalName();
	
	/**
	 * Timer used to schedule the sequence
	 */
	private Timer timer;
	
	/**
	 * The sequencer that are invokes this strategy
	 */
	private Sequencer sequencer;
	
	/**
	 * The context with all game information.
	 */
	private GameContext context;
	
	/**
	 * The sound manager that are playing the musics
	 */
	private SoundManager soundManager;
	
	/**
	 * Responsible for control the flow of the execution.
	 */
	private Positioner positioner;

	
	public GroupSequenceStrategy(Sequencer sequencer, SoundManager soundManager) {
		this.sequencer = sequencer;
		this.context = sequencer.getGameManager().getGameContext();
		this.soundManager = soundManager;
		// By default use the repeat behavior
		setPositionBehavior(PositionBehavior.REPEAT);
	}
	
	protected void startPlayingGroup(int group) {
		Log.d(TAG, "Starting playing group " + group);
		
		synchronized(timer) {
			List<Position> playingPositions = context.getColumnMarkedSquares(group);
			if (playingPositions.size() > 0) {
				playGroupSound(playingPositions);
				
				ExecutionEvent event = new ExecutionEvent();
				event.setPositions(playingPositions);
				for (ExecutionListener listener : sequencer.getExecutionListeners()) {
					listener.onStartPlayingGroup(event);
				}
			}
		}
	}

	protected void stopPlayingGroup(int group) {
		Log.d(TAG, "Stoping playing group " + group);
		
		synchronized (timer) {
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
	
	protected void cleanTimer() {
		if (getTimer() != null) {
			if (getSequencer().getGameManager().getBPM() >= 60) {
				synchronized (getTimer()) {
					Log.d(TAG, "Cancelling timer.");
					getTimer().cancel();
				}
			} else {//If BPM is too slow, do not wait for completion.
				Log.d(TAG, "Cancelling timer.");
				getTimer().cancel();
			}
		}
	}
	
	protected void playGroupSound(List<Position> positions) {
		for (Position position : positions) {
			soundManager.playSound(position.getY());
		}
	}
	
	public synchronized void setPositionBehavior(PositionBehavior positionBehavior) {
		GameContext context = sequencer.getGameManager().getGameContext();
		int totalColumns = context.getDimensions()[0];
		
		switch(positionBehavior) {
			case REPEAT:
				positioner = new RepeatPositioner(totalColumns);
			case BOUNCE:
				positioner = new BouncePositioner(totalColumns);
			default:
				positioner =new RepeatPositioner(totalColumns);
		}
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

	public SoundManager getSoundManager() {
		return soundManager;
	}

	public void setSoundManager(SoundManager soundManager) {
		this.soundManager = soundManager;
	}
	
	public Positioner getPositioner() {
		return positioner;
	}

	public void setPositioner(Positioner positioner) {
		this.positioner = positioner;
	}

	@Override
	public void start() {
		if (soundManager == null) {
			throw new IllegalStateException("SoundManager cannot be null");
		}
		cleanTimer();
		
		Log.d(TAG, "Starting the sequencer at square #" + positioner.getCurrentPosition() + ".");
		/* 
		 * Think this timer is not that trustable. It may be the 
		 * cause of the lags. 
		 * */
		setTimer(new Timer());
		Log.d(TAG, "Scheduling a new timer.");
		getTimer().scheduleAtFixedRate(new GroupTimerTask(), 0, getSequencer().getTimeSequence());
	}
	
	@Override
	public void stop() {
		Log.d(TAG, "Stoping the sequencer, and reset the current playing position");
		cleanTimer();
		positioner.resetPosition();
	}
	
	@Override
	public void pause() {
		Log.d(TAG, "Pausing the sequencer.");
		cleanTimer();
	}
	
	@Override 
	public void cleanUp() {
		soundManager.cleanUp();
	}
	
	private class GroupTimerTask extends TimerTask {
		public void run() {
			synchronized(getTimer()) {
				long startTime = System.currentTimeMillis();
				GroupSequenceStrategy.this.startPlayingGroup(positioner.getCurrentPosition());
				
				/* keep it turned on until the half of the total period time */
				long waitTime = (getSequencer().getTimeSequence() / 2)
						- (System.currentTimeMillis() - startTime); 
	
				// If we really need to wait more
				if (waitTime > 0) { 
					try {
						Thread.sleep(waitTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				GroupSequenceStrategy.this.stopPlayingGroup(positioner.getCurrentPosition());
				// Moving sequencer to the next position
				positioner.nextPosition();
			}
		}
	}
}
