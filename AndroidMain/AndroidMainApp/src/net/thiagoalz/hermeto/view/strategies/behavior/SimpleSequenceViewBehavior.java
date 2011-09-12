package net.thiagoalz.hermeto.view.strategies.behavior;

import net.thiagoalz.hermeto.PadPanelActivity;
import net.thiagoalz.hermeto.R;
import net.thiagoalz.hermeto.audio.InstrumentType;
import net.thiagoalz.hermeto.panel.GameManager;
import net.thiagoalz.hermeto.panel.listeners.SelectionEvent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageButton;

public class SimpleSequenceViewBehavior implements ISelectionViewBehavior {
	private static final String TAG = SimpleSequenceViewBehavior.class.getCanonicalName();
	
	private PadPanelActivity padPanelActivity;
	private GameManager gameManager;
	
	private Drawable buttonPercusionSelected;
	private Drawable buttonVoiceSelected;
	private Drawable buttonDeselected;
	
	public SimpleSequenceViewBehavior(PadPanelActivity padPanelActivity, GameManager gameManager) {
		this.padPanelActivity = padPanelActivity;
		this.gameManager = gameManager;
		
		buttonPercusionSelected = padPanelActivity.getResources().getDrawable(R.drawable.buttonselected);
		buttonVoiceSelected = padPanelActivity.getResources().getDrawable(R.drawable.buttonselected_blue);
		buttonDeselected = padPanelActivity.getResources().getDrawable(R.drawable.buttonstopped);
	}
	
	@Override
	public void onSelected(SelectionEvent event) {
		int x = event.getPosition().getX();
		int y = event.getPosition().getY();
		ImageButton[][] padsMatrix = padPanelActivity.getPadsMatrix();
		
		final ImageButton button = padsMatrix[x][y];
		button.post(new Runnable() {
			public void run() {
				InstrumentType type = gameManager.getGameContext().getCurrentInstrumentType();
				switch (type) {
					case PERCUSIONS: 
						button.setBackgroundDrawable(buttonPercusionSelected);
						break;
					case VOICES: 
						button.setBackgroundDrawable(buttonVoiceSelected);
						break;
				}
			}
		});
	}

	@Override
	public void onDeselected(SelectionEvent event) {
		int x = event.getPosition().getX();
		int y = event.getPosition().getY();
		Log.d(TAG, "Deselecting the square at [" + x + "," + y + "]");
		ImageButton[][] padsMatrix = padPanelActivity.getPadsMatrix();
		
		final ImageButton button = padsMatrix[x][y];
		button.post(new Runnable() {
			public void run() {
				button.setBackgroundDrawable(buttonDeselected);
			}
		});
	}
	
	
}
