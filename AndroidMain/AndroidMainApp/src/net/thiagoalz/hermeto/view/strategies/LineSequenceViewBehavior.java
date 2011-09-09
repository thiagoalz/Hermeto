package net.thiagoalz.hermeto.view.strategies;

import net.thiagoalz.hermeto.PadPanelActivity;
import net.thiagoalz.hermeto.R;
import net.thiagoalz.hermeto.panel.GameManager;
import net.thiagoalz.hermeto.panel.listeners.SelectionEvent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageButton;

public class LineSequenceViewBehavior implements SelectionViewBehavior {
	private final static String TAG = LineSequenceViewBehavior.class.getCanonicalName();
	
	private PadPanelActivity padPanelActivity;
	private GameManager gameManager;
	
	private Drawable buttonSelected;
	private Drawable buttonDeselected;
	
	public LineSequenceViewBehavior(PadPanelActivity padPanelActivity, GameManager gameManager) {
		this.padPanelActivity = padPanelActivity;
		this.gameManager = gameManager;
		
		buttonSelected = padPanelActivity.getResources().getDrawable(R.drawable.buttonselected);
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
					square.setBackgroundDrawable(buttonSelected);
				}
			});
		}
	}

	@Override
	public void onDeselected(SelectionEvent event) {
		synchronized(gameManager.getSequencer().getSequenceStrategy()) {
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
