package co.uk.introapps.NinoMilano.Fragment;

import co.uk.introapps.NinoMilano.R;
import co.uk.introapps.NinoMilano.R.layout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class FragmentTestimonials extends Fragment{
	
	
	public static final String ARG_SECTION_NUMBER = "section_number";
	
	public FragmentTestimonials(){
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView;
			rootView = inflater.inflate(R.layout.fragment_testimonials,
					container, false);
			return rootView;
	}
}