package com.android.jungledjumble.Utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Utils {
    private Activity mActivity;
    public Utils(Activity activity){
        this.mActivity = activity;
    }
    public int getRandomNumber(int min,int max){
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
    public Integer[] getOrangeSizes(int n,int min, int max,int total){

        List<Integer> solution = new ArrayList<> ();
        for (int i = 0; i < n; i++) {
            solution.add(getRandomNumber (min, max));
        }
        for (int i = n; i < total; i++) {
            solution.add(0);
        }

        Collections.shuffle(solution);
        Integer[] array = new Integer[total];

        solution.toArray (array);

        return array;
    }

    public int getSum(Integer[] arr){
        int sum = 0;
        for (int i : arr){
            sum += i;
        }
        return sum;
    }
}
