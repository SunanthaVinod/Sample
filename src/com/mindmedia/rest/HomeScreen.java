package com.mindmedia.rest;


import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;


public class HomeScreen extends TabActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homescreen);

		Resources ressources = getResources(); 
		TabHost tabHost = getTabHost(); 
		
		// Search tab
		Intent intentAndroid = new Intent().setClass(this, SearchRestaurants.class);
		TabSpec tabSpecAndroid = tabHost
			.newTabSpec("Android")
			.setIndicator("", ressources.getDrawable(R.drawable.icon_android_config))
			.setContent(intentAndroid);

		// Add to Favorites tab
		Intent intentApple = new Intent().setClass(this, AddToFavorites.class);
		TabSpec tabSpecApple = tabHost
			.newTabSpec("Apple")
			.setIndicator("", ressources.getDrawable(R.drawable.icon_apple_config))
			.setContent(intentApple);
		
		// Top10 tab
		Intent intentWindows = new Intent().setClass(this, TopList.class);
		TabSpec tabSpecWindows = tabHost
			.newTabSpec("Windows")
			.setIndicator("", ressources.getDrawable(R.drawable.icon_windows_config))
			.setContent(intentWindows);
		
		
		// add all tabs 
		tabHost.addTab(tabSpecAndroid);
		tabHost.addTab(tabSpecApple);
		tabHost.addTab(tabSpecWindows);
		
		
		//set default (zero based)
		tabHost.setCurrentTab(0);
	}

}