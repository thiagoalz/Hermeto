package net.thiagoalz.hermeto.panel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.thiagoalz.hermeto.audio.InstrumentType;
import net.thiagoalz.hermeto.panel.listeners.ConnectEvent;
import net.thiagoalz.hermeto.panel.listeners.ExecutionEvent;
import net.thiagoalz.hermeto.panel.listeners.IExecutionListener;
import net.thiagoalz.hermeto.panel.listeners.MoveEvent;
import net.thiagoalz.hermeto.panel.listeners.IPlayerListener;
import net.thiagoalz.hermeto.panel.listeners.ISelectionControl;
import net.thiagoalz.hermeto.panel.listeners.SelectionEvent;
import net.thiagoalz.hermeto.panel.listeners.ISelectionListener;
import net.thiagoalz.hermeto.panel.sequence.Sequencer;
import net.thiagoalz.hermeto.panel.sequence.strategies.ISequenceStrategy;
import net.thiagoalz.hermeto.panel.sequence.strategies.ISequenceStrategy.PositionBehavior;
import net.thiagoalz.hermeto.panel.sequence.strategies.LineSequenceStrategy;
import net.thiagoalz.hermeto.panel.sequence.strategies.ISequenceStrategy.SequenceStrategyType;
import net.thiagoalz.hermeto.player.DefaultPlayer;
import net.thiagoalz.hermeto.player.IPlayer;
import net.thiagoalz.hermeto.player.IPlayer.Direction;
import net.thiagoalz.hermeto.player.IPlayersManager;
import android.util.Log;

/**
 * Manages the interaction of the users and the panel.
 * 
 * @author Gabriel Ozeas de Oliveira
 * @version 0.1
 */
public class GameManager implements ISquarePanelManager, IPlayersManager,
		IExecutionListener {

	private static final String TAG = GameManager.class.getCanonicalName();
	
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
	private ISelectionControl selectionControl;
	
	/**
	 * The beat per minute used in the game
	 */
	private int bpm = DEFAULT_BPM;
	
	/**
	 * The components that are listening the selection events.
	 */
	private List<ISelectionListener> selectionListeners = new ArrayList<ISelectionListener>();
	
	/**
	 * The components that are listening the players events.
	 */
	private List<IPlayerListener> playerListeners = new ArrayList<IPlayerListener>();
	
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
		IPlayer masterDJ = createPlayer("Master DJ");
		gameContext.setMasterDJ(masterDJ);
		notifyConnectPlayerListeners(masterDJ);
	}

	public boolean move(IPlayer player, Position newPosition) {
		Position oldPos = player.getPosition();
		player.setPosition(newPosition);
		Log.d(TAG, "Changing position of the player: [" + newPosition.getX()
				+ ", " + newPosition.getY() + "]");
		notifyMovePlayerListeners(player, oldPos, player.getPosition());
		return true;
	}

	@Override
	public boolean move(IPlayer player, Direction direction) {
		int columns = gameContext.getDimensions()[0];
		int rows = gameContext.getDimensions()[1];
		
		Position oldPos = player.getPosition();
		int x = oldPos.getX();
		int y = oldPos.getY();

		Log.d(TAG, "Actual position of the player: [" + x + ", " + y + "]");

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
	public boolean mark(IPlayer player) {
		// Delegate to the selectionControl
		return selectionControl.mark(player);
	}

	@Override
	public IPlayer connectPlayer() {
		String playerName = "Player " + (gameContext.getAllPlayers().size() + 1);
		return this.connectPlayer(playerName);
	}

	public IPlayer connectPlayer(String playerName) {
		IPlayer player = createPlayer(playerName);

		Log.d(TAG, "Connection " + playerName + "(" + player.getId()
				+ ") at the position [" + player.getPosition().getX() + ", "
				+ player.getPosition().getY() + "]");
		notifyConnectPlayerListeners(player);
		return player;
	}
	
	private IPlayer createPlayer(String playerName) {
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
	public void disconnectPlayer(IPlayer player) {
		if(player!=null){
			gameContext.getAllPlayers().remove(player.getId());
			notifyDisconnectPlayerListeners(player);
		}
	}

	@Override
	public Map<String, IPlayer> getPlayers() {
		return gameContext.getAllPlayers();
	}

	@Override
	public IPlayer getPlayer(String playerID) {
		return gameContext.getAllPlayers().get(playerID);
	}

	// ==========Listeners==========
	public void addSelectionListener(ISelectionListener selectionListener) {
		this.selectionListeners.add(selectionListener);
	}

	public void removeSelectionListener(ISelectionListener selectionListener) {
		this.selectionListeners.remove(selectionListener);
	}

	public void addPlayerListener(IPlayerListener playerListener) {
		this.playerListeners.add(playerListener);

	}

	public void removePlayerListener(IPlayerListener playerListener) {
		this.playerListeners.remove(playerListener);
	}

	// ==========Notify==========
	private void notifyConnectPlayerListeners(IPlayer player) {
		ConnectEvent event = new ConnectEvent(player, player.getPosition());
		for (IPlayerListener listener : playerListeners) {
			listener.onPlayerConnect(event);
		}
	}

	private void notifyDisconnectPlayerListeners(IPlayer player) {
		ConnectEvent event = new ConnectEvent(player, player.getPosition());
		for (IPlayerListener listener : playerListeners) {
			listener.onPlayerDisconnect(event);
		}
	}

	private void notifyMovePlayerListeners(IPlayer player, Position oldPos,
			Position newPos) {
		MoveEvent event = new MoveEvent(player, oldPos, newPos);
		for (IPlayerListener listener : playerListeners) {
			listener.onPlayerMove(event);
		}
	}
	
	public void notifySelection(IPlayer player, Position position) {
		SelectionEvent event = new SelectionEvent(player, position);
		for (ISelectionListener listener : this.selectionListeners) {
			Log.d(TAG, "Informing the " + listener + " about selection.");
			listener.onSelected(event);
		}
	}

	public void notifyDeselection(IPlayer player, Position position) {
		SelectionEvent event = new SelectionEvent(player, position);
		for (ISelectionListener listener : this.selectionListeners) {
			Log.d(TAG, "Informing the " + listener + " about deselection.");
			listener.onDeselected(event);
		}
	}

	public int getBPM() {
		return bpm;
	}

	public void setBPM(int bpm) {
		boolean wasPlaying = sequencer.isPlaying();
		
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
	
	public IGameContext getGameContext() { 
		return gameContext;
	}

	public void cleanUp() {
		sequencer.stop();
		sequencer.getCurrentSequenceStrategy().cleanUp();
		sequencer = new Sequencer(this);
		gameContext = new GameContextImpl(COLUMNS_CONF, ROWS_CONF);
		selectionListeners = new ArrayList<ISelectionListener>();
		playerListeners = new ArrayList<IPlayerListener>();
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
		//gameContext.setMarkedSquares(new LinkedHashSet<Position>());
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

	public List<IPlayerListener> getPlayerListeners() {
		return playerListeners;
	}

	public void setPlayerListeners(List<IPlayerListener> playerListeners) {
		this.playerListeners = playerListeners;
	}

	public ISelectionControl getSelectionControl() {
		return selectionControl;
	}

	public void setSelectionControl(ISelectionControl selectionControl) {
		this.selectionControl = selectionControl;
	}

	public void setCurrentSequenceStrategy(SequenceStrategyType group) {
		this.getSequencer().setCurrentSequenceStrategy(group);
		
		if(group == SequenceStrategyType.LINE){
			// Configuring the sequence strategy to listing the selection events
			ISequenceStrategy sequenceStrategy = this.getSequencer().getSequenceStrategy(SequenceStrategyType.LINE);
			this.addSelectionListener(((LineSequenceStrategy)sequenceStrategy));
		}
	}

	public void setInstrumentyType(InstrumentType currentInstrumentType) {
		gameContext.setCurrentInstrumentType(currentInstrumentType);
	}

	public void setPositionBehavior(PositionBehavior behavior) {
		sequencer.setPositionBehavior(behavior);
	}
}
