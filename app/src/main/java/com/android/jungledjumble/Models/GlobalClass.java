package com.android.jungledjumble.Models;

import android.app.Application;

public class GlobalClass extends Application{

    private int meanLeft;
    private int meanRight;
    private int updateSize;
    private int accThreshold;

    public GlobalClass() {
        this.meanLeft = 100;
        this.meanRight = 130;
        this.updateSize = 4;
        this.accThreshold = 60;
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
}