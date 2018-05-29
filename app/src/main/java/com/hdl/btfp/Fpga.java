package com.hdl.btfp;

/**
 * Created by xlzhou on 2017/7/5.
 */


public class Fpga {

    //   public int speed;
    private static final int X_axis = 0;
    private static final int Y_axis = 1;
    private static final int cmd_son = 1;

    static {
        System.loadLibrary("native-lib");
    }

    public void servON(int dev, int axis) {

        if (axis == X_axis) {
            SetDo(dev, 131, 1);
            SetDo(dev, 129, cmd_son);
        }
        if (axis == Y_axis) {
            SetDo(dev, 163, 1);
            SetDo(dev, 161, cmd_son);
        }

    }

    public void MotorGoPos(int dev, int id, int pos)
    {
        int distance;
        distance = (pos*600)/5;
        MotPtp(dev,id,distance);
    }

    public void MotorSetSp(int dev, int id, int sp)
    {
        int speed;
        //25000000*5/6000
        speed = 208330/sp ;
        SetSp(dev,id,speed);
    }
    public native int SetDo(int dev, int add, int cmd);
    public native int GetDi(int dev, int add);
    public native int SetSp(int dev, int id, int sp);
    public native int MotorJP(int dev, int id);
    public native int MotorJN(int dev, int id);
    public native int MotorStop(int dev, int id);
    public native int MotorGoHome(int dev,int id);
    public native int MotPtp(int dev, int id, int pos);
    public native void InitAddr();
}
