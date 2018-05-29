package com.hdl.btfp;

/**
 * Created by xlzhou on 2017/7/27.
 */

public class Utils {
    private static float[] gdata = new float[13];
    private static String  gTcpRev ;
    private static boolean[] sensorFlag = new boolean[29];

    public static float getData(int num){
        return gdata[num];
    }

    public static void setData(float sData, int num)
    {
        gdata[num] = sData;
    }

    public static String getString()
    {
        return gTcpRev;
    }
    public static void setString(String string)
    {
        gTcpRev = string;
    }

    public static void setSensorFlag(int num,boolean flag)
    {
        sensorFlag[num] = flag;
    }

    public static boolean getSenorFlag(int num)
    {
        return  sensorFlag[num];
    }
}
