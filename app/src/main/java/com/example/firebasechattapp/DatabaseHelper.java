package com.example.firebasechattapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DataBaseHelper";

    private static final String TABLE_NAME = "message_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "message";

    public DatabaseHelper (Context context){
        super(context,TABLE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP = "DROP TABLE IF EXISTS ";
        db.execSQL(DROP + TABLE_NAME);
        onCreate(db);

    }
    public boolean addData(String item){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,item);

        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME,null,contentValues);

        // if data is inserted incorrectly it will return -1
        if (result== -1){
            return false;
        }else{
            return true;
        }

    }
    //Return all the data from database
    public Cursor getData(){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;

    }
}
