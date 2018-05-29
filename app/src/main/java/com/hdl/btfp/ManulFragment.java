package com.hdl.btfp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by xlzhou on 2017/7/12.
 */

public class ManulFragment extends Fragment implements View.OnClickListener, View.OnTouchListener
{

    private Fpga FpgaAPI = new Fpga();
    private View view;
    Button shellGoBtn ;
    Button shellHomeBtn ;
    Button batterGoBtn ;
    Button batteryHomeBtn ;
    Button lockBatteryCoverGoBtn ;
    Button lockBatteryCoverHomeBtn ;
    Button torsionHeadBtn ;
    Button twistCapBtn ;
    Button waterProofTestGoBtn ;
    Button waterProofTestHomeBtn ;
    Button waterProofCoverBtn ;
    Button waterProofTestStartBtn ;
    Button ultrasonicGoBtn ;
    Button ultrasonicHomeBtn ;
    Button ultrasonicStart ;
    Button dispensingStartBtn ;
    Button batteryShellBtn;
    Button pushBatteryBtn ;
    Button batterySpinBtn ;
    Button conveyorBtn ;
    Button robotTest2 ;
    Button robotTest3 ;
    Button robotTest4 ;
    Button lockBatteryColletBtn ;
   // ParaFragment Para ;
  //  Handler mHandler = null;
/*    Button runBtn , stopBtn, jogBtn , pushB, shrinkB ;
    TextView[] disSensor = new TextView[6];
    boolean [] tvState  = new boolean[12];
    boolean motorSensor;
    boolean gasFrontSensor;
    boolean gasBackSensor;
    int time;
    boolean start;
    boolean finish;
    boolean run;
    boolean isPressed;
*/
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
         view = inflater.inflate(R.layout.manul,container,false);
        btnInit();
        bindListener();
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onPause(){
        super.onPause();
    }
    public void onClick(View v)
    {

        switch (v.getId())
        {
           case R.id.shell_go_btn :
               if(shellGoBtn.isSelected())
               {
                   shellGoBtn.setSelected(false);
                   FpgaAPI.MotorStop(1,0);
               }else {
                   shellGoBtn.setSelected(true);

                   FpgaAPI.servON(1,0);
                   FpgaAPI.InitAddr();
                   FpgaAPI.MotorSetSp(1,0,Math.round(10*Utils.getData(0)));
                 //  FpgaAPI.SetSp(1,0,100);
                 //   FpgaAPI.MotorJP(1,0);
                   FpgaAPI.MotorGoPos(1,0,Math.round(10*Utils.getData(9)));
               }
                break;
            case R.id.shell_home_btn:
                if(shellHomeBtn.isSelected())
                {
                    FpgaAPI.MotorStop(1,0);
                    shellHomeBtn.setSelected(false);
                }
                else {
                    FpgaAPI.servON(1, 0);
                    FpgaAPI.InitAddr();
                //    FpgaAPI.SetSp(1, 0, 100);
                    FpgaAPI.MotorSetSp(1,0,Math.round(10*Utils.getData(0)));
                    FpgaAPI.MotorGoPos(1,0,Math.round(10*Utils.getData(5)));
                    shellHomeBtn.setSelected(true);
                }
                break;

            case R.id.batter_go_btn:
                if(batterGoBtn.isSelected())
                {
                    FpgaAPI.MotorStop(1,1);
                    batterGoBtn.setSelected(false);
                }
                else {
                    FpgaAPI.servON(1, 1);
                    FpgaAPI.InitAddr();
                    FpgaAPI.MotorSetSp(1, 1, Math.round(10*Utils.getData(1)));
                  //  FpgaAPI.SetSp(1, 1, 100);
                  //  FpgaAPI.MotorJP(1, 1);
                    FpgaAPI.MotorGoPos(1,1,Math.round(10*Utils.getData(10)));

                    batterGoBtn.setSelected(true);
                    Log.e("MainActivity", "dispensing_start_btn_down" + Utils.getData(1));
                }
                break;
            case R.id.battery_home_btn:
                if(batteryHomeBtn.isSelected())
                {
                    FpgaAPI.MotorStop(1,1);
                    batteryHomeBtn.setSelected(false);
                }else {

                    FpgaAPI.servON(1,1);
                    FpgaAPI.InitAddr();
                 //   FpgaAPI.SetSp(1,1,100);
                    FpgaAPI.MotorSetSp(1, 1, Math.round(10*Utils.getData(1)));
                    //  FpgaAPI.SetSp(1, 1, 100);
                    //  FpgaAPI.MotorJP(1, 1);
                    FpgaAPI.MotorGoPos(1,1,Math.round(10*Utils.getData(6)));

                    batteryHomeBtn.setSelected(true);
                }
                break;
        /*    case R.id.lock_battery_cover_go_btn:
                if(lockBatteryCoverGoBtn.isSelected()) {
                    FpgaAPI.MotorStop(2,0);
                    lockBatteryCoverGoBtn.setSelected(false);
                }
                else{

                    FpgaAPI.servON(2, 0);
                    FpgaAPI.InitAddr();
                  //  FpgaAPI.SetSp(2, 0, 800);
                    FpgaAPI.MotorSetSp(2, 0, Math.round(10*Utils.getData(2)));
                //    FpgaAPI.MotorJP(2, 0);
                    FpgaAPI.MotorGoPos(2,0,Math.round(10*Utils.getData(8)));
                    lockBatteryCoverGoBtn.setSelected(true);
                }
                break;
            case R.id.lock_battery_cover_home_btn:
                if(lockBatteryCoverHomeBtn.isSelected())
                {
                    FpgaAPI.MotorStop(2,0);
                    lockBatteryCoverHomeBtn.setSelected(false);
                }else {

                    FpgaAPI.servON(2, 0);
                    FpgaAPI.InitAddr();
                    //FpgaAPI.SetSp(2, 0, 800);
                    FpgaAPI.MotorSetSp(2, 0, Math.round(10*Utils.getData(2)));
                    FpgaAPI.MotorGoHome(2, 0);
                    lockBatteryCoverHomeBtn.setSelected(true);
                }
                break;
            case R.id.torsion_head_btn:
                if(torsionHeadBtn.isSelected())
                {
                    torsionHeadBtn.setSelected(false);
                    FpgaAPI.SetDo(1,5,0);
                }
                else
                {
                    torsionHeadBtn.setSelected(true);
                    FpgaAPI.SetDo(1,5,1);
                }
                break;
            case R.id.twist_cap_btn:
                if (twistCapBtn.isSelected())
                {
                    twistCapBtn.setSelected(false);
                    FpgaAPI.SetDo(1,4,0);
                }
                else
                {
                    twistCapBtn.setSelected(true);
                    FpgaAPI.SetDo(1,4,1);
                }
                break;
                */
            case R.id.lock_battery_cover_go_btn:
                if(lockBatteryCoverGoBtn.isSelected()) {
                    FpgaAPI.MotorStop(1,0);
                    lockBatteryCoverGoBtn.setSelected(false);
                }
                else{

                    FpgaAPI.servON(1, 0);
                    FpgaAPI.InitAddr();
                    //FpgaAPI.SetSp(2, 0, 800);
                    FpgaAPI.MotorSetSp(1, 0, Math.round(10*10));
                    FpgaAPI.MotorGoHome(1, 0);
                    lockBatteryCoverGoBtn.setSelected(true);
                }
                break;
            case R.id.lock_battery_cover_home_btn:
                if(lockBatteryCoverHomeBtn.isSelected())
                {
                    FpgaAPI.MotorStop(1,1);
                    lockBatteryCoverHomeBtn.setSelected(false);
                }else {

                    FpgaAPI.servON(1, 1);
                    FpgaAPI.InitAddr();
                    //FpgaAPI.SetSp(2, 0, 800);
                    FpgaAPI.MotorSetSp(1, 1, Math.round(10*10));
                    FpgaAPI.MotorGoHome(1, 1);
                    lockBatteryCoverHomeBtn.setSelected(true);
                }
                break;
            case R.id.torsion_head_btn:
                if(torsionHeadBtn.isSelected())
                {
                    torsionHeadBtn.setSelected(false);
                    FpgaAPI.MotorStop(3,0);
                }
                else
                {
                    torsionHeadBtn.setSelected(true);
                    FpgaAPI.servON(3, 0);
                    FpgaAPI.InitAddr();
                    //FpgaAPI.SetSp(2, 0, 800);
                    FpgaAPI.MotorSetSp(3, 0, Math.round(10*10));
                    FpgaAPI.MotorGoHome(3, 0);
                }
                break;
            case R.id.twist_cap_btn:
                if (twistCapBtn.isSelected())
                {
                    twistCapBtn.setSelected(false);
                    FpgaAPI.MotorStop(2,1);
                }
                else
                {
                    twistCapBtn.setSelected(true);
                    FpgaAPI.servON(2, 1);
                    FpgaAPI.InitAddr();
                    //FpgaAPI.SetSp(2, 0, 800);
                    FpgaAPI.MotorSetSp(2, 1, Math.round(10*10));
                    FpgaAPI.MotorGoHome(2, 1 );
                }
                break;
            case R.id.water_proof_test_go_btn:
                if(waterProofTestGoBtn.isSelected())
                {
                    FpgaAPI.MotorStop(2,1);
                    waterProofTestGoBtn.setSelected(false);
                }else {

                    FpgaAPI.servON(2,1);
                    FpgaAPI.InitAddr();
                    FpgaAPI.MotorSetSp(2,1,Math.round(10*Utils.getData(2)));
                   // FpgaAPI.SetSp(2,1,400);
                  //  FpgaAPI.MotorJP(2,1);
                    FpgaAPI.MotorGoPos(2,1,Math.round(10*Utils.getData(11)));
                    waterProofTestGoBtn.setSelected(true);
                }
                break;
            case R.id.water_proof_test_home_btn:
                if(waterProofTestHomeBtn.isSelected()){
                    waterProofTestHomeBtn.setSelected(false);
                    FpgaAPI.MotorStop(2,1);
                }else {

                    FpgaAPI.InitAddr();
                    FpgaAPI.servON(2, 1);
                 //   FpgaAPI.SetSp(2, 1, 400);
                    FpgaAPI.MotorSetSp(2,1,Math.round(10*Utils.getData(2)));
                    FpgaAPI.MotorGoPos(2,1,Math.round(10*Utils.getData(7)));
                    waterProofTestHomeBtn.setSelected(true );
                }
                break;
            case R.id.water_proof_cover_btn:
                if(waterProofCoverBtn.isSelected())
                {
                    waterProofCoverBtn.setSelected(false);
                    FpgaAPI.SetDo(2,0,0);
                }
                else
                {
                    waterProofCoverBtn.setSelected(true);
                    FpgaAPI.SetDo(2,0,1);
                }
                break;
            case R.id.water_proof_test_start_btn:

                break;
            case R.id.ultrasonic_go_btn:
            //    FpgaAPI.SetSp(2,1,Utils.getData(3));
                if(ultrasonicGoBtn.isSelected()){
                    ultrasonicGoBtn.setSelected(false);
                    FpgaAPI.MotorStop(3,0);
                }
                else{
                    ultrasonicGoBtn.setSelected(true);
                    FpgaAPI.servON(3, 0);
                    FpgaAPI.InitAddr();
                   // FpgaAPI.SetSp(3, 0, 100);
                    FpgaAPI.MotorSetSp(3,0,Math.round(10*Utils.getData(3)));
                    FpgaAPI.MotorGoPos(3,0,Math.round(10*Utils.getData(12)));
                  //  FpgaAPI.MotorJP(3, 0);
                    Log.e("MainActivity", "ultrasonic_go_btn");
                }

                break;
            case R.id.ultrasonic_home_btn:
                if(ultrasonicHomeBtn.isSelected())
                {
                    ultrasonicHomeBtn.setSelected(false);
                    FpgaAPI.MotorStop(3,0);
                }
                else{
                    ultrasonicHomeBtn.setSelected(true);
                    FpgaAPI.servON(3, 0);
                    FpgaAPI.InitAddr();
                 //   FpgaAPI.SetSp(3, 0, 100);
                    FpgaAPI.MotorSetSp(3,0,Math.round(10*Utils.getData(3)));
                    FpgaAPI.MotorGoPos(3,0,Math.round(10*Utils.getData(8)));
                    Log.e("MainActivity", "ultrasonic_home_btn");
                }
                break;
            case R.id.ultrasonic_start:
                break;
            case R.id.dispensing_start_btn:
                break;
            case R.id.battery_shell_btn:
                if(batteryShellBtn.isSelected())
                {
                    batteryShellBtn.setSelected(false);
                    FpgaAPI.SetDo(1,1,0);
                }
                else
                {
                    batteryShellBtn.setSelected(true);
                    FpgaAPI.SetDo(1,1,1);
                }
                break;
            case R.id.push_battery_btn:
                if(pushBatteryBtn.isSelected())
                {
                    pushBatteryBtn.setSelected(false);
                    FpgaAPI.SetDo(1,0,0);
                }
                else
                {
                    pushBatteryBtn.setSelected(true);
                    FpgaAPI.SetDo(1,0,1);
                }
                break;
            case R.id.lock_battery_collet_btn:
                if(lockBatteryColletBtn.isSelected())
                {
                   lockBatteryColletBtn.setSelected(false);
               //     FpgaAPI.SetDo(1,3,0);
                    FpgaAPI.MotorStop(1,0);
                    FpgaAPI.SetDo(1, 4, 0);
                }
                else
                {
                    lockBatteryColletBtn.setSelected(true);
                    FpgaAPI.SetSp(1, 0, Math.round(10*Utils.getData(5)));
                    FpgaAPI.SetSp(1, 0, 8000);
                    FpgaAPI.MotorJP(1, 0);
                    FpgaAPI.SetDo(1, 4, 1);
                  //  FpgaAPI.SetDo(1,3,1);
                }
                break;
            case R.id.battery_spin_btn:
                if(batterySpinBtn.isSelected())
                {
                    batterySpinBtn.setSelected(false);
                    FpgaAPI.MotorStop(0,0);
                    FpgaAPI.SetDo(0, 4, 0);
                    Log.e("MainActivity","battery_spin_btn_down");
                }
                else {
                    batterySpinBtn.setSelected(true);
                    FpgaAPI.SetSp(0, 0, Math.round(10*Utils.getData(5)));
                    FpgaAPI.SetSp(0, 0, 8000);
                    FpgaAPI.MotorJP(0, 0);
                    FpgaAPI.SetDo(0, 4, 1);
                    Log.e("MainActivity","battery_spin_btn");
                }
                break;
            case R.id.conveyor_btn:
                if(conveyorBtn.isSelected()) {
                    conveyorBtn.setSelected(false);
                    FpgaAPI.SetDo(2, 2, 0);
                }
                else {
                    conveyorBtn.setSelected(true);
                    FpgaAPI.SetDo(2, 2, 1);
                }
                break;
            case R.id.robot_test2:
             //   MainActivity.TcpIp.send("UltToDispengsing"+"\r");
                MainActivity.TcpIp.send("passToHome"+"\r");
                break;
            case R.id.robot_test3:
             //   MainActivity.TcpIp.send("DispengsingToLoad" + "\r");
                break;
            case R.id.robot_test4:
                MainActivity.TcpIp.send("passToHome"+"\r");
                break;
            default:
                break;

        }
    }
    public  boolean onTouch(View v, MotionEvent event)
    {
        switch (v.getId())
        {
            case R.id.dispensing_start_btn:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        Log.e("MainActivity","dispensing_start_btn_down");
                        FpgaAPI.SetDo(2,4, 1);
                    break;
                    case MotionEvent.ACTION_UP:
                        FpgaAPI.SetDo(2,4, 0);
                        Log.e("MainActivity","dispensing_start_btn_up");
                    break;
                    default:
                        break;
                }
            break;
            case R.id.ultrasonic_start:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        FpgaAPI.SetDo(2,6,1);
                        Log.e("MainActivity","ultrasonic_start_btn_down");
                        break;
                    case MotionEvent.ACTION_UP:
                        FpgaAPI.SetDo(2,6,0);
                        Log.e("MainActivity","ultrasonic_start_btn_up");
                        break;
                    default:
                        break;
                }
            break;
            case R.id.water_proof_test_start_btn:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        FpgaAPI.SetDo(2,5,1);
                        Log.e("MainActivity","water_proof_test_start_btn_down");
                        break;
                    case MotionEvent.ACTION_UP:
                        FpgaAPI.SetDo(2,5,0);
                        Log.e("MainActivity","water_proof_test_start_btn_up");
                        break;
                    default:
                        break;
                }
            break;
          /*  case R.id.robot_test2:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        FpgaAPI.SetDo(2,1,1);
                        Log.e("MainActivity","water_proof_test_start_btn_down");
                        break;
                    case MotionEvent.ACTION_UP:
                        FpgaAPI.SetDo(2,1,0);
                        Log.e("MainActivity","water_proof_test_start_btn_up");
                        break;
                    default:
                        break;
                }
                break;
            case R.id.robot_test3:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        FpgaAPI.SetDo(2,3,1);
                        Log.e("MainActivity","water_proof_test_start_btn_down");
                        break;
                    case MotionEvent.ACTION_UP:
                        FpgaAPI.SetDo(2,3,0);
                        Log.e("MainActivity","water_proof_test_start_btn_up");
                        break;
                    default:
                        break;
                }
                break;*/
            default:
                break;
        }
        return false;
    }

    private void btnInit()
    {
        shellGoBtn = view.findViewById(R.id.shell_go_btn);
        shellHomeBtn = view.findViewById(R.id.shell_home_btn);
        batterGoBtn = view.findViewById(R.id.batter_go_btn);
        batteryHomeBtn = view.findViewById(R.id.battery_home_btn);
        lockBatteryCoverGoBtn = view.findViewById(R.id.lock_battery_cover_go_btn);
        lockBatteryCoverHomeBtn = view.findViewById(R.id.lock_battery_cover_home_btn);
        torsionHeadBtn = view.findViewById(R.id.torsion_head_btn);
        twistCapBtn = view.findViewById(R.id.twist_cap_btn);
        waterProofTestGoBtn = view.findViewById(R.id.water_proof_test_go_btn);
        waterProofTestHomeBtn = view.findViewById(R.id.water_proof_test_home_btn);
        waterProofCoverBtn = view.findViewById(R.id.water_proof_cover_btn);
        waterProofTestStartBtn = view.findViewById(R.id.water_proof_test_start_btn);
        ultrasonicGoBtn = view.findViewById(R.id.ultrasonic_go_btn);
        ultrasonicHomeBtn = view.findViewById(R.id.ultrasonic_home_btn);
        ultrasonicStart = view.findViewById(R.id.ultrasonic_start);
        dispensingStartBtn = view.findViewById(R.id.dispensing_start_btn);
        batteryShellBtn = view.findViewById(R.id.battery_shell_btn);
        pushBatteryBtn = view.findViewById(R.id.push_battery_btn);
        batterySpinBtn = view.findViewById(R.id.battery_spin_btn);
        lockBatteryColletBtn = view.findViewById(R.id.lock_battery_collet_btn);
        conveyorBtn = view.findViewById(R.id.conveyor_btn);
        robotTest2 = view.findViewById(R.id.robot_test2);
        robotTest3 = view.findViewById(R.id.robot_test3);
        robotTest4 = view.findViewById(R.id.robot_test4);
    }
    private void bindListener()
    {
        shellGoBtn.setOnClickListener(this);
        shellHomeBtn.setOnClickListener(this);
        batterGoBtn.setOnClickListener(this);
        batteryHomeBtn.setOnClickListener(this);
        lockBatteryCoverGoBtn.setOnClickListener(this);
        lockBatteryCoverHomeBtn.setOnClickListener(this);


        waterProofTestGoBtn.setOnClickListener(this);
        waterProofTestHomeBtn.setOnClickListener(this);

  //      waterProofTestStartBtn.setOnClickListener(this);
        ultrasonicGoBtn.setOnClickListener(this);
        ultrasonicHomeBtn.setOnClickListener(this);
     //   ultrasonicStart.setOnClickListener(this);
     //   dispensingStartBtn.setOnClickListener(this);
        batterySpinBtn.setOnClickListener(this);
        conveyorBtn.setOnClickListener(this);
        robotTest2.setOnClickListener(this);
        robotTest3.setOnClickListener(this);
        robotTest4.setOnClickListener(this);
        batteryShellBtn.setOnClickListener(this);
        torsionHeadBtn.setOnClickListener(this);
        twistCapBtn.setOnClickListener(this);
        waterProofCoverBtn.setOnClickListener(this);
        pushBatteryBtn.setOnClickListener(this);
        lockBatteryColletBtn.setOnClickListener(this);

        waterProofTestStartBtn.setOnTouchListener(this);
        ultrasonicStart.setOnTouchListener(this);
        dispensingStartBtn.setOnTouchListener(this);
        robotTest2.setOnTouchListener(this);
        robotTest3.setOnTouchListener(this);
    }

}
