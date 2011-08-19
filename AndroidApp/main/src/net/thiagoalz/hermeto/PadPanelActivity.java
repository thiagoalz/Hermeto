package net.thiagoalz.hermeto;

import net.thiagoalz.hermeto.audio.SoundManager;
import net.thiagoalz.hermeto.panel.GameManager;
import net.thiagoalz.hermeto.panel.listeners.ConnectEvent;
import net.thiagoalz.hermeto.panel.listeners.MoveEvent;
import net.thiagoalz.hermeto.panel.listeners.PlayerListener;
import net.thiagoalz.hermeto.panel.listeners.SelectionEvent;
import net.thiagoalz.hermeto.panel.listeners.SelectionListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;

public class PadPanelActivity extends Activity implements SelectionListener, PlayerListener {
	
	private static final int COLUMNS = 15;
	private static final int ROWS = 15;
		
	private GameManager gameManager;
	private SoundManager soundManager;
	
	private ImageButton[][] padsMatrix;
	private TableLayout tableLayout;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		gameManager = new GameManager(COLUMNS, ROWS);
		constructView();
		
	}
	
	private void constructView() {
		setContentView(R.layout.padpanel);
		tableLayout = (TableLayout) findViewById(R.id.padpanelgrid);
		initializeSquarePanel();
	}
	
	private void initializeSquarePanel() {
		padsMatrix = new ImageButton[COLUMNS][ROWS];
		for (int i = 0; i < padsMatrix.length; i++) {
			TableRow tableRow = new TableRow(this);
			for (int j = 0; j < padsMatrix[i].length; j++) {
				padsMatrix[i][j] = new ImageButton(this);
				padsMatrix[i][j].setBackgroundDrawable(this.getResources().getDrawable(R.drawable.disco));
				padsMatrix[i][j].setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						v.setBackgroundDrawable(PadPanelActivity.this.getResources().getDrawable(R.drawable.discoazul));
					}
				});
				tableRow.addView(padsMatrix[i][j]);
			}
			tableLayout.addView(tableRow);
		}
	}

	@Override
	public void onSelected(SelectionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeselected(SelectionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerMove(MoveEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerConnect(ConnectEvent event) {
		// TODO Auto-generated method stub
		
	}
}
