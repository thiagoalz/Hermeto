package net.thiagoalz.hermeto.panel;

import java.util.List;

public class ExecutionEvent {

	private List<Position> positions;
	
	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(List<Position> playingPositions) {
		positions = playingPositions;		
	}	
}
