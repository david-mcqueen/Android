/**
 * 
 */
package com.democo.expenses;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Dave
 *
 */
public class SyncService extends IntentService {

	private static final String TAG="SyncService";
	
	public SyncService(){
		super("SyncService");
	}
	
	@Override
	public void onCreate() {
		
		super.onCreate();
		Log.i(TAG, "onCreate() called");
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.i(TAG, "Processing Started");
		
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Log.i(TAG, "Processing Finished");
		

	}
}
