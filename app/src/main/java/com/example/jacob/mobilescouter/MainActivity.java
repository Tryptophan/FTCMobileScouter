package com.example.jacob.mobilescouter;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv = (ListView)findViewById(R.id.scouts);
        ArrayAdapter<String> arrayAdapter =  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, initList());
        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fileName = (String)parent.getItemAtPosition(position);
                File file = new File(Environment.getExternalStorageDirectory() + "/ftcMobileScouter/" + fileName);

                if (fileName.contains("Team_")) {
                    try {
                        Intent intent = new Intent(view.getContext(), PitLoadActivity.class);
                        Scanner fileScanner = new Scanner(file);

                        String data = fileScanner.nextLine();
                        intent.putExtra("data", data);

                        fileScanner.close();
                        startActivity(intent);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        Intent intent = new Intent(view.getContext(), FieldLoadActivity.class);
                        Scanner fileScanner = new Scanner(file);

                        String data = fileScanner.nextLine();
                        intent.putExtra("data", data);

                        fileScanner.nextLine();

                        String extras = fileScanner.nextLine();
                        intent.putExtra("extras", extras);

                        fileScanner.close();
                        startActivity(intent);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Button fieldScout = (Button)findViewById(R.id.fieldScout);
        Button pitScout = (Button)findViewById(R.id.pitScout);

        fieldScout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FieldActivity.class);
                startActivity(intent);
            }
        });

        pitScout.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
               Intent intent = new Intent(view.getContext(), PitActivity.class);
               startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<String> initList() {
        ArrayList<String> scouts = new ArrayList<String>();
        File directory = new File(Environment.getExternalStorageDirectory()  + "/ftcMobileScouter/");

        if (!directory.exists()) {
            directory.mkdir();
        }

        File[] dirFiles = directory.listFiles();

        for (int i = 0; i < dirFiles.length; i++) {
            scouts.add(dirFiles[i].getName());
        }

        return scouts;
    }
}
