package net.thiagoalz.hermeto.view.strategies;

import net.thiagoalz.hermeto.PadPanelActivity;
import net.thiagoalz.hermeto.R;
import net.thiagoalz.hermeto.panel.listeners.SelectionEvent;
import android.widget.ImageButton;

public class LineSequenceViewBehavior implements SelectionViewBehavior {
private PadPanelActivity padPanelActivity;
	
	public LineSequenceViewBehavior(PadPanelActivity padPanelActivity) {
		this.padPanelActivity = padPanelActivity;
	}
	
	@Override
	public synchronized void onSelected(SelectionEvent event) {
		int x = event.getPosition().getX();
		int y = event.getPosition().getY();
		ImageButton[][] padsMatrix = padPanelActivity.getPadsMatrix();
		for (int i = 0;  i <= y; i++) {
			padsMatrix[x][i].setBackgroundDrawable(padPanelActivity.getResources().getDrawable(R.drawable.buttonselected));
		}
	}

	@Override
	public synchronized void onDeselected(SelectionEvent event) {
		int x = event.getPosition().getX();
		int y = event.getPosition().getY();
		ImageButton[][] padsMatrix = padPanelActivity.getPadsMatrix();
		for (int i = 0;  i <= y; i++) {
			padsMatrix[x][i].setBackgroundDrawable(padPanelActivity.getResources().getDrawable(R.drawable.buttonstopped));
		}
		
		
	}

}
