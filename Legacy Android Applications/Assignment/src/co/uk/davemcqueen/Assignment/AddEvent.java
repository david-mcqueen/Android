/*
 * David McQueen
 * 10153465@stu.mmu.ac.uk
 */
package co.uk.davemcqueen.Assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddEvent extends Activity {

	boolean updating = false;
	private static final int DATE_DIALOG_ID = 1; //passed into the dialog method so we can determin what to display
	private static final int TIME_DIALOG_ID = 2;
	int day, month, year; //the values for the date & date picker
	int hour, minute; //the values for the time & time picker
	double lng = 0;//variables to store the long & lat coordinates.
	double lat = 0;
	String strLocation;
	boolean includeLocation = false; //if the user clicks to include location, this will enable and save the location to the DB
	DatePicker datePicker;
	TimePicker timePicker;
	String date;
	String time;
	private String provider;
	String locationFormat;
	SharedPreferences prefs;
	Bitmap mImageBitmap;
	Boolean mExtStoragePresent = false;
	Boolean mExtStorageRdOnly = true;
	String mCameraFileName;
	boolean imageExists = false;
	boolean cameraPresent = false;

	Uri UriSavedImage;
	File imageOutput;
	
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
		setContentView(R.layout.addevent);
		
		//Define the visual fields and handlers
		final TextView newEventName = (TextView)findViewById(R.id.newEvent_editEventName);
		final TextView newEventDescription = (TextView)findViewById(R.id.newEvent_editDescription);
		final TextView newEventDate = (TextView)findViewById(R.id.newEvent_txtDate);
		final TextView newEventTime = (TextView)findViewById(R.id.newEvent_txtTime);
		final TextView newEventLocationtxt = (TextView)findViewById(R.id.newEvent_txtLocation);
		final TextView locationTag = (TextView) findViewById(R.id.newEvent_tagLocation);
		final Button newEventbtnGet = (Button) findViewById(R.id.newEvent_btnGetLocation);
		final Button newEventbtnMap = (Button) findViewById(R.id.newEvent_btnshowMap);
		final ImageButton newEventbtnCamera = (ImageButton) findViewById(R.id.newEvent_btnCamera);
		final Bundle extras = getIntent().getExtras();
		final ImageView newEventImage = (ImageView) findViewById(R.id.newEvent_Image);
		CheckBox newEventLocationCheckBox = (CheckBox)findViewById(R.id.newEvent_checkLocation);
		
		
		//hide the visibility of the location fields until location is required, the check box being selected.
		newEventLocationtxt.setVisibility(View.GONE);
		locationTag.setVisibility(View.GONE);
		newEventbtnGet.setVisibility(View.GONE);
		newEventbtnMap.setVisibility(View.GONE);
		
		
		//Get the default shared preferences
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		locationFormat = prefs.getString("location_format", null);
		
		
		//Acquire the calendar date & time of the current date.
		//Used as default values
		Calendar today = Calendar.getInstance();
		year = today.get(Calendar.YEAR);
		month = today.get(Calendar.MONTH);
		day = today.get(Calendar.DAY_OF_MONTH);
		hour = today.get(Calendar.HOUR_OF_DAY);
		minute = today.get(Calendar.MINUTE);
		
		date = (year + "/" + (month+1) + "/" + day);
		newEventDate.setText(day + "/" + month + "/" + year);
		String hourtxt = String.valueOf(hour);
		String minutetxt = String.valueOf(minute);
		if(hour < 10)
		{ //if the hour / minute is below 10, add a 0 to the front. For Display formatting
			hourtxt = (0 + String.valueOf(hour));
		}
		if (minute < 10)
		{
			minutetxt = (0 + String.valueOf(minute));
		}
		newEventTime.setText(hourtxt + ":" + minutetxt);
		
		
		//Create and set up the location listener.
		final LocationManager mlocManager= (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		final LocationListener mlocListener = new MyLocationListener(); //implement a location listener, using the MyLocationListenerClass
		Criteria criteria = new Criteria();
		criteria.setAccuracy(2); //Sets a medium accuracy (100 - 500 meters accurate)
		provider = mlocManager.getBestProvider(criteria, false);
		
		
		//Determine if there is a camera present
		PackageManager pm = getPackageManager();
		cameraPresent = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
		
		
		//---If information has been passed into the intent, then an update is being performed.
		//--- Load that information from the DB and display
		if (extras != null)
		{
			String imageloc = null;
			String[] projection = {Event.EventItem.COLUMN_NAME_EVENT, Event.EventItem.COLUMN_NAME_DESCRIPTION,
					Event.EventItem.COLUMN_NAME_DATE, Event.EventItem.COLUMN_NAME_TIME, Event.EventItem.COLUMN_NAME_LOCATION,
					Event.EventItem.COLUMN_NAME_IMAGE};
			String selection = ("_id=?");
			String[] selectionArgs = new String[] {String.valueOf(extras.getInt("id"))};
			Cursor c = getBaseContext().getContentResolver().query(dbContentProvider.CONTENT_URI,
					projection, selection, selectionArgs, Event.EventItem.COLUMN_NAME_DATE);
			
			//Traverse through the results from the DB and display them in the relevant variables, fields & format
			while (c.moveToNext())
			{
				newEventName.setText(c.getString(0));
				newEventDescription.setText(c.getString(1));
				date = (c.getString(2));
				time = (c.getString(3));
				newEventTime.setText(time);
				strLocation = (c.getString(4));
				imageloc = c.getString(5);
				imageOutput = new File(imageloc);
				if (!imageloc.equalsIgnoreCase(" ")) //Determine if an image already exists for the current event.
				{
					imageExists = true; 
				}
			} 
			if (date.contains("/"))
			{
				//Splits the date string to put values into the datePicker dialog
				String[] dateValues = date.split("/");
				year = Integer.parseInt(dateValues[0]);
				month = Integer.parseInt(dateValues[1]);
				day = Integer.parseInt(dateValues[2]);
				newEventDate.setText(day + "/" + month + "/" + year);//sets the date filed to display the saved date
				month--;//January is 0. Minus 1 so this is reflected in the dialog picker.
			}
			if (time.contains(":"))
			{	//splits the time values down to put into the timePicker dialog
				String[] timeValues = time.split(":");
				hour = (Integer.parseInt(timeValues[0]));
				minute = (Integer.parseInt(timeValues[1]));
			}
			if (strLocation != null && strLocation.contains(", "))
			{	//splits the location string down into the relevant format (lat & long)
				String[] locationValues = strLocation.split(", ");
				lat = Double.parseDouble(locationValues[0]);
				lng = Double.parseDouble(locationValues[1]);
				
				if (lat != 0 && lng != 0)
				{ //If a location has been set, display that location. Else leave blank.
					newEventLocationCheckBox.setChecked(true);
					newEventLocationtxt.setVisibility(View.VISIBLE);
					locationTag.setVisibility(View.VISIBLE);
					newEventbtnGet.setVisibility(View.VISIBLE);
					newEventbtnMap.setVisibility(View.VISIBLE);
					includeLocation = true;
					
					//GeoCode the location (gets street address / city) and pass in the users preferred format.				
					geoCode(lat, lng, locationFormat);
				}
			}
			updating = true; //tell the activity we are performing an update, not a new event.
		}
		
		/*
		 * If a photo is already stored for that event, retrieve and display the phote.
		 * Else check a camera is present, then take a photo.
		 */
		newEventbtnCamera.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (cameraPresent == false)
				{
				if (updating == true && imageExists == true)
				{//if there is already a photo saved
					
					Uri seletcedImageUri = Uri.fromFile(imageOutput);
					try {
						//COnvert the image to bitmap, rotate to portrait an display.
						Bitmap bmp =  BitmapFactory.decodeStream(getContentResolver().openInputStream(seletcedImageUri));
						Display d = getWindowManager().getDefaultDisplay();
						int x = d.getWidth();
						int y = d.getHeight();
						
						Bitmap scaledBitmap = bmp.createScaledBitmap(bmp, y, x, true);
						Matrix matrix = new Matrix();
						matrix.postRotate(90);
						
						Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
						newEventImage.setImageBitmap(rotatedBitmap);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					}
				// If there is no image already, get a unique file name and call the camera.
				if (imageExists == false)
				{
				Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				
				//creates a imageID from the event date. Converts this to an int and checks thats it unique
				//If there were several events with the same date the string is be incremented.
				String imageid = String.valueOf(day) + String.valueOf(month) + String.valueOf(year);
				int imageNumber = Integer.parseInt(imageid);
				File imagesFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "DMCal");
				if (!imagesFolder.exists())
				{//Determines if the relevant directory is present on the device.
					//If not then create all the necessary directory's.
				displayToast(getResources().getString(R.string.toastMakingDirectory));
				imagesFolder.mkdirs();
				}
				//create the file name using the date
				String fileName = "image_" + String.valueOf(imageNumber) + ".jpg";
				imageOutput = new File(imagesFolder, fileName);
				while(imageOutput.exists())
				{//if an image already exists with that name, find a unique name.
					imageNumber++;
					fileName = "image_" + String.valueOf(imageNumber) + ".jpg";
					imageOutput = new File(imagesFolder, fileName);
				}
				
				//Pass the unique image path name into the intent, so the camera activity knows where to save the image.
				UriSavedImage = Uri.fromFile(imageOutput);
				takePictureIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, 1);
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, UriSavedImage);
				
				startActivityForResult(takePictureIntent, 1); //start the camera activity
				}
				}else {
				displayToast(getResources().getString(R.string.toastNoCamera)); //Display an error if the device doesnt have a camera.
				}
			}
			
		});
		
		
		//launches the map view when clicked. Displays the location of the event on google maps.
		newEventbtnMap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//Sets up the maps intent and passes in the event location.
				//if there is no location provided with the event, pass in the default location.
				Intent i = new Intent(getBaseContext(), Map.class);
				i.setData(getIntent().getData());
				if (strLocation != null)
				{
				i.putExtra("lat", lat);
				i.putExtra("lng", lng);
				}else{
					//pass in the default location (Manchester).
					i.putExtra("lat", (53.48));
					i.putExtra("lng", (-2.25));
				}
				startActivity(i);
			}
		});
		
		
		//Gets the users current location from the Location Manager.
		newEventbtnGet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mlocManager.requestLocationUpdates(provider, 0, 10, mlocListener);
			}
		});
		
		//Gets the users preference as to if a location should be obtained automatically
		//if so then set the Location check box to true, display the extra fields and request location updates.
		if (prefs.getBoolean("display_location", false) && updating == false)
		{
			newEventLocationCheckBox.setChecked(true);
			mlocManager.requestLocationUpdates(provider, 0, 10, mlocListener);
			includeLocation = true;
			newEventLocationtxt.setVisibility(View.VISIBLE);
			locationTag.setVisibility(View.VISIBLE);
			newEventbtnGet.setVisibility(View.VISIBLE);
			newEventbtnMap.setVisibility(View.VISIBLE);
			
		}

		//The Location check box changes state
		newEventLocationCheckBox.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (((CheckBox)v).isChecked())
				{ //If it is checked, display the extra location fields and request a location.
					includeLocation = true;
					mlocManager.requestLocationUpdates(provider, 0, 10, mlocListener);
					newEventLocationtxt.setVisibility(View.VISIBLE);
					locationTag.setVisibility(View.VISIBLE);
					newEventbtnGet.setVisibility(View.VISIBLE);
					newEventbtnMap.setVisibility(View.VISIBLE);
					
				} else if(!((CheckBox)v).isChecked())
				{ //If it is unchecked hide the extra location fields and cancel location updates.
					includeLocation = false;
					newEventLocationtxt.setVisibility(View.GONE);
					locationTag.setVisibility(View.GONE);
					newEventbtnGet.setVisibility(View.GONE);
					newEventbtnMap.setVisibility(View.GONE);
					mlocManager.removeUpdates(mlocListener);
				}
			}
		});
		
		
		//When the users clicks on the Time textView ,a timePicker dialog is displayed.
		//This ensures that the same time format is used consistently.
		newEventTime.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				// TODO Auto-generated method stub
				showDialog(TIME_DIALOG_ID);
				newEventTime.clearFocus(); //clear focus off the text field, so that the user can accidently enter some rogue text.
			}
		});
		
		
		//When the users clicks on the date textView, a datePicker dialog is displayed.
		//This ensures that the same date format is used consistently.
		newEventDate.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG_ID);
				newEventDate.clearFocus(); //clear focus off the text field, so that the user can accidently enter some rogue text.
			}
		});
		
		
		//when the user presses to save, either save a new entry, or update an existing one if an update is being performed.
		Button btnSave = (Button) findViewById(R.id.newEvent_btnSave);
		btnSave.setOnClickListener(new OnClickListener()
		{
			public void onClick(View arg0){
				//Checks than an event has a name and a description.
				//Date & time are automatically filled in so they dont need to be checked.
				if (newEventName.getText().toString().equalsIgnoreCase("") || newEventDescription.getText().toString().equalsIgnoreCase(""))
				{
					displayToast(getResources().getString(R.string.toastFillAllFields));
				}else
				{
				if (updating == false)
				{//if the event is a new event
					ContentValues inputValues = new ContentValues();
					inputValues.put(Event.EventItem.COLUMN_NAME_EVENT, newEventName.getText().toString());
					inputValues.put(Event.EventItem.COLUMN_NAME_DESCRIPTION, newEventDescription.getText().toString());
					inputValues.put(Event.EventItem.COLUMN_NAME_DATE, date);
					inputValues.put(Event.EventItem.COLUMN_NAME_TIME, newEventTime.getText().toString());
					if (includeLocation == true)
					{ //put in the location
						inputValues.put(Event.EventItem.COLUMN_NAME_LOCATION, (String.valueOf(lat) + ", " + String.valueOf(lng)));
					} else if (includeLocation = false){
						//if no location is wanted, pass in "0, 0" as a default null value
						inputValues.put(Event.EventItem.COLUMN_NAME_LOCATION, ("0, 0"));
					}
					if (imageOutput != null)
					{//save the URI of the image, if one has been taken
						inputValues.put(Event.EventItem.COLUMN_NAME_IMAGE, String.valueOf(imageOutput));
					}
					else if(imageOutput == null)
					{//save a blank field if no image has been taken.
						inputValues.put(Event.EventItem.COLUMN_NAME_IMAGE, " ");
					}
				//pass in the values to save the event.
				getBaseContext().getContentResolver().insert(dbContentProvider.CONTENT_URI, inputValues);
				displayToast(getResources().getString(R.string.toastEventSaved));
				}else{
				//if the event is one to be edited
					ContentValues values = new ContentValues();
					values.put(Event.EventItem.COLUMN_NAME_EVENT, newEventName.getText().toString());
					values.put(Event.EventItem.COLUMN_NAME_DESCRIPTION, newEventDescription.getText().toString());
					values.put(Event.EventItem.COLUMN_NAME_DATE, date);
					values.put(Event.EventItem.COLUMN_NAME_TIME, newEventTime.getText().toString());
					if (includeLocation == true)
					{
						values.put(Event.EventItem.COLUMN_NAME_LOCATION, (String.valueOf(lat) + ", " + String.valueOf(lng)));
					} else if (includeLocation == false)
					{
						values.put(Event.EventItem.COLUMN_NAME_LOCATION, ("0, 0"));
						//if the user doesn't want to store their location, enter blank values
					}
					//perform the update where the id = the id that has been passed in from the ListView on ListDiary activity
					String selection = "_id=?";
					String[] selectionArgs = new String[] {String.valueOf(extras.getInt("id"))};
					int updateAmount = getBaseContext().getContentResolver().update(dbContentProvider.CONTENT_URI,
							values, selection, selectionArgs);
					displayToast("Updated " + updateAmount + " item(s)");
				}
				finish();//finish the activity, return to ListDiary activity
				}
			}
		});
	}

	//When the camera returns a value, process it here.
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		final ImageView newEventImage = (ImageView) findViewById(R.id.newEvent_Image);
		Uri seletcedImageUri = Uri.fromFile(imageOutput);
		if (resultCode == Activity.RESULT_OK) //If a photo was successfully taken
		{
			displayToast(getResources().getString(R.string.toastImageSaved));
			try {
				//convert the image into Bitmap so that it displays correctly in the imageView
				Bitmap bmp =  BitmapFactory.decodeStream(getContentResolver().openInputStream(seletcedImageUri));
				Display d = getWindowManager().getDefaultDisplay();
				int x = d.getWidth();
				int y = d.getHeight();
				Bitmap scaledBitmap = bmp.createScaledBitmap(bmp, y, x, true);
				Matrix matrix = new Matrix();
				matrix.postRotate(90);
				
				Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
				newEventImage.setImageBitmap(rotatedBitmap); //display the converted image
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		}
}
	//Display a toast message with the passed in string
	public void displayToast(String message)
	{
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
		
	}

	//Depending on the value passed in, perform the relevant action
	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch(id) {
		case DATE_DIALOG_ID:
			//display a dialog with a date picker on it.
			//sets the picker to the currently saved values
			return new DatePickerDialog(
					this, mDateSetListener, year, month, day);
			
		case TIME_DIALOG_ID:
			//display a dialog with a time picker on it
			//sets the picker to the currently saved values
			return new TimePickerDialog(
					this, mTimeSetListener, hour, minute, false);
		
			default: return null;		
	}
	}
	
	//function to display a timePicker dialog
	//The selected values are then saved to the relevant variables.
	private TimePickerDialog.OnTimeSetListener mTimeSetListener = 
 	new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int newMinute) {
			// TODO Auto-generated method stub
			hour = hourOfDay;
			minute = newMinute;
			final TextView newEventTime = (TextView)findViewById(R.id.newEvent_txtTime);
			
			String hourtxt = String.valueOf(hour);
			String minutetxt = String.valueOf(minute);
			if(hour < 10)
			{//if hour is under 10, then append a 0
				hourtxt = (0 + String.valueOf(hour));
			}
			if (minute < 10)
			{//if minute is under 10, then append a 0
				minutetxt = (0 + String.valueOf(minute));
			}
			time = (hourtxt + ":" + minutetxt);
			newEventTime.setText(time); //display the selected time in the textField
		}
	};
	
	//Function to display a datePicker dialog
	//function to display a datePicker dialog
		//The selected values are then saved to the relevant variables.
	private DatePickerDialog.OnDateSetListener mDateSetListener =
    		new DatePickerDialog.OnDateSetListener()
    {
    	public void onDateSet(
    			DatePicker view, int newYear, int monthOfYear, int dayOfMonth)
    	{
    		year = newYear;
    		month = monthOfYear;
    		day = dayOfMonth;
    		
			final TextView newEventDate = (TextView)findViewById(R.id.newEvent_txtDate);
			date = day + "/" + (month+1) + "/" + year;
			newEventDate.setText(date);
			date = (year + "/" + (month+1) + "/" + day);
		}
    };

	
  //Locaion listener, used to get location updates when requested.
   public class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			lat = location.getLatitude();
			lng = location.getLongitude();
			strLocation = (String.valueOf(lat) + ", " + String.valueOf(lng));
			geoCode(lat, lng, locationFormat); //pass the location to be geoCoded (Street address / city)
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}
   
   //Function to retrieve a street address / city from the lat & long coordinates passed in.
   //The location format is passed in so the function knows whichg way to display the address
   public void geoCode(double lat, double lng, String format)
   {
	   final TextView newEventLocationtxt = (TextView)findViewById(R.id.newEvent_txtLocation);
	   Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
	   
	   if (!locationFormat.equalsIgnoreCase("GPS Co-ordinates")) 
	   {//If the user doesn't want the location displayed as coordinates
		   try {
			   List<Address> addresses = geoCoder.getFromLocation(lat, lng, 1);
			   if (addresses.size() > 0)
			   {
				   String add = "";
				   if (format.equalsIgnoreCase("Street address"))
				   {//if the user wants the whole street address displaying.
					   for (int i = 0; i < addresses.get(0).getMaxAddressLineIndex(); i++)
					   {
						   add += addresses.get(0).getAddressLine(i) + "\n";
					   }
				   }  else if (format.equalsIgnoreCase("City"))
				   {//if the user wants just the city displaying
					   add = addresses.get(0).getLocality();
				   }
				   newEventLocationtxt.setText(add); //display the result in the textView
			   }
		   } catch (IOException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
		   }
	   }else
	   {
		 newEventLocationtxt.setText(strLocation);
	   }
   }
   
   //Each time the activity is resumed, is also called after onCreate
   //used to update the location
   @Override
protected void onResume() {
	super.onResume();
	locationFormat = prefs.getString("location_format", null);
	geoCode(lat, lng, locationFormat);
}

   
public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.addevent_optionmenu, menu);
		return true;
	}

//perform the relevant action to what the user selected
	public boolean onOptionsItemSelected(MenuItem item) {		
		switch (item.getItemId()){
			case R.id.menu_settings:
				//Display the settings Activity
				Intent i = new Intent(getBaseContext(), ShowPreferencesActivity.class);
				startActivity(i);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish(); //finish the activity
	}
}



