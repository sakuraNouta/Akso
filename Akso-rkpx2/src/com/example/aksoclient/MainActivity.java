package com.example.aksoclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity {
	
    TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("tag","oncreate");

        
        /*
         * rkpx2设备初始化
         */
    	com.rkpx2.HardCtl.led1_dev_init();
    	com.rkpx2.HardCtl.led2_dev_init();
    	com.rkpx2.HardCtl.led3_dev_init();
    	com.rkpx2.HardCtl.key1_dev_init();
    	com.rkpx2.HardCtl.key2_dev_init();
    	com.rkpx2.HardCtl.key3_dev_init();
    	com.rkpx2.HardCtl.ds18b20_dev_init();
    	com.rkpx2.HardCtl.beep_dev_init();

        new Thread(new ClientThread()).start();   

    }

}

