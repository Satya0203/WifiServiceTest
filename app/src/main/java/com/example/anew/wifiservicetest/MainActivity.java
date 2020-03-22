package com.example.anew.wifiservicetest;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Switch swtch;
    ListView lview;
    WifiManager wmg;
    Vibrator vib;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lview = findViewById(R.id.lv1);
        swtch = findViewById(R.id.s1);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        wmg = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        int state = wmg.getWifiState();
        if (state == 0 || state == 1) {
            swtch.setChecked(false);
        } else if (state == 2 || state == 3) {
            swtch.setChecked(true);
        }
        swtch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                wmg.setWifiEnabled(isChecked);
                vib.vibrate(5000);
            }
        });
    }

    public void getScannedResults(View view)
    {

        List<ScanResult> list = wmg.getScanResults();
        ArrayList<String> templist = new ArrayList<String>();
        for (ScanResult result : list) {
            templist.add(result.SSID + "\t" + result.frequency);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, templist);
        lview.setAdapter(adapter);
        vib.vibrate(1000);
    }

    public void getConnectedDevices(View view)
    {
        vib.vibrate(3000);
        List<WifiConfiguration> list = wmg.getConfiguredNetworks();
        ArrayList<String> templist = new ArrayList<String>();
        for (WifiConfiguration result : list) {
            templist.add(result.SSID + "\t" + result.status);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, templist);
        lview.setAdapter(adapter);
    }
}