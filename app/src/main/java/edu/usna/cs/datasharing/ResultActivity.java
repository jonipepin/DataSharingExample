package edu.usna.cs.datasharing;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends Activity {

	private String color;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);

		Intent intent = getIntent();
		color = intent.getExtras().getString("color");
		
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
				Intent intent = new Intent();

				// using setData() to send a value
				intent.setData(Uri.parse("I really liked " + color));

				// set the result code
				setResult(RESULT_OK, intent);

				// destroy the current activity
				finish();
			}

		});

	}
	
	public boolean isValidColorCode(String code){
		return code.matches("^[A-Fa-f0-9]{6}$");
	}

}
