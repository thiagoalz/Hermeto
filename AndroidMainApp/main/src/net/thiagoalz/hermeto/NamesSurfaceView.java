package net.thiagoalz.hermeto;

import java.util.List;
import java.util.Map;

import net.thiagoalz.hermeto.panel.Position;
import net.thiagoalz.hermeto.player.Player;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class NamesSurfaceView extends View {

	private Bitmap mBitmap;
	private Paint paint;
	
	private Map<Player, Position> playersPosition;
	
	public NamesSurfaceView(Map<Player, Position> playersPosition, Context context) {
		super(context);
		mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.buttonplaying);
		this.playersPosition = playersPosition;
		paint = new Paint();
		paint.setColor(Color.WHITE);
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		for (Player position : playersPosition.keySet()) {
			canvas.drawText("HelloWorld", 6, 6, paint);
		}
	}
	
}
