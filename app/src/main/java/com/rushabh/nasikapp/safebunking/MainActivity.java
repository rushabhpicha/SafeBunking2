package com.rushabh.nasikapp.safebunking;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageButton add,overall;
    Button show;
    DatabaseHelper database;
    public static RecyclerView recyclerView;
    public static RecycleAdapter recycler;
    public static List<DataModel> datamodel;
    public static SQLiteDatabase attendance;
    public static DrawerLayout dl;
    public static ArrayList<DataModel> dataModelArrayList=new ArrayList();
    private boolean isFirstRun = true;
    private SharedPreferences prefs;
    public  static  RecycleAdapter recycleAdapter;
    public static RelativeLayout rl;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static int limit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Instantiating the recyclerView
        recycler=new RecycleAdapter(dataModelArrayList,this);
        rl=findViewById(R.id.clo);
        recyclerView=findViewById(R.id.recycle);
        rupani(MainActivity.this);
        dl=findViewById(R.id.drawer_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
  //      getSupportActionBar().setIcon(R.drawable.bunking_logo);

        Typeface sans = Typeface.createFromAsset(getAssets(), "font/Lato-Bold.ttf");
        mTitle.setTypeface(sans);

//        add=findViewById(R.id.btn_settings);
//        overall=findViewById(R.id.btn_search);

//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(MainActivity.this,Add_Subject.class);
//                startActivity(i);
//            }
//        });
//        overall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(MainActivity.this,overall.class);
//                startActivity(i);
//            }
//        });
        database = new DatabaseHelper(MainActivity.this);
        attendance = database.getWritableDatabase();
        datamodel = database.getdata();

        /* A LayoutManager is responsible for measuring and positioning item views within a
        RecyclerView as well as determining the policy for when to recycle item views that are no longer visible to
        the user. The other options are GridLayoutManager, StaggeredGridLayoutManager or event
        WearableLinearLayoutManager.*/
        recycler = new RecycleAdapter((ArrayList<DataModel>) datamodel, this); // Instance of RecycleAdapter
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext()); // Instance of LayoutManager
        recyclerView.setLayoutManager(mLayoutManager); // setting the LayoutManager to the RecyclerView
        recyclerView.setItemAnimator(new DefaultItemAnimator()); // Setting the Default Animation for RV.
        recyclerView.setAdapter(recycler); //Assigning the adapter to the RV

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open Activity Add_Subject when user clicks on the plus button.
                Intent i=new Intent(MainActivity.this, Add_Subject.class);
                startActivity(i);
            }
        });

        FloatingActionButton overall=findViewById(R.id.overall);
        overall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open Activity Overalll when user clicks on the plus button.
                Intent i=new Intent(MainActivity.this,overall.class);
                i.putExtra("tasks", dataModelArrayList);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        license();

        rupani(MainActivity.this);
    }
    public static void rupani(Context context){
        if(recycler.getItemCount() == 0){
           rl.setBackgroundResource(R.drawable.arrow);
        }
        else
        {
            rl.setBackgroundColor(Color.parseColor("#c0c0c0"));
        }
        if(recycler.getItemCount() > 0){
            Toast.makeText(context, "Click the plus button when you attend a lecture and the minus button when you bunk a lecture for that subject!", Toast.LENGTH_LONG).show();
        }
    }

    // Code to be executed when the app is installed for the first time.
    private void license() {
        prefs = getDefaultSharedPreferences(this);
        isFirstRun = prefs.getBoolean("isFirstRun", true); // get default value of sharedpreferences which is true.
        if(isFirstRun){
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Help");
            String alert1 = "1.Safe Bunking will help you to keep a track of your daily attendance.\n\n" +
                    "2.Start by adding the subject.You can do this by clicking on the plus button at the bottom left of the screen." +
                    "While entering the details the name of the subject is mandatory,rest is optional.\n\n"+
                    "3.For every subject click the '+' button and the '-' button if you attend the lecture or bunk the lecture respectively." +
                    "Your attendance for that subject will get updated.\n\n" +
                    "4.If your attendance is less than the minimum attendance percent the text of that respective subject will turn red else it will be green.\n\n" +
                    "5.You can check your overall attendance by clicking the button at the bottom right of your screen.";
            alertDialog.setMessage(alert1);
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                    String message="Enter the minimum attendance percentage required? ";
                    alertDialog.setTitle(message);

                    final EditText input = new EditText(MainActivity.this);
                    final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);
                    input.setInputType(InputType.TYPE_CLASS_NUMBER);
                    alertDialog.setView(input);
                    alertDialog.setCancelable(false);
                    alertDialog.setPositiveButton("Submit",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try{
                                        limit = Integer.parseInt(input.getText().toString());
                                    }catch(NumberFormatException ex){
                                        System.out.println("Value at TextView is not a valid integer");
                                    }
                                    if(limit>100){
                                        limit=75;
                                        Toast.makeText(MainActivity.this, "Assuming minimum attendance required is 75%", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        SharedPreferences sharedpreferences = getDefaultSharedPreferences(MainActivity.this);
                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putInt("limit",limit);
                                        editor.commit();
                                        Toast.makeText(MainActivity.this, "Your attendance limit is set to " + limit + "%", Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                    }
                                }
                            });
                    alertDialog.show();
                }
            });
            AlertDialog alert = alertDialog.create();
            alert.show();
        }
        isFirstRun = false; // After the firstRun change the variable to false so that the code is executed only once.

        prefs.edit().putBoolean("isFirstRun", isFirstRun).commit(); //commit the value of sharedpreference.

    }
    public boolean onContextItemSelected(MenuItem item)
    {
        return super.onContextItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.clear_all_attendance: {
                // If pressed yes, update the SQL query and set the lectures to 0.
                new AlertDialog.Builder(this)
                        .setMessage("Do you want to clear all the subjects?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                attendance.execSQL("update bunk set total_lectures=0,bunked_lectures=0");
                                updateList();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                break;
            }
            // If pressed yes, delete all the subjects from the database.
            case R.id.delete_all: {
                new AlertDialog.Builder(this)
                        .setMessage("Do you want to delete all the subjects?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                attendance.execSQL("delete from bunk");
                                updateList();
                                rupani(MainActivity.this);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                break;
            }
            // If the user wants to change the limit, change the limit and update the value in the sharedpreference.
            case R.id.change_limit: {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                String message = "Enter the minimum attendance percent required in your college";
                alertDialog.setTitle(message);

                final EditText input = new EditText(MainActivity.this);
                final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                alertDialog.setView(input);
                alertDialog.setPositiveButton("Submit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    limit = Integer.parseInt(input.getText().toString());
                                } catch (NumberFormatException ex) {
                                    System.out.println("Value at TextView is not a valid integer");
                                }
                                if (limit > 100) {
                                    limit = 75;
                                    Toast.makeText(MainActivity.this, "Assuming minimum attendance required is 75%", Toast.LENGTH_SHORT).show();
                                } else {
                                    SharedPreferences sharedpreferences = getDefaultSharedPreferences(MainActivity.this);
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putInt("limit", limit);
                                    editor.commit();
                                    Toast.makeText(MainActivity.this, "Your attendance limit is set to " + limit + "%", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                }
                            }
                        });
                alertDialog.show();
            }
        }
            return super.onOptionsItemSelected(item);
        }

    @SuppressWarnings("StatementWithEmptyBody")
    // Implementation of the NavigationItems.
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        // Use of explicit intent to open the list of messaging application.
        if (id == R.id.nav_share) {
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String sAux = "#SafeBunking\nA.P.J. Abdul Kalam-\n\n\"Sometimes, it's better to bunk a class and enjoy with friends, because now, when I " +
                        "look back, marks never make me laugh, but memories do.\"\n\nManage your bunks with safe bunking.\n\nDownload the app now.\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id="+this.getPackageName();
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "choose one"));
            } catch (Exception e) {
                //e.toString();
            }
        }

        // Use of Explicit intent to open the play store with this app to store ratings.
        else if (id == R.id.rate) {
            Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
            }
        }

        // Use of Explicit Intent to open the MailBox.
        else if(id==R.id.feedback){
            Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "rushabhpicha12@gmail.com"));
            intent1.putExtra(Intent.EXTRA_SUBJECT, "Subject of feedback:-");
            intent1.putExtra(Intent.EXTRA_TEXT, "Content of feedback:-");
            startActivity(intent1);
        }

        // Open the AlertDialodBox when clicked on help
        else if(id==R.id.help){
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Help");
            String alert1 = "1.Attendance Manager will help you to keep a track of your daily attendance.\n\n" +
                    "2.Start by adding the subject.You can do this by clicking on the plus button at the bottom left of the screen." +
                    "While entering the details the name of the subject is mandatory,rest is optional.\n\n"+
                    "3.For every subject click the '+' button and the '-' button if you attend the lecture or bunk the lecture respectively." +
                    "Your attendance for that subject will get updated.\n\n" +
                    "4.If your attendance is less than the minimum attendance percent the text of that respective subject will turn red else it will be green.\n\n" +
                    "5.You can check your overall attendance by clicking the button at the bottom right of your screen.";
            alertDialog.setMessage(alert1);
            alertDialog.setCancelable(false);

            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog alert = alertDialog.create();
            alert.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Returns datamodel size.
    public static int getPosition(){
        int value = 0;
        for(int i=0;i<datamodel.size();i++) {
                value = value+1;
        }
        return value;
    }

    // A very important method which is updated every time a change is made in the Adapter class. This method is called
    // everytime an operation is created on the adapter class to update the view. This is a nice example of MVC Design Pattern.
    public static void updateList()
    {
        datamodel.clear();
        Cursor mems=attendance.rawQuery("select * from bunk",null);
        while(mems.moveToNext()) {
            datamodel.add(new DataModel(mems.getString(0), mems.getInt(1), mems.getInt(2),mems.getString(4)));
        }
        recycler.notifyDataSetChanged();
        mems.close();
    }
}
