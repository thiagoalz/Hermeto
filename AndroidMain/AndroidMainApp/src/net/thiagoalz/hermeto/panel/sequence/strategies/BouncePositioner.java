package net.thiagoalz.hermeto.panel.sequence.strategies;

public class BouncePositioner implements Positioner {
	private int maxPosition;
	private int currentPosition;
	private boolean reversedDirection; 
	
	public BouncePositioner(int maxPosition) {
		this.maxPosition = maxPosition;
	}
	
	@Override
	public int nextPosition() {
		if (!reversedDirection)
			currentPosition++;
		else
			currentPosition--;
		
		if ( currentPosition == 0 ) {
			reversedDirection = false;
		} else if ( currentPosition == maxPosition ) {
			reversedDirection = true;
		}
		return currentPosition;
	}

	public int getMaxPosition() {
		return maxPosition;
	}

	public void setMaxPosition(int maxPosition) {
		this.maxPosition = maxPosition;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}

	public boolean isReversedDirection() {
		return reversedDirection;
	}

	public void setReversedDirection(boolean reversedDirection) {
		this.reversedDirection = reversedDirection;
	}
	
	public void resetPosition() {
		currentPosition = 0;
	}
}
