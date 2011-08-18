package net.thiagoalz.hermeto.panel;

import android.util.Log;
import net.thiagoalz.hermeto.player.Player;
import net.thiagoalz.hermeto.player.Player.Direction;

public class GameplayFacadeImpl implements GameplayFacade {

	private static final String tag = GameplayFacadeImpl.class.getCanonicalName();
	private GameManager gameManager;
	
	public GameplayFacadeImpl(GameManager gameManager) {
		this.gameManager = gameManager;
	}
	
	@Override
	public boolean move(String playerID, String dir) {
		if (gameManager == null) {
			throw new IllegalArgumentException("Game Manager not set.");
		}
		
		Player.Direction direction = parseDirection(dir);
		return gameManager.getPlayer(playerID).move(direction);
	}

	private Direction parseDirection(String direction) {
		Log.d(tag, "Parsing direction '" + direction + "'");
		Player.Direction dir;
		if (direction.equals(Player.Direction.LEFT.getValue())) {
			dir = Player.Direction.LEFT;
		} else if(direction.equals(Player.Direction.RIGHT.getValue())) {
			dir = Player.Direction.RIGHT;
		} else if(direction.equals(Player.Direction.TOP.getValue())) {
			dir = Player.Direction.TOP;
		} else {
			dir = Player.Direction.DOWN;
		}
		return dir;
	}

	@Override
	public boolean mark(String playerID) {
		if (gameManager == null) {
			throw new IllegalArgumentException("Game Manager not set.");
		}
		return gameManager.getPlayer(playerID).mark();
	}

	@Override
	public String connectPlayer() {
		return gameManager.connectPlayer().getId();
	}

	public GameManager getGameManager() {
		return gameManager;
	}

	public void setGameManager(GameManager gameManager) {
		this.gameManager = gameManager;
	}
}
