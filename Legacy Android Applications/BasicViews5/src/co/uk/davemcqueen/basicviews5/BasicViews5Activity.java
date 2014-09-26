package co.uk.davemcqueen.basicviews5;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class BasicViews5Activity extends ListActivity {
	String[] presidents;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //---no need to call this---
        //setContentView(R.layout.main);
        
        ListView lstView = getListView();
        //lstView.setChoiceMode(0); //CHOICE_MORE_NONE
        //lstView.setChoiceMode(1); //CHOICE_MORE_SINGLE
        lstView.setChoiceMode(2); //CHOICE_MORE_MULTI
        lstView.setTextFilterEnabled(true);
        //---could also write this above as:
        // lstView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        
        presidents = 
        		getResources().getStringArray(R.array.presidents_array);
        
        setListAdapter(new ArrayAdapter<String>(this, 
        		android.R.layout.simple_list_item_checked, presidents));
        
    }
    
    public void onListItemClick(
    		ListView parent, View v, int position, long id)
    {
    	//---toggle the check display next to the item---
    	parent.setItemChecked(position,  parent.isItemChecked(position));
    	Toast.makeText(this,
    			"You have selected " + presidents[position],
    			Toast.LENGTH_SHORT).show();
    }
    
    
}