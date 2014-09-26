package co.uk.introapps.NinoMilano.Fragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.uk.introapps.NinoMilano.R;
import co.uk.introapps.NinoMilano.Background.UserSignup;
import co.uk.introapps.NinoMilano.R.id;
import co.uk.introapps.NinoMilano.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FragmentSignup extends Fragment{

public static final String ARG_SECTION_NUMBER = "section_number";
private static final String TAG = "Nino-FragmentSignup";
	
	public FragmentSignup(){
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView;
		
			rootView = inflater.inflate(R.layout.loyalty_signup,
					container, false);
			Log.i(TAG, "SignupFragment");
			
			Button btnSignup = (Button) rootView.findViewById(R.id.btnSignup);
			final EditText txtFirstName = (EditText) rootView.findViewById(R.id.signupFirstname);
			final EditText txtSurName = (EditText) rootView.findViewById(R.id.signupSurname);
			final EditText txtEmail = (EditText) rootView.findViewById(R.id.signupEmail);
			final EditText txtPassword = (EditText) rootView.findViewById(R.id.signupPassword);
			final EditText txtPasswordCheck = (EditText) rootView.findViewById(R.id.signupPasswordCheck);
			
			//Process the users signup
			btnSignup.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					String firstName;
					String surName;
					String email;
					String password;
					String passwordCheck;
					JSONObject newUser = new JSONObject();
					
					Log.i(TAG, "Signup Button Clicked");
					//If all of the fields have been filled in
					firstName = txtFirstName.getText().toString();
					surName = txtSurName.getText().toString();
					email = txtEmail.getText().toString();
					password = txtPassword.getText().toString();
					passwordCheck = txtPasswordCheck.getText().toString();
					
					//Only do this if the passwords match
					if(firstName.length() > 0
							&& surName.length() > 0
							&& email.length() > 0
							&& password.length() > 0
							&& passwordCheck.length() > 0){
						try{
							newUser.put("firstname", firstName);
							newUser.put("surname", surName);
							newUser.put("email", email);
							newUser.put("password", password);			
						}catch(JSONException e){
							e.printStackTrace();
						}
						Log.i(TAG, "Calling Async");
						new UserSignup(newUser).execute();
						
						
					}else{
						//Display message to the user
					}
					
//					
//					FragmentTransaction ft = getFragmentManager().beginTransaction();
//					ft.replace(R.id.loyaltyFragment_placeholder, new FragmentDisplayCard());
//					ft.commit();
				}
			});
			
			
			return rootView;
	}

}
