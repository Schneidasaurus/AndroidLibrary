package com.metadata.androidlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.content.*;
import android.view.*;

public class MainActivity extends AppCompatActivity {

    private String memberID;
    private String memberName;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView home = (ImageView) findViewById(R.id.home_icon);
        ImageView account = (ImageView) findViewById(R.id.account_icon);

        i = getIntent();
        memberID = i.getStringExtra("member_id");
        memberName = i.getStringExtra("member_name");

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                intent.putExtra("memberID", memberID);
                intent.putExtra("memberName", memberName);
                MainActivity.this.startActivity(intent);
            }
        });
    }
}
