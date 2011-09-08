package net.thiagoalz.hermeto.panel.sequence;

import java.util.List;
import java.util.Timer;

import net.thiagoalz.hermeto.audio.SoundManager;
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
	private SoundManager soundManager;
	
	public GroupSequenceStrategy(Sequencer sequencer, SoundManager soundManager) {
		this.sequencer = sequencer;
		this.context = sequencer.getGameManager().getGameContext();
		this.soundManager = soundManager;
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
	
	@Override
	public void start() {
		if (soundManager == null) {
			throw new IllegalStateException("SoundManager cannot be null");
		}
		cleanTimer();
	}
	
	@Override
	public void stop() {
		Log.d(TAG, "Stoping the timer in the sequence strategy");
		cleanTimer();
	}
	
	@Override
	public void pause() {
		cleanTimer();
	}
	
	@Override 
	public void cleanUp() {
		soundManager.cleanUp();
	}
}
