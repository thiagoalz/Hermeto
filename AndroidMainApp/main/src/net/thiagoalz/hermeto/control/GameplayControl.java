package net.thiagoalz.hermeto.control;

/**
 * Control used by the ADK and XMPP to simple execute a command in the application.
 */
public interface GameplayControl {
	
	/**
	 * Process a message coming from a player
	 * 
	 * @param playerReference Remote user reference.
	 * @param message The command that will be executed.
	 * @return True if the command executes successfully. Otherwise return false. 
	 */
	public boolean processMessage(String playerReference, String message);

}
