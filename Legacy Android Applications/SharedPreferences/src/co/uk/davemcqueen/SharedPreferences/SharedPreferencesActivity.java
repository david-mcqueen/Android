package co.uk.davemcqueen.SharedPreferences;

import android.app.Activity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class SharedPreferencesActivity extends Activity {
	private SharedPreferences prefs;
	private String prefName = "MyPrefs";
	private EditText editText;
	private SeekBar seekBar;
	private Button btn;
	
	private static final String FONT_SIZE_KEY = "fontsize";
	private static final String TEXT_VALUE_KEY = "textValue";
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        editText = (EditText) findViewById(R.id.EditText01);
        seekBar = (SeekBar) findViewById(R.id.SeekBar01);
        
        btn = (Button) findViewById(R.id.btnSave);
        
        	//---Runs when the button has been pressed---
        	btn.setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
        			//---get the sharedPreferences object---
        			prefs = getSharedPreferences(prefName, MODE_PRIVATE);
        			SharedPreferences.Editor editor = prefs.edit();
        		
        			//---Saves the values in the EditText view to preferences---
        			editor.putFloat(FONT_SIZE_KEY,  editText.getTextSize());
        			editor.putString(TEXT_VALUE_KEY,  editText.getText().toString());
        		
        			//--saves the values---
        			editor.commit();
        		
        			//---display file saved message---
        			Toast.makeText(getBaseContext(),
        				"Font size saved successfully!",
        				Toast.LENGTH_SHORT).show();
        		
        		}
        	});
        
        //---load the shared prferences---
        prefs = getSharedPreferences(prefName, MODE_PRIVATE);
        
        //---set the textview font size to the previously saved values---
        float fontSize = prefs.getFloat(FONT_SIZE_KEY,  12);
        
        //---init the seekBar and editText---
        seekBar.setProgress((int) fontSize);
        editText.setText(prefs.getString(TEXT_VALUE_KEY, ""));
        editText.setTextSize(seekBar.getProgress());
        
        
        //---Runs when the seekBar changes---
        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				//---change the font size of the editText---
				editText.setTextSize(progress);
				
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
        });
        
    }
}