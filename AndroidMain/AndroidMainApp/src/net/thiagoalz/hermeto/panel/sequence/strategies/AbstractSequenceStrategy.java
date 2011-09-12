package net.thiagoalz.hermeto.panel.sequence.strategies;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import net.thiagoalz.hermeto.audio.SoundManager;
import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.panel.sequence.Sequencer;

/**
 * This is an abstract implementation of the sequence strategy that 
 * will be used by the sequencer.
 */
public abstract class AbstractSequenceStrategy implements ISequenceStrategy {
	/**
	 * All the marked square for that strategy.
	 */
	private Set<Position> markedSquares;
	
	/**
	 * The sequencer that are invokes this strategy
	 */
	private Sequencer sequencer;
	
	/**
	 * The sound manager that are playing the musics
	 */
	private SoundManager soundManager;

	public AbstractSequenceStrategy(Sequencer sequencer, SoundManager soundManager) {
		this.sequencer = sequencer;
		this.soundManager = soundManager;
		this.markedSquares = new LinkedHashSet<Position>();
	}
	
	public List<Position> getColumnMarkedSquares(int column) {
		List<Position> playingPositions = new ArrayList<Position>();
		for (Position position : getMarkedSquares()) {
			if (position.getX() == column) {
				playingPositions.add(position);
			}
		}
		return playingPositions;
	}

	public List<Position> getRowMarkedSquares(int row) {
		List<Position> playingPositions = new ArrayList<Position>();
		for (Position position : getMarkedSquares()) {
			if (position.getY() == row) {
				playingPositions.add(position);
			}
		}
		return playingPositions;
	}
	
	public Set<Position> getMarkedSquares() {
		return markedSquares;
	}

	public void setMarkedSquares(Set<Position> markedSquares) {
		this.markedSquares = markedSquares;
	}

	public Sequencer getSequencer() {
		return sequencer;
	}

	public void setSequencer(Sequencer sequencer) {
		this.sequencer = sequencer;
	}

	public SoundManager getSoundManager() {
		return soundManager;
	}

	public void setSoundManager(SoundManager soundManager) {
		this.soundManager = soundManager;
	}
}
