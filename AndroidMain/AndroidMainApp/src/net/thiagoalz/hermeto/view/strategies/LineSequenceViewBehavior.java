package net.thiagoalz.hermeto.view.strategies;

import android.widget.ImageButton;
import net.thiagoalz.hermeto.PadPanelActivity;
import net.thiagoalz.hermeto.R;
import net.thiagoalz.hermeto.panel.listeners.SelectionEvent;
import net.thiagoalz.hermeto.panel.listeners.SelectionListener;

public class LineSequenceViewBehavior implements SelectionViewBehavior {
private PadPanelActivity padPanelActivity;
	
	public LineSequenceViewBehavior(PadPanelActivity padPanelActivity) {
		this.padPanelActivity = padPanelActivity;
	}
	
	@Override
	public void onSelected(SelectionEvent event) {
		int x = event.getPosition().getX();
		int y = event.getPosition().getY();
		ImageButton[][] padsMatrix = padPanelActivity.getPadsMatrix();
		for (int i = 0;  i <= y; i++) {
			padsMatrix[x][i].setBackgroundDrawable(padPanelActivity.getResources().getDrawable(R.drawable.buttonselected));
		}
	}

	@Override
	public void onDeselected(SelectionEvent event) {
		int x = event.getPosition().getX();
		int y = event.getPosition().getY();
		ImageButton[][] padsMatrix = padPanelActivity.getPadsMatrix();
		for (int i = 0;  i <= y; i++) {
			padsMatrix[x][i].setBackgroundDrawable(padPanelActivity.getResources().getDrawable(R.drawable.buttonstopped));
		}
		
		
	}

}
