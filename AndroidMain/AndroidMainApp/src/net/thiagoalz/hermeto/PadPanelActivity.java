package net.thiagoalz.hermeto;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.thiagoalz.hermeto.audio.InstrumentType;
import net.thiagoalz.hermeto.audio.SoundManager;
import net.thiagoalz.hermeto.control.ADKGameplayControl;
import net.thiagoalz.hermeto.control.XMPPGameplayControl;
import net.thiagoalz.hermeto.panel.GameManager;
import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.panel.controls.listeners.BPMChangeListener;
import net.thiagoalz.hermeto.panel.listeners.ConnectEvent;
import net.thiagoalz.hermeto.panel.listeners.ExecutionEvent;
import net.thiagoalz.hermeto.panel.listeners.IExecutionListener;
import net.thiagoalz.hermeto.panel.listeners.IPlayerListener;
import net.thiagoalz.hermeto.panel.listeners.ISelectionListener;
import net.thiagoalz.hermeto.panel.listeners.MoveEvent;
import net.thiagoalz.hermeto.panel.listeners.SelectionEvent;
import net.thiagoalz.hermeto.panel.sequence.strategies.ISequenceStrategy.PositionBehavior;
import net.thiagoalz.hermeto.panel.sequence.strategies.ISequenceStrategy.SequenceStrategyType;
import net.thiagoalz.hermeto.player.IPlayer;
import net.thiagoalz.hermeto.view.strategies.FreeSelectionStrategy;
import net.thiagoalz.hermeto.view.strategies.GroupSelectionStrategy;
import net.thiagoalz.hermeto.view.strategies.LineSelectionStrategy;
import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.DemoKit.DemoKitActivity;

public class PadPanelActivity extends DemoKitActivity implements ISelectionListener, IPlayerListener, IExecutionListener {
	
	private static final String TAG = PadPanelActivity.class.getCanonicalName();
	
	/**
	 * The number of player in the the ADK
	 */
	private static final int ADK_PLAYERS = 4;
	
	/**
	 * Game Manager is responsible for all the logic of the game.
	 */
	private GameManager gameManager;
	
	/**
	 * It is the interface for the ADK Players
	 */
	private ADKGameplayControl adkControl;
	
	/**
	 * It is the interface for the WEB players.
	 */
	private XMPPGameplayControl xmppControl;
	
	/**
	 * The matrix of buttons where the game is played.
	 */
	private ImageButton[][] padsMatrix;
	
	/**
	 * The layout of the button matrix
	 */
	private TableLayout tableLayout;
	
	/**
	 * The labels with the players name.
	 */
	Map<IPlayer, PlayerNameView> playersName = new HashMap<IPlayer, PlayerNameView>();
	
	
	/**
	 * Empty Constructor
	 */
	public PadPanelActivity(){
		super();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
		SoundManager soundManager = SoundManager.getInstance();
		soundManager.initSounds(this);
		
		gameManager = GameManager.getInstance();
		
		configureScreen();
		configSequenceStrategy(SequenceStrategyType.GROUP);
		constructView();
		
		Log.d(TAG, "Making the panel activity listen to the square selection events.");
		gameManager.addSelectionListener(this);
		Log.d(TAG, "Making the panel activity listen to the sequence events.");
		gameManager.getSequencer().addExecutionListener(this);
		Log.d(TAG, "Making the panel activity listen to the players events.");
		gameManager.addPlayerListener(this);
		
		adkControl = new ADKGameplayControl(gameManager);
		xmppControl = new XMPPGameplayControl(gameManager);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.panel_menu, menu);
		
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu (Menu menu){
		//Configure XMPP menu
		MenuItem xmppItem = menu.findItem(R.id.connect_xmpp);
		if(xmppControl.isRunning()){
			xmppItem.setTitle("Deny Web Players");
			xmppItem.setIcon(R.drawable.menu_phone_on);
		}else{
			xmppItem.setTitle("Allow Web Players");
			xmppItem.setIcon(R.drawable.menu_phone_off);
		}
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
		    case R.id.connect_xmpp:
		    	if (xmppControl.isRunning()) {
		    		xmppControl.stop();
		    	} else {
		    		xmppControl.start();
		    	}
		        return true;
		    case R.id.red:
		        gameManager.setInstrumentyType(InstrumentType.PERCUSIONS);
		        return true;
		    case R.id.blue:
		    	gameManager.setInstrumentyType(InstrumentType.TONES);
		    	return true;
		    case R.id.green:
		    	gameManager.setInstrumentyType(InstrumentType.HERMETO);
		    	return true;
		    case R.id.groupStrategy:
		    	configSequenceStrategy(SequenceStrategyType.GROUP);
		    	return true;
		    case R.id.freeStrategy:
		    	configSequenceStrategy(SequenceStrategyType.FREE);
		    	return true;
		    //case R.id.lineStrategy:
		    	//configSequenceStrategy(SequenceStrategyType.LINE);
		    	//return true;
		    default:
		    	return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onStop() {
		super.onStop();
	}
	
	@Override
	public void onRestart() {
		super.onRestart();
	}
	
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		
		gameManager.getSequencer().pause();
		gameManager.cleanUp();
		xmppControl.stop();
		this.finish();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		
		//Be sure that connected players are shown on correct place.
		if(hasFocus){
			Set<IPlayer> players=playersName.keySet();
			for (IPlayer player : players) {
				PlayerNameView playerNameView = playersName.get(player);
				playerNameView.setLocation(getLocation(player.getPosition()));
			}
			
			RelativeLayout namesLayout = (RelativeLayout) findViewById(R.id.namesLayout);
			namesLayout.invalidate();
		}
	}

	private void configureScreen() {
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
	
	private void constructView() {
		setContentView(R.layout.padpanel);
		tableLayout = (TableLayout) findViewById(R.id.padpanelgrid);
		initializeSquarePanel();
		initializeControls();
	}
	
	private void initializeSquarePanel() {
		int[] dimensions = gameManager.getGameContext().getDimensions();
		
		Log.d(TAG, "Creating panel with [" + dimensions[0]+ ", " + dimensions[1] + "] dimensions");
		padsMatrix = new ImageButton[dimensions[0]][dimensions[1]];
		
		for (int i = 0; i < padsMatrix.length; i++) {
			TableRow tableRow = new TableRow(this);
			for (int j = 0; j < padsMatrix[i].length; j++) {
				ImageButton button = new ImageButton(this);
				TableRow.LayoutParams params = new TableRow.LayoutParams(42, 42);
				params.gravity = Gravity.CENTER;
				button.setLayoutParams(params);
				button.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.buttonstopped));
				button.setOnClickListener(new MasterDjListener(this, gameManager));
				padsMatrix[j][i] = button;
				tableRow.addView(padsMatrix[j][i]);
			}
			tableLayout.addView(tableRow);
		}
	}
		
	private Position getLocation(Position position) {
		ImageButton button = padsMatrix[position.getX()][position.getY()];
		int screenLocation[] = new int[2];
		button.getLocationOnScreen(screenLocation);
		return new Position(screenLocation[0], screenLocation[1]);
	}
	
	private void initializeControls() {
		final SeekBar bpmBar = (SeekBar) findViewById(R.id.bpmBar);
		bpmBar.setProgress(gameManager.getBPM() / 4);
		
		TextView bpmView = (TextView) findViewById(R.id.bpmLabel);
		BPMChangeListener bpmListener = new BPMChangeListener(gameManager, bpmView, bpmBar, this);
		bpmView.setText("BPM: " + gameManager.getBPM());
		bpmBar.setOnSeekBarChangeListener(bpmListener);
				
		final ImageButton play = (ImageButton) findViewById(R.id.play);
		play.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (gameManager.getSequencer().isPlaying()) {
					gameManager.getSequencer().pause();
				} else {
					gameManager.getSequencer().start();
				}
			}
		});
		
		ImageButton stop = (ImageButton) findViewById(R.id.stop);
		stop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "onClick stop button");
				gameManager.getSequencer().stop();
			}
		});
		
		ImageButton reset = (ImageButton) findViewById(R.id.reset);
		reset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				gameManager.getSequencer().reset();
			}
		});
		
		ImageButton pĺus = (ImageButton) findViewById(R.id.plus);
		pĺus.setOnClickListener(bpmListener);
		
		ImageButton minus = (ImageButton) findViewById(R.id.minus);
		minus.setOnClickListener(bpmListener);
		
		final ToggleButton bounce = (ToggleButton) findViewById(R.id.bouncebutton);
		bounce.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (bounce.isChecked()) {
					Log.d(TAG, "Changin to bounce");
					gameManager.setPositionBehavior(PositionBehavior.BOUNCE);
		        } else {
		        	Log.d(TAG, "Changin to repeat");
		        	gameManager.setPositionBehavior(PositionBehavior.REPEAT);
		        }
			}
		});
	}

	@Override
	public void onSelected(SelectionEvent event) {
		int x = event.getPosition().getX();
		int y = event.getPosition().getY();
		InstrumentType type = gameManager.getGameContext().getCurrentInstrumentType();
		switch (type) {
			case PERCUSIONS: 
				padsMatrix[x][y].setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonselected));
				break;
			case TONES: 
				padsMatrix[x][y].setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonselected_blue)); 
				break;
			case HERMETO: 
				padsMatrix[x][y].setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonselected_green)); 
				break;
		}
	}

	@Override
	public void onDeselected(SelectionEvent event) {
		int x = event.getPosition().getX();
		int y = event.getPosition().getY();
		
		padsMatrix[x][y].setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonstopped)); //buttonVoiceSelected
	}

	@Override
	public void onPlayerMove(MoveEvent event) {
		final int padding = 5;
		int alreadyThere = 0;
		
		Map<String, IPlayer> players = gameManager.getPlayers();
		for (String playerID : players.keySet()) {
			if (event.getNewPosition().equals(players.get(playerID)))
				alreadyThere++;
		}
		
		Position newLocation = getLocation(event.getNewPosition());
		Position oldLocation = getLocation(event.getOldPosition());
		
		// Moving the player a little to the left based in how many users are there.
		newLocation = new Position(newLocation.getX() + (alreadyThere * padding), newLocation.getY());
	
		MoveEvent newEvent = new MoveEvent(event.getPlayer(), oldLocation, newLocation);
		PlayerNameView nameView = playersName.get(event.getPlayer());
		if(nameView != null){
			nameView.onPlayerMove(newEvent);
		}
	}

	@Override
	public void onPlayerConnect(ConnectEvent event) {
		IPlayer player = event.getPlayer();
		IPlayer masterDJ = gameManager.getGameContext().getMasterDJ();
		if(!(player.equals(masterDJ))){
			drawPlayerLabel(player);
		}
	}
	
	private void drawPlayerLabel(IPlayer player) {
		PlayerNameView playerNameView = new PlayerNameView(this);
		playerNameView.setText(player.getName());
		playerNameView.setLocation(getLocation(player.getPosition()));		
		playersName.put(player, playerNameView);
		RelativeLayout namesLayout = (RelativeLayout) findViewById(R.id.namesLayout);
		namesLayout.addView(playerNameView);
	}
	
	@Override
	public void onPlayerDisconnect(ConnectEvent event) {
		RelativeLayout namesLayout = (RelativeLayout) findViewById(R.id.namesLayout);
		PlayerNameView playerNameView = playersName.remove(event.getPlayer());
		namesLayout.removeView(playerNameView);
	}

	@Override
	public void onStart(ExecutionEvent event) {
		ImageButton play = (ImageButton) findViewById(R.id.play);
		play.setBackgroundDrawable(PadPanelActivity.this.getResources().getDrawable(R.drawable.stop));
		
	}

	@Override
	public void onStop(ExecutionEvent event) {
		ImageButton play = (ImageButton) findViewById(R.id.play);
		play.setBackgroundDrawable(PadPanelActivity.this.getResources().getDrawable(R.drawable.panel_play_button));
	}

	@Override
	public void onReset(ExecutionEvent event) {
		ImageButton play = (ImageButton) findViewById(R.id.play);
		play.setBackgroundDrawable(PadPanelActivity.this.getResources().getDrawable(R.drawable.panel_play_button));
	}

	@Override
	public void onPause(ExecutionEvent event) {
		ImageButton play = (ImageButton) findViewById(R.id.play);
		play.setBackgroundDrawable(PadPanelActivity.this.getResources().getDrawable(R.drawable.panel_play_button));
	}
	
	public void onStartPlayingGroup(final ExecutionEvent event) {
		
		Map<Position, InstrumentType> positions = event.getPositions();
		for (Position position : positions.keySet()) {
			final ImageButton button = padsMatrix[position.getX()][position.getY()];
			button.post(new Runnable() {
				public void run() {
					Log.d(TAG, "Printing selected buttons with playing color ");
					button.setBackgroundDrawable(PadPanelActivity.this.getResources().getDrawable(R.drawable.buttonplaying));
				}
			});
		}
	}
	
	public void onStopPlayingGroup(final ExecutionEvent event) {
		Log.d(TAG, "Printing selected buttons with selected color ");
		
		final Drawable buttonPercusionSelected = getResources().getDrawable(R.drawable.buttonselected);
		final Drawable buttonToneSelected = getResources().getDrawable(R.drawable.buttonselected_blue);
		final Drawable buttonHermetoSelected = getResources().getDrawable(R.drawable.buttonselected_green);
		
		Map<Position, InstrumentType> positions = event.getPositions();
		for (final Position position : positions.keySet()) {
			final ImageButton button = padsMatrix[position.getX()][position.getY()];
			button.post(new Runnable() {
				public void run() {
					switch (event.getPositions().get(position)) {
						case PERCUSIONS: 
							button.setBackgroundDrawable(buttonPercusionSelected);
							break;
						case TONES: 
							button.setBackgroundDrawable(buttonToneSelected);
							break;
						case HERMETO: 
							button.setBackgroundDrawable(buttonHermetoSelected);
							break;
					}
				}
			});
		}
	}
	
	public ImageButton[][] getPadsMatrix() {
		return padsMatrix;
	}

	public void setPadsMatrix(ImageButton[][] padsMatrix) {
		this.padsMatrix = padsMatrix;
	}
	
	private void configSequenceStrategy(SequenceStrategyType type) {
		switch (type) {
			case GROUP:
				// Configuring the sequence strategy in the sequencer
				gameManager.setCurrentSequenceStrategy(SequenceStrategyType.GROUP);				
				// Configuring the way that the squares are clicked.
				gameManager.setSelectionControl(new GroupSelectionStrategy(gameManager));
				break;
			case LINE:
				// Configuring the sequence strategy in the sequencer
				gameManager.setCurrentSequenceStrategy(SequenceStrategyType.LINE);
				// Configuring the way that the squares are clicked.
				gameManager.setSelectionControl(new LineSelectionStrategy(gameManager));
				break;
			case FREE:
				// Configuring the sequence strategy in the sequencer
				gameManager.setCurrentSequenceStrategy(SequenceStrategyType.FREE);
				// Configuring the way that the squares are clicked.
				gameManager.setSelectionControl(new FreeSelectionStrategy(gameManager));
				break;
		}
	}

	////////////////////////ADK CODE/////////////////////
	@Override
	protected void enableControls(boolean enable){
		if(enable){
			if (adkControl!=null && !adkControl.isDefaultPlayersConnected()) {
				adkControl.connectDefaultPlayers(ADK_PLAYERS);
			}
		}else{
			if(adkControl!=null){
				adkControl.disconnectAllPlayers();
			}
		}
	}
	
	protected void handleJoyMessage(JoyMsg j) {
//		if (mInputController != null) {
//			mInputController.joystickMoved(j.getX(), j.getY());
//		}
	}

	

	protected void handleLightMessage(LightMsg l) {
//		if (mInputController != null) {
//			mInputController.setLightValue(l.getLight());
//		}
	}

	protected void handleTemperatureMessage(TemperatureMsg t) {
//		if (mInputController != null) {
//			mInputController.setTemperature(t.getTemperature());
//		}
	}

	/**
	 * Used to test ADK connection
	 */
	protected void handleSwitchMessage(SwitchMsg o) {
//		if (mInputController != null) {
//			byte sw = o.getSw();
//			if (sw >= 0 && sw < 4) {
//				mInputController.switchStateChanged(sw, o.getState() != 0);
//			} else if (sw == 4) {
//				mInputController
//						.joystickButtonSwitchStateChanged(o.getState() != 0);
//			}
//		}
		
		if(o.getState() != 0){//Not release actions
			//Test LECHUGA
			Log.d("Lechuga","Button!");
	        
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Check!");
			AlertDialog alert = builder.create();
			
			alert.show();
		}
	}
	
	protected void handleSimpleJoyMessage(SwitchMsg k) {
		adkControl.processMessage(k.getSw()+"",k.getState()+"");
	}
}
