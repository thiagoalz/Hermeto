package net.thiagoalz.hermeto.view.strategies;

import net.thiagoalz.hermeto.audio.SoundManager;
import net.thiagoalz.hermeto.panel.GameManager;
import net.thiagoalz.hermeto.player.IPlayer;
import android.util.Log;

public class FreeSelectionStrategy extends AbstractSelectionStrategy {
	private static final String TAG = FreeSelectionStrategy.class.getCanonicalName();
	
	public FreeSelectionStrategy(GameManager gameManager) {
		super(gameManager);
	}
	
	@Override

	public boolean mark(IPlayer player) {
		int column = player.getPosition().getX();
		int row = player.getPosition().getY();
		Log.d(TAG, "Starting playing the button at position [" + column + ", " + row + "].");
		
		synchronized (this) {
			// Play the sound
			SoundManager.getInstance().playSound(row,getGameManager().getGameContext().getCurrentInstrumentType());
		}
		
		return true;
	}

	@Override
	public void cleanAll() {
		// TODO Auto-generated method stub
		
	}

}
