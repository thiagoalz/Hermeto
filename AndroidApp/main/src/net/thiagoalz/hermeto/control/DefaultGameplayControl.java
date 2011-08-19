package net.thiagoalz.hermeto.control;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import net.thiagoalz.hermeto.panel.GameplayFacade;
import net.thiagoalz.hermeto.player.Player;

public class DefaultGameplayControl implements GameplayControl {

	private static final String tag = DefaultGameplayControl.class.getCanonicalName();
	
	private GameplayFacade gameplayFacade;
	private Map<Integer, String> playerIDMap = new HashMap<Integer, String>();
	
	@Override
	public boolean execute(int playerID, int command) {
		if (command > 0 && command <= 4) {
			Log.d(tag, "Executing moving command");
			return movePlayer(playerID, command);			
		} else if (command == 5){
			Log.d(tag, "Executing mark command");
			return markSquare(playerID);
		} else if (command == 6) {
			Log.d(tag, "Executing connecting command");
			return connectPlayer(playerID);
		}
		return false;
	}
	
	private boolean movePlayer(int playerID, int command) {
		Player.Direction direction = null;
		switch(command) {
			case 1:
				direction = Player.Direction.TOP;
				break;
			case 2:
				direction = Player.Direction.DOWN;
				break;
			case 3:
				direction = Player.Direction.LEFT;
				break;
			case 4:
				direction = Player.Direction.RIGHT;
				break;
		}
		if (direction != null) {
			return gameplayFacade.move(playerIDMap.get(playerID), direction.getValue());
		}
		return false;
	}
	
	private boolean markSquare(int id) {
		String playerID = playerIDMap.get(id);
		return gameplayFacade.mark(playerID);
	}
	
	private boolean connectPlayer(int id) {
		String playerID = gameplayFacade.connectPlayer();
		Log.d(tag, "Mapping the player with ID # " + id + " to " + playerID);
		playerIDMap.put(id, playerID);
		return playerID != null;
	}

}
