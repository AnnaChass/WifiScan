package com.example.mobile1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mobile1.databinding.ActivityDetailsBinding;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle bundle = getIntent().getExtras();

        TextView det_SSID = (TextView) findViewById(R.id.det_SSID);
        det_SSID.setText(bundle.getString("SSID"));

        TextView det_BSSID = (TextView) findViewById(R.id.det_BSSID);
        det_BSSID.setText(bundle.getString("BSSID"));

        TextView det_level = (TextView) findViewById(R.id.det_level);
        String level = bundle.getString("Level");
        det_level.setText("Level");
        int i = Integer.parseInt(level);
        if (i>-50){
            det_level.setText("High");
        } else if (i<=-50 && i>-80){
            det_level.setText("Middle");
        } else if (i<=-80){
            det_level.setText("Low");
        }

        TextView det_freq = (TextView) findViewById(R.id.det_freq);
        det_freq.setText(bundle.getString("Frequency"));

        TextView det_encrypt = (TextView) findViewById(R.id.det_encrypt);
        det_encrypt.setText(bundle.getString("Encryption"));

        TextView det_extra = (TextView) findViewById(R.id.det_extra);
        det_extra.setText(bundle.getString("Extra"));
    }
}