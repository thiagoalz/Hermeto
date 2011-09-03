package net.thiagoalz.hermeto.panel.controls.listeners;

import net.thiagoalz.hermeto.panel.GameManager;
import android.content.Context;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Listener that implements the actions that need to be 
 * execute when the user interacts with the BPM seek bar. 
 */
public class BPMBarListener implements SeekBar.OnSeekBarChangeListener  {
	private static final String TAG = BPMBarListener.class.getCanonicalName();
	private static final int BPM_SCALE = 4;	
	
	private GameManager gameManager;
	private TextView bpmView;
	private Context context;
	private int progress;
	
	public BPMBarListener(GameManager gameManager, TextView bpmView, Context context) {
		this.context = context;
		this.gameManager = gameManager;
		this.bpmView = bpmView;
	}
	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
		this.progress = progress;
		bpmView.setText("BPM: " + progress * BPM_SCALE);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		int bpm = progress * BPM_SCALE;
		Log.d(TAG, "BPM Selected in the bar: " + progress + "(scaled: " + bpm + ")");
		
		if (gameManager.getSequencer().isPlaying()) {
			Log.d(TAG, "Updating BPM to " + bpm);
			gameManager.updateBPM(bpm);
		} else {
			Log.d(TAG, "Setting BPM to " + bpm);
			gameManager.setBPM(bpm);
		}
		
	}
}
