package com.mindmedia.rest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TopList extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is list of top 10 hotels");
        setContentView(textview);
    }
}