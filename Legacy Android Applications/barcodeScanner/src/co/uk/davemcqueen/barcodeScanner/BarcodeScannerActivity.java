package co.uk.davemcqueen.barcodeScanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import android.content.SharedPreferences;

public class BarcodeScannerActivity extends Activity {
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        try {
        	String destPath = "/data/data/" + getPackageName() +
        			"/databases/mydb";
        	File f = new File(destPath);
        	if (!f.exists()) {
        		DisplayPopUP("Copying Database");
        		CopyDB(getBaseContext().getAssets().open("mydb"), new FileOutputStream(destPath));
        	}
        	DisplayPopUP("database found");
        } catch(FileNotFoundException e) {
        	e.printStackTrace();
        } catch(IOException e) {
        	e.printStackTrace();
        }
        
        DBAdapter db = new DBAdapter(this);
        
        
        /*
        //---add a product---
        db.open();
        long barcode= 9781118017111L;
        long id = db.insertProduct("android_book", "paper", barcode , "none");
        db.close();
        */
        
        final IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.showDownloadDialog();
        Button btnScan = (Button) findViewById(R.id.btnScan);
        btnScan.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		
        		integrator.initiateScan();
           		 Intent intent = new Intent ("com.google.zxing.client.android.SCAN");
        		 intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
        		 startActivityForResult(intent, 0);
        	}
        });
        
    } 

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	
    	
    	//IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
    	if (resultCode == RESULT_OK) {
    		String contents = intent.getStringExtra("SCAN_RESULT");
			String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
			DBAdapter db = new DBAdapter(this);
			
			//---Handle successful scan---
			long productToGet = Long.parseLong(contents);
			DisplayPopUP(contents);
			
			db.open();
	        Cursor c = db.getProduct(contents);
	        if (c.moveToFirst())
	        {
	        	DisplayProduct(c);
	        } else
	        {
	        	DisplayPopUP("No product Found");
	        	
	        	db.insertProduct("hob nobs", "biscuits", contents, "good for vegetarians");
	        	DisplayPopUP("new product inserted");
	        }
	        db.close();
	       
	        
			//DisplayPopUP(contents);
			//DisplayPopUP(format);
			
	       
			//startActivity(new Intent("co.uk.davemcqueen.barcodeScanner.display_product"));
			
			
    	}else if (resultCode == RESULT_CANCELED){
    		
    	}
    }
    
       
    //---Displays a pop up menu with the text that is passed to it---
    private void DisplayPopUP(String msg)
    {
    	Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }
    
    public void DisplayProduct(Cursor c)
    {
    	Toast.makeText(this,
    			"Name: " + c.getString(0) + "\n" +
    			"Allergys: " + c.getString(1) + "\n" +
    			"Ingrediants: " + c.getString(2),
    			Toast.LENGTH_LONG).show();
    }
    
    public void CopyDB(InputStream inputStream, OutputStream outputStream) throws IOException
    {
    	//---copy 1K bytes at a time---
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = inputStream.read(buffer)) > 0) {
    		outputStream.write(buffer, 0, length);
    	}
    	inputStream.close();
    	outputStream.close();
    }
}