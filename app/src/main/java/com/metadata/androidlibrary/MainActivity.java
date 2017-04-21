package com.metadata.androidlibrary;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.content.*;
import android.view.*;
import android.widget.AdapterView.OnItemClickListener;
import android.graphics.*;

import com.metadata.LibraryDomain.InventoryItem;

import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    private String memberID;
    private String memberName;
    private Intent i;
    private ArrayList<LibraryItem> xmlData;
    private ArrayList<LibraryItem> jsonData;
    private ListView itemList;
    private LibraryAdapter adapter;
    private LibraryAdapter adapter2;
    private FileProcessor fp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView home = (ImageView) findViewById(R.id.home_icon);
        ImageView account = (ImageView) findViewById(R.id.account_icon);
        TextView t = (TextView) findViewById(R.id.header);
        final Button internal = (Button) findViewById(R.id.internal_button);
        final Button external = (Button) findViewById(R.id.external_button);
        itemList = (ListView) findViewById(R.id.display);
        fp = new FileProcessor();
        xmlData = new ArrayList<>();

        //load XML file
        try {
            InputStream input = getAssets().open("externalitem.xml");
            InputStream input2 = getAssets().open("JSONLib.json");
            xmlData = fp.processXMLData(input);
            jsonData = fp.processJSONData(input2);
        } catch (IOException e) {e.printStackTrace();}  catch (ParseException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {e.printStackTrace();
        } catch (SAXException e) {e.printStackTrace();}


        adapter = new LibraryAdapter(getApplicationContext(), jsonData);
        adapter2 = new LibraryAdapter(getApplicationContext(), xmlData);
        itemList.setAdapter(adapter);
        internal.setClickable(false);
        internal.setBackgroundColor(Color.parseColor("#000080"));


        internal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.setAdapter(adapter);
                external.setBackgroundColor(Color.parseColor("#0000FF"));
                internal.setBackgroundColor(Color.parseColor("#000080"));
                internal.setClickable(false);
                external.setClickable(true);
            }
        });


        external.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.setAdapter(adapter2);
                external.setBackgroundColor(Color.parseColor("#000080"));
                internal.setBackgroundColor(Color.parseColor("#0000FF"));
                internal.setClickable(true);
                external.setClickable(false);
            }
        });

        i = getIntent();
        memberID = i.getStringExtra("member_id");
        memberName = i.getStringExtra("member_name");

        itemList.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Item ID: " + view.getTag(), Toast.LENGTH_SHORT).show();
                view.setSelected(true);
                Intent intent = new Intent(MainActivity.this, ItemActivity.class);
                String holdID = (String) view.getTag();
                intent.putExtra("item_id", holdID);
                intent.putExtra("member_id", memberID);
                intent.putExtra("member_name", memberName);
                MainActivity.this.startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.putExtra("member_id", memberID);
                intent.putExtra("member_name", memberName);
                MainActivity.this.startActivity(intent);
                finish();
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                intent.putExtra("member_id", memberID);
                intent.putExtra("member_name", memberName);
                MainActivity.this.startActivity(intent);
                finish();
            }
        });
    }
}
