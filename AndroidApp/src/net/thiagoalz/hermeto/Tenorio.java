package net.thiagoalz.hermeto;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class Tenorio extends View {

	private static final String ACTIVITY_NAME = Tenorio.class
			.getCanonicalName();


	Context context;

	Tenorio(Context context) {
		super(context);
		Log.d(ACTIVITY_NAME, "constructor");
		this.context = context;
		this.setBackgroundResource(R.drawable.wood9);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int fingerX = (int) event.getX();
		int fingerY = (int) event.getY();
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		return false;
	}

	@Override
	protected void onDraw(Canvas canvas) {
	}
}
