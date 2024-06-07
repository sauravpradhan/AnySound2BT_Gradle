package com.saurav.anysound2bt;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.security.Permission;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    AudioManager mAudioMgr = null;
    BluetoothAdapter mBtAdapter;
    public static Context mCtx;
    private Set<BluetoothDevice> mDevices;
    BluetoothManager mBtMgr = null;
    ToggleButton mToggleBtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAudioMgr = (AudioManager) getApplicationContext().getSystemService(getApplicationContext().AUDIO_SERVICE);
        mBtMgr = (BluetoothManager) getApplicationContext().getSystemService(getApplicationContext().BLUETOOTH_SERVICE);
        ImageView img = (ImageView) findViewById(R.id.image1);
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        mToggleBtn = findViewById(R.id.toggleButton1);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 99);
        }
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://github.com/sauravpradhan/"));
                startActivity(intent);
            }
        });
    }

    public void onToggleClicked(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
                == PackageManager.PERMISSION_GRANTED) {
            boolean on = ((ToggleButton) view).isChecked();
            mDevices = mBtAdapter.getBondedDevices();

            if (on) {
                if ((mDevices.size() > 0)) {
                    // TODO Auto-generated method stub
                    mAudioMgr.setMode(mAudioMgr.MODE_IN_COMMUNICATION);
                    mAudioMgr.setBluetoothScoOn(true);
                    mAudioMgr.startBluetoothSco();
                    mAudioMgr.setSpeakerphoneOn(false);
                    Log.d("S@ur@v", "Toggle Button On!");
                } else {
                    mToggleBtn.setChecked(false);
                    Toast.makeText(getApplicationContext(), "BT is not connected, Pls pair your device and restart the app again!", Toast.LENGTH_LONG).show();
                }

            } else {
                mAudioMgr.setMode(mAudioMgr.MODE_NORMAL);
                mAudioMgr.setBluetoothScoOn(false);
                mAudioMgr.stopBluetoothSco();
                mAudioMgr.setSpeakerphoneOn(true);
                Log.d("S@ur@v", "Toggle Button Off!");

            }
        }
    }
}