package net.thiagoalz.hermeto.panel.sequence;

import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;

public class LineSequence {
	private static final String TAG = LineSequence.class.getCanonicalName();
	
	private Timer timer;
	private int playingLine;
	private int currentPlayingSquare;
	private int maxSquare;
	private boolean running;
	
	private LineSequenceStrategy sequenceStrategy;
	
	public LineSequence(LineSequenceStrategy sequenceStrategy, int playingLine) {
		this.sequenceStrategy = sequenceStrategy;
		this.playingLine = playingLine;
	}
	
	public void schedule(int maxSquare, int timeSequence) {
		Log.d(TAG, "Scheduling the line sequence #" + playingLine + " to execute until the #" + maxSquare + "position.");
		this.maxSquare = maxSquare;
		this.running = true;
		this.timer = new Timer();
		this.timer.scheduleAtFixedRate(new LinearLineTimerTask() , 0, timeSequence);
	}
		
	public void unschedule() {
		/* 
		 * Just marking the line sequence to be cancelled in the next
		 * iteration of the sequence.
		 * */
		Log.d(TAG, "Line sequence #" + playingLine + " unsheduling itself");
		this.running = false;
		this.stopTimer();
		
	}
	
	private void stopTimer() {
		Log.d(TAG, "Unscheduling the line sequence #" + playingLine);
		if (timer != null) {
			if (sequenceStrategy.getSequencer().getGameManager().getBPM() >= 60) {
				synchronized (sequenceStrategy) {
					Log.d(TAG, "Cancelling timer.");
					getTimer().cancel();
				}
			} else {//If BPM is too slow, do not wait for completion.
				Log.d(TAG, "Cancelling timer.");
				timer.cancel();
			}
		}
	}
	
	public void resetToStart() {
		currentPlayingSquare = 0;
	}
	
	
	public Timer getTimer() {
		return timer;
	}
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	public int getCurrentPlayingSquare() {
		return currentPlayingSquare;
	}
	public void setCurrentPlayingSquare(int currentPlayingSquare) {
		this.currentPlayingSquare = currentPlayingSquare;
	}
	
	private class LinearLineTimerTask extends TimerTask {
				
		public void run() {
			synchronized(sequenceStrategy) {
				if (!running) {
					stopTimer();
				}
				
				long startTime = System.currentTimeMillis();
				sequenceStrategy.startPlayingSquare(playingLine, currentPlayingSquare);
				
				/* keep it turned on until the half of the total period time */
				long waitTime = (sequenceStrategy.getSequencer().getTimeSequence() / 2)
						- (System.currentTimeMillis() - startTime); 
	
				// If we really need to wait more
				if (waitTime > 0) { 
					try {
						Thread.sleep(waitTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				sequenceStrategy.stopPlayingSquare(playingLine, currentPlayingSquare);
				currentPlayingSquare = (currentPlayingSquare + 1) % (maxSquare + 1);
				Log.d(TAG, "Setting the current playing column to : " + currentPlayingSquare + ". Thread: " + Thread.currentThread().getName());
			}
		}
	}
	
	public int getPlayingLine() {
		return playingLine;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	
	
}
