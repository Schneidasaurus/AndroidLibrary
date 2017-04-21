package com.metadata.androidlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.*;
import android.widget.TextView;

import com.metadata.LibraryDomain.InventoryItem;

import java.util.ArrayList;

public class ItemActivity extends AppCompatActivity {

    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        i = getIntent();
        String item_id = i.getStringExtra("item_id");
        TextView tv = (TextView) findViewById(R.id.message);

        tv.setText(item_id);
    }
}
