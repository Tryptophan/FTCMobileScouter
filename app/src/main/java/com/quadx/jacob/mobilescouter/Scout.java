package com.quadx.jacob.mobilescouter;

/**
 * Created by greenway_881124 on 2/13/2015.
 */
public class Scout {
    private String fileName;
    public Scout() {
        fileName = "";
    }
    public Scout(String f) {
        fileName = f;
    }
    public String toString() {
        return fileName;
    }
}
