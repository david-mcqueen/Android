package co.uk.davemcqueen.barcodeScanner;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class display_product extends Activity {
	

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_product);
		
	}
	
	public void populateFields (String name, String ingrediants, String allergys) {
		TextView updateAllergy  = (TextView)findViewById(R.id.productAllergy);
		updateAllergy.setText(allergys);
		
		TextView updateName  = (TextView)findViewById(R.id.productName);
		updateName.setText(name);
		
		TextView updateIngrediants  = (TextView)findViewById(R.id.productIngrediants);
		updateIngrediants.setText(ingrediants);
	}

}
