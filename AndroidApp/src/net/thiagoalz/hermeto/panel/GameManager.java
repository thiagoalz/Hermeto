package net.thiagoalz.hermeto.panel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.thiagoalz.hermeto.Position;
import net.thiagoalz.hermeto.player.Player;
import net.thiagoalz.hermeto.player.Player.Direction;
import net.thiagoalz.hermeto.player.PlayersManager;

/**
 * Manages the interaction of the users and the panel.
 * 
 * @author Gabriel Ozeas de Oliveira
 * @version 0.1
 */
public class GameManager implements SquarePanelManager, PlayersManager {
	
	private int columns;
	private int rows;
	
	private Map<String, Player> players = new LinkedHashMap<String, Player>();
	private List<Position> markedSquares = new ArrayList<Position>();
	
	/**
	 * Constructor that receives the dimension of the panel.
	 * 
	 * @param columns The number of columns of the panel.
	 * @param rows The number of rows of the panel.
	 */
	public GameManager(int columns, int rows) {
		this(columns, rows, null);
	}
	
	/**
	 * Constructor that received the dimension of the panel and the players.
	 * 
	 * @param columns The number of columns of the panel.
	 * @param rows The number of rows of the panel.
	 * @param players The list of players.
	 */
	public GameManager(int columns, int rows, Map<String, Player> players) {
		if (players != null) {
			this.players = players;
		}
		this.columns = columns > 1 ? columns : 2;
		this.rows = rows > 1 ? columns : 2;
	}
	
	@Override
	public boolean move(Player player, Direction direction) {
		int x = player.getPosition().getX();
		int y = player.getPosition().getY();
		
		switch (direction) {
			case LEFT:
				if (x - 1 < 1) {
					return false;
				} else {
					player.getPosition().setX(x - 1);
					return true;
				}
				
			case RIGHT:
				if (x + 1 > columns) {
					return false;
				} else {
					player.getPosition().setX(x + 1);
					return true;
				}
				
			case TOP:
				if (y - 1 < 1) {
					return false;
				} else {
					player.getPosition().setY(y - 1);
					return true;
				}
				
			case DOWN:
				if (y + 1 > rows) {
					return false;
				} else {
					player.getPosition().setY(y + 1);
					return true;
				}
		}
		return false;
	}

	@Override
	public boolean mark(Player player) {
		if (markedSquares.contains(player.getPosition())) {
			return false;
		} else {
			markedSquares.add(player.getPosition());
			return true;
		}
	}
	
	@Override
	public Position[] getMarkedSquares() {
		return (Position[]) markedSquares.toArray();
	}

	public int getColumns() {
		return columns;
	}

	public int getRows() {
		return rows;
	}

	@Override
	public Player connectPlayer() {
		String playerID = "playerID-" + System.currentTimeMillis();
		players.put(playerID, null);
		return null;
	}

	@Override
	public void disconnectPlayer(Player player) {
		players.remove(player);
		
	}

	@Override
	public Map<String, Player> getPlayers() {
		return players;
	}
	
	@Override
	public Player getPlayer(String playerID) {
		return players.get(playerID);
	}
}
