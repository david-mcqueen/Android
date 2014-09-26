package com.democo.expenses;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class ExpensesListActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
       
    }//End of onCreate
    

	private void addExpenseItem() {
		Intent i = new Intent(getBaseContext(), ExpenseEntryActivity.class);
		startActivity(i);
	}//End of addExpenseItem
	
	private void startSync()
	{
		Intent i = new Intent(getBaseContext(), SyncService.class);
		startService(i);
		//Toast t = Toast.makeText(this, "syncStarted", Toast.LENGTH_LONG);
		//t.show();
	}//End of startSync


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	MenuInflater inflater = getMenuInflater();
	inflater.inflate(R.menu.option_menu, menu);
	return true;
	}//End of onCreateOptionsMenu


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.menu_sync:
				startSync();
				return true;
			case R.id.menu_add:
				addExpenseItem();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}//End of switch (item.getItemId())
		
	}//End of onOptionsItemSelected
	
}//End of Activity