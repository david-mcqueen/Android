package co.uk.davemcqueen.diaryassignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperNew extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "diary";
	public static final String TABLE_EVENTS = "events";
	
	private static final String EVENT_ID = "id";
	private static final String EVENT_NAME = "eventName";
	private static final String EVENT_DESCRIPTION = "eventDescription";
	
	public DBHelperNew(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_EVENT_TABLE = "CREATE TABLE " + TABLE_EVENTS + " ("
				+ EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ EVENT_NAME + " TEXT NOT NULL, "
				+ EVENT_DESCRIPTION + " TEXT " + "); ";
		db.execSQL(CREATE_EVENT_TABLE);
	}
	
	public DBHelperNew(Context context, boolean testMode) {
    	// If the 2nd parameter is null then the database is held in memory -- this form creates an in memory database
    	super(context, null, null, DATABASE_VERSION);
	}
	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
