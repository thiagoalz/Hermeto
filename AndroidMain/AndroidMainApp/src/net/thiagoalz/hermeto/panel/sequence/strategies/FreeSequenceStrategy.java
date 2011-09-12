package net.thiagoalz.hermeto.panel.sequence.strategies;

import net.thiagoalz.hermeto.audio.SoundManager;
import net.thiagoalz.hermeto.panel.sequence.Sequencer;

/**
 * This strategy is an empty implementation of the sequence strategies. 
 * It is related with the possibility of the player to select and 
 * play a square without mark it for execution.
 */
public class FreeSequenceStrategy extends AbstractSequenceStrategy  {
	private static final String TAG = GroupSequenceStrategy.class.getCanonicalName();
	
	public FreeSequenceStrategy(Sequencer sequencer, SoundManager soundManager) {
		super(sequencer, soundManager);
	}
		
	@Override
	public void start() {
		// Do nothing
	}

	@Override
	public void stop() {
		// Do nothing
	}

	@Override
	public void pause() {
		// Do nothing
	}

	@Override
	public void cleanUp() {
		// Do nothing
	}

	@Override
	public void setPositionBehavior(PositionBehavior positionBehavior) {
		// Do nothing
	}
}
