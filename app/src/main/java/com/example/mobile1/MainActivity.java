package com.example.mobile1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.location.Location;
import android.location.LocationManager;
import android.location.Criteria;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Element [] nets;
    private WifiManager wifiManager;
    private List<ScanResult> wifiList;
    private BroadcastReceiver wifiReceiver;
    private Activity context_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context_ = this;

        detectWifi();

        FloatingActionButton button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detectWifi();
                Snackbar.make(view, "Scanning...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void detectWifi(){

        this.wifiManager = (WifiManager)this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(this, "WiFi is disabled ... We need to enable it", Toast.LENGTH_LONG).show();
            wifiManager.setWifiEnabled(true);
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);

        wifiReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent intent) {
                boolean ok = intent.getBooleanExtra(wifiManager.EXTRA_RESULTS_UPDATED, false);
                if (ok) {
                    wifiList = wifiManager.getScanResults();

                    nets = new Element[wifiList.size()];
                    for (int i = 0; i < wifiList.size(); i++) {
                        String item = wifiList.get(i).toString();
                        String[] vector_item = item.split(",");
                        String ssid_ = vector_item[0];
                        String bssid_ = vector_item[1];
                        String encrypt_ = vector_item[2];
                        String level_ = vector_item[3];
                        String frequency_ = vector_item[4];
                        String extra_ = vector_item[5];

                        String ssid = ssid_.split(": ")[1];
                        String bssid = bssid_.split(": ")[1];
                        String encrypt = encrypt_.split(": ")[1];
                        String level = level_.split(": ")[1];
                        String frequency = frequency_.split(": ")[1];
                        String extra = extra_.split(": ")[1];

                        nets[i] = new Element(ssid, bssid, level, frequency, encrypt, extra);

                        AdapterElements adapterElements = new AdapterElements(context_);
                        final ListView netList = (ListView) findViewById(R.id.list_item);

                        netList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                                intent.putExtra("SSID",nets[position].getSSID());
                                intent.putExtra("BSSID",nets[position].getBSSID());
                                intent.putExtra("Level",nets[position].getLevel());
                                intent.putExtra("Frequency",nets[position].getFrequency());
                                intent.putExtra("Encryption",nets[position].getEncrypt());
                                intent.putExtra("Extra",nets[position].getExtra());
                                startActivity(intent);
                            }
                        });

                        netList.setAdapter(adapterElements);
                    }
                }
            }
        };
        registerReceiver(wifiReceiver, intentFilter);
        wifiManager.startScan();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class AdapterElements extends ArrayAdapter<Object> {
        Activity context;

        public AdapterElements(Activity context) {
            super(context, R.layout.content_main, nets);
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.content_main, null);

            TextView Ssid = (TextView) item.findViewById(R.id.network_name);
            Ssid.setText(nets[position].getSSID());

            return item;
        }
    }
}