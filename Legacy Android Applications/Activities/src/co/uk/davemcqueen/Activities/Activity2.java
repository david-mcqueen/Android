package co.uk.davemcqueen.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Activity2 extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity2);
	
	String defaultName="";
	Bundle extras = getIntent().getExtras();
	if (extras !=null)
	{
		defaultName = extras.getString("Name");
	}
	
	//---Get the EditText view---
	EditText txt_username = (EditText) findViewById(R.id.txt_username);
	txt_username.setHint(defaultName);
	
	
	//---Get the OK button---
	Button btn = (Button) findViewById(R.id.btn_OK);
	
	//---Event handler for the OK button---
	btn.setOnClickListener(new View.OnClickListener()
	{
		public void onClick(View view)
		{
			Intent data = new Intent();
			
			//---Get the EditText view---
			EditText txt_username = 
					(EditText) findViewById(R.id.txt_username);
			
			//--set the data to pass back---
			data.setData(Uri.parse(txt_username.getText().toString()));
			setResult(RESULT_OK, data);
			
			//---Closes the activity---
			finish();
		}
	});
	}
}
