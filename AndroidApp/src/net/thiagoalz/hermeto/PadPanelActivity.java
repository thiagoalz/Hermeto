package net.thiagoalz.hermeto;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;

public class PadPanelActivity extends Activity {
	
	private static final int COLUMN_NUMBER = 15;
	private static final int ROW_NUMBER = 15;
	
	ImageButton[][] padsMatrix;
	TableLayout tableLayout;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.padpanel);
		
		tableLayout = (TableLayout) findViewById(R.id.padpanelgrid);
		initializePadPanel();
	}
	
	private void initializePadPanel() {
		padsMatrix = new ImageButton[ROW_NUMBER][COLUMN_NUMBER];
		for (int i = 0; i < padsMatrix.length; i++) {
			TableRow tableRow = new TableRow(this);
			for (int j = 0; j < padsMatrix[i].length; j++) {
				padsMatrix[i][j] = new ImageButton(this);
				tableRow.addView(padsMatrix[i][j]);
			}
			tableLayout.addView(tableRow);
		}
	}
}
