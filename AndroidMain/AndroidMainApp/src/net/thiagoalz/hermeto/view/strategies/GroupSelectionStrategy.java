package net.thiagoalz.hermeto.view.strategies;

import java.util.LinkedHashSet;
import java.util.Set;

import net.thiagoalz.hermeto.panel.GameManager;
import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.panel.sequence.strategies.GroupSequenceStrategy;
import net.thiagoalz.hermeto.panel.sequence.strategies.ISequenceStrategy.SequenceStrategyType;
import net.thiagoalz.hermeto.player.IPlayer;
import android.util.Log;

public class GroupSelectionStrategy extends AbstractSelectionStrategy {
	private static final String TAG = GroupSelectionStrategy.class.getCanonicalName();

	public GroupSelectionStrategy(GameManager gameManager) {
		super(gameManager);
	}
	
	@Override
	public boolean mark(IPlayer player) {
		GroupSequenceStrategy sequenceStrategy = (GroupSequenceStrategy) getSequencer().getSequenceStrategy(SequenceStrategyType.GROUP);
		
		// Retrieving all the marked squares
		Set<Position> markedSquares = sequenceStrategy.getMarkedSquares();
		
		Position selectedPosition = new Position(player.getPosition().getX(),
				player.getPosition().getY());
	
		if (markedSquares.contains(selectedPosition)) {
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
			markedSquares.add(player.getPosition());
			notifySelection(player, selectedPosition);
			return true;
		}
	}	
	
	@Override
	public void cleanAll() {
		// Retrieving the game context.
		GroupSequenceStrategy sequenceStrategy = (GroupSequenceStrategy) getSequencer().getSequenceStrategy(SequenceStrategyType.GROUP);
		// Deselect all the markedSquares and stop playing.
		for (Position position : sequenceStrategy.getMarkedSquares()) {
			notifyDeselection(null, position);
		}
		sequenceStrategy.setMarkedSquares(new LinkedHashSet<Position>());
		
	}
}
