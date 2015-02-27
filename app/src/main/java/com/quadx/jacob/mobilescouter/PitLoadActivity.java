package com.quadx.jacob.mobilescouter;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class PitLoadActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pit_load);

        Bundle bundle = getIntent().getExtras();
        Button saveButton = (Button)findViewById(R.id.pitSaveButton);

        String data = bundle.getString("data");

        Scanner dataScanner = new Scanner(data);
        dataScanner.useDelimiter("/");
        ArrayList<EditText> editTexts = new ArrayList<>();

        editTexts.add((EditText)findViewById(R.id.teamNumber));
        editTexts.add((EditText)findViewById(R.id.autoScore));
        editTexts.add((EditText)findViewById(R.id.teleopScore));
        editTexts.add((EditText)findViewById(R.id.autoNotes));
        editTexts.add((EditText)findViewById(R.id.teleopNotes));
        editTexts.add((EditText)findViewById(R.id.avgScore));

        for (int i = 0; i < editTexts.size(); i++) {
            editTexts.get(i).setText(dataScanner.next());
        }

        dataScanner.close();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String teamNumber = ((EditText) findViewById(R.id.teamNumber)).getText().toString();
                final String autoScore = ((EditText) findViewById(R.id.autoScore)).getText().toString();
                final String teleopScore = ((EditText) findViewById(R.id.teleopScore)).getText().toString();
                final String autoNotes = ((EditText) findViewById(R.id.autoNotes)).getText().toString();
                final String teleopNotes = ((EditText) findViewById(R.id.teleopNotes)).getText().toString();
                final String avgScore = ((EditText) findViewById(R.id.avgScore)).getText().toString();

                Pit pitScout = new Pit(teamNumber, autoScore, teleopScore, autoNotes, teleopNotes, avgScore);
                File directory = new File(Environment.getExternalStorageDirectory() + "/ftcMobileScouter/");

                if (pitScout.containsEmptyString()) {
                    Toast toast = Toast.makeText(view.getContext(), "Please fill in all fields before saving.", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    if (!directory.exists()) {
                        directory.mkdir();
                    }

                    File file = new File(directory, pitScout.toString());

                    try {
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(pitScout.writeFile().getBytes());
                        fos.close();

                        Toast toast = Toast.makeText(view.getContext(), "Scout successfully saved!", Toast.LENGTH_LONG);
                        toast.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast toast = Toast.makeText(view.getContext(), "Error, file not saved!", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pit, menu);
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
}