package net.thiagoalz.hermeto.panel.sequence.strategies;



/**
 * This interface represent a sequence strategy that can be used to 
 * execute the square in a determined sequence.
 */
public interface SequenceStrategy {
	
	/**
	 * All the sequence strategies that can be used.
	 */
	public enum SequenceStrategyType {
		GROUP, LINE, FREE;
	}
	
	/*
	 * All the valid behavior for the strategies
	 */
	public enum PositionBehavior {
		REPEAT, BOUNCE
	}
	
	/**
	 * Starts the execution of the squares. If the pause was pressed before the execution will continue 
	 * from that position.
	 */
	public void start();
	
	/**
	 * Stops the execution of the squares and reset the position of the player to the start.
	 */
	public void stop();
	
	/**
	 * Pauses the execution of the squares, so when the user 
	 * press start again the execution will continue from that position.
	 */
	public void pause();
	
	/**
	 * Clean up all the resources used by the sequence strategy.
	 */
	public void cleanUp();
}
