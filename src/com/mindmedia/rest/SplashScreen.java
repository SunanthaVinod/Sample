package com.mindmedia.rest;


import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class SplashScreen extends Activity {

	private final int DISPLAY_LENGTH = 5000; // splash screen duration 
	private ProgressBar mProgressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		mProgressBar=(ProgressBar)findViewById(R.id.xPbSplash);
		// setting progress bar 
		mProgressBar.setVisibility(ProgressBar.VISIBLE);

		// created handler for waiting 
		// post delayed to wait some time before going to next intenet

		new Handler().postDelayed(new Runnable() {
		@Override
		public void run() {
		Intent login = new Intent(SplashScreen.this, SearchRestaurants.class);

		SplashScreen.this.startActivity(login);
		mProgressBar.setVisibility(ProgressBar.INVISIBLE);
		SplashScreen.this.finish();
		}

		}, DISPLAY_LENGTH);
		}

	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
