package net.thiagoalz.hermeto.control;

import net.thiagoalz.hermeto.panel.GameManager;
import net.thiagoalz.hermeto.player.Player;
import net.thiagoalz.hermeto.player.Player.Direction;
import android.util.Log;

public class XMPPGameplayControl implements GameplayControl {

	private static final String tag = XMPPGameplayControl.class.getCanonicalName();
	private GameManager gameManager;
	
	public XMPPGameplayControl(GameManager gameManager) {
		this.gameManager = gameManager;
	}
	
	@Override
	public boolean processMessage(String playerReference, String message) {
		// TODO Implement ProcessMessage
		return false;
	}
	
	protected boolean movePlayer(String playerID, String dir) {
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

	protected boolean markSquare(String playerID) {
		return gameManager.getPlayer(playerID).mark();
	}

	protected String connectPlayer() {
		return gameManager.connectPlayer().getId();
	}

}
