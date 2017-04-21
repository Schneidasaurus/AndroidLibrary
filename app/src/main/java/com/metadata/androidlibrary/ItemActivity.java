package com.metadata.androidlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.*;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.metadata.LibraryDomain.InventoryItem;

import java.util.ArrayList;

public class ItemActivity extends AppCompatActivity {

    private Intent i;
    String memberID;
    String memberName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        i = getIntent();
        String item_id = i.getStringExtra("item_id");
        memberID = i.getStringExtra("member_id");
        memberName = i.getStringExtra("member_name");
        ImageView home = (ImageView) findViewById(R.id.home_icon);
        ImageView account = (ImageView) findViewById(R.id.account_icon);
        TextView t = (TextView) findViewById(R.id.message);
        t.setText("" + memberID + " " + memberName);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemActivity.this, MainActivity.class);
                intent.putExtra("member_id", memberID);
                intent.putExtra("member_name", memberName);
                ItemActivity.this.startActivity(intent);
                finish();
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemActivity.this, AccountActivity.class);
                intent.putExtra("member_id", memberID);
                intent.putExtra("member_name", memberName);
                ItemActivity.this.startActivity(intent);
                finish();
            }
        });

    }
}
