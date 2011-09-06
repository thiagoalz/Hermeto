package net.thiagoalz.hermeto.panel.controls.listeners;

import net.thiagoalz.hermeto.R;
import net.thiagoalz.hermeto.panel.GameManager;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Listener that implements the actions that need to be 
 * execute when the user interacts with the BPM seek bar. 
 */
public class BPMChangeListener implements SeekBar.OnSeekBarChangeListener, OnClickListener  {
	private static final String TAG = BPMChangeListener.class.getCanonicalName();
	private static final int BPM_SCALE = 4;	
	
	private GameManager gameManager;
	private TextView bpmView;
	private Context context;
	private SeekBar seekBar;
	private int progress;
	
	public BPMChangeListener(GameManager gameManager, TextView bpmView, SeekBar seekBar, Context context) {
		this.context = context;
		this.gameManager = gameManager;
		this.bpmView = bpmView;
		this.seekBar = seekBar;
	}
	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
		this.progress = progress;
		updateText(progress * BPM_SCALE);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		int bpm = progress * BPM_SCALE;
		Log.d(TAG, "BPM Selected in the bar: " + progress + "(scaled: " + bpm + ")");
		
		updateBPM(bpm);
	}

	@Override
	public void onClick(View view) {
		int bpm = gameManager.getBPM();
		
		if(view.getId()==R.id.plus){//Plus button
			bpm = bpm < 400? bpm+1: 400;
		}else{//Minus button
			bpm = bpm > 0? bpm-1: 0;
		}
		
		Log.d(TAG, "BPM fine adjust: " + bpm);
		updateBar(bpm);
		updateText(bpm);
		updateBPM(bpm);
	}

	private void updateText(int bpm) {
		bpmView.setText("BPM: " + bpm);
	}

	private void updateBar(int bpm) {
		seekBar.setProgress(bpm/4);
	}

	private void updateBPM(int bpm) {
		Log.d(TAG, "Setting BPM to " + bpm);
		gameManager.setBPM(bpm);
	}
}
