package net.thiagoalz.hermeto.view.strategies.behavior;

import net.thiagoalz.hermeto.PadPanelActivity;
import net.thiagoalz.hermeto.R;
import net.thiagoalz.hermeto.audio.InstrumentType;
import net.thiagoalz.hermeto.panel.GameManager;
import net.thiagoalz.hermeto.panel.listeners.SelectionEvent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageButton;

public class LineSequenceViewBehavior implements ISelectionViewBehavior {
	private final static String TAG = LineSequenceViewBehavior.class.getCanonicalName();
	
	private PadPanelActivity padPanelActivity;
	private GameManager gameManager;
	
	private Drawable buttonPercusionSelected;
	private Drawable buttonVoiceSelected;
	private Drawable buttonDeselected;
	
	public LineSequenceViewBehavior(PadPanelActivity padPanelActivity, GameManager gameManager) {
		this.padPanelActivity = padPanelActivity;
		this.gameManager = gameManager;
		
		buttonPercusionSelected = padPanelActivity.getResources().getDrawable(R.drawable.buttonselected);
		buttonVoiceSelected = padPanelActivity.getResources().getDrawable(R.drawable.buttonselected_blue);
		buttonDeselected = padPanelActivity.getResources().getDrawable(R.drawable.buttonstopped);
	}
	
	@Override
	public synchronized void onSelected(SelectionEvent event) {
		int x = event.getPosition().getX();
		int y = event.getPosition().getY();
		ImageButton[][] padsMatrix = padPanelActivity.getPadsMatrix();
		for (int i = 0;  i <= y; i++) {
			final ImageButton square = padsMatrix[x][i];
			square.post(new Runnable() {
				public void run() {
					InstrumentType type = gameManager.getGameContext().getCurrentInstrumentType();
					switch (type) {
						case PERCUSIONS: 
							square.setBackgroundDrawable(buttonPercusionSelected);
							break;
						case VOICES: 
							square.setBackgroundDrawable(buttonVoiceSelected);
							break;
					}
				}
			});
		}
	}

	@Override
	public void onDeselected(SelectionEvent event) {
		synchronized(gameManager.getSequencer().getCurrentSequenceStrategy()) {
			int x = event.getPosition().getX();
			int y = event.getPosition().getY();
			ImageButton[][] padsMatrix = padPanelActivity.getPadsMatrix();
			for (int i = 0;  i <= y; i++) {
				final ImageButton square = padsMatrix[x][i];
				final int squareIndex = i;
				square.post(new Runnable() {
					public void run() {
						square.setBackgroundDrawable(buttonDeselected);
						Log.d(TAG, "Reseting square #" + squareIndex + ". Thread: " + Thread.currentThread().getName());
					}
				});
			}
		}
		
	}

}
