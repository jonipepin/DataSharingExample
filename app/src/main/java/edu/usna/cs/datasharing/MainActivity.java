package edu.usna.cs.datasharing;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	RelativeLayout backgroundArea;
	TextView mainDisplay;
	EditText textEntryArea;
	Button button1, button2, button3;

	int request_Code;
	
	SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		backgroundArea = (RelativeLayout) findViewById(R.id.mainPageBackground);
		mainDisplay = (TextView) findViewById(R.id.displayTextView);
		textEntryArea = (EditText) findViewById(R.id.editTextView);
		
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);

		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View view) {
		String colorString = textEntryArea.getText().toString();
		if (view == button1) {
			Intent intent = new Intent(this, NoResultActivity.class);

			// adding extended data to intent using key/value pair
			intent.putExtra("color", colorString);

			// not expecting a result
			startActivity(intent);

		} else if (view == button2) {
			Intent intent = new Intent(this, ResultActivity.class);

			// using a Bundle Object to add data to intent
			Bundle extras = new Bundle();
			extras.putString("color", colorString);
			intent.putExtras(extras);

			// launch activity expecting a result;
			// code used to explicitly identify this activity's response
			request_Code = 1775;
			startActivityForResult(intent, request_Code);

		} else if (view == button3) {
			// save the color code to the apps shared preferences
			Editor editor = prefs.edit();
			editor.putString("color", colorString);
			editor.commit();
			
			// start the activity
			Intent intent = new Intent(this, SharedPrefsActivity.class);
			startActivity(intent);
		}

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		// check to see which activity is returning the result
		if (requestCode == request_Code) {
			// check result code
			if (resultCode == RESULT_OK) {
				// get data set with setData
				String response = data.getData().toString();
				mainDisplay.setText(response);
			}
		}
		
	}
	
	@Override
	public void onResume(){
		super.onResume();
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String color = prefs.getString("color", "ffffff");
		backgroundArea.setBackgroundColor(Color.parseColor("#" + color));
		mainDisplay.setText("Random color chosen = " + color);
	}

}
