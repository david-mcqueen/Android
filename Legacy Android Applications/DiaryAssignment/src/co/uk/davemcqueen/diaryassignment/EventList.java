package co.uk.davemcqueen.diaryassignment;

import java.util.List;



import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

public class EventList extends ListActivity {

	private static final String TAG = "ExpenseListActivity";
	private DAO dao;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		dao = new DAO(this);
		
		DBHandler db = new DBHandler(this);
		Event holiday = new Event("Newyork", "Going on holiday to New York");
		db.createEvent(holiday);
		
		Event test = db.getEvent(1);
		displayToast(test.eventName);	
		
		
		Cursor cursor = dao.queryEvents("eventName", null, null);
					
		
	}
	
	public void displayToast(String message)
	{
		Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
	}
	


}
