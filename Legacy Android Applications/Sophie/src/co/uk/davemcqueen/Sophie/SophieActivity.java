package co.uk.davemcqueen.Sophie;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;



public class SophieActivity extends Activity {
	public static int topImage = 0;
	public static int bottomImage = 0;

	
    /** Called when the activity is first created. */
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
    	   	
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //locks the screen to portrait
        
      //create you ImageView :
        ImageView btnImageTOP = (ImageView) findViewById(R.id.imageViewTop);
        ImageView btnImageBOTTOM = (ImageView) findViewById(R.id.imageViewBottom);
        
       
        
        //--- The click listener for the top image---
        btnImageTOP.setOnClickListener(new View.OnClickListener() {
        	
        	//--- opens up the gallery for the top images---
        	public void onClick(View v) { 
        		startActivity(new Intent("co.uk.davemcqueen.Sophie.topgallery"));
        		
         	}
        });
        
        
        //---The click listener for the bottom image
        btnImageBOTTOM.setOnClickListener(new View.OnClickListener() {
        	
        	//---Opens up the gallery for the bottom images---
        	public void onClick(View v) { 
        		startActivity(new Intent("co.uk.davemcqueen.Sophie.bottomgallery"));
        			
         	}
        });
        
       }
    
     @Override
    protected void onResume(){
    	
    	 ImageView imageTop = (ImageView)findViewById(R.id.imageViewTop);
       	 Integer[] topPics = {
       	    		R.drawable.sample_0,
       	    		R.drawable.sample_1,
       	    		R.drawable.sample_2,
       	    		R.drawable.sample_3,
       	    		R.drawable.sample_4,
       	    		R.drawable.sample_5,
       	    		R.drawable.sample_6,
       	    		R.drawable.sample_7,
       	    };
       	 imageTop.setImageResource(topPics[topImage]);
       	 
       	ImageView imageBottom = (ImageView)findViewById(R.id.imageViewBottom);
    	 Integer[] bottomPics = {
    	    		R.drawable.sample_0,
    	    		R.drawable.sample_1,
    	    		R.drawable.sample_2,
    	    		R.drawable.sample_3,
    	    		R.drawable.sample_4,
    	    		R.drawable.sample_5,
    	    		R.drawable.sample_6,
    	    		R.drawable.sample_7,
    	    };
    	 imageBottom.setImageResource(bottomPics[bottomImage]);
    	super.onResume();
    }
    
}
