package net.thiagoalz.hermeto;

import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.panel.listeners.ConnectEvent;
import net.thiagoalz.hermeto.panel.listeners.MoveEvent;
import net.thiagoalz.hermeto.panel.listeners.IPlayerListener;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PlayerNameView extends TextView implements IPlayerListener {
	private Position newPosition;
	
	public PlayerNameView(Context context) {
		super(context);
		setBackgroundDrawable(getResources().getDrawable(R.drawable.playerlabel));
		this.setTextSize(TypedValue.COMPLEX_UNIT_PX, 9);
		setTextColor(Color.BLACK);
		setPadding(6, 2, 2, 6);
	}
	
	public void setLocation(final Position position) {
		post(new Runnable() {
			public void run() {
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
				params.leftMargin = position.getX();
				params.topMargin = position.getY();
				setLayoutParams(params);
			}
		});
	}

	@Override
	public void onPlayerMove(MoveEvent event) {
		newPosition = event.getNewPosition();
		post(new Runnable() {
			public void run() {
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
				params.leftMargin = newPosition.getX();
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
