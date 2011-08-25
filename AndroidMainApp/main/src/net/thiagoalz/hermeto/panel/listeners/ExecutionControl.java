package net.thiagoalz.hermeto.panel.listeners;

/**
 * Control the execution of the game like play, pause and reset.
 */
public interface ExecutionControl {
	public boolean isPlaying();
	public void start();
	public void stop();
	public void pause(); 
	public void reset();
}
