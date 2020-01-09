package com.rushabh.nasikapp.safebunking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

// SQLiteOpenHelper is a base class for the SQLIte database
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "abc.db";
    public static final String TABLE_NAME = "bunk";
    public static final String col_1 = "id";
    public static final String col_2 = "subject_name";
    public static final String col_3 = "total_lectures";
    public static final String col_4 = "bunked_lectures";
    public static final String col_5="time_table";
    public static final String col_6="notes";
    private static final String IMAGE ="image" ;
    public static final String IMAGE_ID = "id";
    DataModel dataModel;

    // Constructor for the database helper class
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 67);
    }

    // onCreate overriden method to write a create table query.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (subject_name varchar,bunked_lectures varchar,total_lectures varchar,time_table blob,notes varhcar(3000) default ' ',primary key(subject_name))");
    }

    //onUpgrade method to perform operations when the database is upgraded
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTs " + TABLE_NAME);
        onCreate(db);
    }

    // the insertData method to insert the values in the database. Pass the parameters which are the column names in the database.
    public boolean insertData(String subject_name, String total_lectures, String bunked_lectures) {
        // create an instance of SQLiteDatabase to convert it into writable mode
        SQLiteDatabase db = this.getWritableDatabase();
        /* ContentValue class lets you put information inside an object in the form of Key-Value pairs
         for columns and their value. The object can then be passed to the insert() method of an instance
         of the SQLiteDatabase class to insert or update your WritableDatabase.*/
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2, subject_name);
        contentValues.put(col_3,total_lectures);
        contentValues.put(col_4, bunked_lectures);

        // result is set to -1 if there is an error in inserting data into the database.
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public boolean insertNotes(String notes){

//        SQLiteDatabase DB=getWritableDatabase();
//        ContentValues cv=new ContentValues();
//        cv.put(col_6,notes);
//        long result=DB.insert(TABLE_NAME,null,cv);
//        DB.close();
//        Context context = null;
//       Log.d("Inserted",notes);
//        if(result!=-1) {
////            Toast.makeText(context, "Data Not Inserted", Toast.LENGTH_SHORT).show();
//            Log.d("Tag","Data inserted");
//            return false;
//        }
//        else {
////            Toast.makeText(context, "Data Inserted", Toast.LENGTH_SHORT).show();
//            Log.d("Tag","Data not inserted");
//            return true;
//        }
        SQLiteDatabase db=this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();

        db.execSQL("update "+TABLE_NAME+" set notes="+" 'where subject_name='" +dataModel.getSubject_name()+"'");
//        db.update(TABLE_NAME,contentValues,"id=?",new String[]{ id });
        return true;
    }
    public boolean insertTimeTable(byte[] time_table)
    {
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("UPDATE bunk SET time_table = NULL");
        ContentValues c=new ContentValues();
        c.put("time_table",time_table);
        long result=db.insert(TABLE_NAME,null,c);
        if(result==-1)return false;
        else return true;
    }

//    public byte[] retreiveImageFromDB() {
//        SQLiteDatabase db=getWritableDatabase();
//        Cursor cur = db.query(true, TABLE_NAME, new String[]{col_5,},
//                null, null, null, null,
//                null + " DESC", "1");
//        cur.moveToLast();
//        if (cur.moveToLast()) {
//            byte[] blob = cur.getBlob(cur.getColumnIndex(col_5));
//            cur.close();
//            return blob;
//        }
//        cur.close();
//        return null;
//    }

    // this methods retrieve the data
    public List<DataModel> getdata() {
        List<DataModel> data = new ArrayList();  //Create a list and pass the DataModel Class in it.
        SQLiteDatabase db = this.getWritableDatabase(); // Instance of SQLiteDatabase
        /* The cursor points to individual rows retuned after querying the database and you can use several
        methods like getInt(), getString() etc to extract information from specific columns of the current row.*/
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " ;", null);
        StringBuffer stringBuffer = new StringBuffer();
        DataModel dataModel = null;
        while (cursor.moveToNext()) {
            dataModel = new DataModel();
            // using cursor to get the column data.
            String subject_name = cursor.getString(cursor.getColumnIndexOrThrow("subject_name"));
            int total_lectures = cursor.getInt(cursor.getColumnIndexOrThrow("total_lectures"));
            int bunked_lectures = cursor.getInt(cursor.getColumnIndexOrThrow("bunked_lectures"));
            String notes = cursor.getString(cursor.getColumnIndexOrThrow("notes"));
            //using the datamodel object to store the subject name.
            dataModel.setSubject_name(subject_name);
            dataModel.setTotal_Lectures(total_lectures);
            dataModel.setBunked_lectures(bunked_lectures);
            dataModel.setNotes(notes);
            stringBuffer.append(dataModel);
            System.out.println(dataModel);
            // stringBuffer.append(dataModel);
            data.add(dataModel);
        }
        for (DataModel mo:data ) {
            Log.i("Hellomo",""+mo.getSubject_name());
        }
        return data;
    }
//    public void deleteRow(int Mobile){
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_NAME, "id = ?", new String[]{col_1});
//        db.close();
//    }
//
    public boolean update(String notes)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        ContentValues values=new ContentValues();
        values.put("name","aaa");
        values.put("publisher","ppp");
        values.put("price","111");

        db.update(TABLE_NAME,values,dataModel.getSubject_name(),null);

        return true;
    }
}