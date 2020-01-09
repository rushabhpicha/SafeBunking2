package com.rushabh.nasikapp.safebunking;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.ArrayList;

public class overall extends AppCompatActivity {

    public ArrayList<DataModel> dataModelArrayList;
    Animation bounce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(overall.this);
        int limi=sharedPreferences.getInt("limit",MainActivity.limit);
        TextView title = findViewById(R.id.title);
        TextView first = findViewById(R.id.first);
//        TextView second = findViewById(R.id.second);
        TextView third = findViewById(R.id.third);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.drawable.bunking_logo);
        Typeface sans = Typeface.createFromAsset(getAssets(), "font/Lato-Bold.ttf");
        mTitle.setTypeface(sans);

        dataModelArrayList = new ArrayList<DataModel>();
        int j = MainActivity.getPosition();
        float tsum = 0, bsum = 0;
        float asum;
        int safe_bunk;
        for (int i = 0; i < j; i++) {
                float t = ((DataModel) RecycleAdapter.dataModelArrayList.get(i)).getTotal_Lectures();
                float b = ((DataModel) RecycleAdapter.dataModelArrayList.get(i)).getBunked_lectures();
                bsum += b;
                tsum += t;
                System.out.print(tsum);
            }
        float percent;
        asum = tsum + bsum;
//        float d= (tsum-asum);
         percent= (float) (Math.ceil((double) (10.0f * (100.0f * (tsum / asum)))) / 10.0d);
        title.setText(String.valueOf(percent)+" %");
        bounce = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce);
        title.startAnimation(bounce);
        if (percent < limi) {
            safe_bunk = 0;
            third.setText("Your attendance is below "+limi+" percentage!");
            title.setTextColor(Color.parseColor("#f44336"));
        } else {
//            safe_bunk = safebunks(limi, (int) asum, (int) bsum);
            third.setText("Your attendance is above "+limi+" percentage!");
            title.setTextColor(Color.parseColor("#008000"));
        }
        first.setText("You have attended " + (int)tsum + " lectures from a total of " + (int)asum+".");
//        second.setText("You have " + safe_bunk + "left");
//        fourth.setText("You have to attend "+d+" lectures for 100% attendance.");

        first.setTypeface(sans);
//        second.setTypeface(sans);
        third.setTypeface(sans);
        title.setTypeface(sans);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();  //Takes you to the previous activity by default
    }
}
