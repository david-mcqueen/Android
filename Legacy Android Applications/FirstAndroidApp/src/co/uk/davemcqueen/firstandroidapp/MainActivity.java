package co.uk.davemcqueen.firstandroidapp;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import com.facebook.*;
import com.facebook.model.*;
import android.widget.TextView;
import android.content.Intent;

public class MainActivity extends FragmentActivity {

private MainFragment mainFragment;
	
public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	
	if (savedInstanceState == null)
	{
		mainFragment = new MainFragment();
		getSupportFragmentManager().beginTransaction().add(android.R.id.content, mainFragment).commit();
	}else {
		mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(android.R.id.content);
	}
}
}
