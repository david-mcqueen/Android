package co.uk.introapps.NinoMilano.Fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import co.uk.introapps.NinoMilano.MainActivity;
import co.uk.introapps.NinoMilano.R;
import co.uk.introapps.NinoMilano.Background.DisplayOffers;
import co.uk.introapps.NinoMilano.Background.DisplayPrices;
import co.uk.introapps.NinoMilano.Model.RewardItem;
import co.uk.introapps.NinoMilano.Model.RewardAdapter;

public class FragmentDisplayCard extends Fragment{

public static final String ARG_SECTION_NUMBER = "section_number";
private static final String TAG = "Nino-FragmentSignup";
	
	public FragmentDisplayCard(){
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView;
		
			rootView = inflater.inflate(R.layout.loyalty_display_card,
					container, false);
			Log.i(TAG, "DisplayCardFragment");
			
			MainActivity activity = (MainActivity) getActivity();
			
			TextView currentBalance = (TextView) rootView.findViewById(R.id.textCurrentBalance);
			TextView loyaltyID = (TextView) rootView.findViewById(R.id.textLoyaltyID);
			
			currentBalance.setText("0 points");
			loyaltyID.setText("DM0001NM");
			
			
			//Call the async method that will populate & display the offers.
			new DisplayOffers(activity.getBaseContext(), rootView, activity).execute();
			
			
			return rootView;
	}
	
	

}
