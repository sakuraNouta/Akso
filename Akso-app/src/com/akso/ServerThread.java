package com.akso;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.util.Log;
import android.widget.TimePicker;
import android.widget.Toast;

public class ServerThread implements Runnable{
	static Socket s;
	static BufferedReader br;
	static OutputStream os;
	String str = null;
	int myTimer;
	String flag;

	public ServerThread(String flag){
		this.flag = flag;
	}

	@Override
	public void run() {
		try {
			if(socketWait.socketList.isEmpty()){
				Log.i("tag","no socket");
			}
			else
				for(Socket s : socketWait.socketList){
					Log.i("tag", "send" + s);
					OutputStream os = s.getOutputStream();
					os.write((flag + "" + "\n").getBytes("utf-8"));
					Log.i("tag", "send" + flag);
				}

		//如果捕捉到异常，表明socket对应的客户端已经关闭
		}catch(IOException e){
			//删除该socket
			socketWait.socketList.remove(s);
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
				Log.i("tag","Alarm");
			}
		}, myTimer, 1000 * 60 * 60 * 24);//24小时为周期
	}


}

