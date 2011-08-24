package net.thiagoalz.hermeto;

import java.util.HashMap;
import java.util.Map;

import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.panel.listeners.ConnectEvent;
import net.thiagoalz.hermeto.panel.listeners.MoveEvent;
import net.thiagoalz.hermeto.panel.listeners.PlayerListener;
import net.thiagoalz.hermeto.player.Player;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class NamesViewThread extends Thread implements PlayerListener {
	private NamesView namesView;
	private SurfaceHolder surfaceHolder;
	private boolean run = false;
	
	private Map<Player, Position> playersPosition = new HashMap<Player, Position>();
	
	public NamesViewThread(NamesView namesView) {
		this.namesView = namesView;
		this.surfaceHolder = namesView.getHolder();
	}
	
	public void setRunning(boolean run) {
		this.run = run;
	}
	
	@Override
	public void run() {
		Canvas canvas = null;
		while(run) {
			canvas = surfaceHolder.lockCanvas();
			if (canvas != null) {
				namesView.doDraw(canvas);
				surfaceHolder.unlockCanvasAndPost(canvas);
			}
		}
	}

	@Override
	public void onPlayerMove(MoveEvent event) {
		playersPosition.put(event.getPlayer(), event.getNewPosition());
		
	}

	@Override
	public void onPlayerConnect(ConnectEvent event) {
		playersPosition.put(event.getPlayer(), event.getPosition());
	}
}
