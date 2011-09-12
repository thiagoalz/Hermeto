package net.thiagoalz.hermeto.panel.sequence.strategies;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.thiagoalz.hermeto.audio.InstrumentType;
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
	private Map<Position, InstrumentType> markedSquares;
		
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
		this.markedSquares = new LinkedHashMap<Position, InstrumentType>();
	}
	
	public Map<Position, InstrumentType> getColumnMarkedSquares(int column) {
		Map<Position, InstrumentType> playingPositions = new LinkedHashMap<Position, InstrumentType>();
		for (Position position : getMarkedSquares().keySet()) {
			if (position.getX() == column) {
				playingPositions.put(position, getMarkedSquares().get(position));
			}
		}
		return playingPositions;
	}

	public Map<Position, InstrumentType> getRowMarkedSquares(int row) {
		Map<Position, InstrumentType> playingPositions = new LinkedHashMap<Position, InstrumentType>();
		for (Position position : getMarkedSquares().keySet()) {
			if (position.getY() == row) {
				playingPositions.put(position, getMarkedSquares().get(position));
			}
		}
		return playingPositions;
	}
	
	public Map<Position, InstrumentType> getMarkedSquares() {
		return markedSquares;
	}

	public void setMarkedSquares(Map<Position, InstrumentType> markedSquares) {
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
