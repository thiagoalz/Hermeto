package net.thiagoalz.hermeto;

import net.thiagoalz.hermeto.panel.GameManager;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;

public class PadPanelActivity extends Activity {
	
	private static final int COLUMNS = 15;
	private static final int ROWS = 15;
		
	private GameManager gameManager;
	
	private ImageButton[][] padsMatrix;
	private TableLayout tableLayout;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		gameManager = new GameManager(COLUMNS, ROWS);
		addCurrentPlayer();
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
				tableRow.addView(padsMatrix[i][j]);
			}
			tableLayout.addView(tableRow);
		}
	}
	
	private void addCurrentPlayer() {
		 gameManager.connectPlayer();
	}
}
