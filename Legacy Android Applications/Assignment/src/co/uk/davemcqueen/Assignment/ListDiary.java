/*
 * David McQueen
 * 10153465@stu.mmu.ac.uk
 */
package co.uk.davemcqueen.Assignment;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import com.facebook.*;
import com.facebook.model.*;
import android.widget.TextView;

public class ListDiary extends ListActivity {

	private static final String TAG = "MAIN";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		//if this is the first time the user has launched the app, display a welcome dialog and prompt
		// for default settings to be used.
		if (getPreferences("usedBefore")==false)
		{
			String whatsNewTitle = getResources().getString(R.string.welcome);
	        String whatsNewText = getResources().getString(R.string.whatsNew);
	        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(whatsNewTitle).setMessage(whatsNewText).setPositiveButton(
	                "Ok", new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
	                        dialog.dismiss();
	                        Intent settings = new Intent(getBaseContext(), ShowPreferencesActivity.class);
	            			startActivity(settings);
	            			storePreferences("usedBefore", true);
	                    }
	                }).show();
		}
		storePreferences("noEventPrompt", false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.option_menu, menu);
		return true;
	}
	
	//Display a Toast message using the string passed in.
	public void displayToast(String message)
	{	//Pre: String passed in
		//Post: Display a toast message with the value of the string
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
		
	}

	//When an option item is selected, determine which item was pressed and perform the relevant action
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.menu_add:
				//Add a new expense
				Intent i = new Intent(getBaseContext(), AddEvent.class);
				startActivity(i);
				return true;
				
			case R.id.menu_settings:
				//open up the options menu
				Intent settings = new Intent(this, ShowPreferencesActivity.class);
				startActivity(settings);
				return true;
				
			case R.id.menu_deleteAll:
				//delete all the events
				int rowsDeleted = getBaseContext().getContentResolver().delete(dbContentProvider.CONTENT_URI, null, null);
				if ( rowsDeleted > 0)
				{
				displayToast("Success! " + String.valueOf(rowsDeleted) + " row(s) deleted");
				populateList();
				}
				return true;
			case R.id.menu_sync:
				displayToast(getResources().getString(R.string.toastSync));
				Intent startSyncService = new Intent(this, ServerSync.class);
				startService(startSyncService);
				return true;
			default:
					return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onResume() {
		//Each time the activity is Resumed (including the first launch, called after onCreate())
		super.onResume();
		populateList(); //call to populate the listView
	}
	
	//A function to populate the listView with all the information in the DB
	public void populateList()
	{
		String[] projection = {Event.EventItem.COLUMN_NAME_ID,
				Event.EventItem.COLUMN_NAME_EVENT, Event.EventItem.COLUMN_NAME_DESCRIPTION};
		Cursor c = getBaseContext().getContentResolver().query(dbContentProvider.CONTENT_URI, projection, null, null, Event.EventItem.COLUMN_NAME_DATE);
		
		
		//if there are no events returned, then display a pop up to prompt for a new event.
		if (c.getCount() < 1)
		{
			if (getPreferences("noEventPrompt") == false && getPreferences("usedBefore")==true)
			{
			String noEventTitle = getResources().getString(R.string.noEvents);
	        String noEventText = getResources().getString(R.string.noEventsAvaliable);
	        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(noEventTitle).setMessage(noEventText).setPositiveButton(
	                "Ok", new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
	                        dialog.dismiss();
	                        Intent i = new Intent(getBaseContext(), AddEvent.class);
	        				startActivity(i);
	                    }
	                }).show();
	        storePreferences("noEventPrompt", true);
			}
		}else{ //If events are in the DB then populate the list view
			@SuppressWarnings("deprecation")
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(
				this,
				android.R.layout.simple_list_item_1,
				c,
				new String[] {Event.EventItem.COLUMN_NAME_EVENT},
				new int[] {android.R.id.text1});
		
		ListView listView = getListView();
		listView.setChoiceMode(1);
		listView.setAdapter(adapter);
		int backgroundColor = getResources().getColor(R.color.Gray);
		listView.setBackgroundColor(backgroundColor);
		registerForContextMenu(listView);
		
		
		//if an event is clicked on, open up the screen to edit the event
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				editExpenseItem(position, id);
			}
		});
		}
	}
	
	//Gets the event ID from the listView and then passes that id to the 
	//new dialog so that it can retrieve the event and populate the fields.
	private void editExpenseItem(int position, long id) {

		//update the list to highlight the selected item and show the data.
		getListView().setItemChecked(position, true);

	  Log.d(TAG, "Launching ExpenseEntryActivtiy");
		
  	  Intent intent = new Intent(ListDiary.this, AddEvent.class);
  	  intent.setData(getIntent().getData()); // Make the base CP available in the target action
  	  intent.putExtra("id", ((int)(id))); //pass in the event ID
  	  startActivity(intent);
	}
	
	//Inflate the context menu for a long press.
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.long_menu, menu);	
	}

	//Determin which item has been selected in the context menu, and do the relevant action
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()){
		case R.id.menu_event_delete:
			//deletes the selected item
			String selection = "_id=?";
			String[] selectionArgs = new String[] {Long.toString(info.id)};
			
			long count = getBaseContext().getContentResolver().delete(dbContentProvider.CONTENT_URI, selection, selectionArgs);
			displayToast("Deleted " + String.valueOf(count) + " item(s)");
			populateList();
			return true;
			
		case R.id.menu_event_edit:
			//open up edit menu for that item
			
			Intent intent = new Intent(ListDiary.this, AddEvent.class);
		  	  intent.setData(getIntent().getData()); // Make the base CP available in the target action
		  	  intent.putExtra("id", ((int)info.id));
		  	  startActivity(intent);
			return true;
			
		case R.id.share_facebook:
			//Share the selected event on facebook
			Intent facebookLogin = new Intent(ListDiary.this, FacebookLogin.class);
			facebookLogin.putExtra("id", (int)info.id);
			startActivity(facebookLogin);
			return true;
		default:
				return super.onContextItemSelected(item);
	}
	}
	
	//Stores the passed in values into the saved preferences.
	private void storePreferences(String name, boolean value)
	{
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit();
		editor.putBoolean(name, value);
		editor.commit();
	}
	
	//Retrieve the sharePreference for the requested item.
	private boolean getPreferences(String name)
	{
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		return (prefs.getBoolean(name, false));
	}

	/*
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
	  Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	}
	*/

}
