package com.hdl.btfp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hdl.btfp.com.TcpClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.hdl.btfp.MainActivity.TcpIp;

/**
 * Created by xlzhou on 2017/7/12.
 */
public class mainFragment extends Fragment implements View.OnClickListener, View.OnTouchListener{
    private Fpga mainFpga = new Fpga();
    View view;
    Button starBtn;
    Button shellBtn;
    Button batteryCoverBtn;
    Button robotBtn;
    private Handler myHandler = null;
    private proofWaterTest myProofWaterTest = new proofWaterTest();
    private ultrasonic myUltrasonic = new ultrasonic();
    private dispensing myDispensing = new dispensing();
    private loadBattery myLoadBattery = new loadBattery();
    private battery myBattery = new battery();
    private batteryCoverGoHome myBatteryCoverGoHome = new batteryCoverGoHome();
    private shell myShell = new shell();
    private conveyor myConveyor = new conveyor();
    private lockBatteryCover myLockBatteryCover = new lockBatteryCover();
    private start myStart = new start();
    private startStop myStartStop = new startStop();
    private String proofWaterState ;
    private String ultrasonicState ;
    private String dispensingState;
    private String loadBatteryState;
    private String lockBatteryCoverState;
    private String startStepState;
    private String startStopState;
    private boolean shellState;
    private boolean batteryCoverState;
    private boolean conveyorState;
    private boolean alarmLedState;
    private String str;
    private Toast toast = null;
    private EditText finishCount;
    private TextView[]  sensorAndState = new TextView[14];
    private int num = 0;

    ExecutorService exec = Executors.newCachedThreadPool();
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(15);

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.main, container, false);
        btnInit();
        bindListener();
        Utils.setString("start");
        scheduledExecutorService.scheduleAtFixedRate(myProofWaterTest, 10, 100, TimeUnit.MILLISECONDS);
        scheduledExecutorService.scheduleAtFixedRate(myDispensing, 10, 100, TimeUnit.MILLISECONDS);
        scheduledExecutorService.scheduleAtFixedRate(myShell,10,100,TimeUnit.MILLISECONDS);
        scheduledExecutorService.scheduleAtFixedRate(myLoadBattery,10,50,TimeUnit.MILLISECONDS);
        scheduledExecutorService.scheduleAtFixedRate(myBattery,10,50,TimeUnit.MILLISECONDS);
        scheduledExecutorService.scheduleAtFixedRate(myBatteryCoverGoHome,10,50,TimeUnit.MILLISECONDS);
        scheduledExecutorService.scheduleAtFixedRate(myConveyor,10,100,TimeUnit.MILLISECONDS);
        scheduledExecutorService.scheduleAtFixedRate(myLockBatteryCover,10,100,TimeUnit.MILLISECONDS);
        scheduledExecutorService.scheduleAtFixedRate(myUltrasonic, 1, 100, TimeUnit.MILLISECONDS);
        scheduledExecutorService.scheduleAtFixedRate(myStart,10,100,TimeUnit.MILLISECONDS);
        scheduledExecutorService.scheduleAtFixedRate(myStartStop,10,100,TimeUnit.MILLISECONDS);

        TcpIp = new TcpClient("192.168.0.1", 2000);

        myHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if(msg.what<50) {
                    if (msg.what < 30) {
                        sensorAndState[(msg.what)].setSelected(true);
                        //   Utils.setSensorFlag((msg.what),true);
                    } else {
                        sensorAndState[(msg.what - 30)].setSelected(false);
                        //    Utils.setSensorFlag((msg.what - 30),false);
                    }
                }
                if(msg.what == 60) {
                    starBtn.setSelected(true);
                    toast = Toast.makeText(getActivity(), "设备启动完成，可以正常生产", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                if(msg.what == 70) {
                  //  num = Integer.parseInt(finishCount.getText().toString());
                                ++num;
                                finishCount.setText(String.valueOf(num));
                }

                return false;
            }
        });

        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public  void  onPause(){
        super.onPause();
    }

    public void sensorAndStateInit()
    {
        sensorAndState[0] = view.findViewById(R.id.mainShellTV);
        sensorAndState[1] = view.findViewById(R.id.mainBatteryTV);
        sensorAndState[2] = view.findViewById(R.id.mainUltrasonicTV);
        sensorAndState[3] = view.findViewById(R.id.mainLoadBatteryTV);
        sensorAndState[4] = view.findViewById(R.id.mainDispensingTV);
        sensorAndState[5] = view.findViewById(R.id.mainProofWaterTV);
        sensorAndState[6] = view.findViewById(R.id.mainNGTV);
        sensorAndState[7] = view.findViewById(R.id.mainOKTV);
        sensorAndState[8] = view.findViewById(R.id.mainRobotStartTV);
        sensorAndState[9] = view.findViewById(R.id.mainRobotReadyTV);
        sensorAndState[10] = view.findViewById(R.id.mainUltrasonicStateTV);
        sensorAndState[11] = view.findViewById(R.id.mainLoadBatteryStateTV);
        sensorAndState[12] = view.findViewById(R.id.mainDispensingStateTV);
        sensorAndState[13] = view.findViewById(R.id.mainProofWaterStateTV);
    }
    public void btnInit()
    {
        starBtn = view.findViewById(R.id.btnStart);
        shellBtn = view.findViewById(R.id.shell_btn);
        batteryCoverBtn = view.findViewById(R.id.battery_cover_btn);
        robotBtn = view.findViewById(R.id.btnRobotStart);
        finishCount = view.findViewById(R.id.finishCountET);

        sensorAndStateInit();

        proofWaterState = "proofWaterIdle";
        ultrasonicState = "ultrasonicIdle";
        dispensingState = "dispensingIdle";
        loadBatteryState = "loadBatteryIdle";
        lockBatteryCoverState = "lockBatteryCoverIdle";
        startStepState = "startIdle";
        startStopState = "startStopIdle";
    }

    public void sensorAndStateFun()
    {
            if (Utils.getSenorFlag(0) && (!sensorAndState[0].isSelected()))
            {
                //sensorAndState[0].setSelected(true);
                myHandler.sendEmptyMessage(0);
            }
            if((!Utils.getSenorFlag(0)) && sensorAndState[0].isSelected())
            {
              //  sensorAndState[0].setSelected(false);
                myHandler.sendEmptyMessage(30);
            }

            if (Utils.getSenorFlag(3) && (!sensorAndState[1].isSelected()))
            {
              //  sensorAndState[1].setSelected(true);
                myHandler.sendEmptyMessage(1);
            }
            if((!Utils.getSenorFlag(3)) && sensorAndState[1].isSelected())
            {
             //   sensorAndState[1].setSelected(false);
                myHandler.sendEmptyMessage(31);
            }

            if (Utils.getSenorFlag(25) && (!sensorAndState[2].isSelected()))
            {
              //  sensorAndState[2].setSelected(true);
                myHandler.sendEmptyMessage(2);
            }
            if((!Utils.getSenorFlag(25)) && sensorAndState[2].isSelected())
            {
              //  sensorAndState[2].setSelected(false);
                myHandler.sendEmptyMessage(32);
            }
            if (Utils.getSenorFlag(6) && (!sensorAndState[3].isSelected()))
            {
             //   sensorAndState[3].setSelected(true);
                myHandler.sendEmptyMessage(3);
            }
            if((!Utils.getSenorFlag(6)) && sensorAndState[3].isSelected())
            {
            //    sensorAndState[3].setSelected(false);
                myHandler.sendEmptyMessage(33);
            }

            if (Utils.getSenorFlag(26) && (!sensorAndState[4].isSelected()))
            {
             //   sensorAndState[4].setSelected(true);
                myHandler.sendEmptyMessage(4);
            }
            if((!Utils.getSenorFlag(26)) && sensorAndState[4].isSelected())
            {
             //   sensorAndState[4].setSelected(false);
                myHandler.sendEmptyMessage(34);
            }

            if (Utils.getSenorFlag(13) && (!sensorAndState[5].isSelected()))
            {
             //   sensorAndState[5].setSelected(true);
                myHandler.sendEmptyMessage(5);
            }
            if((!Utils.getSenorFlag(13)) && sensorAndState[5].isSelected())
            {
             //   sensorAndState[5].setSelected(false);
                myHandler.sendEmptyMessage(35);
            }

            if (Utils.getSenorFlag(28) && (!sensorAndState[6].isSelected()))
            {
            //    sensorAndState[6].setSelected(true);
                myHandler.sendEmptyMessage(6);
            }
            if((!Utils.getSenorFlag(28)) && sensorAndState[6].isSelected())
            {
                myHandler.sendEmptyMessage(36);
            }

            if (Utils.getSenorFlag(27) && (!sensorAndState[7].isSelected()))
            {
                myHandler.sendEmptyMessage(7);
            }
            if((!Utils.getSenorFlag(27)) && sensorAndState[7].isSelected())
            {
                myHandler.sendEmptyMessage(37);
            }
            if (Utils.getSenorFlag(17) && (!sensorAndState[8].isSelected()))
            {
                myHandler.sendEmptyMessage(8);
            }
            if((!Utils.getSenorFlag(17)) && sensorAndState[8].isSelected())
            {
                myHandler.sendEmptyMessage(38);
            }

            if (Utils.getSenorFlag(18) && (!sensorAndState[9].isSelected()))
            {
                myHandler.sendEmptyMessage(9);
            }
            if((!Utils.getSenorFlag(18)) && sensorAndState[9].isSelected())
            {
                myHandler.sendEmptyMessage(39);
            }

            if ((!ultrasonicState.equals("ultrasonicIdle"))  && (!sensorAndState[10].isSelected()))
            {
                myHandler.sendEmptyMessage(10);
            }
            if(ultrasonicState.equals("ultrasonicIdle") && sensorAndState[10].isSelected()) {
                myHandler.sendEmptyMessage(40);
            }

            if ((!loadBatteryState.equals("loadBatteryIdle"))  && (!sensorAndState[11].isSelected()))
            {
                myHandler.sendEmptyMessage(11);
            }
            if(loadBatteryState.equals("loadBatteryIdle") && sensorAndState[11].isSelected())
            {
                myHandler.sendEmptyMessage(41);
            }

            if ((!dispensingState.equals("dispensingIdle"))  && (!sensorAndState[12].isSelected()))
            {
                myHandler.sendEmptyMessage(12);
            }
            if(dispensingState.equals("dispensingIdle") && sensorAndState[12].isSelected())
            {
                myHandler.sendEmptyMessage(42);
            }

            if ((!proofWaterState.equals("proofWaterIdle"))  && (!sensorAndState[13].isSelected()))
            {
                myHandler.sendEmptyMessage(13);
            }
            if(proofWaterState.equals("proofWaterIdle") && sensorAndState[13].isSelected())
            {
                myHandler.sendEmptyMessage(43);
            }
    }

    public void bindListener()
    {
        starBtn.setOnClickListener(this);
        shellBtn.setOnClickListener(this);
        batteryCoverBtn.setOnClickListener(this);
        robotBtn.setOnClickListener(this);
        robotBtn.setOnTouchListener(this);
    }
    public void onClick(View v)
    {

        switch (v.getId()) {
            case R.id.btnStart :
                if(starBtn.isSelected()) {
                    TcpIp.closeSelf();
                    Log.e("MainActivity","isSelected");
                    startStepState = "startIdle";
          //          scheduledExecutorService.shutdown();
                    starBtn.setSelected(false);
                    ///////robot stop code////////////////////
                    ///////////////////////////////
                }
                else {
                 //   Log.e("MainActivity","starBtn_down");
                 //   exec.execute(TcpIp );
                 //   starBtn.setSelected(true);
                   if(sensorAndState[8].isSelected() && startStepState.equals("startIdle"))
                    {
                        startStepState = "robotStart";
                        toast = Toast.makeText(getActivity(),"开始启动，请耐心等待...",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }
                    else
                    {
                        toast = Toast.makeText(getActivity(),"机器手没启动，请启动机器手，再启动",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }

                }
                break;

            case R.id.btnRobotStart:
                if(robotBtn.isSelected())
                {
                    if(starBtn.isSelected())
                    {
                        toast = Toast.makeText(getActivity(),"请先关设备，再关机器手",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }
                    else {
                        robotBtn.setSelected(false);
                    }
                }
                else
                {
                    robotBtn.setSelected(true);
                }
                break;
            case R.id.shell_btn:
                if(Utils.getSenorFlag(0)&&Utils.getSenorFlag(1)) {
                    mainFpga.servON(1, 0);
                    mainFpga.InitAddr();
                    //   mainFpga.SetSp(1,0,100);
                    //   mainFpga.MotorJP(1,0);
                    mainFpga.MotorSetSp(1, 0, Math.round(10 * Utils.getData(0)));
                    mainFpga.MotorGoPos(1, 0, Math.round(10 * Utils.getData(9)));
                    shellState = true;
                }
                break;
            case R.id.battery_cover_btn:
                if(Utils.getSenorFlag(3)&&Utils.getSenorFlag(4)) {
                    mainFpga.servON(1, 1);
                    mainFpga.InitAddr();
                    //  mainFpga.SetSp(1, 1, 100);
                    //  mainFpga.MotorJP(1, 1);
                    mainFpga.MotorSetSp(1, 1, Math.round(10 * Utils.getData(1)));
                    mainFpga.MotorGoPos(1, 1, Math.round(10 * Utils.getData(10)));
                    batteryCoverState = true;
                }
                break;
            default:
                break;
        }
    }

    public  boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.btnRobotStart:
                if (robotBtn.isSelected()) {
                    if(!starBtn.isSelected())
                    {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                Log.e("MainActivity", "isSelectedACTION_DOWN");
                                mainFpga.SetDo(2, 3, 1);
                                break;
                            case MotionEvent.ACTION_UP:
                                mainFpga.SetDo(2, 3, 0);
                                Log.e("MainActivity", "isSelectedACTION_UP");
                                break;
                            default:
                                break;
                        }
                    }

                } else {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            Log.e("MainActivity", "ACTION_DOWN");
                            mainFpga.SetDo(2, 1, 1);
                            break;
                        case MotionEvent.ACTION_UP:
                            mainFpga.SetDo(2, 1, 0);
                            Log.e("MainActivity", "ACTION_UP");
                            break;
                        default:
                            break;
                    }
                    break;
                }
        }
        return false;
    }

    private class startStop implements Runnable{
        @Override
        public void run()
        {
            switch (startStopState) {

                case "startStopReady":
                    mainFpga.SetDo(2, 3, 1);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mainFpga.SetDo(2, 3, 0);
                    startStopState = "startStopIdle";
                    break;
                default:
                    break;
            }
        }
    }


    private class start implements  Runnable{
        @Override
        public void run()
        {
            switch (startStepState)
            {

            /*    case "robotReady":
                    if(sensorAndState[9].isSelected() )
                    {
                        mainFpga.SetDo(2,1,1);
                        try {
                            Thread.sleep(1000);
                        }catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        mainFpga.SetDo(2,1,0);
                        try {
                            Thread.sleep(3000);
                        }catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        startStepState = "robotStart";

                    }
                    break;
                    */
                case "robotStart":
                    if(sensorAndState[8].isSelected() )
                    {
                //        myHandler.sendEmptyMessage(50);
                        exec.execute(TcpIp );
                    /*    mainFpga.InitAddr();
                        mainFpga.servON(2,1);
                        mainFpga.MotorSetSp(2,1,Math.round(10*50));
                        mainFpga.MotorGoPos(2,1,Math.round(10*20));

                        mainFpga.servON(1,1);
                        mainFpga.MotorSetSp(1,1,Math.round(10*50));
                        mainFpga.MotorGoPos(1,1,Math.round(10*20));

                        mainFpga.servON(3,0);
                        mainFpga.MotorSetSp(3,0,Math.round(10*50));
                        mainFpga.MotorGoPos(3,0,Math.round(10*20));;

                        mainFpga.servON(1,0);
                        mainFpga.MotorSetSp(1,0,Math.round(10*50));
                        mainFpga.MotorGoPos(1,0,Math.round(10*20));

                        try {
                            Thread.sleep(2000);
                        }catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        */
                        mainFpga.InitAddr();
                        mainFpga.servON(2,1);
                        mainFpga.MotorSetSp(2,1,Math.round(10*20));
                        mainFpga.MotorGoHome(2,1);

                        mainFpga.servON(1,1);
                        mainFpga.MotorSetSp(1,1,Math.round(10*20));
                        mainFpga.MotorGoHome(1,1);

                        mainFpga.servON(3,0);//ultrasonic
                        mainFpga.MotorSetSp(3,0,Math.round(10*20));
                        mainFpga.MotorGoHome(3,0);

                        mainFpga.servON(1,0);
                        mainFpga.MotorSetSp(1,0,Math.round(10*20));
                        mainFpga.MotorGoHome(1,0);
                   /*     try {
                            Thread.sleep(5000);
                        }catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }*/
                        startStepState = "ResetAllMotor";
                    }
                    break;
                case "ResetAllMotor":
                    if(Utils.getSenorFlag(1)&&Utils.getSenorFlag(4)&&Utils.getSenorFlag(24)&&Utils.getSenorFlag(20))
                    {
                        try {
                            Thread.sleep(1000);
                        }catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        mainFpga.InitAddr();
                        mainFpga.servON(2,1);
                        mainFpga.MotorSetSp(2,1,Math.round(10*Utils.getData(2)));
                        mainFpga.MotorGoPos(2,1,Math.round(10*Utils.getData(7)));

                        mainFpga.servON(1,1);
                        mainFpga.MotorSetSp(1,1,Math.round(10*Utils.getData(1)));
                        mainFpga.MotorGoPos(1,1,Math.round(10*Utils.getData(10)));

                        mainFpga.servON(3,0);//ultrasonic
                        mainFpga.MotorSetSp(3,0,Math.round(10*Utils.getData(3)));
                        mainFpga.MotorGoPos(3,0,Math.round(10*Utils.getData(8)));;

                        mainFpga.servON(1,0);//shell
                        mainFpga.MotorSetSp(1,0,Math.round(10*Utils.getData(0)));
                        mainFpga.MotorGoPos(1,0,Math.round(10*Utils.getData(5)));
                        try {
                            Thread.sleep(2000);
                        }catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        startStepState = "allMotorInitPos";
                    }
                    break;

                case "allMotorInitPos":
                   // if(Utils.getSenorFlag(1)&&Utils.getSenorFlag(4)&&Utils.getSenorFlag(24)&&Utils.getSenorFlag(20))//未完成
                    {
                        Log.e("MainActivity","allMotorInitPos");
                        myHandler.sendEmptyMessage(60);
                        startStepState = "startFinish";
                    }
                break;
                default:
                    break;
            }

        }
    }


    private class proofWaterTest implements  Runnable{
        @Override
        public void run()
        {
                switch (proofWaterState)
                {
                    case "proofWaterIdle":
                        if (Utils.getString().equals("proofWaterTest"+ "\r\n"))
                        {
                            Utils.setString("Idle");
                            proofWaterState = "proofWaterGoForward";
                        }
                        break;
                    case "proofWaterGoForward":
                        Log.e("MainActivity","proofWaterTest");
                        if(Utils.getSenorFlag(20)&&Utils.getSenorFlag(15)&&Utils.getSenorFlag(13))
                        {

                            mainFpga.servON(2,1);
                            mainFpga.InitAddr();
                         //   mainFpga.SetSp(2,1,400);
                         //   mainFpga.MotorJP(2,1);
                            mainFpga.MotorSetSp(2,1,Math.round(10*Utils.getData(2)));
                            mainFpga.MotorGoPos(2,1,Math.round(10*Utils.getData(11)));
                            proofWaterState = "proofWaterCoverDown";
                        }
                        break;
                    case "proofWaterCoverDown":
                        if(Utils.getSenorFlag(21)&&Utils.getSenorFlag(15)&&Utils.getSenorFlag(13))
                        {
                            mainFpga.SetDo(2,0,1);
                            proofWaterState = "proofWaterStartUp";
                        }
                        break;
                    case "proofWaterStartUp":
                        if(Utils.getSenorFlag(14)&&Utils.getSenorFlag(13)&&Utils.getSenorFlag(21))
                        {
                           mainFpga.SetDo(2,5,1);
                            try {
                                Thread.sleep(1000);
                            }catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                            mainFpga.SetDo(2,5,0);
                            try {
                                Thread.sleep(10000);
                            }catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                            proofWaterState = "proofWaterCoverUp";
                        }
                        break;
                    case "proofWaterCoverUp":
                        if(Utils.getSenorFlag(28)  || Utils.getSenorFlag(27))
                        {
                            mainFpga.SetDo(2,0,0);
                            proofWaterState = "proofWaterGoHome";
                        }
                        break;
                    case "proofWaterGoHome":
                        if(Utils.getSenorFlag(15)&&Utils.getSenorFlag(13)&&Utils.getSenorFlag(21)) //修改开始位置传感器
                        {

                            mainFpga.InitAddr();
                            mainFpga.servON(2,1);
                          //  mainFpga.SetSp(2,1,400);
                            mainFpga.MotorSetSp(2,1,Math.round(10*Utils.getData(2)));
                            mainFpga.MotorGoPos(2,1,Math.round(10*Utils.getData(7)));
                            proofWaterState = "proofWaterMessageRobot";
                        }
                        break;
                    case "proofWaterMessageRobot":
                        if(Utils.getSenorFlag(20)&&(!Utils.getSenorFlag(12))) {
                            if(Utils.getSenorFlag(27)){
                                TcpIp.send("proofWaterToPass"+"\r");
                                proofWaterState = "proofWaterIdle";
                                myHandler.sendEmptyMessage(70);
                            }
                            if(Utils.getSenorFlag(28)) {
                                TcpIp.send("proofWaterToFail" + "\r");
                                proofWaterState = "proofWaterIdle";
                                myHandler.sendEmptyMessage(70);
                            }
                        }
                        break;
                    default:
                        break;
                }
        }
    }
    private class lockBatteryCover implements Runnable{
        @Override
        public synchronized void run(){
            switch (lockBatteryCoverState)
            {
                case "lockBatteryCoverIdle":
                    //  Log.e("MainActivity","dispensingIdle");
                    if(Utils.getString().equals("lockBatteryCover"))
                    {
                        Utils.setString("Idle");
                        lockBatteryCoverState = "lockBatteryCoverGoForward";
                    }
                    break;
                case "lockBatteryCoverGoForward":
                    if (Utils.getSenorFlag(16)&&Utils.getSenorFlag(19)&&Utils.getSenorFlag(18)) {

                        mainFpga.servON(2, 0);
                        mainFpga.InitAddr();
                      //  mainFpga.SetSp(2, 0, 200);
                      //  mainFpga.MotorJP(2, 0);
                        mainFpga.MotorSetSp(2, 0, Math.round(10*Utils.getData(2)));
                        mainFpga.MotorGoPos(2,0,Math.round(10*Utils.getData(8)));
                        lockBatteryCoverState = "lockBatteryCoverColletClose";
                    }
                    break;

                case "lockBatteryCoverColletClose":
                    if(Utils.getSenorFlag(17)&&Utils.getSenorFlag(19)&&Utils.getSenorFlag(18)) {
                        mainFpga.SetDo(1,3,1);
                        try {
                            Thread.sleep(1000);
                        }catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        lockBatteryCoverState = "lockBatteryCoverTorsionHeadDown";
                    }
                    break;
                case "lockBatteryCoverTorsionHeadDown":
                    if(Utils.getSenorFlag(17)&&Utils.getSenorFlag(19)&&Utils.getSenorFlag(18)) {
                        mainFpga.SetDo(1,5,1);
                        try {
                            Thread.sleep(1000);
                        }catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        lockBatteryCoverState = "lockBatteryCoverTwistCapSpin";
                    }
                    break;
                case "lockBatteryCoverTwistCapSpin":
                    if(Utils.getSenorFlag(17)&&Utils.getSenorFlag(19)&&(!Utils.getSenorFlag(18))) {
                        mainFpga.SetDo(1,4,1);
                        try {
                            Thread.sleep(1000);
                        }catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        lockBatteryCoverState = "lockBatteryCoverTorsionHeadUp";
                    }
                    break;
                case "lockBatteryCoverTorsionHeadUp":
                    if(Utils.getSenorFlag(17)&&Utils.getSenorFlag(19)&&(!Utils.getSenorFlag(18))) {
                        mainFpga.SetDo(1,5,0);
                        try {
                            Thread.sleep(1000);
                        }catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        lockBatteryCoverState = "lockBatteryCoverColletOpen";
                    }
                    break;
                case "lockBatteryCoverColletOpen":
                    if(Utils.getSenorFlag(17)&&Utils.getSenorFlag(19)&&Utils.getSenorFlag(18)) {
                        mainFpga.SetDo(1,3,0);
                        try {
                            Thread.sleep(1000);
                        }catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        lockBatteryCoverState = "lockBatteryCoverTwistCapReset";
                    }

                    break;
                case "lockBatteryCoverTwistCapReset":
                    if(Utils.getSenorFlag(17)&&Utils.getSenorFlag(19)&&(Utils.getSenorFlag(18))) {
                        mainFpga.SetDo(1,4,0);
                        try {
                            Thread.sleep(1000);
                        }catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        lockBatteryCoverState = "lockBatteryCoverTorsionGoHome";
                    }
                    break;
                case "lockBatteryCoverTorsionGoHome":
                    if(Utils.getSenorFlag(17)&&Utils.getSenorFlag(19)&&(Utils.getSenorFlag(18)))
                    {

                        mainFpga.servON(2, 0);
                        mainFpga.InitAddr();
                     //   mainFpga.SetSp(2, 0, 200);
                        mainFpga.MotorSetSp(2, 0, Math.round(10*Utils.getData(2)));
                        mainFpga.MotorGoHome(2, 0);
                        lockBatteryCoverState = "lockBatteryCoverMessageRobot";
                    }
                    break;
                case "lockBatteryCoverMessageRobot":
                    if (Utils.getSenorFlag(16)&&Utils.getSenorFlag(19)&&Utils.getSenorFlag(18))
                    {
                        TcpIp.send("lockBatteryCoverToProofWater" + "\r" );
                        lockBatteryCoverState = "lockBatteryCoverIdle";
                    }

            }
        }
    }

    private class shell implements  Runnable{
        @Override
        public synchronized void run()
        {
                    if (Utils.getSenorFlag(0) && Utils.getSenorFlag(2)&&shellState&&(!Utils.getSenorFlag(25))) {
                        TcpIp.send("shellToUlt"+"\r");
                        shellState = false;
                    }
        }
    }
    private class battery implements Runnable{
        @Override
        public synchronized void run()
        {
                    if (Utils.getSenorFlag(3) && Utils.getSenorFlag(5)&&batteryCoverState&&(!Utils.getSenorFlag(13))) {
                        TcpIp.send("BatteryCoverToProofWater" + "\r" );
                        batteryCoverState = false;
                       // shellState = "shellFinish";
                }

        }
    }
    private class conveyor implements Runnable{
        @Override
        public synchronized void run()
        {
            if(Utils.getSenorFlag(12)&& conveyorState)
            {
                mainFpga.SetDo(2,2,0);
                conveyorState = false;
            }
            if((!Utils.getSenorFlag(12))&& (!conveyorState) )
            {
                mainFpga.SetDo(2,2,1);
                conveyorState = true;
            }
            if((!Utils.getSenorFlag(19))&&(!alarmLedState))
            {
                mainFpga.SetDo(1,6,1);
                alarmLedState = true;
            }
            if(Utils.getSenorFlag(19)&&(alarmLedState))
            {
                mainFpga.SetDo(1,6,0);
                alarmLedState = false;
            }
            sensorAndStateFun();
        }
    }

    private class batteryCoverGoHome implements Runnable{
        @Override
        public synchronized void run()
        {
            if(Utils.getString().equals("batteryCoverGoHome"+ "\r\n")) {//修改开始位置传感器
                Log.e("MainActivity","batteryCoverGo");
                if (Utils.getSenorFlag(3) && Utils.getSenorFlag(5) ) {
                    Utils.setString("Idle");
                    mainFpga.servON(1,1);
                    mainFpga.InitAddr();
                   // mainFpga.SetSp(1,1,100);
                    mainFpga.MotorSetSp(1,1,Math.round(10*Utils.getData(1)));
                    mainFpga.MotorGoPos(1,1,Math.round(10*Utils.getData(6)));
                    Log.e("MainActivity","batteryCoverGoHome");
                    // shellState = "shellFinish";
                }
            }
        }
    }


    private class loadBattery implements Runnable{
        @Override
        public synchronized void run()
        {
            switch (loadBatteryState)
            {
                case "loadBatteryIdle" :
                    if (Utils.getString().equals("loadBattery"+ "\r\n"))
                    {
                        Log.e("MainActivity","loadBatteryIdle");
                        Utils.setString("Idle");
                        loadBatteryState = "pressBatteryShell";
                    }
                    break;
                case "pressBatteryShell":
                    if(Utils.getSenorFlag(6))
                    {
                        Log.e("MainActivity","pressBatteryShell");
                        mainFpga.SetDo(1,1,1);
                        try {
                            Thread.sleep(1000);
                        }catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        loadBatteryState = "motorSpin";
                    }
                    break;
                case "motorSpin":
                    if((!Utils.getSenorFlag(9))&&Utils.getSenorFlag(6))
                    {
                        Log.e("MainActivity","pressBatteryShell");
                        mainFpga.SetSp(0,0,8000);
                        mainFpga.MotorJP(0,0);
                        mainFpga.SetDo(0,4, 1);
                        try {
                            Thread.sleep(20);
                        }catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        Log.e("MainActivity","motorSpin");
                        loadBatteryState = "motorStop";
                    }
                    break;
                case "motorStop":
                    if((!Utils.getSenorFlag(9))&&Utils.getSenorFlag(6)&&Utils.getSenorFlag(8)&&Utils.getSenorFlag(11))
                    {
                        mainFpga.MotorStop(0,0);
                        mainFpga.SetDo(0,4,0);
                        try {
                            Thread.sleep(500);
                        }catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        Log.e("MainActivity","motorStop");
                        loadBatteryState = "pushBattery";
                    }
                break;
                case "pushBattery":
                    if((!Utils.getSenorFlag(9))&&Utils.getSenorFlag(6)&&Utils.getSenorFlag(8)&&Utils.getSenorFlag(11))
                    {
                        mainFpga.SetDo(1,0,1);
                        try {
                            Thread.sleep(1000);
                        }catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        Log.e("MainActivity","pushBattery");
                        loadBatteryState = "backBattery";
                    }
                    break;
                case "backBattery":
                    if((!Utils.getSenorFlag(10))&&Utils.getSenorFlag(6)&&Utils.getSenorFlag(11))
                    {
                        mainFpga.SetDo(1,0,0);
                        try {
                            Thread.sleep(500);
                        }catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        Log.e("MainActivity","backBattery");
                        loadBatteryState = "motorSpinSecond";
                    }
                    break;
                case "motorSpinSecond":
                if((!Utils.getSenorFlag(9))&&Utils.getSenorFlag(6))
                {
                    mainFpga.SetSp(0,0,8000);
                    mainFpga.MotorJP(0,0);
                    mainFpga.SetDo(0,4, 1);
                    try {
                        Thread.sleep(3000);
                    }catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    loadBatteryState = "motorStopSecond";
                }
                break;
                case "motorStopSecond":
                    if((!Utils.getSenorFlag(9))&&Utils.getSenorFlag(6)&&Utils.getSenorFlag(8)&&Utils.getSenorFlag(11))
                    {
                        mainFpga.MotorStop(0,0);
                        mainFpga.SetDo(0,4, 0);
                        try {
                            Thread.sleep(1000);
                        }catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        loadBatteryState = "pushBatterySecond";
                    }
                    break;
                case "pushBatterySecond":
                    if((!Utils.getSenorFlag(9))&&Utils.getSenorFlag(6)&&Utils.getSenorFlag(8)&&Utils.getSenorFlag(11))
                    {
                        mainFpga.SetDo(1,0,1);
                        try {
                            Thread.sleep(2000);
                        }catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        loadBatteryState = "backBatterySecond";
                    }
                    break;
                case "backBatterySecond":
                    if((!Utils.getSenorFlag(10))&&Utils.getSenorFlag(6)&&Utils.getSenorFlag(11))
                    {
                        mainFpga.SetDo(1,0,0);
                        try {
                            Thread.sleep(500);
                        }catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        loadBatteryState = "openBatteryShell";
                    }
                    break;
                case "openBatteryShell":
                    if((!Utils.getSenorFlag(9))&&Utils.getSenorFlag(6)&&Utils.getSenorFlag(11)) {
                        mainFpga.SetDo(1, 1, 0);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        loadBatteryState = "MessageRobot";
                    }
                break;
                case "MessageRobot" :
                    if( Utils.getSenorFlag(5)) {
                        if(!Utils.getSenorFlag(3)) {
                            TcpIp.send("loadBatteryToBatteryCover" + "\r");
                            loadBatteryState = "loadBatteryIdle";
                        }
                    }
                    break;

            }
        }
    }
    private class dispensing implements Runnable{
        @Override
        public synchronized void run()
        {

                switch (dispensingState)
                {
                    case "dispensingIdle":
                        if(Utils.getString().equals("dispensing"+ "\r\n"))
                        {
                            Utils.setString("Idle");
                            dispensingState = "dispensingStartUp";
                            Log.e("MainActivity","dispensingIdle");
                        }
                            break;
                    case "dispensingStartUp":
                        if (Utils.getSenorFlag(7)) {
                            mainFpga.SetDo(2, 4, 1);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            mainFpga.SetDo(2, 4, 0);
                            Log.e("MainActivity","dispensingStartUp");
                            dispensingState = "dispensingFinish";
                        }
                        break;
                    case "dispensingFinish":
                       if(Utils.getSenorFlag(26)) {
                           try {
                               Thread.sleep(3000);
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                           Log.e("MainActivity","dispensingStartUp");
                           dispensingState = "dispensingMessageRobot";
                       }
                        break;
                    case "dispensingMessageRobot":
                        if(!Utils.getSenorFlag(6)){
                        TcpIp.send("DispengsingToLoad"+"\r");
                        dispensingState = "dispensingIdle";
                        }
                    default:
                        break;
                }
        }
    }
    private class ultrasonic implements  Runnable{
        @Override
        public synchronized void run()
        {

                switch (ultrasonicState)
                {
                    case "ultrasonicIdle":
                        if(Utils.getString().equals("ultrasonic" + "\r\n"))
                        {
                            Utils.setString("Idle");
                            ultrasonicState = "ultrasonicGoForward";
                        }
                        break;
                    case "ultrasonicGoForward":
                        if(Utils.getSenorFlag(22)&&(Utils.getSenorFlag(24))&&(Utils.getSenorFlag(25)))
                        {

                            mainFpga.servON(3,0);
                            mainFpga.InitAddr();
                           // mainFpga.SetSp(3,0,100);
                           // mainFpga.MotorJP(3,0);
                            mainFpga.MotorSetSp(3,0,Math.round(10*Utils.getData(3)));
                            mainFpga.MotorGoPos(3,0,Math.round(10*Utils.getData(12)));

                            mainFpga.servON(1,0);
                            mainFpga.InitAddr();
                           // mainFpga.SetSp(1,0,100);
                            mainFpga.MotorSetSp(1,0,Math.round(10*Utils.getData(0)));
                            mainFpga.MotorGoPos(1,0,Math.round(10*Utils.getData(5)));
/*
                            mainFpga.servON(1, 1);
                            mainFpga.InitAddr();
                            mainFpga.SetSp(1, 1, 100);
                            mainFpga.MotorJP(1, 1);
*/
                            ultrasonicState = "ultrasonicStartup";
                        }
                        break;
                    case "ultrasonicStartup":
                        //     if((mainFpga.GetDi(2,10) == 0)&&(mainFpga.GetDi(2,9)==0))
                        if(Utils.getSenorFlag(23)&&(Utils.getSenorFlag(22))&&(Utils.getSenorFlag(25)))
                        {
                            mainFpga.SetDo(2,6,1);
                            try {
                                Thread.sleep(1000);
                            }catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                            mainFpga.SetDo(2,6,0);
                            try {
                                Thread.sleep(1000);
                            }catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                            ultrasonicState = "ultrasonicGoHome";
                        }
                        break;
                    case "ultrasonicGoHome":
                        Log.e("MainActivity","ultrasonic2");
                        if(Utils.getSenorFlag(22)&&(Utils.getSenorFlag(25)))//修改开始位置传感器
                        {
                            mainFpga.servON(3,0);
                            mainFpga.InitAddr();
                          //  mainFpga.SetSp(3,0,100);
                            mainFpga.MotorSetSp(3,0,Math.round(10*Utils.getData(3)));
                            mainFpga.MotorGoPos(3,0,Math.round(10*Utils.getData(8)));
                            ultrasonicState = "ultrasonicMessageRobot";
                        }
                        break;
                    case "ultrasonicMessageRobot":
                        Log.e("MainActivity","ultrasonic3");
                        // if((mainFpga.GetDi(2,11)==0)&&(mainFpga.GetDi(2,12)==0))
                       // if(Utils.getSenorFlag(24)&&(Utils.getSenorFlag(25))&&(!Utils.getSenorFlag(7)))
                        if(Utils.getSenorFlag(24)&&(Utils.getSenorFlag(25))&&(!Utils.getSenorFlag(6)))
                        {
                           // TcpIp.send("UltToDispengsing"+"\r");
                            TcpIp.send("UltToLoadbattery"+"\r");
                            ultrasonicState = "ultrasonicIdle";
                        }
                        break;
                }
        }
    }
}
