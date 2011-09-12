package net.thiagoalz.hermeto.panel;

import java.util.LinkedHashMap;
import java.util.Map;

import net.thiagoalz.hermeto.player.IPlayer;
import android.util.Log;

/**
 * The default implementation of the {@code GameContext} interface.
 *
 */
public class GameContextImpl implements IGameContext {
	private final String TAG = GameContextImpl.class.getCanonicalName();
	
	private int columns;
	private int lines;
	
	private Map<String, IPlayer> players;
	private IPlayer masterDJ;
	
	private boolean playing;
	
	
	public GameContextImpl(int columns, int lines) {
		this.columns = columns > 1 ? columns : 2;
		this.lines = lines > 1 ? lines : 2;
		Log.d(TAG, "Creating game context with [" + this.columns + ", " + this.lines + "]");
		
		players = new LinkedHashMap<String, IPlayer>();
	}
	
	@Override
	public int[] getDimensions() {
		return new int[] {columns, lines};
	}

	@Override
	public Map<String, IPlayer> getAllPlayers() {
		return players;
	}

	@Override
	public IPlayer getMasterDJ() {
		return masterDJ;
	}
	
	public void setMasterDJ(IPlayer player) {
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
}
