package net.thiagoalz.hermeto.panel;

import java.util.LinkedHashMap;
import java.util.Map;

import net.thiagoalz.hermeto.audio.InstrumentType;
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
	
	private InstrumentType currentInstrumentType;
		
	public GameContextImpl(int columns, int lines) {
		this.columns = columns > 1 ? columns : 2;
		this.lines = lines > 1 ? lines : 2;
		Log.d(TAG, "Creating game context with [" + this.columns + ", " + this.lines + "]");
		
		players = new LinkedHashMap<String, Player>();
		
		currentInstrumentType = InstrumentType.PERCUSIONS;
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

	public InstrumentType getCurrentInstrumentType() {
		return currentInstrumentType;
	}

	public void setCurrentInstrumentType(InstrumentType currentInstrumentType) {
		this.currentInstrumentType = currentInstrumentType;
	}
}
