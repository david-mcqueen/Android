/*
 * David McQueen
 * 10153465@stu.mmu.ac.uk
 */
package co.uk.davemcqueen.Assignment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class FacebookLogin extends FragmentActivity {
	private FacebookFragment mainFragment;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		Bundle extras = getIntent().getExtras();
		String eventName = " ";
		String eventDescription = " ";
		String eventLocation = " ";
		String eventDate = " ";
		String eventTime = " ";
		
		/*
		 * Used to launch the facebook integration.
		 * If the user wants to share an event, then event ID is passed in,
		 * The information is retirved and then passed onto the facebook fragment.
		 */
		if (extras != null)
		{//retrieve the information for that event.
			String[] projection = {Event.EventItem.COLUMN_NAME_EVENT, Event.EventItem.COLUMN_NAME_DESCRIPTION,
					Event.EventItem.COLUMN_NAME_DATE, Event.EventItem.COLUMN_NAME_TIME, Event.EventItem.COLUMN_NAME_LOCATION,
					Event.EventItem.COLUMN_NAME_IMAGE};
			
			String selection = ("_id=?");
			String[] selectionArgs = new String[] {String.valueOf(extras.getInt("id"))};
			Cursor c = getBaseContext().getContentResolver().query(dbContentProvider.CONTENT_URI,
					projection, selection, selectionArgs, Event.EventItem.DEFAULT_SORT_ORDER);
			while (c.moveToNext())
			{
				eventName = c.getString(0);
				eventDescription = c.getString(1);
				eventDate = c.getString(2);
				eventTime = c.getString(3);
				eventLocation = c.getString(4);
			}
		}
		
		//if there isn't already an instance of the activity
		if (savedInstanceState == null)
		{
			mainFragment = new FacebookFragment();
			Bundle values = new Bundle();
			values.putString(Event.EventItem.COLUMN_NAME_EVENT, eventName);//pass in the name and the description
			values.putString(Event.EventItem.COLUMN_NAME_DESCRIPTION, eventDescription);
			
			mainFragment.setArguments(values);
			//launch the fragment with the event information (if any has been passed in)
			getSupportFragmentManager().beginTransaction().add(android.R.id.content, mainFragment).commit();
		}else {
			//if there is a saved instance then contine the last fragment.
			mainFragment = (FacebookFragment) getSupportFragmentManager().findFragmentById(android.R.id.content);
		}
	}
}
