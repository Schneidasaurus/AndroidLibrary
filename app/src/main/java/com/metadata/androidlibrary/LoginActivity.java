package com.metadata.androidlibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import org.xml.sax.SAXException;

import java.io.*;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button b = (Button)findViewById(R.id.login_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //call inputs by tag ids
                EditText password = (EditText) findViewById(R.id.password);
                EditText username = (EditText) findViewById(R.id.username);
                TextView errorMessage = (TextView) findViewById(R.id.error_message);

                //convert editable file type into string type
                String holdPass = password.getText().toString();
                String holdID = username.getText().toString();
                ArrayList<Member> memberList = new ArrayList<Member>();
                FileProcessor fileProcessor = new FileProcessor();

                try {
                    InputStream input = getAssets().open("member.xml");
                    memberList = fileProcessor.readMemberXML(input);
                }
                catch (IOException e) {e.printStackTrace();}
                catch (ParserConfigurationException e) {e.printStackTrace();}
                catch (SAXException e) {e.printStackTrace();}

                //login condition
                for(int i = 0; i < memberList.size(); i++){
                    //if authentication succeed
                    if(memberList.get(i).getPassword().equals(holdPass) &&
                            memberList.get(i).getID().equals(holdID)){

                        //set edittext in login page to be empty
                        username.setText("");
                        password.setText("");

                        //jump to main page after authentication succeed
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("member_id", memberList.get(i).getID());
                        intent.putExtra("member_name", memberList.get(i).getName());
                        LoginActivity.this.startActivity(intent);

                        errorMessage.setText("");
                        break;
                    }
                    else{
                        //display erro message if authentication failed
                        errorMessage.setText("Username or Password Invalid");
                    }
                }
            }
        });
    }
}
