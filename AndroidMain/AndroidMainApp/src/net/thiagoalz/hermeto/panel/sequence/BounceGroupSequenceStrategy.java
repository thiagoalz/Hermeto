package net.thiagoalz.hermeto.panel.sequence;

import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;

/**
 *  The bounce group sequence strategy will make all the lines play together but when 
 *  the last square play the order of execution reverse.
 */
public class BounceGroupSequenceStrategy extends GroupSequenceStrategy  {
	private static final String TAG = BounceGroupSequenceStrategy.class.getCanonicalName();

	private int currentPlayingSquare;
	
	public BounceGroupSequenceStrategy(Sequencer sequencer) {
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
		getTimer().scheduleAtFixedRate(new BounceGroupTimerTask(), 0, getSequencer().getTimeSequence());
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
	
	private class BounceGroupTimerTask extends TimerTask {
		private boolean reversedDirection;

		public void run() {
			synchronized(getTimer()) {
				long startTime = System.currentTimeMillis();
				BounceGroupSequenceStrategy.this.startPlayingGroup(currentPlayingSquare);
				
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
				
				BounceGroupSequenceStrategy.this.stopPlayingGroup(currentPlayingSquare);
				// Circular
				int[] dimensions = getSequencer().getGameManager().getGameContext().getDimensions();
				
				if (!reversedDirection)
					currentPlayingSquare = (currentPlayingSquare + 1) % dimensions[0];
				else
					currentPlayingSquare = (currentPlayingSquare - 1) % dimensions[0];
				
				if ( currentPlayingSquare == 0 ) 
					reversedDirection = false;
				if ( currentPlayingSquare == (dimensions[0]-1) ) 
					reversedDirection = true;
				
				Log.d(TAG, "Setting the current playing column to : " + currentPlayingSquare + ".");
			}
		}
	}

}
