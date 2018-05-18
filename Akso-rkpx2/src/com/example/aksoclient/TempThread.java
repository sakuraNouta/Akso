package com.example.aksoclient;

import android.app.Activity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;
import android.widget.Toast;

/*
 * 读取并发送温度
 */
public class TempThread implements Runnable {
	
	OutputStream os;
	
	public TempThread(OutputStream os) {
		this.os = os;
	}

    @Override
    public void run(){
    
    	try {
    		while(true){
    	        float s = com.rkpx2.HardCtl.ds18b20_dev_get();
    	        if(s <= 50){
    	        	os.write((s + "\n").getBytes("utf-8"));
        	        Thread.sleep(1000);	
    	        }
    		}
    		
    		} catch (InterruptedException e) {
				e.printStackTrace();
			} catch(IOException e){
				e.printStackTrace();
			}
 
    }
    
    
}
