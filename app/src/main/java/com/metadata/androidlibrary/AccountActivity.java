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
        final Intent i = getIntent();

        final String memberID = i.getStringExtra("member_id");
        final String memberName = i.getStringExtra("member_name");

        account_id.setText("ID: " + memberID);
        account_name.setText("Name: " + memberName);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                AccountActivity.this.startActivity(intent);
                finish();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                intent.putExtra("member_id", memberID);
                intent.putExtra("member_name", memberName);
                AccountActivity.this.startActivity(intent);
                finish();
            }
        });
    }
}
