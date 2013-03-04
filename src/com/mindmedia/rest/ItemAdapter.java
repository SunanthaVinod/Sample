package com.mindmedia.rest;

import java.util.ArrayList;
import java.util.HashMap;



import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ItemAdapter extends BaseAdapter{
	
	 private Runnable activity;
	    private ArrayList<HashMap<String, String>> data;
	    private static LayoutInflater inflater=null;
	    
	    public ItemAdapter(Runnable runnable, ArrayList<HashMap<String, String>> d) {
	        activity = runnable;
	        data=d;
	        //inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        //imageLoader=new ImageLoader(activity.getApplicationContext());
	    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	 public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_item, null);

        TextView name = (TextView)vi.findViewById(R.id.name); // title
        //TextView address = (TextView)vi.findViewById(R.id.address); // artist name
        //RatingBar rating = (RatingBar)vi.findViewById(R.id.ratingBar); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        int random = (int)Math.ceil(Math.random()*100) % 4; 
        String uri = "@drawable/pic" + Integer.toString(random);
        
      //  int imageResource = getResources().getIdentifier(uri, null, getPackageName());
       // icon.setImageResource(imageResource);
        
        
        HashMap<String, String> items = new HashMap<String, String>();
        items = data.get(position);
        
        // Setting all values in listview
        name.setText(items.get(LoadingPlaces.KEY_NAME));
       // address.setText(items.get(AddToFavorites.kEY_ADD));
        //rating.setText(items.get(AddToFavorites.KEY_RAT));
        //imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
        return vi;
}
}
