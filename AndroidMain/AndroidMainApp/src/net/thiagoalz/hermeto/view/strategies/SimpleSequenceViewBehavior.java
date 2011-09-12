package net.thiagoalz.hermeto.view.strategies;

import net.thiagoalz.hermeto.PadPanelActivity;
import net.thiagoalz.hermeto.R;
import net.thiagoalz.hermeto.panel.listeners.SelectionEvent;
import android.util.Log;
import android.widget.ImageButton;

public class SimpleSequenceViewBehavior implements SelectionViewBehavior {
	private static final String TAG = SimpleSequenceViewBehavior.class.getCanonicalName();
	
	private PadPanelActivity padPanelActivity;
	
	public SimpleSequenceViewBehavior(PadPanelActivity padPanelActivity) {
		this.padPanelActivity = padPanelActivity;
	}
	
	@Override
	public void onSelected(SelectionEvent event) {
		int x = event.getPosition().getX();
		int y = event.getPosition().getY();
		ImageButton[][] padsMatrix = padPanelActivity.getPadsMatrix();
		
		final ImageButton button = padsMatrix[x][y];
		button.post(new Runnable() {
			public void run() {
				button.setBackgroundDrawable(padPanelActivity.getResources().getDrawable(R.drawable.buttonselected));
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
				button.setBackgroundDrawable(padPanelActivity.getResources().getDrawable(R.drawable.buttonstopped));
			}
		});
	}
}
