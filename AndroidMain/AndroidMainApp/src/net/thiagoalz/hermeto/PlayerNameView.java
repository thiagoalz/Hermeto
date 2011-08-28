package net.thiagoalz.hermeto;

import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.panel.listeners.ConnectEvent;
import net.thiagoalz.hermeto.panel.listeners.MoveEvent;
import net.thiagoalz.hermeto.panel.listeners.PlayerListener;
import android.content.Context;
import android.graphics.Color;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PlayerNameView extends TextView implements PlayerListener {
	private Position newPosition;
	
	public PlayerNameView(Context context) {
		super(context);
		setBackgroundColor(Color.BLACK);
		setTextColor(Color.RED);
		setPadding(6, 2, 2, 6);
	}
	
	public void setLocation(Position position) {
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.leftMargin = position.getX() - 10;
		params.topMargin = position.getY();
		setLayoutParams(params);
	}

	@Override
	public void onPlayerMove(MoveEvent event) {
		newPosition = event.getNewPosition();
		post(new Runnable() {
			public void run() {
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
				params.leftMargin = newPosition.getX() - 10;
				params.topMargin = newPosition.getY();
				setLayoutParams(params);
			}
		});
	}

	@Override
	public void onPlayerConnect(ConnectEvent event) {
		
		
	}	
	
	@Override
	public void onPlayerDisconnect(ConnectEvent event) {
		
	}
}
