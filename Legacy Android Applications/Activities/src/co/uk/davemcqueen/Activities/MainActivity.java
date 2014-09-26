package co.uk.davemcqueen.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends Activity {
	String tag = "Events";
	int request_Code = 1;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //---Hides the title bar ---
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.main);
        Log.d(tag, "In the onCreate() event");
    }
    
    public boolean onKeyDown (int keyCode, KeyEvent event)
    {
    	if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
    			{
    		//startActivity(new Intent("co.uk.davemcqueen.ACTIVITY2"));
    		//startActivity(new Intent(this, Activity2.class));
    		/*
    		startActivityForResult(new Intent ("co.uk.davemcqueen.ACTIVITY2"),
    				request_Code);
    				*/
    		
    		Intent i = new Intent("co.uk.davemcqueen.ACTIVITY2");
    		Bundle extras = new Bundle();
    		extras.putString("Name", "Your name here");
    		i.putExtras(extras);
    		startActivityForResult(i, request_Code);
    		  	}
    	return false;
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    	if (requestCode == request_Code) {
    		if (resultCode == RESULT_OK) {
    			Toast.makeText(this,data.getData().toString(),Toast.LENGTH_SHORT).show();
    		}
    	}
    }
    
    public void onStart()
    {
    	super.onStart();
    	Log.d(tag, "In the onStart() event");
    }
    public void onRestart()
    {
    	super.onRestart();
    	Log.d(tag, "In the onRestard() event");
    }
    public void onResume()
    {
    	super.onResume();
    	Log.d(tag, "In the onResume() event");
    }
    public void onPause()
    {
    	super.onPause();
    	Log.d(tag, "IN the onPause() event");
    }
    public void onStop()
    {
    	super.onStop();
    	Log.d(tag, "In the onStop() event");
    }
    public void onDestroy()
    {
    	super.onDestroy();
    	Log.d(tag, "in the onDestroy() event");
    }
}