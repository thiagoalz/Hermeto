package net.thiagoalz.hermeto;

import net.thiagoalz.hermeto.panel.GameManager;
import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.player.Player;
import android.view.View;
import android.widget.ImageButton;

public class MasterDjListener implements View.OnClickListener {

	private PadPanelActivity padPanel;
	private GameManager gameManager;
	
	public MasterDjListener(PadPanelActivity padPanel, GameManager gameManager) {
		this.padPanel = padPanel;
		this.gameManager = gameManager;
	}
	
	@Override
	public void onClick(View view) {
		ImageButton[][] padsMatrix = padPanel.getPadsMatrix();
		ImageButton selectedButton = (ImageButton) view;
		for (int i = 0; i < padsMatrix.length; i++) {
			for (int j = 0; j < padsMatrix[i].length; j++) {
				if (padsMatrix[j][i] == selectedButton) {
					Player masterDJ = gameManager.getGameContext().getMasterDJ();
					gameManager.move(masterDJ, new Position(j, i));
					gameManager.mark(masterDJ);
				}
			}
		}
	}
}
