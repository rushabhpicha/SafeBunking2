package com.rushabh.nasikapp.safebunking;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class Individual extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    String notes,subject_name;
    EditText e_notes;
    TextView second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        Typeface sans = Typeface.createFromAsset(getAssets(), "font/Lato-Bold.ttf");
        mTitle.setTypeface(sans);
        e_notes = findViewById(R.id.first);
        e_notes.setTypeface(sans);

        Intent i = getIntent();
        try {
            subject_name=i.getStringExtra("subject_name");
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        databaseHelper=new DatabaseHelper(this);
        final SQLiteDatabase db=databaseHelper.getReadableDatabase();
        Cursor c=db.rawQuery("select notes from bunk where subject_name='"+subject_name+"'",null);
        if(c!=null) {
            c.moveToFirst();
                    notes = c.getString(0);
            }
            c.close();


        e_notes.setText(notes);
        e_notes.setSelection(e_notes.getText().length());

        mTitle.setText("Notes for "+subject_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            String noot=e_notes.getText().toString();
            noot=noot.replaceAll("'","''");
//            noot=noot.replaceAll("/","//");
//            noot=noot.replaceAll("\","\\");
            databaseHelper = new DatabaseHelper(this);
            SQLiteDatabase db=databaseHelper.getWritableDatabase();
            db.execSQL("update bunk set notes='"+noot+" ' where subject_name='" +subject_name+"'");
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        String noot=e_notes.getText().toString();
        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db=databaseHelper.getWritableDatabase();
        db.execSQL("update bunk set notes='"+noot+" ' where subject_name='" +subject_name+"'");
        finish();
        super.onBackPressed();
    }
}
