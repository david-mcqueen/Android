package co.uk.davemcqueen.packOfCards;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

//---This code it run each time the user selects to view the cards played from the menu
//This calculates which has been played already, and how many are left to be played, and populates the table.

public class cardsDrawn extends Activity {

@Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.tablecardsdrawn);
	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	
	int intRemainingCards = 52; //The counter for how many cards have been played
	String strRemainingCards = ""; //Holds the above int as a string
	
	//---runs through each segment of the array, at each array position, it cycles through the cards
	//if a card is found. Then it is passed to the sub-function to populate the table
	//In short. Every card is checked at each array location.
	for (int arrayPosition = 0; arrayPosition < 52; arrayPosition++)
	{
		for (int cardNumber = 1; cardNumber <=52; cardNumber++)
				{
			if (co.uk.davemcqueen.packOfCards.PackOfCardsActivity.intCardsDrawn[arrayPosition] == cardNumber)
					{
					populateTable(cardNumber);
					intRemainingCards--; //counts down how many cards are left
					}
				}
		
	}
	strRemainingCards = Integer.toString(intRemainingCards); //converts the int to a string
	
	TextView update  = (TextView)findViewById(R.id.txtRemainingCards);
	update.setText(strRemainingCards); //updates the text field to show whats left
}
private void populateTable(int cardsToPopulate)
{
	switch (cardsToPopulate){
	case 1: 
		try {
			TextView update  = (TextView)findViewById(R.id.txt1);
			update.setText("¤");
		} catch (Exception e){
			e.printStackTrace();
		}
			break;
	case 2: 
			TextView update2  = (TextView)findViewById(R.id.txt2);
			update2.setText("¤");
			break;
	case 3: 
			TextView update3  = (TextView)findViewById(R.id.txt3);
			update3.setText("¤");
			break;
	case 4: 
			TextView update4  = (TextView)findViewById(R.id.txt4);
			update4.setText("¤");
			break;
	case 5: 
			TextView update5  = (TextView)findViewById(R.id.txt5);
			update5.setText("¤");
			break;
	case 6: 
			TextView update6  = (TextView)findViewById(R.id.txt6);
			update6.setText("¤");
			break;
	case 7: 
			TextView update7  = (TextView)findViewById(R.id.txt7);
			update7.setText("¤");
			break;
	case 8: 
			TextView update8  = (TextView)findViewById(R.id.txt8);
			update8.setText("¤");
			break;
	case 9: 
			TextView update9  = (TextView)findViewById(R.id.txt9);
			update9.setText("¤");
			break;
	case 10:
			TextView update10  = (TextView)findViewById(R.id.txt10);
			update10.setText("¤");
			break;
	case 11:
			TextView update11  = (TextView)findViewById(R.id.txt11);
			update11.setText("¤");
			break;
	case 12:
			TextView update12  = (TextView)findViewById(R.id.txt12);
			update12.setText("¤");
			break;
	case 13: 
			TextView update13  = (TextView)findViewById(R.id.txt13);
			update13.setText("¤");
			break;
	case 14:
			TextView update14  = (TextView)findViewById(R.id.txt14);
			update14.setText("¤");
			break;
	case 15:
			TextView update15  = (TextView)findViewById(R.id.txt15);
			update15.setText("¤");
			break;
	case 16:
			TextView update16  = (TextView)findViewById(R.id.txt16);
			update16.setText("¤");
			break;
	case 17:
			TextView update17  = (TextView)findViewById(R.id.txt17);
			update17.setText("¤");
			break;
	case 18:
			TextView update18  = (TextView)findViewById(R.id.txt18);
			update18.setText("¤");
			break;
	case 19:
			TextView update19  = (TextView)findViewById(R.id.txt19);
			update19.setText("¤");
			break;
	case 20:
			TextView update20  = (TextView)findViewById(R.id.txt20);
			update20.setText("¤");
			break;
	case 21:
			TextView update21  = (TextView)findViewById(R.id.txt21);
			update21.setText("¤");
			break;
	case 22:
			TextView update22  = (TextView)findViewById(R.id.txt22);
			update22.setText("¤");
			break;
	case 23:
			TextView update23  = (TextView)findViewById(R.id.txt23);
			update23.setText("¤");
			break;
	case 24:
			TextView update24  = (TextView)findViewById(R.id.txt24);
			update24.setText("¤");
			break;
	case 25:
			TextView update25  = (TextView)findViewById(R.id.txt25);
			update25.setText("¤");
			break;
	case 26:
			TextView update26  = (TextView)findViewById(R.id.txt26);
			update26.setText("¤");
			break;
	case 27:
			TextView update27  = (TextView)findViewById(R.id.txt27);
			update27.setText("¤");
			break;
	case 28:
			TextView update28  = (TextView)findViewById(R.id.txt28);
			update28.setText("¤");
			break;
	case 29:
			TextView update29  = (TextView)findViewById(R.id.txt29);
			update29.setText("¤");
			break;
	case 30:
			TextView update30  = (TextView)findViewById(R.id.txt30);
			update30.setText("¤");
			break;
	case 31:
			TextView update31  = (TextView)findViewById(R.id.txt31);
			update31.setText("¤");
			break;
	case 32:
			TextView update32  = (TextView)findViewById(R.id.txt32);
			update32.setText("¤");
			break;
	case 33:
			TextView update33  = (TextView)findViewById(R.id.txt33);
			update33.setText("¤");
			break;
	case 34:
			TextView update34  = (TextView)findViewById(R.id.txt34);
			update34.setText("¤");
			break;
	case 35:
			TextView update35  = (TextView)findViewById(R.id.txt35);
			update35.setText("¤");
			break;
	case 36:
			TextView update36  = (TextView)findViewById(R.id.txt36);
			update36.setText("¤");
			break;
	case 37:
			TextView update37  = (TextView)findViewById(R.id.txt37);
			update37.setText("¤");
			break;
	case 38:
			TextView update38  = (TextView)findViewById(R.id.txt38);
			update38.setText("¤");
			break;
	case 39:
			TextView update39  = (TextView)findViewById(R.id.txt39);
			update39.setText("¤");
			break;
	case 40:
			TextView update40  = (TextView)findViewById(R.id.txt40);
			update40.setText("¤");
			break;
	case 41:
			TextView update41  = (TextView)findViewById(R.id.txt41);
			update41.setText("¤");
			break;
	case 42:
			TextView update42  = (TextView)findViewById(R.id.txt42);
			update42.setText("¤");
			break;
	case 43:
			TextView update43  = (TextView)findViewById(R.id.txt43);
			update43.setText("¤");
			break;
	case 44:
			TextView update44  = (TextView)findViewById(R.id.txt44);
			update44.setText("¤");
			break;
	case 45:
			TextView update45  = (TextView)findViewById(R.id.txt45);
			update45.setText("¤");
			break;
	case 46:
			TextView update46  = (TextView)findViewById(R.id.txt46);
			update46.setText("¤");
			break;
	case 47:
			TextView update47  = (TextView)findViewById(R.id.txt47);
			update47.setText("¤");
			break;
	case 48:
			TextView update48  = (TextView)findViewById(R.id.txt48);
			update48.setText("¤");
			break;
	case 49:
			TextView update49  = (TextView)findViewById(R.id.txt49);
			update49.setText("¤");
			break;
	case 50:
			TextView update50  = (TextView)findViewById(R.id.txt50);
			update50.setText("¤");
			break;
	case 51:
			TextView update51  = (TextView)findViewById(R.id.txt51);
			update51.setText("¤");
			break;
	case 52:
			TextView update52  = (TextView)findViewById(R.id.txt52);
			update52.setText("¤");
			break;
	}
}
}
