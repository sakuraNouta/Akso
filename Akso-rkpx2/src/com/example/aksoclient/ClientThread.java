package com.example.aksoclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;
import android.widget.Toast;

public class ClientThread implements Runnable{

    static Socket s;
    BufferedReader br = null;
    OutputStream os = null;
    String str = null;
    int myTimer;

    @Override
    public void run() {

    	try {
            s = new Socket("192.168.43.118",1028);
            Log.i("tag","i am socketed");
            os = s.getOutputStream(); 
            //建立连接发送温度
            new Thread(new TempThread(os)).start();
            
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            while(true){
                
                str = br.readLine();
                if(str!= null){
                    Log.i("tag",str);
                    if(str.equals("on")){
                        //开灯
                        com.rkpx2.HardCtl.led1_dev_on();
                        com.rkpx2.HardCtl.beep_dev_on();
                        Log.i("tag","open led");
                    }
                    else if(str.equals("off")){
                        com.rkpx2.HardCtl.led1_dev_off();
                        com.rkpx2.HardCtl.beep_dev_off();
                        Log.i("tag","close led");
                    }
                    else{
                    	/*
            			接收定时器
            			 */
        				myTimer = Integer.valueOf(str);
        				Log.i("tag",myTimer + "");
        				setAlarm(myTimer);
            			
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //设定闹钟
  	public void setAlarm(int myTimer){
  		//设置定时器
  		Timer timer = new Timer();
  		timer.scheduleAtFixedRate(new TimerTask() {
  			@Override
  			public void run() {
  				com.rkpx2.HardCtl.led2_dev_on();
  				while(true){
  	  				if(com.rkpx2.HardCtl.key1_dev_receive()==1){
  	  					com.rkpx2.HardCtl.led2_dev_off();
  	  					//服药记录上传
  	  					new Thread(new post()).start();
  	  					break;
  	  				}
  	  			}
  				Log.i("tag","Alarm");
  			}
  		}, myTimer, 1000 * 60 * 60 * 24);//24小时为周期
  	}
}
