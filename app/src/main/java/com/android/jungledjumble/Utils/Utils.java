package com.android.jungledjumble.Utils;

import android.app.Activity;
import android.util.Log;
import android.view.View;

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


    public List<Integer> n_random(int targetSum, int num, int min, int max){
        Random r = new Random();
        List<Integer> load = new ArrayList<>();

        //random numbers
        int sum = 0;
        for (int i=0;i<num;i++){
            int next = r.nextInt (max-min+1);
            load.add(next);
            sum += next;
        }

        //scale to the desired target sum
        double scale = 1d * (targetSum-min*num) / sum;
        sum = 0;
        for (int i = 0; i < num; i++) {
            load.set(i, (int) (load.get(i) * scale + min));
            sum += load.get(i);
        }

        //take rounding issues into account
        while(sum++ < targetSum) {
            int i = r.nextInt(num);
            load.set(i, load.get(i) + 1);
        }
        return load;
    }

    public int[] genOrangeSizes(int meanS, int meanL, int min, int max, int num){

        List<Integer> sizeS = n_random (meanS*num,num,min,max);
        List<Integer> sizeL = n_random (meanL*num,num,min,max);

        int[] array = new int[24];

        int i = 0,j= 0;
        while (i<12){
            if (!(i == 0 || i==3 || i==8 ||i ==11)){
                array[i] = sizeS.get (j);
                j ++;
            }
            i ++;
        }
        j =0;
        while (i<24){
            if (!(i == 12 || i==15 || i==20 ||i ==23)){
                array[i] = sizeL.get (j);
                j ++;
            }
            i ++;
        }

        return array;
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

//    public Integer[] getOrangeSizes_old(int n,int min, int max,int total){
//
//        List<Integer> solution = new ArrayList<> ();
//        for (int i = 0; i < n; i++) {
//            solution.add(getRandomNumber (min, max));
//        }
//        for (int i = n; i < total; i++) {
//            solution.add(0);
//        }
//
//        Collections.shuffle(solution);
//        Integer[] array = new Integer[total];
//
//        solution.toArray (array);
//
//        return array;
//    }

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
    public int AccToFruits(int acc){
        int result;
        if (acc > 0 && acc <51){
            result = 5;
        }else if(acc < 71){
            result = 10;
        }else if (acc < 91){
            result = 15;
        }else{
            result = 30;
        }
        return result;
    }
    public void hideSystemUI(){
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = this.mActivity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    public void showSystemUI() {
        View decorView = this.mActivity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
