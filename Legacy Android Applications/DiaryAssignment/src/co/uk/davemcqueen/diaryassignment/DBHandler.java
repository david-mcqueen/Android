package co.uk.davemcqueen.diaryassignment;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "diary";
	private static final String TABLE_EVENTS = "events";
	
	private static final String EVENT_ID = "id";
	private static final String EVENT_NAME = "eventName";
	private static final String EVENT_DESCRIPTION = "eventDescription";
	
	public DBHandler(Context context)
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

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	public void createEvent(Event event)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(EVENT_NAME, event.getName());
		values.put(EVENT_DESCRIPTION, event.getDescription());
		
		db.insert(TABLE_EVENTS, null, values);
		db.close();
	}
	
	public Event getEvent(int id)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_EVENTS, 
				new String[] {EVENT_ID, EVENT_NAME, EVENT_DESCRIPTION },
				EVENT_ID + "=?",
				new String[] {String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
		{
			cursor.moveToFirst();
		}
		
		Event event = new Event(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
		
		return event;
	}
	
	public List<Event> getAll()
	{
		List<Event> eventList = new ArrayList<Event>();
		
		String selectQuery = "SELECT * FROM " + TABLE_EVENTS;
		
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if (cursor.moveToFirst())
		{
			do {
				Event event = new Event();
				event.setID(Integer.parseInt(cursor.getString(0)));
				event.setName(cursor.getString(1));
				event.setDescription(cursor.getString(2));
				
				eventList.add(event);
			}while (cursor.moveToNext());
		}
		return eventList;
	}

	
	
	public int getCount()
	{
		String selectQuery = "SELECT * FROM " + TABLE_EVENTS;
		
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.rawQuery(selectQuery, null);
		cursor.close();
		
		return cursor.getCount();
	}

}
