package net.thiagoalz.hermeto.view.strategies;

import java.util.LinkedHashMap;
import java.util.Map;

import net.thiagoalz.hermeto.audio.InstrumentType;
import net.thiagoalz.hermeto.panel.GameManager;
import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.panel.sequence.strategies.LineSequenceStrategy;
import net.thiagoalz.hermeto.panel.sequence.strategies.ISequenceStrategy.SequenceStrategyType;
import net.thiagoalz.hermeto.player.IPlayer;
import android.util.Log;

public class LineSelectionStrategy extends AbstractSelectionStrategy {
	
	private static final String TAG = LineSelectionStrategy.class.getCanonicalName();
	
	public LineSelectionStrategy(GameManager gameManager) {
		super(gameManager);
	}
	
	@Override
	public boolean mark(IPlayer player) {
		// Retrieving the game context.
		LineSequenceStrategy sequenceStrategy = (LineSequenceStrategy) getSequencer().getSequenceStrategy(SequenceStrategyType.LINE);
		
		// Retrieving all the marked squares
		Map<Position, InstrumentType> markedSquares = sequenceStrategy.getMarkedSquares();
		
		Position selectedPosition = new Position(player.getPosition().getX(),
				player.getPosition().getY());
	
		if (markedSquares.containsKey(selectedPosition)) {
			synchronized(this) {
				// If the position is already selected, undo it.
				Log.d(TAG, "Dismarking square [" + player.getPosition().getX() + ", "
						+ player.getPosition().getY() + "]");
				markedSquares.remove(selectedPosition);
				
				deselectLine(player,selectedPosition);
				
				return false;
			}
		} else {
			synchronized(this) {
				Position selectedSquare = null;
				
				// Verify if any position in the same line is already selected, if it is, undo it.
				for (Position markedSquare: markedSquares.keySet()) {
					if (markedSquare.getX() == player.getPosition().getX()) {
						selectedSquare = markedSquare;
					}
				}
				
				if (selectedSquare != null) {
					Log.d(TAG, "Dismarking square [" + selectedSquare.getX() + ", "
							+ selectedSquare.getY() + "]");
					markedSquares.remove(selectedSquare);
					deselectLine(player, selectedSquare);
				}
				
				// If the position is not marked yet, mark it.
				Log.d(TAG, "Marking square [" + player.getPosition().getX() + ", "
						+ player.getPosition().getY() + "]");
				markedSquares.put(player.getPosition(), getGameManager().getGameContext().getCurrentInstrumentType());
				selectLine(player, selectedPosition);
				return true;
			}
		}
	}
	
	private void selectLine(IPlayer player, Position selectedPosition) {
		for(int y=0; y<=selectedPosition.getY();y++){
			Position pos=new Position(selectedPosition.getX(),y);
			notifySelection(player, pos);
		}
	}

	private void deselectLine(IPlayer player, Position selectedPosition) {
		for(int y=0; y<=selectedPosition.getY();y++){
			Position pos=new Position(selectedPosition.getX(),y);
			notifyDeselection(player, pos);
		}
	}

	@Override
	public void cleanAll() {
		// Retrieving the game context.
		LineSequenceStrategy sequenceStrategy = (LineSequenceStrategy) getSequencer().getSequenceStrategy(SequenceStrategyType.LINE);
		// Deselect all the markedSquares and stop playing.
		for (Position position : sequenceStrategy.getMarkedSquares().keySet()) {
			deselectLine(null, position);
		}
		sequenceStrategy.setMarkedSquares(new LinkedHashMap<Position, InstrumentType>());
	}
	
	
}
