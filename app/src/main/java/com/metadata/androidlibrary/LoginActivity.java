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
    final static String admin = "500";
    final static String user = "100";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button b = (Button)findViewById(R.id.login_button);

        //conditions when login button clicked
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //call inputs by tag ids
                EditText password = (EditText) findViewById(R.id.password);
                EditText username = (EditText) findViewById(R.id.username);
                TextView errorMessage = (TextView) findViewById(R.id.error_message);

                //convert editable text type into string type
                String holdPass = password.getText().toString();
                String holdID = username.getText().toString();
                ArrayList<Member> memberList = new ArrayList<Member>();
                FileProcessor fileProcessor = new FileProcessor();

                //call member.xml file from assets folder
                //assign memberList to hold member elements from FileProcessor class
                try {
                    InputStream input = getAssets().open("member.xml");
                    memberList = fileProcessor.readMemberXML(input);
                }
                catch (IOException e) {System.out.println("Bad Input/Output");}
                catch (ParserConfigurationException e) {System.out.println("File Cannot Parse");}
                catch (SAXException e) {System.out.println("Invalid XML Element");}

                //login condition
                for(int i = 0; i < memberList.size(); i++){

                    //if authentication succeed
                    if(memberList.get(i).getPassword().equals(holdPass) &&
                            memberList.get(i).getID().equals(holdID) &&
                            memberList.get(i).getPrivilege().equals(user)){

                        //set edittext in login page to be empty
                        username.setText("");
                        password.setText("");

                        //jump to main page after authentication accomplished
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("member_id", memberList.get(i).getID());
                        intent.putExtra("member_name", memberList.get(i).getName());
                        LoginActivity.this.startActivity(intent);
                        finish();

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

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
