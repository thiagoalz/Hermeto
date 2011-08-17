package net.thiagoalz.hermeto.hardware;

import net.thiagoalz.hermeto.Player;
import net.thiagoalz.hermeto.Position;
import net.thiagoalz.hermeto.panel.SquarePanelManager;

/**
 * User that is playing by the hardware.
 */
public class HardwarePlayer implements Player {
	private String name;
	private String id;
	private Position position;
	
	private SquarePanelManager squarePanelManager; 
	
	public HardwarePlayer(String name) {
		this(name, null);
	}
	
	public HardwarePlayer(String id, SquarePanelManager squarePanelManager) {
		this.squarePanelManager = squarePanelManager;
	}
	
	@Override
	public boolean move(Direction direction) {
		if (squarePanelManager == null)
			throw new IllegalStateException("The Panel Manager wasn't set.");
		return squarePanelManager.move(this, direction);		
	}

	@Override
	
	public boolean mark() {
		if (squarePanelManager == null)
			throw new IllegalStateException("The Panel Manager wasn't set.");
		return squarePanelManager.mark(this);
		
	}

	@Override
	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
