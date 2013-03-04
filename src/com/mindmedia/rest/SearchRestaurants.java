package com.mindmedia.rest;



import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;


public class SearchRestaurants extends Activity {

	private LocationManager locationman;
	private LocationListener locationlist;
	double final_latitude, final_longitude,final_altitude;
	String message;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchrestaurants);
		
		
		
		Button but=(Button)findViewById(R.id.find_hotels);
		Button search=(Button)findViewById(R.id.search_by_place);
		but.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(getBaseContext(),"flat:"+final_latitude+"flon:"+final_longitude, Toast.LENGTH_LONG).show();
					
					Intent i = new Intent(getApplicationContext(),
							LoadingPlaces.class);
					// Sending user current geo location
					i.putExtra("user_latitude", Double.toString(final_latitude));
					i.putExtra("user_longitude", Double.toString(final_longitude));

					// staring activity
					startActivity(i);
			}
		});
		
		search.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			/*	startActivity(new Intent(getApplicationContext(),
						PlacesAutocomplete.class)); */
				 
				Intent i = new Intent(android.content.Intent.ACTION_VIEW,Uri.parse("https://maps.google.co.in/maps?hl=en&tab=wl"));
		         startActivity(i);
				
			}
		});
		
		 locationman = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
         
	        locationman.requestLocationUpdates(LocationManager.GPS_PROVIDER,BIND_AUTO_CREATE,
	        		BIND_NOT_FOREGROUND,new MyLocationListener());
	        		showCurrentLocation();
	}
	

	private void showCurrentLocation() {
		// TODO Auto-generated method stub
        		 Location location = locationman.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        	   	 
     	        if (location != null) {
     	        
                 String message = String.format(
     	                    "Current Location \n Longitude: %1$s \n Latitude: %2$s \n Altitude: %3$s",
    	                    location.getLongitude(), location.getLatitude(), location.getAltitude()
                 );
               
	}
        	}
        	 private class MyLocationListener implements LocationListener {
     	    	   	 
       	        public void onLocationChanged(Location location) {
       	            message = String.format(
      	                    "New Location \n Longitude: %1$s \n Latitude: %2$s \n Altitude: %3$s",
       	                    location.getLongitude(), location.getLatitude(), location.getAltitude()

       	            );
       	           final_latitude= (location.getLatitude());
                   final_longitude=(location.getLongitude());
       	        
       	            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
       	            Myclass.latitude=final_latitude;
       	            Myclass.longitude=final_longitude;
       	        }
   				public void onProviderDisabled1(String provider) {
   					// TODO Auto-generated method stub
   					
   				}

   				public void onProviderEnabled1(String provider) {
   					// TODO Auto-generated method stub
   					
   				}

   				public void onStatusChanged1(String provider, int status,
   						Bundle extras) {
   					// TODO Auto-generated method stub
   					
   				}
				@Override
				public void onProviderDisabled(String provider) {
					// TODO Auto-generated method stub
					
				}
				@Override
				public void onProviderEnabled(String provider) {
					// TODO Auto-generated method stub
					
				}
				@Override
				public void onStatusChanged(String provider, int status,
						Bundle extras) {
					// TODO Auto-generated method stub
					
				}
       	        }
        
       	        public void onStatusChanged(String s, int i, Bundle b) {
       	        	
       	            Toast.makeText(getBaseContext(), "Provider status changed",
       	                    Toast.LENGTH_LONG).show();
       	        }
       	 
       	        public void onProviderDisabled(String s) {
       	            Toast.makeText(getBaseContext(),
       	                    "Provider disabled by the user. GPS turned off",
       	                    Toast.LENGTH_LONG).show();
       	        }
       	 
       	        public void onProviderEnabled(String s) {
       	            Toast.makeText(getBaseContext(),
       	                    "Provider enabled by the user. GPS turned on",
       	                    Toast.LENGTH_LONG).show();
       	        }

       	    
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}




			
}

