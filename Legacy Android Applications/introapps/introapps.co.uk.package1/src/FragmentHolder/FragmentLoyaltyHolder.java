package FragmentHolder;

import co.uk.introapps.NinoMilano.MainActivity;
import co.uk.introapps.NinoMilano.R;
import co.uk.introapps.NinoMilano.Fragment.FragmentDisplayCard;
import co.uk.introapps.NinoMilano.Fragment.FragmentLogin;
import co.uk.introapps.NinoMilano.R.id;
import co.uk.introapps.NinoMilano.R.layout;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class FragmentLoyaltyHolder extends Fragment {

	public static final String ARG_SECTION_NUMBER = "section_number";
	private static final String TAG = "Nino-FragmentLoyalty";
	public static final String NINO_PREFS = "NinoPrefs";

	public FragmentLoyaltyHolder() {
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView;

		// --Load the main Fragment for Loyalty, which contains the replaceable
		// placeholder
		rootView = inflater.inflate(R.layout.holder_loyalty, container, false);

		// Get the shared Preferences
		MainActivity activity = (MainActivity) getActivity();
		SharedPreferences preferences = activity.getSharedPreferences(
				NINO_PREFS, 0);
		boolean loggedIn = preferences.getBoolean("userLoggedIn", false);
		String userName = preferences.getString("userName", "");

		// Initalise the Fragment Transaction Manager
		FragmentTransaction ft = getFragmentManager().beginTransaction();

		
		if (!loggedIn || userName == "") {
			// If the user isn't already logged in, display the login screen
			ft.replace(R.id.loyaltyFragment_placeholder, new FragmentLogin());
			ft.commit();
		}else{
			// ---They are signed in, display their loyaltyCard---
			ft.replace(R.id.loyaltyFragment_placeholder, new FragmentDisplayCard());
			ft.commit();
		}
		
		return rootView;
	}

}