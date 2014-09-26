/*
 * David McQueen
 * 10153465@stu.mmu.ac.uk
 */
package co.uk.davemcqueen.Assignment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

public class ServerSync extends IntentService {
/*
 * A seperate thread that sends the database information to a server.
 * Works as a Service to prevent interruption to the user.
 */
	private static final String TAG="SyncService";
	
	public ServerSync() {
		super("SyncService");
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i(TAG, "onCreateCalled");
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		// TODO Auto-generated method stub
		Log.i(TAG, "Processing started");
		Toast.makeText(getBaseContext(), "Sync Started", Toast.LENGTH_LONG).show();
		
		//Define where the information is to be sent.
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://www.davemcqueen.co.uk/android/index.php");
		
		//retrieves all of the event and all of their information
		String[] projection = {Event.EventItem.COLUMN_NAME_ID,
				Event.EventItem.COLUMN_NAME_EVENT, Event.EventItem.COLUMN_NAME_DESCRIPTION,
				Event.EventItem.COLUMN_NAME_DATE, Event.EventItem.COLUMN_NAME_TIME, Event.EventItem.COLUMN_NAME_LOCATION};
		Cursor c = getBaseContext().getContentResolver().query(dbContentProvider.CONTENT_URI, projection, null, null, Event.EventItem.COLUMN_NAME_DATE);
		
		while (c.moveToNext())
		{
			/*
			 * for each of the events, add the information into key / value pairs and send to the server.
			 */
		try{
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
		nameValuePairs.add(new BasicNameValuePair("_id", c.getString(0)));
		nameValuePairs.add(new BasicNameValuePair("eventName", c.getString(1)));
		nameValuePairs.add(new BasicNameValuePair("eventDescription", c.getString(2)));
		nameValuePairs.add(new BasicNameValuePair("eventDate", c.getString(3)));
		nameValuePairs.add(new BasicNameValuePair("eventTime", c.getString(4)));
		nameValuePairs.add(new BasicNameValuePair("eventLocation", c.getString(5)));
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		
		HttpResponse response = httpClient.execute(httppost); //send the current event to the server
		
		} catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
		}
		Toast.makeText(getBaseContext(), "Sync Finished", Toast.LENGTH_LONG).show();
		Log.i(TAG,"processing finished");

	}

}
