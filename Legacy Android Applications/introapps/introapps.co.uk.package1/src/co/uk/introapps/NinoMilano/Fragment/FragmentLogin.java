package co.uk.introapps.NinoMilano.Fragment;

import co.uk.introapps.NinoMilano.MainActivity;
import co.uk.introapps.NinoMilano.R;
import co.uk.introapps.NinoMilano.Background.Encrypt_Decrypt;
import co.uk.introapps.NinoMilano.R.id;
import co.uk.introapps.NinoMilano.R.layout;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FragmentLogin extends Fragment{

public static final String ARG_SECTION_NUMBER = "section_number";
private static final String TAG = "Nino-FragmentLogin";
public static final String NINO_PREFS = "NinoPrefs";
	
	public FragmentLogin(){
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView;
		
			rootView = inflater.inflate(R.layout.loyalty_login,
					container, false);
			Log.i(TAG, "LoginFragment");
			MainActivity activity = (MainActivity) getActivity();
			
			SharedPreferences preferences = activity.getSharedPreferences(NINO_PREFS, 0);
			
			boolean loggedIn = preferences.getBoolean("loggedIn", false);
			String userName = preferences.getString("userName", "");
			
			loggedIn = true;
			
			
			//If the user is logged in, then display the loyalty card
			if(loggedIn){
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.replace(R.id.loyaltyFragment_placeholder, new FragmentDisplayCard());
				ft.commit();
			}
			
			
			
			Button btnLogin = (Button) rootView.findViewById(R.id.btnlogin_loginButton);
			Button btnSignup = (Button) rootView.findViewById(R.id.btnLogin_signupButton);
			final EditText email = (EditText) rootView.findViewById(R.id.loginEmail);
			EditText password = (EditText) rootView.findViewById(R.id.loginPassword);
			
			
			/*When the user clicks to login:
				- Encrypt their password
				- Encrypt their email
				- Pass this to the server
				- Wait for a response from the server, possibly spinner on the UI thread?
				- on success, display the users loyalty card - and store their ID and loggedIn status
				- on fail, display fail message and keep on this screen
			 */
			btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(TAG, "Login Button Clicked");
				
				String dataToEncryptDecrypt = email.getText().toString();
			    String encryptionDecryptionKey = "1234567812345678";
			    String ivs = "12345678";

			    byte[] encryptedData = Encrypt_Decrypt.encrypt(dataToEncryptDecrypt.getBytes(), encryptionDecryptionKey.getBytes(),
			            ivs.getBytes());
			    Log.i(TAG, new String(encryptedData));
			    
			    // here you will get the encrypted bytes. Now you can use Base64 encoding on these bytes, before sending to your web-service

			    byte[] decryptedData = Encrypt_Decrypt.decrypt(encryptedData, encryptionDecryptionKey.getBytes(), ivs.getBytes());
			    System.out.println(new String(decryptedData));
			    Log.i(TAG, new String(decryptedData));
			}
		});
			
			btnSignup.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Log.i(TAG, "Signup Button Clicked");
					FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.loyaltyFragment_placeholder, new FragmentSignup());
                    ft.addToBackStack("signupTransaction");
                    ft.commit();
				}
			});
			
			return rootView;
	}

}
