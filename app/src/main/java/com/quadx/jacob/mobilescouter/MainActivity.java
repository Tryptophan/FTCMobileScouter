package com.quadx.jacob.mobilescouter;

import android.app.Activity;
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
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabs = (TabHost)findViewById(R.id.scoutTabs);
        tabs.setup();

        TabHost.TabSpec fieldTab = tabs.newTabSpec("Field");
        fieldTab.setContent(R.id.Field);
        fieldTab.setIndicator("Field");
        tabs.addTab(fieldTab);

        TabHost.TabSpec pitTab = tabs.newTabSpec("Pit");
        pitTab.setContent(R.id.Pit);
        pitTab.setIndicator("Pit");
        tabs.addTab(pitTab);

        // Instantiate and fill spinners with content
        String[] fieldSpinnerArray = {"Sort by: Match Number", "Sort by: Match Score"};
        Spinner fieldSpinner = (Spinner)findViewById(R.id.fieldSpinner);
        ArrayAdapter<String> fieldAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, fieldSpinnerArray);
        fieldSpinner.setAdapter(fieldAdapter);

        String[] pitSpinnerArray = {"Sort by: Team Number", "Sort by: Autonomous Score", "Sort by: Teleop Score"};
        Spinner pitSpinner = (Spinner)findViewById(R.id.pitSpinner);
        ArrayAdapter<String> pitAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pitSpinnerArray);
        pitSpinner.setAdapter(pitAdapter);

        // Instantiate and fill listViews with corresponding ArrayLists using initList()
        final ListView fieldListView = (ListView)findViewById(R.id.fieldScouts);
        ArrayAdapter<String> fieldArrayAdapter =  new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, initList("field", "matchNumber"));
        fieldListView.setAdapter(fieldArrayAdapter);

        final ListView pitListView = (ListView)findViewById(R.id.pitScouts);
        ArrayAdapter<String> pitArrayAdapter =  new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, initList("pit", "teamNumber"));
        pitListView.setAdapter(pitArrayAdapter);

        fieldSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
                if (position == 0) {
                    ArrayAdapter<String> newArrayAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, initList("field", "matchNumber"));
                    fieldListView.setAdapter(newArrayAdapter);
                }
                else if (position == 1) {
                    ArrayAdapter<String> newArrayAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, initList("field", "matchScore"));
                    fieldListView.setAdapter(newArrayAdapter);
                }
            }
            public void onNothingSelected(AdapterView<?> arg0) { }
        });

        pitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
                if (position == 0) {
                    ArrayAdapter<String> newArrayAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, initList("pit", "teamNumber"));
                    pitListView.setAdapter(newArrayAdapter);
                }
                else if (position == 1) {
                    ArrayAdapter<String> newArrayAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, initList("pit", "autoScore"));
                    pitListView.setAdapter(newArrayAdapter);
                }
                else if (position == 2) {
                    ArrayAdapter<String> newArrayAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, initList("pit", "teleopScore"));
                    pitListView.setAdapter(newArrayAdapter);
                }
            }
            public void onNothingSelected(AdapterView<?> arg0) { }
        });


        fieldListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fileName = (String)parent.getItemAtPosition(position);
                File file = new File(Environment.getExternalStorageDirectory() + "/ftcMobileScouter/" + fileName);

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
        });

        pitListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fileName = (String)parent.getItemAtPosition(position);
                File file = new File(Environment.getExternalStorageDirectory() + "/ftcMobileScouter/" + fileName);

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

    public ArrayList<String> initList(String listType, String sortBy) {
        ArrayList<String> scouts = new ArrayList<>();
        File directory = new File(Environment.getExternalStorageDirectory()  + "/ftcMobileScouter/");

        if (!directory.exists()) {
            directory.mkdir();
        }

        File[] dirFiles = directory.listFiles();

        for (int i = 0; i < dirFiles.length; i++) {
            if (listType.equals("field") && dirFiles[i].getName().contains("Division")) {
                scouts.add(dirFiles[i].getName());
            }
            else if (listType.equals("pit") && dirFiles[i].getName().contains("Team_")) {
                scouts.add(dirFiles[i].getName());
            }
        }

        scouts = sortScoutList(listType, sortBy);
        return scouts;
    }

    public ArrayList<String> sortScoutList(String listType, String sortBy) {
        ArrayList<File> scouts = new ArrayList<>();
        File directory = new File(Environment.getExternalStorageDirectory()  + "/ftcMobileScouter/");
        File[] dirFiles = directory.listFiles();

        ArrayList<Sorter> sortedScouts = new ArrayList<>();

        if (listType.equals("field")) {
            // Add all field scouts to the dirFiles array to return later
            for (int i = 0; i < dirFiles.length; i++) {
                if (dirFiles[i].getName().contains("Division")) {
                    scouts.add(dirFiles[i]);
                }
            }

            // If the user chooses the matchNumber spinner option, sort the scouts by matchNumber
            if (sortBy.equals("matchNumber")) {
                for (int i = 0; i < scouts.size(); i++) {
                    try {
                        Scanner fileScanner = new Scanner(scouts.get(i));
                        int numSorter = fileScanner.nextInt();
                        sortedScouts.add(new Sorter(numSorter, scouts.get(i).getName()));
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                Collections.sort(sortedScouts);
            }

            // If the user chooses the highScore spinner option, sort the scouts by highScore
            else if (sortBy.equals("matchScore")) {
                for (int i = 0; i < scouts.size(); i++) {
                    try {
                        Scanner fileScanner = new Scanner(scouts.get(i));
                        for (int j = 0; j < 5; j++) {
                            fileScanner.next();
                        }

                        int numSorter = fileScanner.nextInt();
                        int temp = fileScanner.nextInt();
                        if (numSorter < temp) {
                            numSorter = temp;
                        }

                        sortedScouts.add(new Sorter(numSorter, scouts.get(i).getName()));
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                Collections.sort(sortedScouts, Collections.reverseOrder());
            }
        }

        else if (listType.equals("pit")) {
            for (int i = 0; i < dirFiles.length; i++) {
                if (dirFiles[i].getName().contains("Team_")) {
                    scouts.add(dirFiles[i]);
                }
            }

            if (sortBy.equals("teamNumber")) {
                for (int i = 0; i < scouts.size(); i++) {
                    try {
                        Scanner fileScanner = new Scanner(scouts.get(i));
                        fileScanner.useDelimiter("\\|");
                        int numSorter = fileScanner.nextInt();
                        sortedScouts.add(new Sorter(numSorter, scouts.get(i).getName()));
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                Collections.sort(sortedScouts);
            }

           else if (sortBy.equals("autoScore")) {
                for (int i = 0; i < scouts.size(); i++) {
                    try {
                        Scanner fileScanner = new Scanner(scouts.get(i));
                        fileScanner.useDelimiter("\\|");
                        fileScanner.nextInt();
                        int numSorter = fileScanner.nextInt();
                        sortedScouts.add(new Sorter(numSorter, scouts.get(i).getName()));
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                Collections.sort(sortedScouts, Collections.reverseOrder());
            }

            else if (sortBy.equals("teleopScore")) {
                for (int i = 0; i < scouts.size(); i++) {
                    try {
                        Scanner fileScanner = new Scanner(scouts.get(i));
                        fileScanner.useDelimiter("\\|");
                        fileScanner.nextInt();
                        fileScanner.nextInt();
                        int numSorter = fileScanner.nextInt();
                        sortedScouts.add(new Sorter(numSorter, scouts.get(i).getName()));
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                Collections.sort(sortedScouts, Collections.reverseOrder());
            }
        }

        for (int i = 0; i < sortedScouts.size(); i++) {
            System.out.println(sortedScouts.get(i).getNumSorter());
        }

        ArrayList<String> sortedFileNames = new ArrayList<>();

        for (int i = 0; i < sortedScouts.size(); i++) {
            sortedFileNames.add(sortedScouts.get(i).toString());
        }

        return sortedFileNames;
    }
}
