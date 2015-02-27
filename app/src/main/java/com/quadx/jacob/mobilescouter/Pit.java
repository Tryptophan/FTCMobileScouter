package com.quadx.jacob.mobilescouter;

import java.util.ArrayList;

/**
 * Created by greenway_881124 on 2/13/2015.
 */
public class Pit extends Scout {
    private String fileName;
    private String teamNumber;
    private String autoScore;
    private String teleopScore;
    private String autoNotes;
    private String teleopNotes;
    private String avgScore;

    public Pit() {
        fileName = "new Pit";
        teamNumber = "";
        autoScore = "";
        teleopScore = "";
        autoNotes = "";
        teleopNotes = "";
        avgScore = "";
    }

    public Pit(String tNum, String aScore, String tScore, String aNotes, String tNotes, String avg) {
        fileName = "Team_" + tNum;
        teamNumber = tNum;
        autoScore = aScore;
        teleopScore = tScore;
        autoNotes = aNotes;
        teleopNotes = tNotes;
        avgScore = avg;
    }

    public boolean containsEmptyString() {
        boolean containsEmptyString = false;
        ArrayList<String> strings = new ArrayList<>();

        strings.add(teamNumber);
        strings.add(autoScore);
        strings.add(teleopScore);
        strings.add(autoNotes);
        strings.add(teleopNotes);
        strings.add(avgScore);

        for (int i = 0; i < strings.size(); i++) {
            if (strings.get(i).equals("")) {
                containsEmptyString = true;
            }
        }

        return containsEmptyString;
    }

    public String writeFile() {
        return teamNumber + "/" + autoScore + "/" + teleopScore + "/" + autoNotes + "/" + teleopNotes + "/" + avgScore;
    }

    @Override
    public String toString() {
        return fileName;
    }
}
