package co.uk.introapps.NinoMilano.Background;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import co.uk.introapps.NiniMilano.Database.CategoryChild;
import co.uk.introapps.NiniMilano.Database.CategoryFilter;
import co.uk.introapps.NiniMilano.Database.CategoryParent;
import co.uk.introapps.NiniMilano.Database.Treatment;
import co.uk.introapps.NiniMilano.Database.dbContentProvider;
import co.uk.introapps.NinoMilano.MainActivity;
import co.uk.introapps.NinoMilano.R;
import co.uk.introapps.NinoMilano.Fragment.FragmentPrices;
import co.uk.introapps.NinoMilano.Model.PricesCategory;
import co.uk.introapps.NinoMilano.Model.PricesAdapter;
import co.uk.introapps.NinoMilano.Model.PricesItem;


/*
 * This class populates the main prices list
 * First. It checks if the DB needs to be updated
 * If it does then it will perform this
 * Second, it will take all the data and put this into an expandable list view
 * Once the list view has been created, it will hide the progressBar spinner and show the list 
 *
 *
 *TODO if there is no date to put into a list, display a message.
 */
public class DisplayPrices extends AsyncTask<Void, Void, Void> {
	
	private static final String TAG = "Nino-Async Display Prices";
	
	private final Context mContext;
	private View mRootView;
	private Activity mActivity;
	private List<PricesCategory> catList;
	private static final String NINO_PREFS = "NinoPrefs";
	
	public DisplayPrices(Context context, View rootView, Activity activity){
		super();
		this.mContext = context;
		this.mRootView = rootView;
		this.mActivity = activity;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		Log.i(TAG, "doInBackground");
		//If the DB needs updating, do that. Else finish
		if (DBNeedsUpdating()){
			
			updateTreatments();
			updateCategoryParent();
			updateCategoryChild();
		}
		return null;
	}
	
	protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(Void result) {
     //TODO Trigger an event so that we know the update has completed.
    	Log.i(TAG, "onPostExecute");
    	showLoading(false);
    }
	
	public void updateTreatments(){
		Log.i(TAG, "Update Treatment Method");
		
		ContentValues inputValuesTreatment = new ContentValues();
		
		//JSON call to the data service. This is currently running in he main thread and needs putting into an async class
		String dateCreated;
		String name;
		double price;
		int treatmentID;
		int categoryID;
		String treatmentSrc="http://www.introapps.co.uk/Service/rest/Nino/Treatments/";
		boolean truncated = false;
		
	    try{
	    	
	    	
	        URL url = new URL(treatmentSrc);
	        URLConnection urlc = url.openConnection();
	        BufferedReader bfr = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
	        String line;
	        while((line = bfr.readLine()) != null)
	        {
	        	if (!truncated){
	        		//Delete all the previous data. This is getting replaced with the data from the data service
	    	    	this.mContext.getContentResolver().delete(dbContentProvider.CONTENT_URI_TREATMENT, null, null);
	    	    	Log.i(TAG, "Treatment Table Truncated");
	    	    	truncated = true;
	        	}
	        JSONArray jsa = new JSONArray(line);
	        for(int i=0;i<jsa.length();i++)
	           {
	        		inputValuesTreatment.clear();
	        		JSONObject jo = (JSONObject)jsa.get(i);
	        		
	        		dateCreated = jo.getString("DateCreated"); 
	        		name = jo.getString("Name");
	        		price = jo.getDouble("Price");
	        		treatmentID = jo.getInt("TreatmentID");
	        		categoryID = jo.getInt("ChildCategoryID");
	        		
	        		inputValuesTreatment.put(Treatment.TreatmentItem.TREATMENT_ID, treatmentID);
	        		inputValuesTreatment.put(Treatment.TreatmentItem.NAME, name);
	        		inputValuesTreatment.put(Treatment.TreatmentItem.PRICE, price);
	        		inputValuesTreatment.put(Treatment.TreatmentItem.DATE_CREATED, dateCreated);
	        		inputValuesTreatment.put(Treatment.TreatmentItem.CATEGORY_ID, categoryID);
	        		
	        		//Insert into the database
	        		this.mContext.getContentResolver().insert(dbContentProvider.CONTENT_URI_TREATMENT, inputValuesTreatment);
	           }
	        }
	    }
	    catch(Exception e){
	    	Log.w(TAG, "Error: " + e);
	    }
	    //End of the JSON call to web service 
	    
	    Log.i(TAG, "Updated Treatment");
	}
	
	public void updateCategoryParent(){
		Log.i(TAG, "Update Category-Parent Method");
	
		ContentValues inputValuesCategoryParent = new ContentValues();
		
		//JSON call to the data service. This is currently running in he main thread and needs putting into an async class
		String dateCreated;
		String name;
		int categoryID;
		boolean truncated = false;
		String categoryParentSrc="http://introapps.co.uk/service/rest/nino/GetParentCategories/";
        String line;
		
	    try{
	    	
	    	
	        URL url = new URL(categoryParentSrc);
	        URLConnection urlc = url.openConnection();
	        BufferedReader bfr = new BufferedReader(new InputStreamReader(urlc.getInputStream()));

	        while((line = bfr.readLine()) != null)
	        {
	        	if (!truncated){
	        		//Delete all the previous data. This is getting replaced with the data from the data service
	        		//only want to truncated the DB once. If the JSON has new data. Before new data is input.
	        		this.mContext.getContentResolver().delete(dbContentProvider.CONTENT_URI_CATEGORY_PARENT, null, null);
	    	    	Log.i(TAG, "Category-Parent Table Truncated");
	    	    	truncated = true;
	        	}
	        	
	        JSONArray jsa = new JSONArray(line);
	        for(int i=0;i<jsa.length();i++)
	           {
	        	inputValuesCategoryParent.clear();
	        		JSONObject jo = (JSONObject)jsa.get(i);
	        			dateCreated = jo.getString("DateCreated"); 
	        			name = jo.getString("Name");
	        			categoryID = jo.getInt("CategoryID");
	        		
	        		inputValuesCategoryParent.put(CategoryParent.CategoryParentItem.CATEGORY_ID, categoryID);
	        		inputValuesCategoryParent.put(CategoryParent.CategoryParentItem.NAME, name);
	        		inputValuesCategoryParent.put(CategoryParent.CategoryParentItem.DATE_CREATED, dateCreated);
	        		
	        		//Insert into the database
	        		this.mContext.getContentResolver().insert(dbContentProvider.CONTENT_URI_CATEGORY_PARENT,
	        				inputValuesCategoryParent);
	           }
	        }
	    }
	    catch(Exception e){
	    	Log.w(TAG, "Error-Parent: " + e);
	    }
	    //End of the JSON call to web service 
	  
	    Log.i(TAG, "Updated Category-Parent");
	}
	
	public void updateCategoryChild(){
		Log.i(TAG, "Update Category-Child Method");
		ContentValues inputValuesCategoryChild = new ContentValues();
		
		//JSON call to the data service. This is currently running in he main thread and needs putting into an async class
		String dateCreated;
		String name;
		int categoryID;
		int parentID;
		String imageURL;
		boolean truncated = false;
		
		String categoryChildSrc="http://introapps.co.uk/service/rest/nino/GetChildCategories/";
		
	    try{
	        URL url = new URL(categoryChildSrc);
	        URLConnection urlc = url.openConnection();
	        BufferedReader bfr = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
	        String line;
	        while((line = bfr.readLine()) != null)
	        {
	        	//Delete all the previous data. This is getting replaced with the data from the data service
	        	//only want to truncated the DB once. If the JSON has new data. Before new data is input.
	        	if (!truncated){
	        		this.mContext.getContentResolver().delete(dbContentProvider.CONTENT_URI_CATEGORY_CHILD, null, null);
	    	    	Log.i(TAG, "Category-Child Table Truncated");
	    	    	truncated = true;
	        	}
	        	
	        JSONArray jsa = new JSONArray(line);
	        for(int i=0;i<jsa.length();i++)
	           {
	        	inputValuesCategoryChild.clear();
	        		JSONObject jo = (JSONObject)jsa.get(i);
	        			dateCreated = jo.getString("DateCreated"); 
	        			name = jo.getString("Name");
	        			categoryID = jo.getInt("CategoryID");
	        			parentID = jo.getInt("ParentCategoryID");
	        			imageURL = jo.getString("ImageURL");
	        		
	        		inputValuesCategoryChild.put(CategoryChild.CategoryChildItem.CATEGORY_ID, categoryID);
	        		inputValuesCategoryChild.put(CategoryChild.CategoryChildItem.PARENT_ID, parentID);
	        		inputValuesCategoryChild.put(CategoryChild.CategoryChildItem.NAME, name);
	        		inputValuesCategoryChild.put(CategoryChild.CategoryChildItem.IMAGE_URL, imageURL);
	        		inputValuesCategoryChild.put(CategoryChild.CategoryChildItem.DATE_CREATED, dateCreated);
	        		
	        		//Insert into the database
	        		this.mContext.getContentResolver().insert(dbContentProvider.CONTENT_URI_CATEGORY_CHILD,
	        				inputValuesCategoryChild);
	           }
	        }
	    }
	    catch(Exception e){
	    	Log.w(TAG, "Error-Child: " + e);
	    }
	    //End of the JSON call to web service
	    
	    Log.i(TAG, "Updated Category-Child");
	}
	
	public void updateCategoryFilter(){
		Log.i(TAG, "Update Category-Filter Method");
		ContentValues inputValuesCategoryFilter = new ContentValues();
		
		//JSON call to the data service. This is currently running in he main thread and needs putting into an async class
		int filterID;
		int parentID;
		int childID;
		int treatmentID;
		String categoryFilterSrc="";
		boolean truncated = false;
		
	    try{
	    	//Delete all the previous data. This is getting replaced with the data from the data service
	    	
	    	
	        URL url = new URL(categoryFilterSrc);
	        URLConnection urlc = url.openConnection();
	        BufferedReader bfr = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
	        String line;
	        while((line = bfr.readLine()) != null)
	        {
	        	//only want to truncated the DB once. If the JSON has new data. Before new data is input.
	        	if (!truncated){
	        		this.mContext.getContentResolver().delete(dbContentProvider.CONTENT_URI_CATEGORY_FILTER, null, null);
	    	    	Log.i(TAG, "Category-Filter Table Truncated");
	        	}
	        JSONArray jsa = new JSONArray(line);
	        for(int i=0;i<jsa.length();i++)
	           {
	        	inputValuesCategoryFilter.clear();
	        		JSONObject jo = (JSONObject)jsa.get(i);
	        			filterID = jo.getInt("CategoryLinkID"); 
	        			parentID = jo.getInt("ParentCategoryID");
	        			childID = jo.getInt("ChildCategoryID");
	        			treatmentID = jo.getInt("TreatmentID");
	        		
	        		inputValuesCategoryFilter.put(CategoryFilter.CategoryFilterItem.FILTER_ID, filterID);
	        		inputValuesCategoryFilter.put(CategoryFilter.CategoryFilterItem.TREATMENT_ID, treatmentID);
	        		inputValuesCategoryFilter.put(CategoryFilter.CategoryFilterItem.PARENT_ID, parentID);
	        		inputValuesCategoryFilter.put(CategoryFilter.CategoryFilterItem.CHILD_ID, childID);
	        		
	        		//Insert into the database
	        		this.mContext.getContentResolver().insert(dbContentProvider.CONTENT_URI_CATEGORY_FILTER,
	        				inputValuesCategoryFilter);
	           }
	        }
	    }
	    catch(Exception e){
	    	Log.w(TAG, "Error-Filter: " + e);
	    }
	    //End of the JSON call to web service
	    
	    Log.i(TAG, "Updated Category-Filter");
	}
	
    private boolean DBNeedsUpdating(){
    	//Checks if the DB should be updated
    	
    	boolean result;
    	
		Calendar calendar = Calendar.getInstance();
		
		//Get the shared preferences
		SharedPreferences preferences = mActivity.getSharedPreferences(NINO_PREFS, 0);
		boolean previouslyUpdatedPrices = preferences.getBoolean("previouslyUpdatedPrices", false);
		boolean forceUpdate = preferences.getBoolean("forceUpdate", false);
		int updatedDay = preferences.getInt("updatedDay", 1);
		int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
		
		//Check to see if there is data in the DB
		String[] projection = {Treatment.TreatmentItem.TREATMENT_ID};
		Cursor c = mActivity.getContentResolver().query(dbContentProvider.CONTENT_URI_TREATMENT, projection, null, null, Treatment.TreatmentItem.DEFAULT_SORT_ORDER);

		//TODO Forceupdated used for debugging
		forceUpdate = false;
		
		Log.i(TAG, "Checking if the DB should be updated");
		if(!previouslyUpdatedPrices //If it hasn't been previously updated.
				||(dayOfYear - 7) >= updatedDay //Or if the previously updated day was 7 days ago or more
				|| forceUpdate //Or if the user wants to force an update.
				|| c.getCount() < 1) // Or if the treatments table is empty
		{
			
			//After we have updated, save the new preferences
			SharedPreferences.Editor preferencesEditor = preferences.edit();
			preferencesEditor.putBoolean("previouslyUpdatedPrices", true);
			preferencesEditor.putInt("updatedDay", dayOfYear);
			preferencesEditor.putBoolean("forceUpdate", false);
			
			preferencesEditor.commit();
			
			result =  true;
		}else{
			result =  false;
		}
		
    	c.close();
    	return result;
    }
    
    
    private void showLoading(boolean toggle){
		ExpandableListView exList = (ExpandableListView) mRootView.findViewById(R.id.expandableListView1);
		ProgressBar progress = (ProgressBar) mRootView.findViewById(R.id.progressPrices);
		
		if(toggle){
			progress.setVisibility(View.VISIBLE);
			exList.setVisibility(View.INVISIBLE);
		}else{
			populateData();
			progress.setVisibility(View.INVISIBLE);
			exList.setVisibility(View.VISIBLE);
		}
		
	}
    
    private void populateData(){
		initData();
		ExpandableListView exList = (ExpandableListView) mRootView.findViewById(R.id.expandableListView1);
		PricesAdapter exAdpt = new PricesAdapter(catList, mContext);
        exList.setIndicatorBounds(exList.getRight() -20, exList.getWidth());
        exList.setAdapter(exAdpt);
	}
    
    private void initData() {

    	catList = new ArrayList<PricesCategory>();
    	
    	//Get all the ID's for the parent categories
    	String parentQuery = "SELECT P." + CategoryParent.CategoryParentItem._ID
    			+ ", P." + CategoryParent.CategoryParentItem.NAME
    			+ " FROM " + CategoryParent.CategoryParentItem.TABLE_NAME
    			+ " P ORDER BY P." + CategoryParent.CategoryParentItem._ID;
    	Cursor parentCursor = dbContentProvider.getData(parentQuery, null);
    	
    	int categoriesFound = parentCursor.getCount();
    	
    	Log.i(TAG,(String.valueOf(categoriesFound) + " parent categories found"));
    	
    	//For each of the categories found, get all of its treatments and build up a list.
    	for (int i = 1; i <= categoriesFound; i++){
    		
    		//Query to get all of the info for each treatment
	    	String treatmentQuery = "SELECT C." + CategoryChild.CategoryChildItem.NAME
					+ ", T." + Treatment.TreatmentItem.NAME
					+ ", T." + Treatment.TreatmentItem.PRICE
					+ ", C." + CategoryChild.CategoryChildItem.IMAGE_URL
					+ " FROM " + CategoryChild.CategoryChildItem.TABLE_NAME
					+ " C INNER JOIN " + Treatment.TreatmentItem.TABLE_NAME
						+ " T ON T." + Treatment.TreatmentItem.CATEGORY_ID
						+ " = C." + CategoryChild.CategoryChildItem.CATEGORY_ID
					+ " WHERE " + CategoryChild.CategoryChildItem.PARENT_ID + " = ? "
					+ " GROUP BY C." + CategoryChild.CategoryChildItem.NAME
					+ ", T." + Treatment.TreatmentItem.NAME
					+ ", T." + Treatment.TreatmentItem.PRICE
					+ " ORDER BY C." + CategoryChild.CategoryChildItem.NAME;
	    	
	    	//The parent category ID
			String[] SelectionArgs = {
					String.valueOf(i)
			};
	
			//Create a cursor for each treatment under the parent category
			Cursor treatmentCursor = dbContentProvider.getData(treatmentQuery, SelectionArgs);
			
			//Move the cursor along
			if (parentCursor.moveToNext()){
				PricesCategory cat = createCategory(parentCursor.getString(1), "Ladies Haircuts", 1);
				List<PricesItem> ListOfItems = new ArrayList<PricesItem>();
				
				while (treatmentCursor.moveToNext()){
					PricesItem item = new PricesItem(
								1, //ID
								0, //Image ID
								treatmentCursor.getString(1), //Name
								treatmentCursor.getString(0), //Description
								"�" + treatmentCursor.getString(2), //price
								treatmentCursor.getString(3) //Image URL
							);
					ListOfItems.add(item);
				}
				cat.setItemList(ListOfItems);
				catList.add(cat);
			}
			
			treatmentCursor.close();
    	}
    	
    	parentCursor.close();
    	
    }
	
	private PricesCategory createCategory(String name, String descr, long id) {
    	return new PricesCategory(id, name, descr);
    }

}
