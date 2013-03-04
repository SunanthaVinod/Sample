package com.mindmedia.rest;

import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.DataSource;




import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class LoadingPlaces extends Activity implements OnSeekBarChangeListener
{
	
	double latitude,longitude;
	
	// Alert Dialog Manager
		AlertDialogManager alert = new AlertDialogManager();

		// Google Places
		GooglePlaces googlePlaces;

		// Places List
		PlacesList nearPlaces;

		// Button
		Button btnShowOnMap,btnDecrease,btnIncrease;
		TextView textKms;
		// private static int counter = 0;
		// private String rad;
		 int kms;
		// Progress dialog
		ProgressDialog pDialog;
		int iniValue=1, maxValue=5;
		// Places Listview
		ListView lv;
		
		double final_latitude, final_longitude,final_altitude;
		
		TextView seekText;
		SeekBar seekKms;
		int seekValue;
		ListAdapter adapter ;
		String user_latitude,user_longitude;
		
		// ListItems data
		ArrayList<HashMap<String, String>> placesListItems = new ArrayList<HashMap<String,String>>();
		
		
		// KEY Strings
		public static String KEY_REFERENCE = "reference"; // id of the place
		public static String KEY_NAME = "name"; // name of the place
		public static String KEY_VICINITY = "vicinity"; // Place area name
		
		
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.place_list);
		
		
		seekText=(TextView)findViewById(R.id.textView1);
		seekKms=(SeekBar)findViewById(R.id.seekBarKms);
		seekKms.setOnSeekBarChangeListener(this);
		//Toast.makeText(getApplicationContext(), "Seeking called", Toast.LENGTH_LONG).show();
		
		Refresh();
		//passValue();
		
		
	}
	
	
	
	public void Refresh()
	{
		Intent i = getIntent();
	
		// Users current geo location
		String user_latitude = i.getStringExtra("user_latitude");
		String user_longitude = i.getStringExtra("user_longitude");
	
		latitude=Double.parseDouble(user_latitude);
		longitude=Double.parseDouble(user_longitude);
	
		
		
		
		/*Intent i1 = new Intent(getApplicationContext(), SinglePlaceActivity.class);

		i.putExtra("user_latitude", Double.toString(final_latitude));
		i.putExtra("user_longitude", Double.toString(final_longitude));

		startActivity(i1);
		
		*/
		
		// Getting listview
			lv = (ListView) findViewById(R.id.list);	
		   
			  lv.setOnItemClickListener(new OnItemClickListener() 
			  {
				  
		            @Override
		            public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
		            {
		                // getting values from selected ListItem
		                String reference = ((TextView) view.findViewById(R.id.reference)).getText().toString();
		 
		                // Starting new intent
		                Intent in = new Intent(getApplicationContext(),SinglePlaceActivity.class);
		                 // Sending place refrence id to single place activity
		                // place refrence id used to get "Place full details"
		                in.putExtra(KEY_REFERENCE, reference);
		               // in.putExtra("user_latitude", latitude);
		               // in.putExtra("user_longitude", longitude);
		                startActivity(in);
		           }
		       });	
				// button show on map

				// calling background Async task to load Google Places
				// After getting places from Google all the data is shown in listview
			new LoadPlaces().execute();
	}
	
	
	/*public void passValue()
	{
		//Toast.makeText(getApplicationContext(), "pass value starts", Toast.LENGTH_LONG).show();
		
		Intent i=new Intent(this,SinglePlaceActivity.class);
		//Toast.makeText(getApplicationContext(), "inside intent", Toast.LENGTH_LONG).show();
		 Bundle myBundle = i.getExtras();

	        String lat1 = user_latitude;
	        String long1 = user_longitude;
		
	        Log.d("Tag1",lat1+long1);
		startActivityForResult(i,1);
		Toast.makeText(getApplicationContext(), "value passed", Toast.LENGTH_LONG).show();
		
		Log.d("Tag2",lat1+long1);
		
		//Toast.makeText(getApplicationContext(), lat1, Toast.LENGTH_LONG).show();
		//Toast.makeText(getApplicationContext(), long1, Toast.LENGTH_LONG).show();
		
	}
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{

		if (requestCode == 1) 
		{

		     if(resultCode == RESULT_OK)
		     {

		      String result=data.getStringExtra("result");

		     }

		     if (resultCode == RESULT_CANCELED) 
		     {

		     //Write your code on no result return 

		     }
		}
	}*/
	class LoadPlaces extends AsyncTask<String, String, String> 
	{

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
			pDialog = new ProgressDialog(LoadingPlaces.this);
			pDialog.setMessage(Html.fromHtml("<b>Search</b><br/>Loading Places..."));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting Places JSON
		 * */
		public String doInBackground(String... args) 
		{
			// creating Places class object
			googlePlaces = new GooglePlaces();
			
			try 
			{			
				
				// Separate your place types by PIPE symbol "|"
				// If you want all types places make it as null
				// Check list of types supported by google
				// 
				String types = "cafe|restaurant"; // Listing places only cafes, restaurants        cafe|restaurant
				double radius;
				// Radius in meters - increase this value if you don't find any places
				if(seekValue!=0)
				{				
						radius = seekValue*1000;
				}
				else 
				{
						radius=1000;
				}
								 
				Log.d("RADIUS VALUE",String.valueOf(radius));
				// get nearest places
				nearPlaces = googlePlaces.search(latitude,longitude, radius, types);
								

			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * and show the data in UI
		 * Always use runOnUiThread(new Runnable()) to update UI from background
		 * thread, otherwise you will get error
		 * **/
		protected void onPostExecute(String file_url) 
		{
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() 
			{
				public void run() 
				{
					/**
					 * Updating parsed Places into LISTVIEW
					 * */
					// Get json response status
					String status = nearPlaces.status;
					
					// Check for all possible status
					if(status.equals("OK"))
					{
						// Successfully got places details
						if (nearPlaces.results != null) 
						{
							// loop through each place
							for (Place p : nearPlaces.results) 
							{
								HashMap<String, String> map = new HashMap<String, String>();
								
								// Place reference won't display in listview - it will be hidden
								// Place reference is used to get "place full details"
								map.put(KEY_REFERENCE, p.reference);
								
								// Place name
								map.put(KEY_NAME, p.name);
								
								
								// adding HashMap to ArrayList
								placesListItems.add(map);
							}
							// list adapter
							ListAdapter adapter = new SimpleAdapter(LoadingPlaces.this, placesListItems,
					                R.layout.list_item,
					                new String[] { KEY_REFERENCE, KEY_NAME}, new int[] {
					                        R.id.reference, R.id.name });
							
							// Adding data into listview
				
							lv.setAdapter(adapter);
							
							
						}
					}
					else if(status.equals("ZERO_RESULTS"))
					{
						// Zero results found
						alert.showAlertDialog(LoadingPlaces.this, "Near Places",
								"Sorry no places found. Try to change the types of places",
								false);
					}
					else if(status.equals("UNKNOWN_ERROR"))
					{
						alert.showAlertDialog(LoadingPlaces.this, "Places Error",
								"Sorry unknown error occured.",
								false);
					}
					else if(status.equals("OVER_QUERY_LIMIT"))
					{
						alert.showAlertDialog(LoadingPlaces.this, "Places Error",
								"Sorry query limit to google places is reached",
								false);
					}
					else if(status.equals("REQUEST_DENIED"))
					{
						alert.showAlertDialog(LoadingPlaces.this, "Places Error",
								"Sorry error occured. Request is denied",
								false);
					}
					else if(status.equals("INVALID_REQUEST"))
					{
						alert.showAlertDialog(LoadingPlaces.this, "Places Error",
								"Sorry error occured. Invalid Request",
								false);
					}
					else
					{
						alert.showAlertDialog(LoadingPlaces.this, "Places Error",
								"Sorry error occured.",
								false);
					}
				}
			});

		}

	}



	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		
		seekValue=seekBar.getProgress();
		Log.d("STOP TRACKING",String.valueOf(seekValue));
		placesListItems.clear();
		Refresh();
		// TODO Auto-generated method stub
		
	}

}
