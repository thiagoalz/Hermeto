package net.thiagoalz.hermeto.player;

import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.panel.SquarePanelManager;

public class DefaultPlayer implements Player {

	private String name;
	private String id;
	private Position position;
	
	private SquarePanelManager squarePanelManager; 
	
	//public DefaultPlayer(String name, String id) {
		//this(name, id, null);
	//}
	
	public DefaultPlayer(String name, String id, SquarePanelManager squarePanelManager) {
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
	
	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name=name;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultPlayer other = (DefaultPlayer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	

}
