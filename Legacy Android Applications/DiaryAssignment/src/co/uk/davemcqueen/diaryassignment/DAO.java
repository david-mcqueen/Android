package co.uk.davemcqueen.diaryassignment;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;



public class DAO {

	private static final String TAG="DAO";
	private DBHelperNew mDbHelper;
	
	public DAO(Context context) {
		// TODO 1 Create an instance of the Database helper
		// Hint: note that the DatabaseHelper constructor takes a context
		 mDbHelper = new DBHelperNew(context);
	}
	
protected DAO(Context context, boolean testMode) {
		
		if(testMode){
			mDbHelper = new DBHelperNew(context, testMode);
		} else {
			mDbHelper = new DBHelperNew(context);
		}
	}	
	
public Cursor queryEvents(String[] projection, String selection, String[] selectionArgs)
{
	SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
	qb.setTables(DBHelperNew.TABLE_EVENTS);
	SQLiteDatabase db = mDbHelper.getReadableDatabase();
	
	
	
	// TODO  5 Complete the query command below.
	// The parameters you need have been passed in to this method
		
       Cursor c = qb.query(
	           db,            // The database to query
	           projection,     // The columns to return from the query (the projection)
	           selection,     // The columns for the where clause
	           selectionArgs, 	  // The values for the where clause
	           null,          // don't group the rows
	           null,          // don't filter by row groups
	           "date"        // The sort order
	       );
	return c;
}
	
	public DBHelperNew getDbHelperForTest() {
    return mDbHelper;
}    

}
