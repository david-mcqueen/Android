package co.uk.davemcqueen.AR;

import co.uk.davemcqueen.AR.ARActivity.CustomCameraView;
import android.app.Activity;
import android.content.Context;
import android.graphics.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;

public class ARActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	try {
        super.onCreate(savedInstanceState);
        CustomCameraView cv = new CustomCameraView(this.getApplicationContext());
        FrameLayout r1 = new FrameLayout(this.getApplicationContext());
        setContentView(r1);
        r1.addView(cv);
        
    	} catch(Exception e) {}
    }
    
    public class CustomCameraView extends SurfaceView
    {
    	Camera camera;
    	SurfaceHolder previewHolder;
    	public CustomCameraView(Context context) {
			super(context);
		}
		
    	
    }
}
