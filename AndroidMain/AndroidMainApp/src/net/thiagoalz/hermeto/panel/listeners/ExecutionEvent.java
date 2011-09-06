package net.thiagoalz.hermeto.panel.listeners;

import java.util.List;

import net.thiagoalz.hermeto.panel.Position;

public class ExecutionEvent {

	private List<Position> positions;
	private Position position;
	
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(List<Position> playingPositions) {
		positions = playingPositions;		
	}	
}
