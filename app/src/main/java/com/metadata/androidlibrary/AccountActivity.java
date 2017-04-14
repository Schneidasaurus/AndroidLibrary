package com.metadata.androidlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.content.*;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Button b = (Button) findViewById(R.id.logout_button);
        ImageView home = (ImageView) findViewById(R.id.home_icon2);
        TextView account_name = (TextView) findViewById(R.id.account_name);
        TextView account_id = (TextView) findViewById(R.id.account_id);
        Intent i = getIntent();

        String memberID = i.getStringExtra("memberID");
        String memberName = i.getStringExtra("memberName");

        account_id.setText("ID: " + memberID);
        account_name.setText("Name: " + memberName);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                AccountActivity.this.startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                AccountActivity.this.startActivity(intent);
            }
        });
    }
}
