package net.thiagoalz.hermeto.control;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import net.thiagoalz.hermeto.panel.GameManager;
import net.thiagoalz.hermeto.panel.GameplayFacade;
import net.thiagoalz.hermeto.panel.GameplayFacadeImpl;
import net.thiagoalz.hermeto.player.Player;

public class ADKGameplayControl implements GameplayControl {

	protected static final String tag = ADKGameplayControl.class.getCanonicalName();
	
	protected GameplayFacade gameplayFacade;
	protected Map<Integer, String> playerIDMap = new HashMap<Integer, String>();
	
	public ADKGameplayControl(GameManager gameManager, int controlNumber){
		this.gameplayFacade = new GameplayFacadeImpl(gameManager);
		
		//Connecting ADK players
		for(int i=0; i<controlNumber; i++){
			connectPlayer(i);
		}
	}
	
	public void processMessage(String player, String message){
		try{
			execute(Integer.parseInt(player), Integer.parseInt(message));
		}catch(NumberFormatException e){
			Log.d(tag, "Error trying to parseint: Player("+player+"), Message("+message+")");
		}
	}
	
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
		if (direction != null && playerIDMap.get(playerID)!=null) {
			return gameplayFacade.move(playerIDMap.get(playerID), direction.getValue());
		}
		return false;
	}
	
	private boolean markSquare(int id) {
		String playerID = playerIDMap.get(id);

		if(playerID!=null){
			return gameplayFacade.mark(playerID);
		}
		return false;
	}
	
	private boolean connectPlayer(int id) {
		String playerID = gameplayFacade.connectPlayer();
		Log.d(tag, "Mapping the player with ID # " + id + " to " + playerID);
		playerIDMap.put(id, playerID);
		return playerID != null;
	}

}
