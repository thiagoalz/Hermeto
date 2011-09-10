package net.thiagoalz.hermeto.panel;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.thiagoalz.hermeto.panel.listeners.ConnectEvent;
import net.thiagoalz.hermeto.panel.listeners.ExecutionEvent;
import net.thiagoalz.hermeto.panel.listeners.ExecutionListener;
import net.thiagoalz.hermeto.panel.listeners.MoveEvent;
import net.thiagoalz.hermeto.panel.listeners.PlayerListener;
import net.thiagoalz.hermeto.panel.listeners.SelectionControl;
import net.thiagoalz.hermeto.panel.listeners.SelectionListener;
import net.thiagoalz.hermeto.panel.sequence.Sequencer;
import net.thiagoalz.hermeto.player.DefaultPlayer;
import net.thiagoalz.hermeto.player.Player;
import net.thiagoalz.hermeto.player.Player.Direction;
import net.thiagoalz.hermeto.player.PlayersManager;
import android.util.Log;

/**
 * Manages the interaction of the users and the panel.
 * 
 * @author Gabriel Ozeas de Oliveira
 * @version 0.1
 */
public class GameManager implements SquarePanelManager, PlayersManager,
		ExecutionListener {

	private static final String tag = GameManager.class.getCanonicalName();
	
	private static final int COLUMNS_CONF = 16;
	private static final int ROWS_CONF = 16;
	
	private static final int DEFAULT_BPM = 300;

	private static GameManager instance;

	
	
	/**
	 * Responsible for the behavior of the execution and actions 
	 * like play, pause and stop. 
	 */
	private Sequencer sequencer;
	
	/**
	 * The control responsible for mark the selected positions.
	 */
	private SelectionControl selectionControl;
	
	/**
	 * The beat per minute used in the game
	 */
	private int bpm = DEFAULT_BPM;
	
	/**
	 * The components that are listening the selection events.
	 */
	private List<SelectionListener> selectionListeners = new ArrayList<SelectionListener>();
	
	/**
	 * The components that are listening the players events.
	 */
	private List<PlayerListener> playerListeners = new ArrayList<PlayerListener>();
	
	/**
	 * The game context keep all the game data.
	 */
	private GameContextImpl gameContext;

	public synchronized static GameManager getInstance() {
		return getInstance(COLUMNS_CONF, ROWS_CONF);
	}

	public synchronized static GameManager getInstance(int columns, int rows) {
		if (instance == null)
			instance = new GameManager(columns, rows);
		return instance;
	}

	/**
	 * Constructor that receives the dimension of the panel.
	 * 
	 * @param columns
	 *            The number of columns of the panel.
	 * @param rows
	 *            The number of rows of the panel.
	 */
	private GameManager(int columns, int rows) {
		gameContext = new GameContextImpl(columns, rows);
		sequencer = new Sequencer(this);
		Player masterDJ = createPlayer("Master DJ");
		gameContext.setMasterDJ(masterDJ);
		notifyConnectPlayerListeners(masterDJ);
	}

	public boolean move(Player player, Position newPosition) {
		Position oldPos = player.getPosition();
		player.setPosition(newPosition);
		Log.d(tag, "Changing position of the player: [" + newPosition.getX()
				+ ", " + newPosition.getY() + "]");
		notifyMovePlayerListeners(player, oldPos, player.getPosition());
		return true;
	}

	@Override
	public boolean move(Player player, Direction direction) {
		int columns = gameContext.getDimensions()[0];
		int rows = gameContext.getDimensions()[1];
		
		Position oldPos = player.getPosition();
		int x = oldPos.getX();
		int y = oldPos.getY();

		Log.d(tag, "Actual position of the player: [" + x + ", " + y + "]");

		switch (direction) {
		case LEFT:
			if (x - 1 < 0) {
				return false;
			} else {
				Position newPos = new Position(x - 1, y);
				return move(player, newPos);
			}

		case RIGHT:
			if (x + 1 >= columns) {
				return false;
			} else {
				Position newPos = new Position(x + 1, y);
				return move(player, newPos);
			}

		case UP:
			if (y - 1 < 0) {
				return false;
			} else {
				Position newPos = new Position(x, y - 1);
				return move(player, newPos);
			}

		case DOWN:
			if (y + 1 >= rows) {
				return false;
			} else {
				Position newPos = new Position(x, y + 1);
				return move(player, newPos);
			}
		}
		return false;
	}

	@Override
	public boolean mark(Player player) {
		// Delegate to the selectionControl
		return selectionControl.mark(player);
	}

	@Override
	public Player connectPlayer() {
		String playerName = "Player " + (gameContext.getAllPlayers().size() + 1);
		return this.connectPlayer(playerName);
	}

	public Player connectPlayer(String playerName) {
		Player player = createPlayer(playerName);

		Log.d(tag, "Connection " + playerName + "(" + player.getId()
				+ ") at the position [" + player.getPosition().getX() + ", "
				+ player.getPosition().getY() + "]");
		notifyConnectPlayerListeners(player);
		return player;
	}
	
	private Player createPlayer(String playerName) {
		int[] dimensions = gameContext.getDimensions();
		
		long nanotime = System.nanoTime();
		String playerID = "playerID-" + nanotime;

		Position position = null;
		Random myRandom = new Random(nanotime);
		position = new Position(myRandom.nextInt(dimensions[0]),
				myRandom.nextInt(dimensions[1]));

		DefaultPlayer player = new DefaultPlayer(playerName, playerID, this);
		player.setPosition(position);
		gameContext.getAllPlayers().put(player.getId(), player);
		return player;
	}

	@Override
	public void disconnectPlayer(Player player) {
		if(player!=null){
			gameContext.getAllPlayers().remove(player.getId());
			notifyDisconnectPlayerListeners(player);
		}
	}

	@Override
	public Map<String, Player> getPlayers() {
		return gameContext.getAllPlayers();
	}

	@Override
	public Player getPlayer(String playerID) {
		return gameContext.getAllPlayers().get(playerID);
	}

	// ==========Listeners==========
	public void addSelectionListener(SelectionListener selectionListener) {
		this.selectionListeners.add(selectionListener);
	}

	public void removeSelectionListener(SelectionListener selectionListener) {
		this.selectionListeners.remove(selectionListener);
	}

	public void addPlayerListener(PlayerListener playerListener) {
		this.playerListeners.add(playerListener);

	}

	public void removePlayerListener(PlayerListener playerListener) {
		this.playerListeners.remove(playerListener);
	}

	// ==========Notify==========
	private void notifyConnectPlayerListeners(Player player) {
		ConnectEvent event = new ConnectEvent(player, player.getPosition());
		for (PlayerListener listener : playerListeners) {
			listener.onPlayerConnect(event);
		}
	}

	private void notifyDisconnectPlayerListeners(Player player) {
		ConnectEvent event = new ConnectEvent(player, player.getPosition());
		for (PlayerListener listener : playerListeners) {
			listener.onPlayerDisconnect(event);
		}
	}

	private void notifyMovePlayerListeners(Player player, Position oldPos,
			Position newPos) {
		MoveEvent event = new MoveEvent(player, oldPos, newPos);
		for (PlayerListener listener : playerListeners) {
			listener.onPlayerMove(event);
		}
	}

	public int getBPM() {
		return bpm;
	}

	public void setBPM(int bpm) {
		boolean wasPlaying=sequencer.isPlaying();
		
		this.bpm = bpm;
		if (bpm < 1) {
			sequencer.setTimeSequence(60000);
			sequencer.pause();
			return;
		}
		sequencer.setTimeSequence(60000 / bpm);
		
		if(wasPlaying){
			sequencer.start();// Renew sequencer
		}
	}
	
	public GameContext getGameContext() { 
		return gameContext;
	}

	public void cleanUp() {
		sequencer.stop();
		sequencer.getSequenceStrategy().cleanUp();
		sequencer = new Sequencer(this);
		gameContext = new GameContextImpl(COLUMNS_CONF, ROWS_CONF);
		selectionListeners = new ArrayList<SelectionListener>();
		playerListeners = new ArrayList<PlayerListener>();
		GameManager.instance=null;
	}

	@Override
	public void onStart(ExecutionEvent event) {
		gameContext.setPlaying(true);
	}

	@Override
	public void onStop(ExecutionEvent event) {
		gameContext.setPlaying(false);
	}

	@Override
	public void onReset(ExecutionEvent event) {
		gameContext.setPlaying(false);
		// Deselect all the markedSquares and stop playing.
		selectionControl.cleanAll();
		gameContext.setMarkedSquares(new LinkedHashSet<Position>());
	}

	@Override
	public void onPause(ExecutionEvent event) {
		gameContext.setPlaying(false);
		
	}

	@Override
	public void onStartPlayingGroup(ExecutionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopPlayingGroup(ExecutionEvent event) {
		// TODO Auto-generated method stub
		
	}

	public Sequencer getSequencer() {
		return sequencer;
	}

	public List<SelectionListener> getSelectionListeners() {
		return selectionListeners;
	}

	public void setSelectionListeners(List<SelectionListener> selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public List<PlayerListener> getPlayerListeners() {
		return playerListeners;
	}

	public void setPlayerListeners(List<PlayerListener> playerListeners) {
		this.playerListeners = playerListeners;
	}

	public SelectionControl getSelectionControl() {
		return selectionControl;
	}

	public void setSelectionControl(SelectionControl selectionControl) {
		this.selectionControl = selectionControl;
	}
}
