package co.uk.davemcqueen.Sophie;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

public class bottomgallery extends Activity {
    Integer[] pics = {
    		R.drawable.sample_0,
    		R.drawable.sample_1,
    		R.drawable.sample_2,
    		R.drawable.sample_3,
    		R.drawable.sample_4,
    		R.drawable.sample_5,
    		R.drawable.sample_6,
    		R.drawable.sample_7,
    };
    Integer selectedImage = 0;
    ImageView imageView;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottomimagegallery);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //locks the screen to portrait
        
        Gallery ga = (Gallery)findViewById(R.id.GalleryBottom);
        ga.setAdapter(new ImageAdapter(this));
        
        imageView = (ImageView)findViewById(R.id.ImageViewBottom);
        imageView.setImageResource(pics[co.uk.davemcqueen.Sophie.SophieActivity.bottomImage]);
        ga.setOnItemClickListener(new OnItemClickListener() {
       
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				imageView.setImageResource(pics[arg2]);
				
				selectedImage = arg2;
			}
        	
        });
        
        Button btnSelect;
        btnSelect = (Button) findViewById(R.id.btnSelectBottomImage);
        btnSelect.setOnClickListener(new View.OnClickListener()
            {
            	public void onClick(View v)
            	{
            		co.uk.davemcqueen.Sophie.SophieActivity.bottomImage = selectedImage;
            		finish();
            	}
        });
    }
       
    
    public class ImageAdapter extends BaseAdapter {

    	private Context ctx;
    	int imageBackground;
    	
    	public ImageAdapter(Context c) {
			ctx = c;
			TypedArray ta = obtainStyledAttributes(R.styleable.Gallery1);
			imageBackground = ta.getResourceId(R.styleable.Gallery1_android_galleryItemBackground, 1);
			ta.recycle();
		}

		public int getCount() {
    		
    		return pics.length;
    	}

    	public Object getItem(int arg0) {
    		
    		return arg0;
    	}

    	public long getItemId(int arg0) {
    		
    		return arg0;
    	}

    	public View getView(int arg0, View arg1, ViewGroup arg2) {
    		ImageView iv = new ImageView(ctx);
    		iv.setImageResource(pics[arg0]);
    		iv.setScaleType(ImageView.ScaleType.FIT_XY);
    		iv.setLayoutParams(new Gallery.LayoutParams(150,120));
    		iv.setBackgroundResource(imageBackground);
    		return iv;
    	}

    }
}