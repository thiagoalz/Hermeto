package net.thiagoalz.hermeto.player;

import net.thiagoalz.hermeto.Position;
import net.thiagoalz.hermeto.panel.SquarePanelManager;

public class AbstractPlayer implements Player {

	private String name;
	private String id;
	private Position position;
	
	private SquarePanelManager squarePanelManager; 
	
	public AbstractPlayer(String name, String id) {
		this(name, id, null);
	}
	
	public AbstractPlayer(String name, String id, SquarePanelManager squarePanelManager) {
		this.name = name;
		this.id = id;
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

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getId() {
		return id;
	}

}
