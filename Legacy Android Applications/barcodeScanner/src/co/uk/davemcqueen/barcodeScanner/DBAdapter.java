package co.uk.davemcqueen.barcodeScanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "product_name";
    public static final String KEY_BARCODE = "product_barcode";
    public static final String KEY_ALLERGYS = "product_allergys";
    public static final String KEY_INGREDIANTS = "product_ingrediants";
    private static final String TAG = "DBAdapter";
    
    private static final String DATABASE_NAME = "mydb";
    private static final String DATABASE_TABLE = "products";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
        "create table "+ DATABASE_TABLE + "(_id integer primary key autoincrement, " +
        KEY_NAME + " text not null, " +
        KEY_BARCODE+ " integer unique," +
        KEY_ALLERGYS+ " product_allergys text," +
        KEY_INGREDIANTS+ " product_ingrediants text not null);";
        
    private final Context context;    

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) 
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
        
    private static class DatabaseHelper extends SQLiteOpenHelper 
    {
        DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) 
        {
        	try {
        		db.execSQL(DATABASE_CREATE);	
        	} catch (SQLException e) {
        		e.printStackTrace();
        	}
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS products");
            onCreate(db);
        }
    }    

    //---opens the database---
    public DBAdapter open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---    
    public void close() 
    {
        DBHelper.close();
    }
    
    //---insert a contact into the database---
    public long insertProduct(String name, String ingrediants, String barcode, String allergys) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_BARCODE, barcode);
        initialValues.put(KEY_ALLERGYS, allergys);
        initialValues.put(KEY_INGREDIANTS, ingrediants);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---retrieves a particular product---
    public Cursor getProduct(String contents) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {
                KEY_NAME, KEY_ALLERGYS, KEY_INGREDIANTS}, KEY_BARCODE + "=" + contents, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
}
