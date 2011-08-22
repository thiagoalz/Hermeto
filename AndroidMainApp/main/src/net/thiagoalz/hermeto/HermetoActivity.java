package net.thiagoalz.hermeto;

import net.thiagoalz.hermeto.control.ADKGameplayControl;
import net.thiagoalz.hermeto.panel.GameManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.DemoKit.DemoKitActivity;

public class HermetoActivity extends DemoKitActivity {
	
	private static final int ADK_PLAYERS = 4;


	private GameManager gameManager;
	private ADKGameplayControl ADKControl;
	
	public HermetoActivity(){
		super();
	}
	@Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    gameManager = GameManager.getInstance();
	    ADKControl = new ADKGameplayControl(gameManager, ADK_PLAYERS);
	    
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
		configureScreen();
		setContentView(R.layout.presentation);
		Button button = (Button) findViewById(R.id.presentationplay);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(), PadPanelActivity.class);
				startActivity(intent);
				HermetoActivity.this.finish();
			}
		});
		
		
		if (mAccessory != null) {
			//Mostra tela conectado conectado
		} else {
			//Mostra trela desconectado
		}
    }
    
    private void configureScreen() {
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
    
    protected void handleJoyMessage(JoyMsg j) {
//		if (mInputController != null) {
//			mInputController.joystickMoved(j.getX(), j.getY());
//		}
	}

	protected void handleLightMessage(LightMsg l) {
//		if (mInputController != null) {
//			mInputController.setLightValue(l.getLight());
//		}
	}

	protected void handleTemperatureMessage(TemperatureMsg t) {
//		if (mInputController != null) {
//			mInputController.setTemperature(t.getTemperature());
//		}
	}

	protected void handleSwitchMessage(SwitchMsg o) {
//		if (mInputController != null) {
//			byte sw = o.getSw();
//			if (sw >= 0 && sw < 4) {
//				mInputController.switchStateChanged(sw, o.getState() != 0);
//			} else if (sw == 4) {
//				mInputController
//						.joystickButtonSwitchStateChanged(o.getState() != 0);
//			}
//		}
		
		//Test LECHUGA
//		Log.d("Lechuga","Botao changed");
//        
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		builder.setMessage("Botao");
//		AlertDialog alert = builder.create();
//		
//		alert.show();
	}
	
	protected void handleSimpleJoyMessage(SwitchMsg k) {
		ADKControl.processMessage(k.getSw()+"",k.getState()+"");
	}
}
