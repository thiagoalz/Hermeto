package net.thiagoalz.hermeto.player;

import net.thiagoalz.hermeto.panel.SquarePanelManager;

/**
 * Local user playing using the server. 
 *  
 * @author Gabriel Ozeas de Oliveira
 * @version 0.1
 */
public class LocalPlayer extends AbstractPlayer {

	public LocalPlayer(String name, String id) {
		super(name, id);
	}
	
	public LocalPlayer(String name, String id, SquarePanelManager squarePanelManager) {
		super(name, id, squarePanelManager);
	}
}
