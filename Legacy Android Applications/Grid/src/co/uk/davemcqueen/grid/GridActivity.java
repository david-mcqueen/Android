package co.uk.davemcqueen.grid;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class GridActivity extends Activity {
	
	//---The images to display---
	Integer[] imageIDs = {
			R.drawable.pic1,
			R.drawable.pic2,
			R.drawable.pic3,
			R.drawable.pic4
	};
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter(this));
        
        gridView.setOnItemClickListener(new OnItemClickListener()
        {
        	public void onItemClick(AdapterView<?> parent,
        			View v, int position, long id)
        	{
        		Toast.makeText(getBaseContext(),
        				"pic " + (position + 1) + " selected",
        				Toast.LENGTH_SHORT).show();
        	}

        });
        
    }
    
    
    public class ImageAdapter extends BaseAdapter
    {
    	private Context context;
    	
    	public ImageAdapter(Context c)
    	{
    		context = c;
    	}
    	
    	//---Returns the number of images---
    	public int getCount()
    	{
    		return imageIDs.length;
    	}
    	
    	//---returns the ID of an item
    	public Object getItem(int position)
    	{
    		return position;
    	}
    	
    	//---returns he ID of an item
    	public long getItemId(int position)
    	{
    		return position;
    	}
    	
    	//---returns an ImageView view---
    	public View getView(int position, View convertView, ViewGroup parent)
    	{
    		ImageView imageView;
    		if (convertView == null)
    			{
    			imageView = new ImageView(context);
    			imageView.setLayoutParams(new
    					GridView.LayoutParams(85, 85));
    			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    			imageView.setPadding(5, 5, 5, 5);
    			} else {
    				imageView = (ImageView) convertView;
    			}
    		imageView.setImageResource(imageIDs[position]);
    		return imageView;
    	}
    }
}