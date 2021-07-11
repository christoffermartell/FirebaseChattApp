package com.example.firebasechattapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListDataActivity extends AppCompatActivity {


    private static final String TAG = "ListDataActivity";
    DatabaseHelper mDatabaseHelper;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);
        mListView = findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);

        messageListView();


    }

    private void messageListView() {


        Log.d(TAG, "messageListView: Displaying data in the ListView.");
        //get the data abd append to a list
        Cursor data = mDatabaseHelper.getData();

        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()){
            // get the value from the database in column 1
            //then add it to the ArrayList
            listData.add(data.getString(1));
        }
        //Create the list adapter and ser the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listData);
        mListView.setAdapter(adapter);

    }
}