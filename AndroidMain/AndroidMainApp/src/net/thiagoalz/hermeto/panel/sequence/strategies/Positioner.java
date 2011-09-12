package net.thiagoalz.hermeto.panel.sequence.strategies;

/**
 * The positioner is responsible for the behavior of the sequencer. 
 * In other words, it gives the next square that will be played.
 */
public interface Positioner {
	/**
	 * Advance to the next position.
	 * 
	 * @return The next position to be played.
	 */
	public int nextPosition();
	
	/**
	 * Retrieve the current position that is been played.
	 * 
	 * @return The current position
	 */
	public int getCurrentPosition();
	
	/**
	 * The current position that is been played.
	 * 
	 * @return
	 */
	public void setCurrentPosition(int position);
	
	/**
	 * Retrieve the maximum position that the positioner will count.
	 * @return
	 */
	public int getMaxPosition();
	
	/**
	 * Set the maximum position that the positioner will count.
	 * 
	 * @return
	 */
	public void setMaxPosition(int position);
	
	/**
	 * Reset the positioner to the start.
	 */
	public void resetPosition();
}
