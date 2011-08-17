package net.thiagoalz.hermeto;

import net.thiagoalz.hermeto.panel.GameManager;
import net.thiagoalz.hermeto.player.Player;
import net.thiagoalz.hermeto.player.Player.Direction;

public class GameplayFacadeImpl implements GameplayFacade {

	private GameManager gameManager;
	
	public GameplayFacadeImpl(GameManager gameManager) {
		this.gameManager = gameManager;
	}
	
	@Override
	public boolean move(String playerID, String dir) {
		if (gameManager == null) {
			throw new IllegalArgumentException("Game Manager not set.");
		}
		
		Player.Direction direction = processDirection(dir);
		return gameManager.getPlayer(playerID).move(direction);
	}

	private Direction processDirection(String direction) {
		Player.Direction dir;
		
		if ("left".equals(direction)) {
			dir = Player.Direction.LEFT;
		} else if("right".equals(direction)) {
			dir = Player.Direction.RIGHT;
		} else if("top".equals(direction)) {
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
