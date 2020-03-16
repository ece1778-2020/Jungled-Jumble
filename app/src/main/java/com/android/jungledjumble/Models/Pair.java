package com.android.jungledjumble.Models;

import java.util.List;

public class Pair {
    private double[] sizes_small;
    private double[] sizes_large;
    public Pair(double[] sizes_small, double[] sizes_large)
    {
        this.sizes_small = sizes_small;
        this.sizes_large = sizes_large;

    }
    public double[] getSmallSizes() { return sizes_small; }
    public double[]  getLargeSizes() { return sizes_large; }
}
