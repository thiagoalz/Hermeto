package net.thiagoalz.hermeto.panel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.thiagoalz.hermeto.player.Player;
import android.util.Log;

/**
 * The default implementation of the {@code GameContext} interface.
 *
 */
public class GameContextImpl implements GameContext {
	private final String TAG = GameContextImpl.class.getCanonicalName();
	
	private int columns;
	private int lines;
	
	private Map<String, Player> players;
	private Player masterDJ;
	
	private boolean playing;
	
	private Set<Position> markedSquares;
	
	public GameContextImpl(int columns, int lines) {
		this.columns = columns > 1 ? columns : 2;
		this.lines = lines > 1 ? lines : 2;
		Log.d(TAG, "Creating game context with [" + this.columns + ", " + this.lines + "]");
		
		players = new LinkedHashMap<String, Player>();
		markedSquares = new LinkedHashSet<Position>();
	}
	
	@Override
	public int[] getDimensions() {
		return new int[] {columns, lines};
	}

	@Override
	public Map<String, Player> getAllPlayers() {
		return players;
	}

	@Override
	public Player getMasterDJ() {
		return masterDJ;
	}
	
	public void setMasterDJ(Player player) {
		this.masterDJ = player;
		players.put(player.getId(), player);
	}

	@Override
	public boolean isPlaying() {
		return playing;
	}
	
	public void setPlaying(boolean playing) {
		this.playing = playing;
	}

	@Override
	public Set<Position> getMarkedSquares() {
		return markedSquares;
	}

	public void setMarkedSquares(Set<Position> markedSquares) {
		this.markedSquares = markedSquares;
	}

	@Override
	public List<Position> getColumnMarkedSquares(int column) {
		List<Position> playingPositions = new ArrayList<Position>();
		for (Position position : getMarkedSquares()) {
			if (position.getX() == column) {
				playingPositions.add(position);
			}
		}
		return playingPositions;
	}

	@Override
	public List<Position> getRowMarkedSquares(int row) {
		List<Position> playingPositions = new ArrayList<Position>();
		for (Position position : getMarkedSquares()) {
			if (position.getY() == row) {
				playingPositions.add(position);
			}
		}
		return playingPositions;
	}

}
