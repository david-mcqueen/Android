package co.uk.davemcqueen.Databases;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import android.database.Cursor;

public class DatabasesActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        DBAdapter db = new DBAdapter(this);
        
        
        //--- add a contact---
        db.open();
        long id = db.insertContact("David McQueen", "david_mcqueen@hotmail.co.uk");
        id = db.insertContact("Mary Jackson", "mary@jackson.com");
        db.close();
        
        
        
        //---get all the contacts---
        db.open();
        Cursor c = db.getAllContacts();
        if (c.moveToFirst())
        {
        	do {
        		DisplayContact (c);
        	} while (c.moveToNext());
        }
        db.close();
    
    
        /*
        //---get a contact---
        db.open();
        Cursor c = db.getContact(2);
        if (c.moveToFirst())
        {
        	DisplayContact(c);
        } else
        {
        	Toast.makeText(this, "No contact found", Toast.LENGTH_LONG).show();
        }
        db.close();
        
        
        
        //---update contact---
        db.open();
        if (db.updateContact(1, "Sophie Mulqueen", "sophie@mulqueen.com"))
        {
        Toast.makeText(this, "update successful.", Toast.LENGTH_LONG).show();
        }
        else
        {
        	Toast.makeText(this, "Update failed!", Toast.LENGTH_LONG).show();
        }
        db.close();
        
        
        //---Delete a contact---
        db.open();
        if (db.deleteContact(1))
        {
        	Toast.makeText(this, "Delete successful.",
        			Toast.LENGTH_LONG).show();
        } else
        {
        	Toast.makeText(this, "Delete failed!", Toast.LENGTH_LONG).show();
        }
        */
    }
    
    
    
    public void DisplayContact(Cursor c)
    {
    	Toast.makeText(this,
    			"id: " + c.getString(0) + "\n" +
    			"Name: " + c.getString(1) + "\n" +
    			"Email: " + c.getString(2),
    			Toast.LENGTH_LONG).show();
    }
    
         	
}