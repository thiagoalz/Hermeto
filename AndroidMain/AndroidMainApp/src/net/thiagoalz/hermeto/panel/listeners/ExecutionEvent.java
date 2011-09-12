package net.thiagoalz.hermeto.panel.listeners;

import java.util.Map;

import net.thiagoalz.hermeto.audio.InstrumentType;
import net.thiagoalz.hermeto.panel.Position;

public class ExecutionEvent {

	private Map<Position, InstrumentType> positions;
	private Position position;
	
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Map<Position, InstrumentType> getPositions() {
		return positions;
	}

	public void setPositions(Map<Position, InstrumentType> playingPositions) {
		positions = playingPositions;		
	}	
}
