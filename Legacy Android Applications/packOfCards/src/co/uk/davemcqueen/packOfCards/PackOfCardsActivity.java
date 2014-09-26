package co.uk.davemcqueen.packOfCards;

import java.util.Random;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.content.pm.ActivityInfo;



public class PackOfCardsActivity extends Activity {
    //---Variables to hold the information necessary to run the application---
	String cardsDrawn = "";
	static int [] intCardsDrawn = new int[52];
	int amountCardsDrawn = 0;
	boolean cardDuplicate = false;
	int pictureForCard;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //locks the screen to portrait
        
        final Random myRandom = new Random(); //A random number generator  
        
        //makes the image on the main screen 'click-able'
        ImageView btnOpen = (ImageView) findViewById(R.id.imgCard);
        btnOpen.setOnClickListener(new View.OnClickListener() {
        	
        	public void onClick(View v) { 
        		//---Checks if the card has already been selected---
        		cardDuplicate = true;
        		if (amountCardsDrawn < 52)
        		{
        		do
        		{
        		int randomCard = myRandom.nextInt(52)+1;
        		for (int x = 0; x <52; x++)
        		{
        			if (intCardsDrawn[x] == randomCard)
        			{
        				cardDuplicate = true;
        				break;
        			}
        			else if (x == 51)
        			{
        				cardDuplicate = false;
        				break;
        			}
        		}
        		
        		if (cardDuplicate == false){
        			//---If the card hasn't been selected already
        			//plays the card, keeps a record and shows the picture---
    				intCardsDrawn[amountCardsDrawn] = randomCard;
    				amountCardsDrawn++;        				
    				cardsDrawn += ("\n" + String.valueOf(randomCard));
    				pictureForCard = (randomCard);
        		}
        		} while (cardDuplicate == true);
        		
        		//---Displays the image relevant to the card generated---
        		ImageView image = (ImageView)findViewById(R.id.imgCard);
        		switch (pictureForCard){
        		case 1: 
        			try {
        				image.setImageResource(R.drawable.s1);   
        			} catch (Exception e){
        				e.printStackTrace();
        			}
        				break;
        		case 2: image.setImageResource(R.drawable.s2);
        				break;
        		case 3: image.setImageResource(R.drawable.s3);
        				break;
        		case 4: image.setImageResource(R.drawable.s4);
        				break;
        		case 5: image.setImageResource(R.drawable.s5);
        				break;
        		case 6: image.setImageResource(R.drawable.s6);
        				break;
        		case 7: image.setImageResource(R.drawable.s7);
        				break;
        		case 8: image.setImageResource(R.drawable.s8);
        				break;
        		case 9: image.setImageResource(R.drawable.s9);
        				break;
        		case 10: image.setImageResource(R.drawable.s10);
        				break;
        		case 11: image.setImageResource(R.drawable.s11);
        				break;
        		case 12: image.setImageResource(R.drawable.s12);
        				break;
        		case 13: image.setImageResource(R.drawable.s13);
        				break;
        		case 14: image.setImageResource(R.drawable.d1);
        				break;
        		case 15: image.setImageResource(R.drawable.d2);
        				break;
        		case 16: image.setImageResource(R.drawable.d3);
        				break;
        		case 17: image.setImageResource(R.drawable.d4);
        				break;
        		case 18: image.setImageResource(R.drawable.d5);
        				break;
        		case 19: image.setImageResource(R.drawable.d6);
        				break;
        		case 20: image.setImageResource(R.drawable.d7);
        				break;
        		case 21: image.setImageResource(R.drawable.d8);
        				break;
        		case 22: image.setImageResource(R.drawable.d9);
        				break;
        		case 23: image.setImageResource(R.drawable.d10);
        				break;
        		case 24: image.setImageResource(R.drawable.d11);
        				break;
        		case 25: image.setImageResource(R.drawable.d12);
        				break;
        		case 26: image.setImageResource(R.drawable.d13);
        				break;
        		case 27: image.setImageResource(R.drawable.h1);
        				break;
        		case 28: image.setImageResource(R.drawable.h2);
        				break;
        		case 29: image.setImageResource(R.drawable.h3);
        				break;
        		case 30: image.setImageResource(R.drawable.h4);
        				break;
        		case 31: image.setImageResource(R.drawable.h5);
        				break;
        		case 32: image.setImageResource(R.drawable.h6);
        				break;
        		case 33: image.setImageResource(R.drawable.h7);
        				break;
        		case 34: image.setImageResource(R.drawable.h8);
        				break;
        		case 35: image.setImageResource(R.drawable.h9);
        				break;
        		case 36: image.setImageResource(R.drawable.h10);
        				break;
        		case 37: image.setImageResource(R.drawable.h11);
        				break;
        		case 38: image.setImageResource(R.drawable.h12);
        				break;
        		case 39: image.setImageResource(R.drawable.h13);
        				break;
        		case 40: image.setImageResource(R.drawable.c1);
        				break;
        		case 41: image.setImageResource(R.drawable.c2);
        				break;
        		case 42: image.setImageResource(R.drawable.c3);
        				break;
        		case 43: image.setImageResource(R.drawable.c4);
        				break;
        		case 44: image.setImageResource(R.drawable.c5);
        				break;
        		case 45: image.setImageResource(R.drawable.c6);
        				break;
        		case 46: image.setImageResource(R.drawable.c7);
        				break;
        		case 47: image.setImageResource(R.drawable.c8);
        				break;
        		case 48: image.setImageResource(R.drawable.c9);
        				break;
        		case 49: image.setImageResource(R.drawable.c10);
        				break;
        		case 50: image.setImageResource(R.drawable.c11);
        				break;
        		case 51: image.setImageResource(R.drawable.c12);
        				break;
        		case 52: image.setImageResource(R.drawable.c13);
						break;
        		}
        		
          		}
        		else {
        			//---If all the cards have been played
        			DisplayPopUP("End of deck!");
        			ImageView image = (ImageView)findViewById(R.id.imgCard);
        			image.setImageResource(R.drawable.cardback);
        			
        		}
        	}
        });
    }
    
    
    //---Displays a pop up menu with the text that is passed to it---
    private void DisplayPopUP(String msg)
    {
    	Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }
    
    //---Function used to create a menu---
    public void CreateMenu(Menu menu)
    {
    	MenuItem resetDeck = menu.add(0, 0, 0, "Reset");
    	MenuItem viewDrawnCads = menu.add(0,1,1, "View drawn cards");
    	//MenuItem options = menu.add(0,2,1, "Options");
    	
    }
    
    //---Processes the menu to be displayed, this is on the 'menu' button---
    private boolean MenuChoice(MenuItem item)
    {
    	switch (item.getItemId()) {
    	case 0:
    		//Reset the deck to 0 cards drawn
    			ImageView image = (ImageView)findViewById(R.id.imgCard);
    			image.setImageResource(R.drawable.cardback);
    			amountCardsDrawn = 0;
    			cardDuplicate = false;
    			for (int i = 0; i < 52; i++) //ensures that the array is blank or cards played
    			{
    			intCardsDrawn[i] = 0;
    			}
    			DisplayPopUP("Deck Reset!");
    			return true;
    	case 1:
    		//view the cards already drawn
    		startActivity(new Intent("co.uk.davemcqueen.PackOfCards.cardsDrawn"));
    			return true;
    	}
    	return false;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	CreateMenu(menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	return MenuChoice(item);
    }
}