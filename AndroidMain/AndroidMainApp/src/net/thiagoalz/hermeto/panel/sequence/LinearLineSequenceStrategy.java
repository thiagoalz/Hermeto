package net.thiagoalz.hermeto.panel.sequence;

import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.panel.listeners.SelectionEvent;
import net.thiagoalz.hermeto.panel.listeners.SelectionListener;


/**
 * The linear line sequence strategy will make the the lines play until the 
 * last square and back to the first square, like a circular list.  
 */
public class LinearLineSequenceStrategy extends LineSequenceStrategy implements SelectionListener {
	private static final String TAG = LinearLineSequenceStrategy.class.getCanonicalName();
	
	
	public LinearLineSequenceStrategy(Sequencer sequencer) {
		super(sequencer);
	}
	
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSelected(SelectionEvent event) {
		Position position = event.getPosition();
		LineSequence lineSequence = null;
		if (getLineSequences().containsKey(position.getX())) {
			lineSequence = getLineSequences().get(position.getX());
		} else {
			lineSequence = new LineSequence(this);
			getLineSequences().put(position.getX(), lineSequence);
		}
		lineSequence.schedule(position.getY(), getSequencer().getTimeSequence());
	}

	@Override
	public void onDeselected(SelectionEvent event) {
		// TODO Auto-generated method stub
		
	}
}
