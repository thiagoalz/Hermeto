package net.thiagoalz.hermeto.panel.sequence;

import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;

/**
 * The linear group sequence strategy will make the lines play together until the last 
 * square. After it will return to the first square of the lines.
 */
public class LinearGroupSequenceStrategy extends GroupSequenceStrategy {
	private static final String TAG = LinearGroupSequenceStrategy.class.getCanonicalName();

	private int currentPlayingSquare;
	
	public LinearGroupSequenceStrategy(Sequencer sequencer) {
		super(sequencer);
	}

	@Override
	public void start() {
		if (getTimer() != null) {
			Log.d(TAG, "Cancelling timer.");
			getTimer().cancel();
		}
		Log.d(TAG, "Starting the sequencer at square #" + currentPlayingSquare + ".");
		// Think this timer is not that trustable. It may be the cause of the
		// lags.
		setTimer(new Timer());
		Log.d(TAG, "Scheduling a new timer.");
		getTimer().scheduleAtFixedRate(new LinearGroupTimerTask(), 0, getSequencer().getTimeSequence());
	}

	@Override
	public synchronized void stop() {
		if (getTimer() != null) {
			Log.d(TAG, "Stoping the sequencer, and reset the current playing square #" + currentPlayingSquare+ " to 0.");
			getTimer().cancel();
			setTimer(null);
			currentPlayingSquare = 0;
		}
	}

	@Override
	public synchronized void pause() {
		if (getTimer() != null) {
			Log.d(TAG, "Pausing the sequencer at column #" + currentPlayingSquare + ".");
			getTimer().cancel();
			setTimer(null);
		}
	}

	private class LinearGroupTimerTask extends TimerTask {
		public void run() {
			synchronized(getTimer()) {
				long startTime = System.currentTimeMillis();
				LinearGroupSequenceStrategy.this.startPlayingGroup(currentPlayingSquare);
				
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
				
				LinearGroupSequenceStrategy.this.stopPlayingGroup(currentPlayingSquare);
				// Circular
				int[] dimensions = getSequencer().getGameManager().getGameContext().getDimensions();
				currentPlayingSquare = (currentPlayingSquare + 1) % dimensions[0];
				Log.d(TAG, "Setting the current playing column to : " + currentPlayingSquare + ".");
			}
		}
	}
}
