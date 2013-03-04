package com.mindmedia.rest;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;
 
public class SinglePlaceActivity extends Activity {
	private RatingBar ratingBar;
	private TextView txtRatingValue;
	private Button btnSubmit;
	public String website,phone,name,address,latitude,longitude,rating;
	ImageButton imgb;
	public Double lat,longi;
    // flag for Internet connection status
    Boolean isInternetPresent = false;
 
    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();
 
    // Google Places
    GooglePlaces googlePlaces;
 
    // Place Details
    PlaceDetails placeDetails;
 
    // Progress dialog
    ProgressDialog pDialog;
 
    DatabaseHandler db;
    String reference;
    // KEY Strings
    
   
    public static String KEY_REFERENCE = "reference"; // id of the place
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_place);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        
        
        Intent i = getIntent();
        // Place referece id
        reference = i.getStringExtra(KEY_REFERENCE);
        
        
 
        // Calling a Async Background thread
        new LoadSinglePlaceDetails().execute(reference);
        
		
    }
    
    /**
     * Background Async Task to Load Google places
     * */
    class LoadSinglePlaceDetails extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SinglePlaceActivity.this);
            pDialog.setMessage("Loading profile ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        /**
         * getting Profile JSON
         * */
        protected String doInBackground(String... args) {
            String reference = args[0];
 
            // creating Places class object
            googlePlaces = new GooglePlaces();
 
            // Check if used is connected to Internet
            try {
                placeDetails = googlePlaces.getPlaceDetails(reference);
       
 
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed Places into LISTVIEW
                     * */
                    if(placeDetails != null){
                        String status = placeDetails.status;
 
                        // check place deatils status
                        // Check for all possible status
                        if(status.equals("OK")){
                            if (placeDetails.result != null) {
                                 name = placeDetails.result.name;
                                 address = placeDetails.result.formatted_address;
                                 phone = placeDetails.result.formatted_phone_number;
                                 latitude = Double.toString(placeDetails.result.geometry.location.lat);
                                 longitude = Double.toString(placeDetails.result.geometry.location.lng);
                                 website = placeDetails.result.website;
                                 rating = Float.toString(placeDetails.result.rating);
                                           
                                double lat2 = placeDetails.result.geometry.location.lat;
                                double lon2 = placeDetails.result.geometry.location.lng; 
                                
                                Log.d("Place ", name + address + phone + latitude + longitude + website + rating);
                            //    Toast.makeText(getApplicationContext(),url, Toast.LENGTH_LONG).show();
                                // Displaying all the details in the view
                                // single_place.xml
                                TextView lbl_name = (TextView) findViewById(R.id.name);
                                TextView lbl_address = (TextView) findViewById(R.id.address);
                                TextView lbl_phone = (TextView) findViewById(R.id.phone);
                                TextView lbl_location = (TextView) findViewById(R.id.location);
                                TextView lbl_website = (TextView)findViewById(R.id.website);
                                TextView lbl_rating = (TextView)findViewById(R.id.rating1);
                                imgb=(ImageButton)findViewById(R.id.imgb);
                                
                                // Check for null data from google
                                // Sometimes place details might missing
                                name = name == null ? "Not present" : name; // if name is null display as "Not present"
                                address = address == null ? "Not present" : address;
                                phone = phone == null ? "Not present" : phone;
                                latitude = latitude == null ? "Not present" : latitude;
                                longitude = longitude == null ? "Not present" : longitude;
                                website = website == null ? "Not present" : website;
                                rating = rating == null ? "Not present" : rating;
                                
                                
                                lbl_name.setText(name);
                                lbl_address.setText(address);
                                lbl_phone.setText(Html.fromHtml("<b>Phone:</b> " + phone));
                                lbl_location.setText(Html.fromHtml("<b>Latitude:</b> " + latitude + ", <b>Longitude:</b> " + longitude));
                                lbl_website.setText(website);
                                lbl_rating.setText(rating);
                                lbl_website.setOnClickListener(new View.OnClickListener() {
									
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										Intent i = new
							         Intent(android.content.Intent.ACTION_VIEW,Uri.parse(website));
							         startActivity(i);
									}
								});
                                Log.i("PHONE NO", phone);
                               imgb.setOnClickListener(new View.OnClickListener() {
									
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										 Log.i("PHONE NO IN", phone);
										Intent i = new Intent(android.content.Intent.ACTION_DIAL,Uri.parse("tel:" + phone));
										         startActivity(i);
									}
								});
                               Log.i("RATING VALUE", rating);
                               ratingBar.setRating(Float.parseFloat(rating));
                               ratingBar.setStepSize((float) 0.0);
                               
                             lat = Myclass.latitude;
                               longi = Myclass.longitude;
                
                               
                              /* ImageButton maproute = (ImageButton) findViewById(R.id.maproute);
   							maproute.setOnClickListener(new View.OnClickListener() {
   								
   								@Override
   								public void onClick(View v) {
   									// TODO Auto-generated method stub
   									Log.d("LOCATION", lat.toString());
   							String uri = "http://maps.google.com/maps?f=d&hl=en&saddr=lat,longi+&daddr=lat2,lon2";
   							//String uri = "https://maps.google.com/maps?hq=http://maps.google.com/help/maps/directions/driving/mapleft.kml&f=d&dirflg=d&mid=1361178834";
   							
   							
   							//		String uri = "http://maps.google.com/maps?f=d&hl=en&saddr="+source_latitude+","+source_longitude+"&daddr="+destination_latitude+","+destination_longitude";
   									Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
   									intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
   									startActivity(intent);
   								}
   							});   
   							Button save = (Button)findViewById(R.id.save);
   							save.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									db=new DatabaseHandler(getApplicationContext());					
									Log.d("Place_db ", name + address + phone + latitude + longitude + rating + website + reference);
									db.insertContact(name, phone, address, latitude, longitude, rating, reference);
								}
							});*/
   							
                               
                            }
                        }
                        else if(status.equals("ZERO_RESULTS")){
                            alert.showAlertDialog(SinglePlaceActivity.this, "Near Places",
                                    "Sorry no place found.",
                                    false);
                        }
                        else if(status.equals("UNKNOWN_ERROR"))
                        {
                            alert.showAlertDialog(SinglePlaceActivity.this, "Places Error",
                                    "Sorry unknown error occured.",
                                    false);
                        }
                        else if(status.equals("OVER_QUERY_LIMIT"))
                        {
                            alert.showAlertDialog(SinglePlaceActivity.this, "Places Error",
                                    "Sorry query limit to google places is reached",
                                    false);
                        }
                        else if(status.equals("REQUEST_DENIED"))
                        {
                            alert.showAlertDialog(SinglePlaceActivity.this, "Places Error",
                                    "Sorry error occured. Request is denied",
                                    false);
                        }
                        else if(status.equals("INVALID_REQUEST"))
                        {
                            alert.showAlertDialog(SinglePlaceActivity.this, "Places Error",
                                    "Sorry error occured. Invalid Request",
                                    false);
                        }
                        else
                        {
                            alert.showAlertDialog(SinglePlaceActivity.this, "Places Error",
                                    "Sorry error occured.",
                                    false);
                        }
                    }else{
                        alert.showAlertDialog(SinglePlaceActivity.this, "Places Error",
                                "Sorry error occured.",
                                false);
                    }
 
                }
            });
 
        }
 
    }
 
}
