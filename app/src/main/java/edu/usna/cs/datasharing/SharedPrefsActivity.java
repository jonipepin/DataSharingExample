package edu.usna.cs.datasharing;

import java.util.Random;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SharedPrefsActivity extends Activity {

	private SharedPreferences prefs;
	private String color;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		color = prefs.getString("color", "ff69b4");
		
		if(!isValidColorCode(color)){
			Toast.makeText(getBaseContext(), "Invalid color code", Toast.LENGTH_SHORT).show();
			color = "ff69b4";
		}

		RelativeLayout main = (RelativeLayout) findViewById(R.id.mainLayout);
		TextView tv = (TextView) findViewById(R.id.textView);

		main.setBackgroundColor(Color.parseColor("#" + color));
		tv.setText(color);

		Button closeButton = (Button) findViewById(R.id.button4);
		closeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				// save the color code to the apps shared preferences
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
				Editor editor = prefs.edit();
				editor.putString("color", getRandomColorCode());
				editor.commit();

				// destroy the current activity
				finish();
			}

		});

	}
	
	private boolean isValidColorCode(String code){
		return code.matches("^[A-Fa-f0-9]{6}$");
	}
	
	private String getRandomColorCode(){
		String code = "";
		String[] possibleValues = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
		for(int i = 0; i < 6; i++){
			code += possibleValues[new Random().nextInt(possibleValues.length)];
		}
		return code;
	}
	

}
