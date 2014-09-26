/*
 * David McQueen
 * 10153465@stu.mmu.ac.uk
 */
package co.uk.davemcqueen.Assignment;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class Map extends MapActivity {
/*
 * Displays the events location on a map
 * 
 */
	double lat;
	double lng;
	GeoPoint point;
	
	
	MapView mapView;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.map_layout);
		mapView = (MapView) findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true); //activates zoom control in the map.
		final Bundle extras = getIntent().getExtras();
		
		lat = extras.getDouble("lat");//retrieves the location coordinates of the event.
		lng = extras.getDouble("lng");
		
		MapController controller = mapView.getController();
		point = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));//Sets the point to be that of the coordinates passed in.
		//Define the map center and  the zoom level
		controller.setCenter(point);
		controller.animateTo(point);
		controller.setZoom(16);
		
		
		//Add a location marker overlay
		MapOverlay mapOverlay = new MapOverlay();
		List<Overlay> listOfOverlays = mapView.getOverlays();
		listOfOverlays.clear();
		listOfOverlays.add(mapOverlay);
	}
	
	class MapOverlay extends com.google.android.maps.Overlay
	{
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when)
		{
			super.draw(canvas, mapView, shadow);
			//translate the location to screen pixels
			Point screenPts = new Point();
			mapView.getProjection().toPixels(point, screenPts);
			
			//add the marker
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.flag_orange);
			canvas.drawBitmap(bmp, screenPts.x-16, screenPts.y-16, null);
			//the size of the image is 16, so remove to allow for more accurate placement
			return true;
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
