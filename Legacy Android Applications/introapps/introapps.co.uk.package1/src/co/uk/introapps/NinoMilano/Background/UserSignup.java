package co.uk.introapps.NinoMilano.Background;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class UserSignup extends AsyncTask<JSONObject, Void, Void>{

	private static final String TAG = "Nino-ExtConnections";
	
	JSONObject newUser;
	
	
	public UserSignup(JSONObject newUser){
		super();
		this.newUser = newUser;
	}

	protected Void doInBackground(JSONObject... args) {
		Log.i(TAG, "Start Async");
		String signupSrc = "http://www.introapps.co.uk/service/rest/nino/signup";
		String dataToEncryptDecrypt;
		String encryptionDecryptionKey;
		String ivs;
		
		try{
			dataToEncryptDecrypt = newUser.toString();
		    encryptionDecryptionKey = "1ntr04pp54351280";
		    ivs = "12345678";
		    byte[] encryptedData = Encrypt_Decrypt.encrypt(dataToEncryptDecrypt.getBytes(), encryptionDecryptionKey.getBytes(),
		            ivs.getBytes());
		    Log.i(TAG, new String(encryptedData));
		    
		    //signupSrc += new String(encryptedData);
		    
		    Log.i(TAG, "URL: " + signupSrc);
		    
		    URL url = new URL(signupSrc);
	        URLConnection urlc = url.openConnection();
	        BufferedReader bfr = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
	        String line;
	        while((line = bfr.readLine()) != null)
	        {
	        	Log.i(TAG, "Inside Response: "+String.valueOf(line));
	        }
	        
		}catch(Exception e){
			Log.i(TAG, "Exception");
			e.printStackTrace();
		}
		return null;
	}
	
    protected void onPostExecute(Long result) {
        //TODO Trigger an event so that we know the update has completed.
       }


}
