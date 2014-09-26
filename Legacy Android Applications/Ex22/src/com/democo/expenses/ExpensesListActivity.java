package com.democo.expenses;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ExpensesListActivity extends Activity {
	private static final String TAG="ExpensesListActivity";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.main);
        startSync();
    }
    
    private void startSync() {
    	Intent startSyncService = new Intent(this, SyncService.class);
    	startService(startSyncService);
    	
    }
    
}