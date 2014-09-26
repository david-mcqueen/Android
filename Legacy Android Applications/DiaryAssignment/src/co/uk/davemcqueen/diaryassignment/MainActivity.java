package co.uk.davemcqueen.diaryassignment;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		
		DBHandler db = new DBHandler(this);
		Intent intent = getIntent();
		Event holiday = new Event("Newyork", "Going on holiday to New York");
		db.createEvent(holiday);
		List<Event> allEvents;
		allEvents = db.getAll();
		
		//setListAdapter(new ArrayAdapter<String>(this,
			//	android.R.layout.simple_list_item_1));
		
		Event test = db.getEvent(1);
		displayToast(test.eventName);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void displayToast(String message)
	{
		Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
	}

}
