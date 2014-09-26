package com.democo.expenses;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.democo.expenses.data.DAO;
import com.democo.expenses.data.Expense;


public class ExpensesListActivity extends ListActivity {
	@SuppressWarnings("unused")
	private static final String TAG = "ExpenseListActivity";
	private DAO dao;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO 10 Create an instance of the DAO
		// Hint: use "this" as the Context parameter
        // dao = 
        
        // TODO 11 Call the queryExpenses method on the DAO you just created.
        // The first parameter is an array representing the columns you wish to be returned
        // Use Expense.ExpenseItem.LIST_PROJECTION for this
        // We want to return all the expenses so selection and selectionArgs can both be null
		
        // Cursor cursor = dao.
        
		// The names of the cursor columns to display in the view, initialized
		// to the description column
        // TODO Note the creation of these two arrays specifying the columns to be displayed        
        // and the resource ID's to use to display them
        // android.R.id.text1 is a built in resource to show simple text
        String[] dataColumns = { Expense.ExpenseItem.COLUMN_NAME_DESCRIPTION } ;
        int[] viewIDs = { android.R.id.text1 };

        
        // TODO Create a new SimpleCursorAdapter
        // Creates the backing adapter for the ListView.
/*        ___________ adapter
            = new ___________(
                      this,                             // The Context for the ListView
                      R.layout.expenses_list_item,      // Points to the XML for a list item
                      ___________,                      // The cursor to get items from
                      ___________,						// The array of Strings holding the names of the data columns to display	
                      ___________						// The array of resource ids used to display the data 
              );
*/        
        // TODO 14 set the new adapter as the list adapter
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.option_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_sync:
			startSync();
			return true;
		case R.id.menu_add:
			addExpenseItem();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(this, ExpenseEntryActivity.class);	
		//  Fetch the cursor from the list element and then get the description from there
		Cursor cursor = (Cursor)l.getAdapter().getItem(position);
		// Pass the description as Extra data with the Intent to simulate behavior from last exercise
		intent.putExtra(Constants.EXPENSE_DESCRIPTION, cursor.getString(1)); 
		startActivity(intent);
		
	}	
	

	private void addExpenseItem() {

		Intent intent = new Intent(this, ExpenseEntryActivity.class);
		startActivity(intent);

	}

	private void startSync() {
		Toast toast = Toast.makeText(this, "startSync", Toast.LENGTH_LONG);
		toast.show();

		Intent startSyncService = new Intent(this, SyncService.class);
		startService(startSyncService);

	}

// Leave this alone until the end of the exercise	
//  @Override
//  public boolean onContextItemSelected(MenuItem item) {
//  	
//      // The data from the menu item.
//      AdapterView.AdapterContextMenuInfo info;
//
//      /*
//       * Gets the extra info from the menu item. When an expense in the Expense list is long-pressed, a
//       * context menu appears. The menu items for the menu automatically get the data
//       * associated with the note that was long-pressed. The data comes from the provider that
//       * backs the list.
//       *
//       */
//      try {
//          // Casts the data object in the item into the type for AdapterView objects.
//          info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//      } catch (ClassCastException e) {
//
//          // If the object can't be cast, logs an error
//          Log.e(TAG, "bad menuInfo", e);
//
//          // Triggers default processing of the menu item.
//          return false;
//      }
//      
//      Log.i(TAG, "ID to delete=" + info.id);
//	      switch (item.getItemId()) {
//	      case R.id.delete:        
//	    	  mDao.deleteExpensesById((int)info.id);
//	    	  	// Note, cursor.requery is actually deprecated.
//	    	    // We are using it here as a quick and dirty intermediate solution before we add a ContentProvider to the solution
//	    	 ((SimpleCursorAdapter)this.getListAdapter()).getCursor().requery();
//	        return true;
//	      default:
//	        return super.onContextItemSelected(item);
//	      }
//  }		

}