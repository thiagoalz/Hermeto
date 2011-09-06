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
	
	private LineSequenceStrategy sequenceStrategy;
	
	public LineSequence(LineSequenceStrategy sequenceStrategy) {
		this.sequenceStrategy = sequenceStrategy;
	}
	
	public void schedule(int maxSquare, int timeSequence) {
		this.maxSquare = maxSquare;
		
		timer = new Timer();
		timer.scheduleAtFixedRate(new LinearLineTimerTask() , 0, timeSequence);
	}
	
	public void stop() {
		
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
			synchronized(getTimer()) {
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
				currentPlayingSquare = (currentPlayingSquare + 1) % maxSquare;
				Log.d(TAG, "Setting the current playing column to : " + currentPlayingSquare + ".");
			}
		}
	}

	public int getPlayingLine() {
		return playingLine;
	}

	public void setPlayingLine(int playingLine) {
		this.playingLine = playingLine;
	}
	
	
}
