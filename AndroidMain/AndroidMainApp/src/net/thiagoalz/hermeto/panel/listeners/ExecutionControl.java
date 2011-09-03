package net.thiagoalz.hermeto.panel.listeners;

/**
 * Control the execution of the game like play, pause and reset.
 */
public interface ExecutionControl {
	/**
	 * Retrieve if the game is playing the sequence.
	 * 
	 * @return True indicates if the game is playing.
	 */
	public boolean isPlaying();
	
	/**
	 * Start execute the sequence selected.
	 */
	public void start();
	
	/**
	 * Stop the execution of the sequence.
	 */
	public void stop();
	
	/**
	 * Pause the execute of the sequence without start to the init.
	 */
	public void pause(); 
	
	/**
	 * Stop and clean all the selected squares.
	 */
	public void reset();
	
	/**
	 * Update the bpm.
	 * 
	 * @param bpm
	 */
	public void updateBPM(int bpm);
}
