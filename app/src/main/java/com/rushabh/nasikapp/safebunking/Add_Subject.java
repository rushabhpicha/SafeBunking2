package com.rushabh.nasikapp.safebunking;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Add_Subject extends AppCompatActivity {

    Button submit;
    DatabaseHelper databaseHelper;
    EditText etsubject_name, ettotal_lectures, etbunked_lectures;
    String subject_name;
    String total_lectures, bunked_lectures;
    int b,t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_subject);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setIcon(R.drawable.bunking_logo);
        Typeface sans = Typeface.createFromAsset(getAssets(), "font/Lato-Bold.ttf");
        mTitle.setTypeface(sans);

        etsubject_name = (EditText) findViewById(R.id.etsubject_name);
        ettotal_lectures = (EditText) findViewById(R.id.ettotal_lectures);
        etbunked_lectures = (EditText) findViewById(R.id.etbunked_lectures);
        submit = (Button) findViewById(R.id.submit);

            subject_name = etsubject_name.getText().toString().trim();
            total_lectures = ettotal_lectures.getText().toString().trim();
            bunked_lectures = etbunked_lectures.getText().toString().trim();
        try {
            b = Integer.parseInt(bunked_lectures);
            t = Integer.parseInt(total_lectures);

        }catch(NumberFormatException e)
        {
            e.printStackTrace();
        }
        submit = (Button) findViewById(R.id.submit);
        databaseHelper = new DatabaseHelper(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean validate(){
        try {
            subject_name = etsubject_name.getText().toString().trim();
            total_lectures = ettotal_lectures.getText().toString().trim();
            bunked_lectures = etbunked_lectures.getText().toString().trim();

        }catch(NumberFormatException e)
        {
            e.printStackTrace();
        }
        submit = (Button) findViewById(R.id.submit);

        if (TextUtils.isEmpty(subject_name)) {
            Toast.makeText(Add_Subject.this, "Please enter the subject name", Toast.LENGTH_SHORT).show();
        }
            else if(b==0){
                Toast.makeText(this, "Assuming 0 bunked lectures!", Toast.LENGTH_SHORT).show();
                insert();
            }
            else if(t==0){
            Toast.makeText(this, "Assuming 0 attended lectures!", Toast.LENGTH_SHORT).show();
            insert();
        }
            else if((b==0)&&(t==0)) {
                Toast.makeText(this, "Assuming 0% attendance!", Toast.LENGTH_SHORT).show();
                insert();
            }
        else{
          insert();
        }
    return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();  //Takes you to the previous activity by default
    }
    public void insert(){
        databaseHelper = new DatabaseHelper(this);
        databaseHelper.getWritableDatabase();
        databaseHelper.insertData(subject_name, total_lectures, bunked_lectures);
        etsubject_name.setText("");
        ettotal_lectures.setText("");
        etbunked_lectures.setText("");
        databaseHelper.close();
        MainActivity.updateList();
        MainActivity.rupani(this);
        Toast.makeText(this,"Click on the subject to save the notes and important points of the subject",Toast.LENGTH_LONG).show();
        finish();
    }
}
