package net.hermeto.android.main;

import net.hermeto.android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

public class Main extends Activity {
		
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		Window janela = getWindow();
		janela.requestFeature(Window.FEATURE_LEFT_ICON);
		setContentView(R.layout.main);
		janela.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
				R.drawable.icon);

		// / When b_up button clicked do ...
		final ImageButton b_Up = (ImageButton) findViewById(R.id.b_up);
		b_Up.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Toast.makeText(Main.this, R.string.up, Toast.LENGTH_SHORT)
				.show();

			}
		});

		// When b_left button clicked do ...
		final ImageButton b_Left = (ImageButton) findViewById(R.id.b_left);
		b_Left.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Toast.makeText(Main.this, R.string.left, Toast.LENGTH_SHORT)
				.show();

			}
		});

		// When b_right button clicked do ...
		final ImageButton b_Right = (ImageButton) findViewById(R.id.b_right);
		b_Right.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Toast.makeText(Main.this, R.string.right, Toast.LENGTH_SHORT)
				.show();

			}
		});

		// When b_down button clicked do ...
		final ImageButton b_Down = (ImageButton) findViewById(R.id.b_down);
		b_Down.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Toast.makeText(Main.this, R.string.down, Toast.LENGTH_SHORT)
				.show();

			}
		});

		// When b_center button clicked do ...
		final ImageButton b_Center = (ImageButton) findViewById(R.id.b_center);
		b_Center.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Toast.makeText(Main.this, R.string.center, Toast.LENGTH_SHORT)
				.show();

			}
		});

		// When b_connect button clicked do ...
		final ImageButton b_Connect = (ImageButton) findViewById(R.id.b_connect);
		b_Connect.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				AlertDialog.Builder dialogo = new AlertDialog.Builder(Main.this);
				dialogo.setTitle(R.string.d_title);
				dialogo.setIcon(R.drawable.net_fail);
				dialogo.setMessage(R.string.d_text);

				final EditText input = new EditText(Main.this);
				input.setSingleLine();

				dialogo.setView(input);				
				
				dialogo.setPositiveButton(R.string.d_ok,
						new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						
						String value = input.getText().toString();

						Toast.makeText(Main.this, value.toString(),
								Toast.LENGTH_SHORT).show();


					}
				});

				dialogo.setNegativeButton(R.string.d_cancel,
						new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {


					}
				});

				dialogo.show();

			}
		});

	}
}