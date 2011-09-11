package net.thiagoalz.hermeto.panel.sequence.strategies;

public interface Positioner {
	public int nextPosition();
	public int getCurrentPosition();
	public int getMaxPosition();
	public void resetPosition();
}
