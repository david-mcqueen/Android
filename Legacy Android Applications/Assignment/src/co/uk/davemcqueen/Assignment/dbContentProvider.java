/*
 * David McQueen
 * 10153465@stu.mmu.ac.uk
 */
package co.uk.davemcqueen.Assignment;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class dbContentProvider extends ContentProvider {
/*
 * Content provider for the application
 * Provides internal & external access to the database
 */
	
	//Set the authority and content URI
	public static final String AUTHORITY = "content://co.uk.davemcqueen.Assignment.provider";
	public static final Uri CONTENT_URI = Uri.parse(AUTHORITY);
	DBHelper mDBHelper;
	SQLiteDatabase db;
	
	@Override
	public boolean onCreate() {
		mDBHelper = new DBHelper(getContext());
		return true; //successfully created
	}
	
	
	//Determines if asking for a specific item, or a directory
	@Override
	public String getType(Uri uri) {
		if (uri.getLastPathSegment() == null)
		{
			return "vnd.andoid.cursor.item/vnd.co.uk.davemcqueen.status";
		}else {
			return "vnd.andoid.cursor.dir/vnd.co.uk.davemcqueen.status";
		}
	}
	
	//Insert the passed in values into the DB
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		db = mDBHelper.getWritableDatabase();
		long id = db.insert(Event.EventItem.TABLE_NAME, null, values);
		
		//if it was successful, return the position of the new event
		if (id != -1){
			uri = Uri.withAppendedPath(uri, Long.toString(id));
		}
		return uri;
	}

	//Performs a query using the projection, selection and selectionArguments passed in from the calling method
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		// Constructs a new query builder and sets its table name
	       SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
	       qb.setTables(Event.EventItem.TABLE_NAME);
	       SQLiteDatabase db = mDBHelper.getReadableDatabase();
	       return qb.query(db, projection, selection, selectionArgs, null, null, null);
		
	}
	
	
	//Performs an update on the relevant entry using the information passed in from the calling method.
	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		SQLiteDatabase db = mDBHelper.getWritableDatabase();
		return db.update(Event.EventItem.TABLE_NAME, values, selection, selectionArgs);
	}	
	
	
	
	//Deletes the entry relevant to the infromation passed from the calling method.
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = mDBHelper.getWritableDatabase();
		return db.delete(Event.EventItem.TABLE_NAME, selection, selectionArgs);
	}
}
