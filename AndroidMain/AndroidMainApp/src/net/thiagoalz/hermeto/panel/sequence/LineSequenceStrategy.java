package net.thiagoalz.hermeto.panel.sequence;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * In the line sequence strategy, the lines in the panel are independent and can be 
 * started when the user select the first square in that line.
 */
public abstract class LineSequenceStrategy implements SequenceStrategy {
	private static final String TAG = LineSequenceStrategy.class.getCanonicalName();
	
	private List<Timer> lineTimers;
	
	public LineSequenceStrategy() {
		lineTimers = new ArrayList<Timer>();
	}

	public List<Timer> getLineTimers() {
		return lineTimers;
	}

	public void setLineTimers(List<Timer> lineTimers) {
		this.lineTimers = lineTimers;
	}
}
