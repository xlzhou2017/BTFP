package com.hdl.btfp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by xlzhou on 2017/8/31.
 */

public class  SensorFragment  extends Fragment {

    private Fpga fpgaSensorStatus = new Fpga();
    private TextView[]  disSensor = new TextView[29];
    private SensorCheckTask mySensorCheckTask = new SensorCheckTask();
    private Handler mHandler = null;
    private View view;
  //  ScheduledExecutorService sensorStatusCheck = Executors.newSingleThreadScheduledExecutor();

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.sensor, container, false);
     //   sensorStatusCheck.scheduleAtFixedRate(mySensorCheckTask,1,100, TimeUnit.MILLISECONDS);
        disSensorInit();
        fpgaSensorStatus.InitAddr();

            mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if(msg.what < 30)
                {
                    disSensor[(msg.what)].setSelected(true);
                    Utils.setSensorFlag((msg.what),true);
                }
                else
                {
                    disSensor[(msg.what - 30)].setSelected(false);
                    Utils.setSensorFlag((msg.what - 30),false);
                }
                return false;
            }
        });


       Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
              //  Log.i("MainActivity", "run:TEST ");
                for (int i = 0; i<13; ++i)
                {
                 //   Log.e("MainActivity",String.valueOf(i) );
                    if ((fpgaSensorStatus.GetDi(1,i) == 0)&&(!disSensor[i].isSelected()))
                    {
                        mHandler.sendEmptyMessage(i);
                        //         Log.e("MainActivity",String.valueOf(i));
                    }
                    if((fpgaSensorStatus.GetDi(1,i) == 1)&&(disSensor[i].isSelected()))
                    {
                        mHandler.sendEmptyMessage(i+30);
                        //          Log.e("MainActivity",String.valueOf(i+30) );
                    }

                }

                for (int j = 0; j < 13; ++j)
                {
                    if ((fpgaSensorStatus.GetDi(2,j) == 0)&&(!disSensor[j+13].isSelected()))
                    {
                        mHandler.sendEmptyMessage(j+13);
                                 Log.e("MainActivity",String.valueOf(j+13));
                    }
                    if((fpgaSensorStatus.GetDi(2,j) == 1)&&(disSensor[j+13].isSelected()))
                    {
                        mHandler.sendEmptyMessage(j+43);
                                Log.e("MainActivity",String.valueOf(j+43) );
                    }
                }

                for(int k = 10; k < 13; ++k) {
                 //   Log.e("MainActivity",String.valueOf(k) );
                    if ((fpgaSensorStatus.GetDi(0, k) == 0) && (!disSensor[k+16].isSelected())) {
                        mHandler.sendEmptyMessage(k+16);
                        Log.e("MainActivity",String.valueOf(k) );
                    }
                    if ((fpgaSensorStatus.GetDi(0, k) == 1) && (disSensor[k+16].isSelected())) {
                        mHandler.sendEmptyMessage(k+46);
                        Log.e("MainActivity",String.valueOf(k) );
                    }
                }
            }
        },
            1,50);

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

    private void disSensorInit()
    {
      //  disSensor[0] = (TextView)view.findViewById(R.id.sensor1);
        disSensor[0] = view.findViewById(R.id.shellSS );
        disSensor[1] = view.findViewById(R.id.leaveWorkpiecePosSS );
        disSensor[2] = view.findViewById(R.id.grabMaterialsPosSS);
        disSensor[3] = view.findViewById(R.id.BatteryCoverSS );
        disSensor[4] = view.findViewById(R.id.leaveBatteryCoverPosSS );
        disSensor[5] = view.findViewById(R.id.grabBatteryCoverPosSS);
        disSensor[6] = view.findViewById(R.id.loadBatterySS );
      //  disSensor[7] = view.findViewById(R.id.lackBatterySS );
        disSensor[7] = view.findViewById(R.id.dispensingWorkPiece );
        disSensor[8] = view.findViewById(R.id.batterySS );
        disSensor[9] = view.findViewById(R.id.backBatteryPosSS );
        disSensor[10] = view.findViewById(R.id.pushBatterySS );
        disSensor[11] = view.findViewById(R.id.SpinBatteryPosSS );
      //  disSensor[10] = view.findViewById(R.id.NGSS);
      //  disSensor[11] = view.findViewById(R.id.OKSS);

        disSensor[12] = view.findViewById(R.id.passLimit);
        disSensor[13] = view.findViewById(R.id.waterPieceSS);
        disSensor[14] = view.findViewById(R.id.testWaterPosDownLimtSS );
        disSensor[15] = view.findViewById(R.id.testWaterPosUpLimtSS);
        disSensor[16] = view.findViewById(R.id.grablockBatteryCoverPosSS  );
        disSensor[17] = view.findViewById(R.id.armRunning);
        disSensor[18] = view.findViewById(R.id.armReady);

      //  disSensor[17] = view.findViewById(R.id.lockBatteryCoverPosSS );
      //  disSensor[18] = view.findViewById(R.id.lockHome);
     //   disSensor[19] = view.findViewById(R.id.lockBatteryCover);
        disSensor[19] = view.findViewById(R.id.lackBatterySS );
        disSensor[20] = view.findViewById(R.id.grabWaterPosSS);
        disSensor[21] = view.findViewById(R.id.testWaterPosSS );

        disSensor[22] = view.findViewById(R.id.ultrasonicHomeSS);
        disSensor[23] = view.findViewById(R.id.grabUltrasonicPosSS);
        disSensor[24] = view.findViewById(R.id.leaveUltrasonicPosSS);
        disSensor[25] = view.findViewById(R.id.ultrasonicSS);


        disSensor[26] = view.findViewById(R.id.dispensingPosSS);
        disSensor[27] = view.findViewById(R.id.OKSS);
        disSensor[28] = view.findViewById(R.id.NGSS);

    }



    private class SensorCheckTask implements Runnable{
        @Override
        public synchronized void run(){
            Log.i("MainActivity", "run:TEST ");

            for (int i = 0; i < 13; ++i) {
                 //    Log.e("MainActivity",String.valueOf(i) );
                 if ((fpgaSensorStatus.GetDi(1, i) == 0) && (!disSensor[i].isSelected())) {
                     mHandler.sendEmptyMessage(i);
                             Log.e("MainActivity",String.valueOf(i));
                 }
                 if ((fpgaSensorStatus.GetDi(1, i) == 1) && (disSensor[i].isSelected())) {
                     mHandler.sendEmptyMessage(i + 30);
                              Log.e("MainActivity",String.valueOf(i+30) );
                 }

             }

             for (int j = 0; j < 13; ++j) {
                 if ((fpgaSensorStatus.GetDi(2, j) == 0) && (!disSensor[j + 13].isSelected())) {
                     mHandler.sendEmptyMessage(j + 13);
                 //    Log.e("MainActivity", String.valueOf(j + 13));
                 }
                 if ((fpgaSensorStatus.GetDi(2, j) == 1) && (disSensor[j + 13].isSelected())) {
                     mHandler.sendEmptyMessage(j + 43);
                 //    Log.e("MainActivity", String.valueOf(j + 43));
                 }
             }

             for (int k = 10; k < 13; ++k) {
                 //   Log.e("MainActivity",String.valueOf(k) );
                 if ((fpgaSensorStatus.GetDi(0, k) == 0) && (!disSensor[k + 16].isSelected())) {
                     mHandler.sendEmptyMessage(k + 16);
                 //    Log.e("MainActivity", String.valueOf(k));
                 }
                 if ((fpgaSensorStatus.GetDi(0, k) == 1) && (disSensor[k + 16].isSelected())) {
                     mHandler.sendEmptyMessage(k + 46);
                  //   Log.e("MainActivity", String.valueOf(k));
                 }
             }

        }


    }

}
