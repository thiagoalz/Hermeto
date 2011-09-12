package net.thiagoalz.hermeto.view.strategies;

import java.util.LinkedHashMap;
import java.util.Map;

import net.thiagoalz.hermeto.audio.InstrumentType;
import net.thiagoalz.hermeto.panel.GameManager;
import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.panel.sequence.strategies.GroupSequenceStrategy;
import net.thiagoalz.hermeto.panel.sequence.strategies.SequenceStrategy.SequenceStrategyType;
import net.thiagoalz.hermeto.player.Player;
import android.util.Log;

public class GroupSelectionStrategy extends AbstractSelectionStrategy {
	private static final String TAG = GroupSelectionStrategy.class.getCanonicalName();

	public GroupSelectionStrategy(GameManager gameManager) {
		super(gameManager);
	}
	
	@Override
	public boolean mark(Player player) {
		GroupSequenceStrategy sequenceStrategy = (GroupSequenceStrategy) getSequencer().getSequenceStrategy(SequenceStrategyType.GROUP);
		
		// Retrieving all the marked squares
		Map<Position, InstrumentType> markedSquares = sequenceStrategy.getMarkedSquares();
		
		Position selectedPosition = new Position(player.getPosition().getX(),
				player.getPosition().getY());
	
		if (markedSquares.containsKey(selectedPosition)) {
			// If the position is already selected, so undo it.
			Log.d(TAG, "Dismarking square [" + player.getPosition().getX() + ", "
					+ player.getPosition().getY() + "]");
			markedSquares.remove(selectedPosition);
			notifyDeselection(player, selectedPosition);
			return false;
		} else {
			// If the position is not marked yet, mark it.
			Log.d(TAG, "Marking square [" + player.getPosition().getX() + ", "
					+ player.getPosition().getY() + "]");
			markedSquares.put(player.getPosition(), getGameManager().getGameContext().getCurrentInstrumentType());
			notifySelection(player, selectedPosition);
			return true;
		}
	}	
	
	@Override
	public void cleanAll() {
		// Retrieving the game context.
		GroupSequenceStrategy sequenceStrategy = (GroupSequenceStrategy) getSequencer().getSequenceStrategy(SequenceStrategyType.GROUP);
		// Deselect all the markedSquares and stop playing.
		for (Position position : sequenceStrategy.getMarkedSquares().keySet()) {
			notifyDeselection(null, position);
		}
		sequenceStrategy.setMarkedSquares(new LinkedHashMap<Position, InstrumentType>());
		
	}
}
