package co.uk.davemcqueen.Emails;


import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;

public class EmailsActivity extends Activity {
	Button btnSendSleep;
	Button btnSendShut_Down;
	Button btnSendTakePhoto;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btnSendSleep = (Button) findViewById(R.id.btnSendSleep);
        btnSendSleep.setOnClickListener(new View.OnClickListener()
        {
        	public void onClick(View v)
        	{
        		String[] to = {"control@davemcqueen.co.uk"};
        		String[] cc = {""};
        		sendEmail(to, cc, "*#sleep#*", ".");
        	}
        });
        
        btnSendShut_Down = (Button) findViewById(R.id.btnSendShut_Down);
        btnSendShut_Down.setOnClickListener(new View.OnClickListener()
            {
            	public void onClick(View v)
            	{
            		String[] to = {"control@davemcqueen.co.uk"};
            		String[] cc = {""};
            		sendEmail(to, cc, "*#shutDown#*", ".");
            	}
        });
        
        btnSendTakePhoto = (Button) findViewById(R.id.btnSendTakePhoto);
        btnSendTakePhoto.setOnClickListener(new View.OnClickListener()
            {
            	public void onClick(View v)
            	{
            		String[] to = {"control@davemcqueen.co.uk"};
            		String[] cc = {""};
            		sendEmail(to, cc, "*#takePicture#*", ".");
            	}
        });
        
    }
    //---Sends an SMS message to another device---
    private void sendEmail(String[] emailAddresses, String[] carbonCopies,
    		String subject, String message)
    {
    	Intent emailIntent = new Intent(Intent.ACTION_SEND);
    	emailIntent.setData(Uri.parse("mailto:"));
    	String[] to = emailAddresses;
    	String[] cc = carbonCopies;
    	emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
    	emailIntent.putExtra(Intent.EXTRA_CC, cc);
    	emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
    	emailIntent.putExtra(Intent.EXTRA_TEXT, message);
    	emailIntent.setType("message/rfc822");
    	startActivity(Intent.createChooser(emailIntent,  "Email"));
    }
}