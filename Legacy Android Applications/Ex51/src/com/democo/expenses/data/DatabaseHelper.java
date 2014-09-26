package com.democo.expenses.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Provides support for using the SQLite database. Will create the 
 * database if they have not yet been created.
 *  
 * @author Development Team
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "expenses.db";	

	private static final int DATABASE_VERSION = 1;

	/** Define a static String containing the CREATE statement to execute */
    private static final String EXPENSES_TABLE_CREATE =
                "CREATE TABLE " + Expense.ExpenseItem.TABLE_NAME + " (" +
                Expense.ExpenseItem.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," + 
                Expense.ExpenseItem.COLUMN_NAME_DESCRIPTION + " TEXT, " +
                Expense.ExpenseItem.COLUMN_NAME_AMOUNT + " REAL, " + 
                Expense.ExpenseItem.COLUMN_NAME_EXPENSE_DATE + " REAL " +
                ");";
    
    public DatabaseHelper(Context context) {
    	// When calling super: if the 2nd parameter is null then the database is held in memory -- this form creates a file backed database
    	super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
    
    /**
     * Alternative constructor for test mode
     * @param context
     * @param testMode state of flag is irrelevant. The presence of the 2nd argument causes the in-memory db to be created
     */
    public DatabaseHelper(Context context, boolean testMode) {
    	// If the 2nd parameter is null then the database is held in memory -- this form creates an in memory database
    	super(context, null, null, DATABASE_VERSION);
	}    
    
    /**
     * Called when the database is created for the first time. 
     * This is where the creation of tables and the initial population of the tables should happen
     */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// Run the query defined above to initialize the database.
		db.execSQL(EXPENSES_TABLE_CREATE);
	}

	/**
	 * Use this method to perform a controlled update of the database when the
	 * version changes. Typically adding new columns to the database but the possibilites
	 * are endless...
	 * @param db - instance of the database
	 * @param oldVersion - the old version id
	 * @param newVersion - the new version id
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
