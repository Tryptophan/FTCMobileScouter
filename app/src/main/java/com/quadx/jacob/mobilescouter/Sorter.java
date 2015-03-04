package com.quadx.jacob.mobilescouter;

/**
 * Created by greenway_881124 on 3/1/2015.
 */
public class Sorter implements Comparable {
    private int numSorter;
    private String fileName;

    public Sorter() {
        numSorter = 0;
        fileName = "";
    }

    public Sorter(int nS, String fN) {
        numSorter = nS;
        fileName = fN;
    }

    public int getNumSorter() {
        return numSorter;
    }

    public int compareTo(Object o) {
        Sorter sorter = (Sorter)o;
        int compare = (getNumSorter() > sorter.getNumSorter()) ? 1 : 0;
        if(compare == 0){
            compare = (getNumSorter() == sorter.getNumSorter()) ? 0 : -1;
        }
        return compare;
    }

    public String toString() {
       return fileName;
    }
}
