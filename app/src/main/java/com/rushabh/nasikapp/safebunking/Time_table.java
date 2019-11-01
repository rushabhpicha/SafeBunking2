//package com.rushabh.nasikapp.safebunking;
//
//import android.content.Intent;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.provider.MediaStore;
//import android.support.design.widget.CoordinatorLayout;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.AppCompatImageView;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.jsibbold.zoomage.ZoomageView;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//public class Time_table extends AppCompatActivity {
//
//    private static final int SELECT_PICTURE =100;
//    private ZoomageView imageView;
//
//    public DatabaseHelper db;
//    Button btnSelectImage,btnDeleteImage;
//    AppCompatImageView imgView;
//    CoordinatorLayout coordinatorLayout;
//    public SQLiteDatabase SQLITEDATABASE;
//    private static final String TAG = "MainActivity";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_time_table);
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
//
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//
//        btnSelectImage = (Button) findViewById(R.id.btnSelectImage);
//   //     btnDeleteImage =  findViewById(R.id.btndeleteImage);
//        imgView = (AppCompatImageView) findViewById(R.id.imgView);
////        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
//
//        DatabaseHelper db = new DatabaseHelper(this);
//        final SQLiteDatabase dbHelper = db.getReadableDatabase();
//        Cursor mems = dbHelper.rawQuery("SELECT time_table from bunk",null);
//        if (mems.getCount()>0){
//////            btnSelectImage.setVisibility(View.INVISIBLE);
////            Toast.makeText(this, ""+mems.getCount(), Toast.LENGTH_SHORT).show();
//            loadImageFromDB();
//       }
//
////        else{
////            btnDeleteImage.setVisibility(View.INVISIBLE);
////        }
//
////        btnDeleteImage.setOnClickListener(new View.OnClickListener() {
////
////            @Override
////            public void onClick(View view) {
////                Cursor meme = dbHelper.rawQuery("UPDATE bunk SET time_table = NULL",null);
////                Toast.makeText(Time_table.this, ""+meme.getCount(), Toast.LENGTH_SHORT).show();
////               imgView.setImageDrawable(null);
////            }
////        });
//
//
//        btnSelectImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openImageChooser();
//            }
//        });
//        // Create the Database helper object
//    }
//
//    // Show simple message using SnackBar
//    void showMessage(String message) {
//        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
//        snackbar.show();
//    }
//
//    // Choose an image from Gallery
//    void openImageChooser() {
//        Intent intent = new Intent(Intent.ACTION_PICK,
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
//    }
//
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            if (requestCode == SELECT_PICTURE) {
//
//                Uri selectedImageUri = data.getData();
//
//                if (null != selectedImageUri) {
//
//                    // Saving to Database...
//                    if (saveImageInDB(selectedImageUri)) {
//                       // showMessage("Image Saved in Database...");
//                        imgView.setImageURI(selectedImageUri);
//                    }
//
//                    // Reading from Database after 3 seconds just to show the message
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (loadImageFromDB()) {
////                                showMessage("Image Loaded from Database...");
//                            }
//                        }
//                    }, 3000);
//                }
//            }
//        }
//    }
//
//    // Save the
//    Boolean saveImageInDB(Uri selectedImageUri) {
//        try {
//            DatabaseHelper db = new DatabaseHelper(this);
//            SQLiteDatabase dbHelper = db.getReadableDatabase();
//            InputStream iStream = getContentResolver().openInputStream(selectedImageUri);
//            byte[] inputData = LoadImageTask.getBytes(iStream);
//            db.insertTimeTable(inputData);
//            dbHelper.close();
//            return true;
//        } catch (IOException ioe) {
//            Log.e(TAG, "<saveImageInDB> Error : " + ioe.getLocalizedMessage());
//            db.close();
//            return false;
//        }
//    }
//
//    Boolean loadImageFromDB() {
//        try {
//            DatabaseHelper db = new DatabaseHelper(this);
//            SQLiteDatabase dbHelper = db.getReadableDatabase();
//            byte[] bytes = db.retreiveImageFromDB();
//            dbHelper.close();
//            // Show Image from DB in ImageView
//            imgView.setImageBitmap(LoadImageTask.getImage(bytes));
//            return true;
//        } catch (Exception e) {
//            Log.e(TAG, "<loadImageFromDB> Error : " + e.getLocalizedMessage());
//            return false;
//        }
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId()==android.R.id.home){
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();  //Takes you to the previous activity by default
//    }
//}