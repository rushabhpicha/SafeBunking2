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
        // creating an instance of DatabaseHelper class
        databaseHelper = new DatabaseHelper(this);
        final SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor c=db.rawQuery("select notes from bunk where subject_name='"+subject_name+"'",null);

        // If the cursor is not null move it to the first row. Use the getString(int) to retrieve the notes value.

        if(c!=null) {
            c.moveToFirst();
            notes = c.getString(0);
        }
            c.close();  //closing the cursor. Very very important.


        e_notes.setText(notes);
        e_notes.setSelection(e_notes.getText().length());

        mTitle.setText("Notes for "+subject_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    // Update notes whenever the user presses the back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            updateNotes();
        }
        return super.onOptionsItemSelected(item);
    }

    // Updates the notes table in the database whenever a user presses the back button.
    private void updateNotes() {
        String noot = e_notes.getText().toString();
        noot = noot.replaceAll("'","''");
        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db=databaseHelper.getWritableDatabase();
        // Whatever is is written in the notes textview update the notes column
        db.execSQL("update bunk set notes='"+noot+" ' where subject_name='" +subject_name+"'");
        finish();  // Close the activity after the database is updated.
    }

    // Call updateNotes on backPressed
    @Override
    public void onBackPressed() {
        updateNotes();
        super.onBackPressed();
    }
}
