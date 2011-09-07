package net.thiagoalz.hermeto.view.strategies;

import net.thiagoalz.hermeto.PadPanelActivity;
import net.thiagoalz.hermeto.R;
import net.thiagoalz.hermeto.panel.listeners.SelectionEvent;
import net.thiagoalz.hermeto.panel.listeners.SelectionListener;
import android.widget.ImageButton;

public class GroupSequenceViewBehavior implements SelectionListener {

	private PadPanelActivity padPanelActivity;
	
	public GroupSequenceViewBehavior(PadPanelActivity padPanelActivity) {
		this.padPanelActivity = padPanelActivity;
	}
	
	@Override
	public void onSelected(SelectionEvent event) {
		int x = event.getPosition().getX();
		int y = event.getPosition().getY();
		ImageButton[][] padsMatrix = padPanelActivity.getPadsMatrix();
		padsMatrix[x][y].setBackgroundDrawable(padPanelActivity.getResources().getDrawable(R.drawable.buttonselected));
		
	}

	@Override
	public void onDeselected(SelectionEvent event) {
		int x = event.getPosition().getX();
		int y = event.getPosition().getY();
		ImageButton[][] padsMatrix = padPanelActivity.getPadsMatrix();
		padsMatrix[x][y].setBackgroundDrawable(padPanelActivity.getResources().getDrawable(R.drawable.buttonstopped));
		
	}

}
