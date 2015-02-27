package com.quadx.jacob.mobilescouter;

import java.util.ArrayList;

/**
 * Created by greenway_881124 on 2/13/2015.
 */
public class Field extends Scout {
    private String fileName;
    private String roundNum;
    private String division;
    private String blueTeam1;
    private String blueTeam2;
    private String redTeam1;
    private String redTeam2;
    private String blueTeamScore;
    private String redTeamScore;
    private String blueTeamNotes;
    private String redTeamNotes;

    public Field() {
        fileName = "";
        roundNum = "";
        division = "";
        blueTeam1 = "";
        blueTeam2 = "";
        redTeam1 = "";
        redTeam2 = "";
        blueTeamScore = "";
        redTeamScore = "";
        blueTeamNotes = "";
        redTeamNotes = "";
    }

    public boolean containsEmptyString() {
        boolean containsEmptyString = false;

        ArrayList<String> strings = new ArrayList<String>();
        strings.add(roundNum);
        strings.add(redTeam1);
        strings.add(redTeam2);
        strings.add(blueTeam1);
        strings.add(blueTeam2);
        strings.add(redTeamScore);
        strings.add(blueTeamScore);

        for (int i = 0; i < strings.size(); i++) {
            if (strings.get(i).equals("")) {
                containsEmptyString = true;
            }
        }

        return containsEmptyString;
    }

    public Field(String rN, String div, String rT1, String rT2, String bT1, String bT2, String rTScore, String bTScore, String rTNotes, String bTNotes) {
        fileName = "Round_" + rN + "_" + div + "_Division";
        roundNum = rN;
        division = div;
        redTeam1 = rT1;
        redTeam2 = rT2;
        blueTeam1 = bT1;
        blueTeam2 = bT2;
        redTeamScore = rTScore;
        blueTeamScore = bTScore;
        redTeamNotes = rTNotes;
        blueTeamNotes = bTNotes;
    }

    public String writeFile() {
        String fileContent = roundNum + " " + redTeam1 + " " + redTeam2 + " " + blueTeam1 + " " + blueTeam2 + " " + redTeamScore + " " + blueTeamScore + "\n";
        return fileContent;
    }

    public String writeExtras() {
        return ("EXTRAS \n Division: " + division + "Red Team Notes: " + redTeamNotes + "Blue Team Notes: " + blueTeamNotes);
    }

    @Override
    public String toString() {
        return fileName;
    }
}
