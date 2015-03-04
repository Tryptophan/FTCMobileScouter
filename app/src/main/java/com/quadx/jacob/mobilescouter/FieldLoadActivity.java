package com.quadx.jacob.mobilescouter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
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


public class FieldLoadActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field);

        Bundle bundle = getIntent().getExtras();
        Button saveButton = (Button)findViewById(R.id.fieldSaveButton);

        String data = bundle.getString("data");
        String extras = bundle.getString("extras");

        Scanner dataScanner = new Scanner(data);
        ArrayList<EditText> editTexts = new ArrayList<>();

        editTexts.add((EditText)findViewById(R.id.matchNumber));
        editTexts.add((EditText)findViewById(R.id.red1Number));
        editTexts.add((EditText)findViewById(R.id.red2Number));
        editTexts.add((EditText)findViewById(R.id.blue1Number));
        editTexts.add((EditText)findViewById(R.id.blue2Number));
        editTexts.add((EditText)findViewById(R.id.redScore));
        editTexts.add((EditText)findViewById(R.id.blueScore));

        for (int i = 0; dataScanner.hasNext(); i++) {
            editTexts.get(i).setText(dataScanner.next());
        }

        dataScanner.close();

        ((EditText)findViewById(R.id.division)).setText((extras.substring(extras.indexOf("Division: "), extras.indexOf("Red Team Notes:"))).replace("Division: ", ""));
        ((EditText)findViewById(R.id.redNotes)).setText((extras.substring(extras.indexOf("Red Team Notes: "), extras.indexOf("Blue Team Notes:"))).replace("Red Team Notes: ", ""));
        ((EditText)findViewById(R.id.blueNotes)).setText((extras.substring(extras.indexOf("Blue Team Notes: "), extras.length())).replace("Blue Team Notes: ", ""));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String matchNumber = ((EditText)findViewById(R.id.matchNumber)).getText().toString();
                final String division = ((EditText)findViewById(R.id.division)).getText().toString();
                final String red1Number = ((EditText)findViewById(R.id.red1Number)).getText().toString();
                final String red2Number = ((EditText)findViewById(R.id.red2Number)).getText().toString();
                final String blue1Number = ((EditText)findViewById(R.id.blue1Number)).getText().toString();
                final String blue2Number = ((EditText)findViewById(R.id.blue2Number)).getText().toString();
                final String redScore = ((EditText)findViewById(R.id.redScore)).getText().toString();
                final String blueScore = ((EditText)findViewById(R.id.blueScore)).getText().toString();
                final String redNotes = ((EditText)findViewById(R.id.redNotes)).getText().toString();
                final String blueNotes = ((EditText)findViewById(R.id.blueNotes)).getText().toString();

                Field fieldScout = new Field(matchNumber, division, red1Number, red2Number, blue1Number, blue2Number, redScore, blueScore, redNotes, blueNotes);
                File directory = new File(Environment.getExternalStorageDirectory() + "/ftcMobileScouter/");

                if (fieldScout.containsEmptyString()) {
                    Toast toast = Toast.makeText(view.getContext(), "Please fill in all fields before saving.", Toast.LENGTH_LONG);
                    toast.show();
                }

                else {
                    if (!directory.exists()) {
                        directory.mkdir();
                    }

                    File file = new File(directory, fieldScout.toString());

                    try {
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(fieldScout.writeFile().getBytes());
                        fos.write(fieldScout.writeExtras().getBytes());
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
        getMenuInflater().inflate(R.menu.menu_field, menu);
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
