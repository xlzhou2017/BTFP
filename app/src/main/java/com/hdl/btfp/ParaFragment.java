package com.hdl.btfp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

/**
 * Created by xlzhou on 2017/7/12.
 */

public class ParaFragment extends Fragment implements View.OnClickListener {
    private SharedPreferences pref;
    SharedPreferences.Editor editor;
    private EditText stepSpeed;
    private EditText shellSpeed;
    private EditText batterySpeed;
//    private EditText lockBatterySpeed;
    private EditText waterProofSpeed;
    private EditText ultrasonicSpeed;

    private EditText shellStartPos;
    private EditText batteryStartPos;
    private EditText proofWaterStartPos;
    private EditText ultrasonicStartPos;

    private EditText shellPos;
    private EditText batteryPos;
//    private EditText lockBatteryPos;
    private EditText proofWaterPos;
    private EditText ultrasonicPos;

    private Button saveBtn;
    private Button applyBtn;

    private View view;
    private ScrollView mScrollView;
    private Handler nHandler = new Handler();
    //  int speed;
    private String str;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("MainActivity","onCreate" );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.para, container, false);
        init();
        saveInit();
        bindListen();
        Log.e("MainActivity","onCreateView" );
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("MainActivity","onActivityCreated" );
    }
/*
    @Override
    public void onAttach(Context context)
    {
        Log.e("MainActivity","onAttach" );
        super.onAttach(context);
    }
*/
    @Override
    public void onPause() {
        super.onPause();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                editor = pref.edit();
                editor.clear();
             /*   editor.putInt("saveShellSpeed", Integer.parseInt(shellSpeed.getText().toString()));
                editor.putInt("saveBatterySpeed", Integer.parseInt(batterySpeed.getText().toString()));
                editor.putInt("saveLockBatterySpeed", Integer.parseInt(lockBatterySpeed.getText().toString()));
                editor.putInt("saveWaterProofSpeed", Integer.parseInt(waterProofSpeed.getText().toString()));
                editor.putInt("saveUltrasonicSpeed", Integer.parseInt(ultrasonicSpeed.getText().toString()));
                editor.putInt("saveStepSpeed", Integer.parseInt(stepSpeed.getText().toString()));

                editor.putInt("saveShellPos", Integer.parseInt(shellPos.getText().toString()));
                editor.putInt("saveBatteryPos", Integer.parseInt(batteryPos.getText().toString()));
                editor.putInt("saveLockBatteryPos", Integer.parseInt(lockBatteryPos.getText().toString()));
                editor.putInt("saveProofWaterPos", Integer.parseInt(proofWaterPos.getText().toString()));
                editor.putInt("saveUltrasonicPos", Integer.parseInt(ultrasonicPos.getText().toString()));
                */

                editor.putFloat("saveShellSpeed",Float.parseFloat(shellSpeed.getText().toString()));
                editor.putFloat("saveBatterySpeed",Float.parseFloat(batterySpeed.getText().toString()));
            //    editor.putFloat("saveLockBatterySpeed",Float.parseFloat(lockBatterySpeed.getText().toString()));
                editor.putFloat("saveWaterProofSpeed",Float.parseFloat(waterProofSpeed.getText().toString()));
                editor.putFloat("saveUltrasonicSpeed",Float.parseFloat(ultrasonicSpeed.getText().toString()));
                editor.putFloat("saveStepSpeed",Float.parseFloat(stepSpeed.getText().toString()));

                editor.putFloat("saveShellStartPos",Float.parseFloat(shellStartPos.getText().toString()));
                editor.putFloat("saveBatteryStartPos",Float.parseFloat(batteryStartPos.getText().toString()));
                editor.putFloat("saveProofWaterStartPos",Float.parseFloat(proofWaterStartPos.getText().toString()));
                editor.putFloat("saveUltrasonicStartPos",Float.parseFloat(ultrasonicStartPos.getText().toString()));

                editor.putFloat("saveShellPos",Float.parseFloat(shellPos.getText().toString()));
                editor.putFloat("saveBatteryPos",Float.parseFloat(batteryPos.getText().toString()));
            //    editor.putFloat("saveLockBatteryPos",Float.parseFloat(lockBatteryPos.getText().toString()));
                editor.putFloat("saveProofWaterPos",Float.parseFloat(proofWaterPos.getText().toString()));
                editor.putFloat("saveUltrasonicPos",Float.parseFloat(ultrasonicPos.getText().toString()));
                editor.apply();
                break;
            case R.id.apply:
                /*
                Utils.setData(Integer.parseInt(shellSpeed.getText().toString()),0);
                Utils.setData(Integer.parseInt(batterySpeed.getText().toString()),1);
                Utils.setData(Integer.parseInt(lockBatterySpeed.getText().toString()),2);
                Utils.setData(Integer.parseInt(waterProofSpeed.getText().toString()),3);
                Utils.setData(Integer.parseInt(ultrasonicSpeed.getText().toString()),4);
                Utils.setData(Integer.parseInt(stepSpeed.getText().toString()),5);

                Utils.setData(Integer.parseInt(shellPos.getText().toString()),6);
                Utils.setData(Integer.parseInt(batteryPos.getText().toString()),7);
                Utils.setData(Integer.parseInt(lockBatteryPos.getText().toString()),8);
                Utils.setData(Integer.parseInt(proofWaterPos.getText().toString()),9);
                Utils.setData(Integer.parseInt(ultrasonicPos.getText().toString()),10);
                */
                Utils.setData(Float.parseFloat(shellSpeed.getText().toString()),0);
                Utils.setData(Float.parseFloat(batterySpeed.getText().toString()),1);
            //    Utils.setData(Float.parseFloat(lockBatterySpeed.getText().toString()),2);
                Utils.setData(Float.parseFloat(waterProofSpeed.getText().toString()),2);
                Utils.setData(Float.parseFloat(ultrasonicSpeed.getText().toString()),3);
                Utils.setData(Float.parseFloat(stepSpeed.getText().toString()),4);

                Utils.setData(Float.parseFloat(shellStartPos.getText().toString()),5);
                Utils.setData(Float.parseFloat(batteryStartPos.getText().toString()),6);
                Utils.setData(Float.parseFloat(proofWaterStartPos.getText().toString()),7);
                Utils.setData(Float.parseFloat(ultrasonicStartPos.getText().toString()),8);

                Utils.setData(Float.parseFloat(shellPos.getText().toString()),9);
                Utils.setData(Float.parseFloat(batteryPos.getText().toString()),10);
            //    Utils.setData(Float.parseFloat(lockBatteryPos.getText().toString()),8);
                Utils.setData(Float.parseFloat(proofWaterPos.getText().toString()),11);
                Utils.setData(Float.parseFloat(ultrasonicPos.getText().toString()),12);


                shellSpeed.setText(String.valueOf(Utils.getData(0)));
                batterySpeed.setText(String.valueOf(Utils.getData(1)));
            //    lockBatterySpeed.setText(String.valueOf(Utils.getData(2)));
                waterProofSpeed.setText(String.valueOf(Utils.getData(2)));
                ultrasonicSpeed.setText(String.valueOf(Utils.getData(3)));
                stepSpeed.setText(String.valueOf(Utils.getData(4)));

                shellStartPos.setText(String.valueOf(Utils.getData(5)));
                batteryStartPos.setText(String.valueOf(Utils.getData(6)));
                proofWaterStartPos.setText(String.valueOf(Utils.getData(7)));
                ultrasonicStartPos.setText(String.valueOf(Utils.getData(8)));

                shellPos.setText(String.valueOf(Utils.getData(9)));
                batteryPos.setText(String.valueOf(Utils.getData(10)));
             //   lockBatteryPos.setText(String.valueOf(Utils.getData(8)));
                proofWaterPos.setText(String.valueOf(Utils.getData(11)));
                ultrasonicPos.setText(String.valueOf(Utils.getData(12)));




                break;
            case R.id.scroll:
                nHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mScrollView.fullScroll(View.FOCUS_DOWN);
                    }
                }, 100);
                break;
            default:
                break;
        }
    }

    private void init() {
        stepSpeed = view.findViewById(R.id.stepSpeedET);
        shellSpeed = view.findViewById(R.id.shellSpeedET);
        batterySpeed = view.findViewById(R.id.batterySpeedET);
    //    lockBatterySpeed = view.findViewById(R.id.lockBatterySpeedET);
        waterProofSpeed = view.findViewById(R.id.waterProofSpeedET);
        ultrasonicSpeed = view.findViewById(R.id.ultrasonicSpeedET);

        shellStartPos = view.findViewById(R.id.shellStartPosET);
        batteryStartPos = view.findViewById(R.id.batteryStartPosET);
        proofWaterStartPos = view.findViewById(R.id.waterProofStartPosET);
        ultrasonicStartPos = view.findViewById(R.id.ultrasonicStartPosET);

        shellPos = view.findViewById(R.id.shellPosET);
        batteryPos = view.findViewById(R.id.batteryPosET);
     //   lockBatteryPos = view.findViewById(R.id.lockBatteryPosET);
        proofWaterPos = view.findViewById(R.id.waterProofPosET);
        ultrasonicPos = view.findViewById(R.id.ultrasonicPosET);

        saveBtn = view.findViewById(R.id.save);
        applyBtn = view.findViewById(R.id.apply);
        mScrollView = view.findViewById(R.id.scroll);
    }

    private void bindListen() {
        saveBtn.setOnClickListener(this);
        applyBtn.setOnClickListener(this);
        mScrollView.setOnClickListener(this);

        shellSpeed.setText(String.valueOf(Utils.getData(0)));
        batterySpeed.setText(String.valueOf(Utils.getData(1)));
  //      lockBatterySpeed.setText(String.valueOf(Utils.getData(2)));
        waterProofSpeed.setText(String.valueOf(Utils.getData(2)));
        ultrasonicSpeed.setText(String.valueOf(Utils.getData(3)));
        stepSpeed.setText(String.valueOf(Utils.getData(4)));

        shellStartPos.setText(String.valueOf(Utils.getData(5)));
        batteryStartPos.setText(String.valueOf(Utils.getData(6)));
        proofWaterStartPos.setText(String.valueOf(Utils.getData(7)));
        ultrasonicStartPos.setText(String.valueOf(Utils.getData(8)));

        shellPos.setText(String.valueOf(Utils.getData(9)));
        batteryPos.setText(String.valueOf(Utils.getData(10)));
 //       lockBatteryPos.setText(String.valueOf(Utils.getData(8)));
        proofWaterPos.setText(String.valueOf(Utils.getData(11)));
        ultrasonicPos.setText(String.valueOf(Utils.getData(12)));
    }

    private void saveInit()
    {
        pref = this.getActivity().getSharedPreferences("saveStepSpeed", Context.MODE_PRIVATE);
        pref = this.getActivity().getSharedPreferences("saveShellSpeed",Context.MODE_PRIVATE);
        pref = this.getActivity().getSharedPreferences("saveBatterySpeed",Context.MODE_PRIVATE);
  //      pref = this.getActivity().getSharedPreferences("saveLockBatterySpeed",Context.MODE_PRIVATE);
        pref = this.getActivity().getSharedPreferences("saveWaterProofSpeed",Context.MODE_PRIVATE);
        pref = this.getActivity().getSharedPreferences("saveUltrasonicSpeed",Context.MODE_PRIVATE);

        pref = this.getActivity().getSharedPreferences("saveShellStartPos",Context.MODE_PRIVATE);
        pref = this.getActivity().getSharedPreferences("saveBatteryStartPos",Context.MODE_PRIVATE);
        pref = this.getActivity().getSharedPreferences("saveProofWaterStartPos",Context.MODE_PRIVATE);
        pref = this.getActivity().getSharedPreferences("saveUltrasonicStartPos",Context.MODE_PRIVATE);

        pref = this.getActivity().getSharedPreferences("saveShellPos",Context.MODE_PRIVATE);
        pref = this.getActivity().getSharedPreferences("saveBatteryPos",Context.MODE_PRIVATE);
 //       pref = this.getActivity().getSharedPreferences("saveLockBatteryPos",Context.MODE_PRIVATE);
        pref = this.getActivity().getSharedPreferences("saveProofWaterPos",Context.MODE_PRIVATE);
        pref = this.getActivity().getSharedPreferences("saveUltrasonicPos",Context.MODE_PRIVATE);
/*
        Utils.setData(pref.getInt("saveShellSpeed", 0),0);
        Utils.setData(pref.getInt("saveBatterySpeed", 0),1);
        Utils.setData(pref.getInt("saveLockBatterySpeed", 0),2);
        Utils.setData(pref.getInt("saveWaterProofSpeed", 0),3);
        Utils.setData(pref.getInt("saveUltrasonicSpeed", 0),4);
        Utils.setData(pref.getInt("saveStepSpeed", 0),5);

        Utils.setData(pref.getInt("saveShellPos", 0),6);
        Utils.setData(pref.getInt("saveBatteryPos", 0),7);
        Utils.setData(pref.getInt("saveLockBatteryPos", 0),8);
        Utils.setData(pref.getInt("saveProofWaterPos", 0),9);
        Utils.setData(pref.getInt("saveUltrasonicPos", 0),10);
        */
        Utils.setData(pref.getFloat("saveShellSpeed", 0),0);
        Utils.setData(pref.getFloat("saveBatterySpeed", 0),1);
    //    Utils.setData(pref.getFloat("saveLockBatterySpeed", 0),2);
        Utils.setData(pref.getFloat("saveWaterProofSpeed", 0),2);
        Utils.setData(pref.getFloat("saveUltrasonicSpeed", 0),3);
        Utils.setData(pref.getFloat("saveStepSpeed", 0),4);

        Utils.setData(pref.getFloat("saveShellStartPos", 0),5);
        Utils.setData(pref.getFloat("saveBatteryStartPos", 0),6);
        Utils.setData(pref.getFloat("saveProofWaterStartPos", 0),7);
        Utils.setData(pref.getFloat("saveUltrasonicStartPos", 0),8);

        Utils.setData(pref.getFloat("saveShellPos", 0),9);
        Utils.setData(pref.getFloat("saveBatteryPos", 0),10);
  //      Utils.setData(pref.getFloat("saveLockBatteryPos", 0),8);
        Utils.setData(pref.getFloat("saveProofWaterPos", 0),11);
        Utils.setData(pref.getFloat("saveUltrasonicPos", 0),12);

    }
}
