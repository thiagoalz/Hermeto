package net.thiagoalz.hermeto;

import java.util.Map;

import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.player.Player;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class NamesView extends SurfaceView implements SurfaceHolder.Callback {

	private Bitmap mBitmap;
	private Paint paint;
	
	private Map<Player, Position> playersPosition;
	
	
	public NamesView(Context context) {
		this(null, context);
	}
	
	public NamesView(Map<Player, Position> playersPosition, Context context) {
		super(context);
		mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.buttonplaying);
		getHolder().addCallback(this);
		this.playersPosition = playersPosition;
		paint = new Paint();
		paint.setColor(Color.WHITE);
	}
	
	public void doDraw(Canvas canvas) {
		for (Player position : playersPosition.keySet()) {
			canvas.drawText("HelloWorld", 6, 6, paint);
		}
	}

	public void addName(Player player, Position position) {
		playersPosition.put(player, position);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
