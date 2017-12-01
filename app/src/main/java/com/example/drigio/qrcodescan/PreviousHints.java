package com.example.drigio.qrcodescan;

import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PreviousHints extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_hints);


        listView = (ListView) findViewById(R.id.listView);
        databaseHelper = new DatabaseHelper(this);

        Cursor data = databaseHelper.getData();

        String[] columns = new String[] {
                databaseHelper.HINTNO,
                databaseHelper.HINT
        };

        int[] boundTo = new int[] {
                R.id.hno,
                R.id.mainHint
        };

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,R.layout.list_item,data,columns,boundTo,0);
        listView.setAdapter(simpleCursorAdapter);

    }



}
