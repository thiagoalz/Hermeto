package net.hermeto.android.main;

import net.hermeto.android.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;


public class Main extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) { 	
        super.onCreate(icicle);
        
    	Window janela = getWindow();
        janela.requestFeature(Window.FEATURE_LEFT_ICON);
        janela.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.housetopapp);
        
        setContentView(R.layout.main);

        
        /// When b_up button clicked do ...
        final ImageButton b_Up = (ImageButton) findViewById(R.id.b_up);
        b_Up.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
               
            	Toast.makeText(Main.this, R.string.up, Toast.LENGTH_SHORT).show();
                          	
            }
        });
        
     // When b_left button clicked do ...
        final ImageButton b_Left = (ImageButton) findViewById(R.id.b_left);
        b_Left.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
               
            	Toast.makeText(Main.this, R.string.left, Toast.LENGTH_SHORT).show();
                          	
            }
        });
        
     // When b_right button clicked do ...
        final ImageButton b_Right = (ImageButton) findViewById(R.id.b_right);
        b_Right.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
               
            	Toast.makeText(Main.this, R.string.right, Toast.LENGTH_SHORT).show();
                          	
            }
        });
        
        // When b_down button clicked do ...
        final ImageButton b_Down = (ImageButton) findViewById(R.id.b_down);
        b_Down.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
               
            	Toast.makeText(Main.this, R.string.down, Toast.LENGTH_SHORT).show();
                          	
            }
        });    
        
     // When b_center button clicked do ...
        final ImageButton b_Center = (ImageButton) findViewById(R.id.b_center);
        b_Center.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
               
            	Toast.makeText(Main.this, R.string.center, Toast.LENGTH_SHORT).show();
                          	
            } 
        });
        
        
     // When b_connect button clicked do ...
        final ImageButton b_Connect = (ImageButton) findViewById(R.id.b_connect);
        b_Connect.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
               
            	Toast.makeText(Main.this, R.string.connect, Toast.LENGTH_SHORT).show();
                          	
            }
        });
        
        
        
    }
}