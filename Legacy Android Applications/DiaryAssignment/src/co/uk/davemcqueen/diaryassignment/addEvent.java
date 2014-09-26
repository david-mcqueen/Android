/**
 * 
 */
package co.uk.davemcqueen.diaryassignment;

import java.sql.Date;
import java.text.DateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Dave
 *
 */
public class addEvent extends Activity {

	
	private Button btnSave;
	private TextView eventTitle;
	private TextView eventDescription;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_event);
		final DBHandler db = new DBHandler(this);
		
		
		
		eventTitle = (TextView) findViewById(R.id.txt_addTitle);
		eventDescription = (TextView) findViewById(R.id.txt_addDescription);
		
		
		btnSave = (Button) findViewById(R.id.btn_save);
		
		btnSave.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Event newEvent = new Event();
				newEvent.setName(eventTitle.toString());
				newEvent.setDescription(eventDescription.toString());
				
				db.createEvent(newEvent);
				
			}
		});
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
