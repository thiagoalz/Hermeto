package net.thiagoalz.hermeto.panel.sequence;


/**
 * This interface represent a sequence strategy that can be used to 
 * execute the square in a determined sequence.
 */
public interface SequenceStrategy {
	public void start();
	public void stop();
	public void pause();
	public void cleanUp();
}
