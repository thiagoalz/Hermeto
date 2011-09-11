package net.thiagoalz.hermeto.panel.sequence.strategies;

public class RepeatPositioner implements Positioner {

	private int maxPosition;
	private int currentPosition;
	
	public RepeatPositioner(int maxPosition) {
		this.maxPosition = maxPosition;
	}
	
	@Override
	public int nextPosition() {
		currentPosition = (currentPosition + 1) % (maxPosition + 1);
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
	
	@Override
	public void resetPosition() {
		this.currentPosition = 0;
	}

}
