package com.democo.expenses;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

// TODO 1 Change the base class to be ListActivity
// Fix imports as required
public class ExpensesListActivity extends ListActivity {
	
	// TODO 2  Add new fields here
	private ArrayAdapter<String> mExpensesAdapter;
	private ArrayList<String> mExpensesArrayList;
	
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO 3 Remove the next line as ListActivity sets up the Content View automatically
      
        // TODO 4 call getExpensesArrayList() here
        // (It's a member of this class)
       mExpensesArrayList = getExpensesArrayList();
       
        // TODO 5 Create an ArrayAdapter<String> and assign it to mExpensesAdapter
        // Parameters for the constructor: 
        //	 this – the Context, 
        //   R.layout.expenses_list_item – the ID of the layout, 
        //   mExpensesArrayList – the ArrayList of dummy data
        
        mExpensesAdapter = new ArrayAdapter(this, R.layout.expenses_list_item, mExpensesArrayList);
        
        // TODO 6 Call setListAdapter() passing the newly created ArrayAdapter as the adapter to use
        setListAdapter(mExpensesAdapter);
        
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
	
	
	private void addExpenseItem() {
		
  	  Intent intent = new Intent(this, ExpenseEntryActivity.class);
  	  startActivity(intent);
	  	  

	}	
	private void startSync()
	{
		Toast toast = Toast.makeText(this, "startSync", Toast.LENGTH_LONG);
		toast.show();		

    	Intent startSyncService = new Intent(this, 
				SyncService.class);
    	startService(startSyncService);		
		
	}
	private ArrayList<String> getExpensesArrayList(){
//		Create an ArrayList of strings using the dummy data defined
//      at the end of this class  
		ArrayList<String> expensesArrayList = new ArrayList<String>();
		expensesArrayList.addAll(Arrays.asList(EXPENSES));  
		return expensesArrayList;
		
	}
	
	
	// Dummy data
	static final String[] EXPENSES = new String[] {"Trip to NYC on QM2", "Venice on Orient Express", "Beijing on Trans-Sibera Express"};



	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		String expense = mExpensesArrayList.get((int)id);
		Intent i = new Intent(this, ExpenseEntryActivity.class);
		i.putExtra(Constants.EXPENSE_DESCRIPTION, expense);
		startActivity(i);
	}	
}