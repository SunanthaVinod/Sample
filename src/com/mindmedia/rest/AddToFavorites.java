package com.mindmedia.rest;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AddToFavorites extends Activity {
	
	DatabaseHandler db;
	ListView lv;
	ItemAdapter adapter;
	// KEY Strings
			public static String KEY_REFERENCE = "reference"; // id of the place
			public static String KEY_NAME = "name"; // name of the place
			public static String kEY_ADD = "address"; // Place area name
			public static String KEY_LAT = "latitude"; // id of the place
			public static String KEY_LON = "longitude"; // name of the place
			public static String KEY_PHONE = "phone"; // Place area name
			public static String KEY_RAT = "rating";
			
    public void onCreate(Bundle savedInstanceState) {
    	
    	db=new DatabaseHandler(getApplicationContext());
        super.onCreate(savedInstanceState);
        //TextView textview = new TextView(this);
        //textview.setText("This is the list of favorite hotels");
        setContentView(R.layout.favorites);
        HashMap<String, String> map;
        ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();
      
    db.open();
    Cursor c = db.getAllContacts();  		
   		if( c.moveToFirst() ){  			
   			
                       do
                       {
                           map = new HashMap<String, String>(); // <---- moved here
                           
                           map.put(KEY_NAME, c.getString(1).toString());
                           map.put(KEY_PHONE, c.getString(2).toString());
                           map.put(kEY_ADD, c.getString(3).toString());
                           map.put(KEY_LAT, c.getString(4).toString());
                           map.put(KEY_LON, c.getString(5).toString());
                           map.put(KEY_RAT, c.getString(6).toString());
                           map.put(KEY_REFERENCE, c.getString(7).toString());
                           dataList.add(map);   
                           
                       }while(c.moveToNext());
           	
    }
    db.close();
    
    lv = (ListView)findViewById(R.id.list);
 // Getting adapter by passing xml data ArrayList
  //  adapter=new ItemAdapter(this, dataList);        
    lv.setAdapter(adapter);

}
}