package com.rushabh.nasikapp.safebunking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 67);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (subject_name varchar,bunked_lectures varchar,total_lectures varchar,time_table blob,notes varhcar(3000) default ' ',primary key(subject_name))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTs " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String subject_name, String total_lectures, String bunked_lectures) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2, subject_name);
        contentValues.put(col_3,total_lectures);
        contentValues.put(col_4, bunked_lectures);

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

    public byte[] retreiveImageFromDB() {
        SQLiteDatabase db=getWritableDatabase();
        Cursor cur = db.query(true, TABLE_NAME, new String[]{col_5,},
                null, null, null, null,
                null + " DESC", "1");
        cur.moveToLast();
        if (cur.moveToLast()) {
            byte[] blob = cur.getBlob(cur.getColumnIndex(col_5));
            cur.close();
            return blob;
        }
        cur.close();
        return null;
    }

    public List<DataModel> getdata() {
        // DataModel dataModel = new DataModel();
        List<DataModel> data = new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " ;", null);
        StringBuffer stringBuffer = new StringBuffer();
        DataModel dataModel = null;
        while (cursor.moveToNext()) {
            dataModel = new DataModel();
            String subject_name = cursor.getString(cursor.getColumnIndexOrThrow("subject_name"));
            int total_lectures = cursor.getInt(cursor.getColumnIndexOrThrow("total_lectures"));
            int bunked_lectures = cursor.getInt(cursor.getColumnIndexOrThrow("bunked_lectures"));
            String notes=cursor.getString(cursor.getColumnIndexOrThrow("notes"));
            dataModel.setSubject_name(subject_name);
            dataModel.setTotal_Lectures(total_lectures);
            dataModel.setBunked_lectures(bunked_lectures);
            dataModel.setNotes(notes);
            stringBuffer.append(dataModel);
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