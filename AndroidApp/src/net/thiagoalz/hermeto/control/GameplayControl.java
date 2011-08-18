package net.thiagoalz.hermeto.control;

/**
 * Control used by the ADK to simple execute a command in the application.
 */
public interface GameplayControl {
	/**
	 * Execute a command in the application. The command is associated 
	 * with the player. The command options can be see here:
	 * 
	 * Command - 	Value - 	Description
	 * 
	 * TOP			1			Move to the top one square from where the player is.
	 * DOWN			2			Move to the down one square from where the player is.
	 * LEFT			3			Move to the left one square from where the player is.
	 * RIGHT		4			Move to the right one square from where the player is.
	 * BUTTON		5			Mark the square where the player is located.
	 * CONNECT		6			Connect the player to the game.
	 * 
	 * @param playerID The number that identify the user.
	 * @param command The command that will be executed.
	 * @return True if the command executes successfully. Otherwise return false. 
	 */
	public boolean execute(int playerID, int command);
}
