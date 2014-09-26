package co.uk.introapps.NinoMilano.Fragment;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import co.uk.introapps.NinoMilano.R;
import co.uk.introapps.NinoMilano.R.drawable;
import co.uk.introapps.NinoMilano.R.id;
import co.uk.introapps.NinoMilano.R.layout;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

public class FragmentHome extends Fragment{
	
	
	public static final String ARG_SECTION_NUMBER = "section_number";
	private static final String TAG = "Nino-Fragment Home";
	private UiLifecycleHelper uiHelper;
	public static View rootView;
	private boolean pendingPublishReauthorization;
	
	// Your Facebook APP ID
    private static String APP_ID = "289228091233524"; // Replace your App ID here
 
	
	public FragmentHome(){
	}
	
	@SuppressWarnings("deprecation")
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
			 rootView = inflater.inflate(R.layout.fragment_home, container, false);	
			 Session session = Session.getActiveSession();
			
			ImageButton btnCall = (ImageButton) rootView.findViewById(R.id.btnCall);
			ImageButton btnEmail = (ImageButton) rootView.findViewById(R.id.btnEmail);
			ImageButton btnFacebook = (ImageButton) rootView.findViewById(R.id.btnFacebookShare);
			
			//Facebook login/logout button
			LoginButton authButton = (LoginButton) rootView.findViewById(R.id.authButton);
			authButton.setFragment(this);
			authButton.setPublishPermissions(Arrays.asList("user_likes", "user_status", "publish_actions"));
			authButton.setBackgroundResource(R.drawable.fb_grey);
			authButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
			
			//if the user is logged in
			if (session != null && session.isOpened()){
				isFacebookLoggedIn(true);
			}else{
				isFacebookLoggedIn(false);
			}
			
			btnCall.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent callIntent = new Intent(Intent.ACTION_DIAL);
					callIntent.setData(Uri.parse("tel:07712454780"));
					startActivity(callIntent);
				}
			});
		
			btnEmail.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
				            "mailto","abc@gmail.com", null));
				emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Nino Milano App");
				emailIntent.putExtra(Intent.EXTRA_TEXT, "kittens");
				startActivity(Intent.createChooser(emailIntent, "Send email..."));
				}
			});
			
			//Button to share to facebook. This is hidden when not logged in
			btnFacebook.setOnClickListener(new View.OnClickListener(){
				public void onClick(View v){
					if (isLoggedIn()){
						//publishStory();
						displayToast("share");
					}
				}
			});
			
			return rootView;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    uiHelper.onCreate(savedInstanceState);
	}

	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    if (state.isOpened()) {
	        Log.i(TAG, "Logged in...");
	        isFacebookLoggedIn(true);
	    } else if (state.isClosed()) {
	        Log.i(TAG, "Logged out...");
	        isFacebookLoggedIn(false);
	    }
	}
	
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
	
	@Override
	public void onResume() {
	    super.onResume();
	    
	    // For scenarios where the main activity is launched and user
	    // session is not null, the session state change notification
	    // may not be triggered. Trigger it if it's open/closed.
	    Session session = Session.getActiveSession();
	    if (session != null &&
	           (session.isOpened() || session.isClosed()) ) {
	        onSessionStateChange(session, session.getState(), null);
	    }

	    uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}
	
	public void isFacebookLoggedIn(boolean toggle){
		ImageButton btnFacebook = (ImageButton) rootView.findViewById(R.id.btnFacebookShare);
		LoginButton authButton = (LoginButton) rootView.findViewById(R.id.authButton);
		
		if (toggle){
			//show the facebook share button, and hide the login / logout button
			btnFacebook.setVisibility(View.VISIBLE);
			authButton.setVisibility(View.GONE);
		}else{
			btnFacebook.setVisibility(View.GONE);
			authButton.setVisibility(View.VISIBLE);
		}
	}
	
	private void publishStory() {
		Session session = Session.getActiveSession();

		if (session != null){

		    // Check for publish permissions    
		    List<String> permissions = session.getPermissions();
		    if (!isSubsetOf(permissions, permissions)) {
		        pendingPublishReauthorization = true;
		        Session.NewPermissionsRequest newPermissionsRequest = new Session
		                .NewPermissionsRequest(this, permissions);
		    session.requestNewPublishPermissions(newPermissionsRequest);
		        return;
		    }

		    Bundle postParams = new Bundle();
		    postParams.putString("name", "Facebook SDK for Android");
		    postParams.putString("caption", "Build great social apps and get more installs.");
		    postParams.putString("description", "The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.");
		    postParams.putString("link", "https://developers.facebook.com/android");
		    postParams.putString("picture", "https://raw.github.com/fbsamples/ios-3.x-howtos/master/Images/iossdk_logo.png");

		    Request.Callback callback= new Request.Callback() {
		        public void onCompleted(Response response) {
		            JSONObject graphResponse = response
		                                       .getGraphObject()
		                                       .getInnerJSONObject();
		            String postId = null;
		            try {
		                postId = graphResponse.getString("id");
		            } catch (JSONException e) {
		                Log.i(TAG,
		                    "JSON error "+ e.getMessage());
		            }
		            FacebookRequestError error = response.getError();
		            if (error != null) {
		                Toast.makeText(getActivity()
		                     .getApplicationContext(),
		                     error.getErrorMessage(),
		                     Toast.LENGTH_SHORT).show();
		                } else {
		                    Toast.makeText(getActivity()
		                         .getApplicationContext(), 
		                         postId,
		                         Toast.LENGTH_LONG).show();
		            }
		        }
		    };

		    Request request = new Request(session, "me/feed", postParams, 
		                          HttpMethod.POST, callback);

		    RequestAsyncTask task = new RequestAsyncTask(request);
		    task.execute();
		}
	}


		private boolean isSubsetOf(Collection<String> subset, Collection<String> superset) {
		    for (String string : subset) {
		        if (!superset.contains(string)) {
		            return false;
		        }
		    }
		    return true;
		}
		
		//Checks if the user is logged into facebook.
		public boolean isLoggedIn() {
		    Session session = Session.getActiveSession();
		    if (session != null && session.isOpened()) {
		        return true;
		    } else {
		        return false;
		    }
		}
		
		public void displayToast (String message){
			Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
		}
}
