package net.thiagoalz.hermeto.player;

import net.thiagoalz.hermeto.panel.SquarePanelManager;

/**
 * User that is playing by the hardware.
 * 
 * @author Gabriel Ozeas de Oliveira
 * @version 0.1
 */
public class HardwarePlayer extends AbstractPlayer {
		
	public HardwarePlayer(String name) {
		super(name, null);
	}
	
	public HardwarePlayer(String name, String id, SquarePanelManager squarePanelManager) {
		super(name, id, squarePanelManager);
	}
}
