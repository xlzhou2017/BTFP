package com.hdl.btfp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.hdl.btfp.com.TcpClient;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public static TcpClient TcpIp = null;
//    private InputFilter inputFilter;
 //   private MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
//    private static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager vp = (ViewPager)findViewById(R.id.viewpager);
        vp.setOffscreenPageLimit(4);
        List<Fragment> fragments = new ArrayList<Fragment>();


        fragments.add(new mainFragment());
        fragments.add(new ManulFragment());
        fragments.add(new SensorFragment());
        fragments.add(new ParaFragment());



        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(),fragments);
        vp.setAdapter(adapter);
        vp.setCurrentItem(0);
    }
    /*
    private class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String mAction = intent.getAction();
            switch (mAction)
            {
                case "tcpSend":
                    String msg = intent.getStringExtra("tcpSend");
                    TcpIp.send(msg);
                    break;
            }
        }
    }
    private void bindReceiver()
    {
        IntentFilter intentFilter = new IntentFilter("tcpSend");
        registerReceiver(myBroadcastReceiver,intentFilter);
    }
    */

}
