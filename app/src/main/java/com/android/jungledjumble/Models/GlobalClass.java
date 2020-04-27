package com.android.jungledjumble.Models;

import android.app.Application;

public class GlobalClass extends Application{

    private int meanLeft;
    private int meanRight;
    private int updateSize;
    private int accThreshold;
    private String fruit_lock;
    private String char_lock;

    public GlobalClass() {
        this.meanLeft = 100;
        this.meanRight = 130;
        this.updateSize = 4;
        this.accThreshold = 60;
        this.fruit_lock = "4 5";
        this.char_lock = "1";
    }


    public int getMeanLeft() {
        return meanLeft;
    }

    public void setMeanLeft(int meanLeft) {
        this.meanLeft = meanLeft;
    }

    public int getMeanRight() {
        return meanRight;
    }

    public void setMeanRight(int meanRight) {
        this.meanRight = meanRight;
    }

    public int getUpdateSize() {
        return updateSize;
    }

    public void setUpdateSize(int updateSize) {
        this.updateSize = updateSize;
    }

    public int getAccThreshold() {
        return accThreshold;
    }

    public void setAccThreshold(int accThreshold) {
        this.accThreshold = accThreshold;
    }

    public String getFruit_lock() {
        return fruit_lock;
    }

    public void setFruit_lock(String fruit_lock) {
        this.fruit_lock = fruit_lock;
    }

    public String getChar_lock() {
        return char_lock;
    }

    public void setChar_lock(String char_lock) {
        this.char_lock = char_lock;
    }
}