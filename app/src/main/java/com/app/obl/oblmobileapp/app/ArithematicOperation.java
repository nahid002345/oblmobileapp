package com.app.obl.oblmobileapp.app;

import java.text.DecimalFormat;

/**
 * Created by ONE BANK 1 on 1/14/2016.
 */
public class ArithematicOperation {

    public static float floatArraySum(float[] flotArray)
    {
        float totalSum=0;
        for (float f : flotArray) { totalSum += f; }
        return totalSum;
    }

    public static double doubleArraySum(double[] dArray)
    {
        double totalSum=0;
        for (double d : dArray) { totalSum += d; }
        return totalSum;
    }

    public static double roundToDecimals(double val) {
        DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.valueOf(df2.format(val));
    }
}
