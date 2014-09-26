/*
 * David McQueen
 * 10153465@stu.mmu.ac.uk
 */
package co.uk.davemcqueen.Assignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	
	private static final String TAG = "DBHelper";
	
	/*
	 * Define the column names that are used through the DB, retrieved from Event class which holds the variables.
	 * These variables can be called when they column name is needed.
	 * Ensures consistency throughout, and eliminates chance of typing error.
	 */
	private static final String DBName = "diary.db";
	private static final int DBVersion = 1;
	
	public static final String TABLE_EVENTS = Event.EventItem.TABLE_NAME;
	public static final String COLUMN_ID = Event.EventItem.COLUMN_NAME_ID;
	public static final String COLUMN_EVENT = Event.EventItem.COLUMN_NAME_EVENT;
	public static final String COLUMN_DESCRIPTION = Event.EventItem.COLUMN_NAME_DESCRIPTION;
	public static final String COLUMN_DATE = Event.EventItem.COLUMN_NAME_DATE;
	public static final String COLUMN_TIME = Event.EventItem.COLUMN_NAME_TIME;
	public static final String COLUMN_LOCATION = Event.EventItem.COLUMN_NAME_LOCATION;
	public static final String COLUMN_IMAGE = Event.EventItem.COLUMN_NAME_IMAGE;
	
	
	//the query to create the table, if it doesnt already exist.
	private static final String CreateDBTable = "CREATE TABLE "
			+ TABLE_EVENTS + " ("
			+ COLUMN_ID + " INTEGER PRIMARY KEY, "
			+ COLUMN_EVENT + " TEXT, "
			+ COLUMN_DESCRIPTION + " TEXT, "
			+ COLUMN_DATE + " TEXT, "
			+ COLUMN_TIME + " TEXT, "
			+ COLUMN_LOCATION + " TEXT, "
			+ COLUMN_IMAGE + " TEXT"
			+ ");";
		
	
	public DBHelper(Context context)
	{
	super (context, DBName, null, DBVersion);
	}
	
	public DBHelper(Context context, boolean testMode)
	{
		super(context, null, null, DBVersion);
	}
			
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.w(TAG, CreateDBTable + " created");
		db.execSQL(CreateDBTable); //If the database doesn't exist, create it using the Query made above.
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}
}
