package com.android.jungledjumble.Models;

import android.app.Application;

public class GlobalClass extends Application{

    private int meanLeft;
    private int meanRight;


    public GlobalClass() {
        this.meanLeft = 100;
        this.meanRight = 130;
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
}