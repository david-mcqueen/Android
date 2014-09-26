package co.uk.introapps.NinoMilano.Background;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class DownloadImage extends AsyncTask<String, Void, Bitmap>{
	ImageView bmImage;
	
	protected void onPreExecute(){
		super.onPreExecute();
	}
	
	public DownloadImage(ImageView bmImage){
		this.bmImage = bmImage;
	}


	@Override
	protected Bitmap doInBackground(String... urls) {
		String urlDisplay = urls[0];
		Bitmap mIcon11 = null;
		
		try {
			InputStream in = new java.net.URL(urlDisplay).openStream();
			mIcon11 = BitmapFactory.decodeStream(in);
		}catch (Exception e){
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}
		
		return mIcon11;
	}

	protected void onPostExecute(Bitmap result){
		super.onPostExecute(result);
		bmImage.setImageBitmap(result);
	}
}
