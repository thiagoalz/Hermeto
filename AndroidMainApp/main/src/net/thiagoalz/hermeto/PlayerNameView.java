package net.thiagoalz.hermeto;

import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.panel.listeners.ConnectEvent;
import net.thiagoalz.hermeto.panel.listeners.MoveEvent;
import net.thiagoalz.hermeto.panel.listeners.PlayerListener;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PlayerNameView extends TextView implements PlayerListener {
	private static final String tag = PlayerNameView.class.getCanonicalName();
	private Position newPosition;
	
	public PlayerNameView(Context context) {
		super(context);
		setBackgroundColor(Color.BLACK);
		setTextColor(Color.RED);
		setPadding(6, 2, 2, 6);
	}
	
	public void setLocation(Position position) {
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.leftMargin = position.getX();
		params.topMargin = position.getY();
		setLayoutParams(params);
	}

	@Override
	public void onPlayerMove(MoveEvent event) {
		Log.d(tag, "Moving to [" + event.getNewPosition().getX() + ", " + event.getNewPosition().getY() + "]");
		newPosition = event.getNewPosition();
		
		int xDelta = -(event.getOldPosition().getX() - event.getNewPosition().getX());
		int yDelta = -(event.getOldPosition().getY() - event.getNewPosition().getY());
		
		final Animation animation = getAnimation(xDelta, yDelta);
		
		post(new Runnable() {
			public void run() {
				startAnimation(animation);
			}
		});
	}
	
	private Animation getAnimation(int xDelta, int yDelta) {
		Animation animation = new TranslateAnimation(
				Animation.ABSOLUTE, 0,   
	            Animation.ABSOLUTE, xDelta,   
	            Animation.ABSOLUTE, 0,   
	            Animation.ABSOLUTE, yDelta);
		
		animation.setDuration(500);
		animation.setFillAfter(true);
		animation.setInterpolator(new AccelerateDecelerateInterpolator());
		return animation;
	}

	@Override
	public void onPlayerConnect(ConnectEvent event) {
		
	}

	@Override
	public void onAnimationEnd() {
		super.onAnimationEnd();
		setVisibility(INVISIBLE);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.leftMargin = newPosition.getX();
		params.topMargin = newPosition.getY();
		setLayoutParams(params);
		
	}
	
	@Override
	public void onAnimationStart() {
		super.onAnimationStart();
	}
	
	
}
