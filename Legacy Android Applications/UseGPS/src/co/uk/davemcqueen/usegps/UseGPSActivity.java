package co.uk.davemcqueen.usegps;

import com.mobfox.sdk.MobFoxView;
import com.mobfox.sdk.Mode;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import co.uk.davemcqueen.FindMe.R;

public class UseGPSActivity extends Activity {
	boolean locationProvided = false; // Flag to identify if the current location has been returned. To ensure that the location is updated
	// only when the users presses upon the button to obtain the location.
	String myLocation = "LOCATION NOT FOUND!!!!";
	String Url;
	public ProgressDialog progressDialog;
	double longLoc;
	double latLoc;
	boolean slowConnection = false;
	boolean gpsEnabled = false;
	private RelativeLayout layout;
	private MobFoxView mobfoxView;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); // removes the title bar from the app
        setContentView(R.layout.main);  
        layout = (RelativeLayout) findViewById(R.id.mobfoxContent);
        mobfoxView = new MobFoxView(this, "e4b42e54238276520bcafa1d17a265e0", Mode.LIVE, true, true);
        
        mobfoxView.setVisibility(View.VISIBLE);
        layout.addView(mobfoxView);
        
        showDialogMessage(3); //---Instructions---
    	             
        //---Handler to control the find location button---
        Button getLocation;
        getLocation = (Button) findViewById(R.id.btngetLocation);
        getLocation.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getLocationFunction(); //---calls the function to obtain the users locations---
			}
		});
        
        //---Handler to control the send location button---
        Button sendLocation;
        sendLocation = (Button) findViewById(R.id.btnSendLocation);
        sendLocation.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendLocation();
			}	
        });
    } //---End of onCreate method.
    
    
    //---Class MyLocation Listener---
    public class MyLocationListener implements LocationListener
    {
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			/*
			 * When the users location changes, this function is called.
			 * Stores the location details in variables.
			 * Also creates the variables to pass into the email / text message.
			 */
			location.getLatitude();
			location.getLongitude();

			if (locationProvided == false)
			{
				//---Displays the location in Degrees (Lat & Long)---
			String Text = "Your current location is: " + "lat = " + (location.getLatitude()) +
					" long = " + (location.getLongitude());
			DisplayPopUp(Text); //Displays a pop up to the users as confirmation.
			longLoc = location.getLongitude();
			latLoc = location.getLatitude();
			Url = "http://maps.google.com/maps?q=Find+Me!@" +
					String.valueOf(location.getLatitude()) + "," +
					String.valueOf(location.getLongitude());
			myLocation = "Click here to see my location!: " + Url +
					"" +
					"  Sent with Find Me! Avaliable on the android market now!!";
			locationProvided = true; //This function isn't called again unless the user requests it.
			showDialogMessage(2); //Progress Dialog. Searching for location.
			}
		}
		
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			gpsEnabled = false;
			showDialogMessage(1); //Displays a pop up prompting to enable GPS
		}
		
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			gpsEnabled = true;
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
		}
    } //---End of MyLocation Listener Class---
    
    
    //---Function to display a short pop-up. Displaying text passed in---
    public void DisplayPopUp (String Text)
    {
    	Toast.makeText(getApplicationContext(), Text, Toast.LENGTH_LONG).show();
    }//---END OF DisplayPopUp function---
    
    
    /*
     * Function to launch the email Intent and pass in the 
     * users current location.
     */
    private void sendEmail (String message) {
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.setData(Uri.parse("mailto:"));
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Find Me!");
		emailIntent.putExtra(Intent.EXTRA_TEXT, message);
		emailIntent.setType("message/rfc822");
		startActivity(Intent.createChooser(emailIntent, "Email"));
	}
    
    
    /*
     * Displays a system dialog box, relevant to the id which is called
     * 0 = Error Message. No Location Obtained. Prompts for location.
     * 1 = Error Message. GPS not enabled. Prompts to enable GPS
     * 2 = Whilst the app is searching for a location.
     * 3 = Instructions displayed at app onCreate.
     */
    private void showDialogMessage (int id) {
    	switch (id){
    	
    	case 0: //Display error message, no location
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setCancelable(true);
    		builder.setTitle("Error!");
    		builder.setMessage("No location obtained! Please obtain location before sending!");
    		builder.setInverseBackgroundForced(true);
    		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
    			public void onClick(DialogInterface dialog, int which) {
    				// TODO Auto-generated method stub
    				locationProvided = false;
    				final LocationManager mlocManager= (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    	            final LocationListener mlocListener = new MyLocationListener();
    				if (!mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
    					showDialogMessage(1); //if GPS not enabled, show message
    				} 
    				else if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
    				showDialogMessage(2); //If GPS enabled continue
    				mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
    				}
    			}
    		});
    		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
    			public void onClick(DialogInterface dialog, int which) {
    				// TODO Auto-generated method stub
    				dialog.dismiss();
    			}
    		});
    		AlertDialog alert = builder.create(); //---Creates the pop up---
    		alert.show(); //---Display the pop up---
    	return;
    	
    	case 1: //Display error message, GPS not enabled!
    		AlertDialog.Builder builderGPS = new AlertDialog.Builder(this);
    		builderGPS.setCancelable(true);
    		builderGPS.setTitle("Error!");
    		builderGPS.setMessage("Your GPS is disabled! Would you like to enable it?");
    		builderGPS.setInverseBackgroundForced(true);
    		builderGPS.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int which) {
    				// TODO Auto-generated method stub
    				dialog.dismiss();
    				showGpsOptions();
    			}
    		});
    		builderGPS.setNegativeButton("No", new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int which) {
    				// TODO Auto-generated method stub
    				dialog.dismiss();
    			}
    		});
    		AlertDialog alertGPS = builderGPS.create();
    		alertGPS.show(); //Displays the pop up
    	return;
    	
    	case 2: //progress dialog. Searching for location!
    		
    		if (locationProvided == false) {
    			progressDialog = ProgressDialog.show(UseGPSActivity.this, "Obtaining Location", "Searching...", true);
    		}
    		else if (locationProvided == true) {
    			progressDialog.dismiss();
    			progressDialog.cancel();
    		}
    		return;	
    		
    	case 3: // Instructions
    		AlertDialog.Builder builderInstructions = new AlertDialog.Builder(this);
    		builderInstructions.setCancelable(true);
    		builderInstructions.setTitle("Instructions.");
    		builderInstructions.setMessage("1) Press the thought bubble to find your location.\n" +
    				"2) Press the pigeon to send your location.\n\n" +
    				"It's that simple!");
    		builderInstructions.setInverseBackgroundForced(true);
    		builderInstructions.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    			
    			public void onClick(DialogInterface dialog, int which) {
    				// TODO Auto-generated method stub
    				dialog.dismiss();
    			}
    		});
    		
    		AlertDialog alertInstructions = builderInstructions.create();
    		alertInstructions.show();
    		return;	
    }
    }//---End of showDialogMessage function---
    
    
    /*
     * If the GPS isn't enabled. User is prompted to enable it using the showDialogMessage function
     * When the user wants to enable GPS, this function launches the intent to actiavte it.
     */
    private void showGpsOptions() {
    	Intent gpsOptionsIntent = new Intent (
    			android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
    	startActivity(gpsOptionsIntent);
    }//--- End of showGpsOptions function---
    
    
    /*
     * Displays the options menu, using the information created
     * in the CreateMenu function below.
     */
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	CreateMenu(menu);
    	return true;
    }//---end of the onCreateOptionsMenu function
    
    /*
     * Returns the selected menu, allowing it to be processed.
     */
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	return MenuChoice(item);
    }//---End the onOptionsItemSelected function
    
    /*
     * Creates and populates an option menu
     * which gets displayed as an alternative & extension to the main GUI operations.
     */
    private void CreateMenu(Menu menu)
    {
    	MenuItem mnu1 = menu.add(0, 0, 0, "Get Location");
    	{
    		mnu1.setAlphabeticShortcut('g');
    	}
    	MenuItem mnu2 = menu.add(0, 1, 1, "Send Location");
    	{
    		mnu2.setAlphabeticShortcut('s');
    	}
    	MenuItem mnu3 = menu.add(0, 2, 2, "Show Instructions");
    	{
    		mnu3.setAlphabeticShortcut('i');
    	}
    	MenuItem mnu4 = menu.add(0, 3, 3, "Leave Feedback");
    	{
    		mnu4.setAlphabeticShortcut('f');
    	}
    	MenuItem mnu5 = menu.add(0, 4, 4, "Display Current Location");
    	{
    		mnu5.setAlphabeticShortcut('c');
    	}
    }//---End of CreateMenu function---
    
    /*
     * Gets the ID from onOptionsItemSelected and displays
     * the dialog relevant to what was selected.
     */
    private boolean MenuChoice(MenuItem item) {
    	switch (item.getItemId()) {
    	case 0: // run the get location code
    		getLocationFunction();
    		return true;
    	
    	case 1: // run the send location code
    		sendLocation();
    		return true;
    	
    	case 2: //run the instructions code
    		showDialogMessage(3);
    		return true;
    		
    	case 3: //run the leave feedback code
    		Intent i = new Intent(Intent.ACTION_VIEW);
    		i.setData(Uri.parse("market://details?id=" + getPackageName()));
    		startActivity(i);
    		return true;
    	
    	case 4: //Launches the maps Intent to show the current location.
    		if (locationProvided == true) {
    		Intent mapsIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(Url));
    		startActivity (mapsIntent);
    		} else if (locationProvided == false) {
    			showDialogMessage(0);
    		}
    	}
    	return false; 
    }
 
    /*
     * The function that runs to get the users location
     * Implements the MyLocationListener class
     */
    private void getLocationFunction() {
    	final LocationManager mlocManager= (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        final LocationListener mlocListener = new MyLocationListener();
        
			// TODO Auto-generated method stub
			locationProvided = false;
			if (!mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
				showDialogMessage(1); //if GPS not enabled, show message
			} 
			else if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
				//If GPS enabled continue				
			showDialogMessage(2); 
			mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mlocListener);
			mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
		}
    }//---End of getLocationFunction
    
    
    /*
     * Function that gets called to send the location
     * Either to an email address or via text message.
     */
    private void sendLocation() {
			// TODO Auto-generated method stub
			/*
			 * Create the dialog box, but doesn't display it.
			 */
			DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					switch (which) {
					case DialogInterface.BUTTON_POSITIVE: //Send by email
					//---"Email" button clicked
						sendEmail(myLocation);						
						break;
						
					case DialogInterface.BUTTON_NEGATIVE: //Send by text
						//---"Text" button clicked---
						Intent i = new Intent(android.content.Intent.ACTION_VIEW);
						i.putExtra("sms_body", myLocation);
						i.setType("vnd.android-dir/mms-sms");
						startActivity(i);
						break;
					}
				}
			};
			/*
			 * If the users location has been obtained, then the dialog creatd above
			 * (to specify which method to send by) gets created.
			 */
			if (locationProvided == true) {
			//---The control to display a 2 button dialog---
			AlertDialog.Builder builder = new AlertDialog.Builder(UseGPSActivity.this);
			builder.setMessage("Send location by...")
			.setPositiveButton("Email", dialogClickListener)
			.setNegativeButton("Text", dialogClickListener).show();
			} else if (locationProvided == false) {
				showDialogMessage(0);
			}
    } //--End of sendLocation function
}//---End of Activity
    

