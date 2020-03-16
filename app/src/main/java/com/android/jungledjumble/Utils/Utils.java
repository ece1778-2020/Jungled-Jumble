package com.android.jungledjumble.Utils;

import android.app.Activity;
import android.util.Log;

import com.android.jungledjumble.Models.Pair;
import com.android.jungledjumble.Models.Sizes;
import com.android.jungledjumble.Models.UserResults;

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

    public Pair GetOrangeSizes(final ArrayList<Integer> indices){
        Sizes sizes = new Sizes ();
        double [][] mat = sizes.getMat ();
        Integer k = indices.get(0);
        indices.remove(0);
        List<Double> sizes_small = new ArrayList<Double>();
        for (int i=0;i<4;i++){
            sizes_small.add(mat[k][i]);
            sizes_small.add(mat[k][i]);  //repeat to get 8 sizes
        }
        Collections.shuffle (sizes_small);
        List<Double> sizes_large = new ArrayList<Double>();
        for (int i=4;i<8;i++){
            sizes_large.add(mat[k][i]);
            sizes_large.add(mat[k][i]);
        }
        Collections.shuffle (sizes_large);

        double[] arraySmall = new double[12];
        double[] arrayLarge = new double[12];
        int i = 0,j= 0;
        while (i<12){
            if (!(i == 0 || i==3 || i==8 ||i ==11)){
                arraySmall[i] = sizes_small.get(j);
                arrayLarge[i] = sizes_large.get(j);
                j ++;
            }
            i ++;
        }
//        Log.d("TAG",arraySmall.toString ());

        Pair pair = new Pair(arraySmall,arrayLarge);
        return pair;
    }


    public Integer[] getOrangeSizes_old2(int min, int max){
        int total = 12;
        List<Integer> solution = new ArrayList<> ();
        solution.add(0);
        solution.add(getRandomNumber (min, max));
        solution.add(getRandomNumber (min, max));
        solution.add(0);
        solution.add(getRandomNumber (min, max));
        solution.add(getRandomNumber (min, max));
        solution.add(getRandomNumber (min, max));
        solution.add(getRandomNumber (min, max));
        solution.add(0);
        solution.add(getRandomNumber (min, max));
        solution.add(getRandomNumber (min, max));
        solution.add(0);


        Integer[] array = new Integer[total];

        solution.toArray (array);

        return array;

    }

    public Integer[] getOrangeSizes_old(int n,int min, int max,int total){

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

    public String getCorrectRate(UserResults userResults, int TOTAL_LEVELS){
        double correct_choice_d = (double)userResults.getPoints ()/TOTAL_LEVELS*100;
        int correct_choice_rate = (int)correct_choice_d;
        return String.valueOf (correct_choice_rate);
    }
}
