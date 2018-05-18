package com.akso;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.akso.MainActivity.tempThread;

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
import android.widget.TimePicker;
import android.widget.Toast;


public class ClockActivity extends Activity {
    
	public int isClick;
	Button btn1,btn2,btn3;
	ArrayList<String> alarmList = new ArrayList<String>();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clock);
        isClick = 0;

       
        //绑定闹钟
        bindViews();
    }
    
    private void bindViews(){
	    btn1 = (Button)findViewById(R.id.btn1);
	    btn2 = (Button)findViewById(R.id.btn2);
	    btn3 = (Button)findViewById(R.id.btn3);
	    
	    btn1.setOnClickListener(new smartLinear());
	    btn2.setOnClickListener(new smartLinear());
	    btn3.setOnClickListener(new smartLinear());
}

class smartLinear implements OnClickListener {
    @Override
    public void onClick(final View v) {

        Calendar currentTime = Calendar.getInstance();

        new TimePickerDialog(ClockActivity.this, 0,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // 设置当前时间
                        Calendar c = Calendar.getInstance();
                        //用户选择时间
                        c.set(Calendar.HOUR, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
						
                        /*
						 * 以定时器实现闹钟功能
						 */
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(System.currentTimeMillis());

                        //将用户输入的时间与当前时间取差值,换算为毫秒
					    /*int myTimer = (hourOfDay - calendar.get(Calendar.HOUR_OF_DAY)) *60*60*1000
							+ (minute - calendar.get(Calendar.MINUTE)) * 60 * 1000;*/

                        //以用户设定的时间整分钟执行
                        int myTimer = (hourOfDay - calendar.get(Calendar.HOUR_OF_DAY)) * 60 * 60 * 1000
                                + (minute - calendar.get(Calendar.MINUTE)) * 60 * 1000
                                - (calendar.get(Calendar.SECOND)) * 1000;


                        //第二天闹钟/立刻执行
                        if(myTimer <  -60*1000) myTimer += 24 * 60 * 60 * 1000;
                        else if(myTimer < 0)
                        	myTimer = 0;
                       // if (myTimer < 0) myTimer = 0;


                        //直接将间隔时间发送给kit
                        new Thread(new ServerThread(myTimer + "")).start();

                        //设置定时器
                        Timer timer = new Timer();
                        timer.scheduleAtFixedRate(new TimerTask() {

                            @Override
                            public void run() {
                                Log.i("tag","other");
                                Intent intent = new Intent(ClockActivity.this, Jump.class);
                                startActivity(intent);
                            }
                        }, myTimer, 1000 * 60 * 60 * 24);//24小时为周期

                        Toast.makeText(ClockActivity.this, "闹钟设置完毕" + c.getTimeInMillis(),
                                Toast.LENGTH_SHORT).show();

                        String str;
                        //加入闹钟列表
                        if(minute < 10){
                        	String a = "0" + minute;
                        	str = hourOfDay + ":" + a;
                        }
                        else{
                        	str = hourOfDay + ":" + minute;
                        }
                        alarmList.add(str);
                        switch(v.getId()){
                        case R.id.btn1:
                        	setButton(1,str);
                        	break;
	                    case R.id.btn2:
	                    	setButton(2,str);
	                    	break;
		                case R.id.btn3:
		                	setButton(3,str);
		                	break;
		                }
                        

                    }
                }, currentTime.get(Calendar.HOUR_OF_DAY),
                currentTime.get(Calendar.MINUTE), true).show();
    	}
	}

	//实时更新
	private Handler handlers = new Handler();
	private void setButton(final int flag,final String str){
		Timer timer = new Timer(); 	
	    //建立一个定时任务
	    TimerTask timerTask = new TimerTask(){
	        @Override
	        public void run() {	
	            handlers.post(new Runnable(){
	                @Override
	                public void run() {
	                	switch(flag){
	                	case 1:
	                		btn1.setText(str);
	                		break;
	                	case 2:
	                		btn2.setText(str);
	                		break;
	                	case 3:
	                		btn3.setText(str);
	                		break;
	                	}
	                	//btn1.setText(hour + ":" + minute);
	                	
	                	String alarm = compare(alarmList);
	                	MainActivity.time.setText("下次吃药的时间是:\n" + "\t" + alarm);
	                }
	            });
	        }
	    };
	    //定时执行
	    timer.schedule(timerTask,0,1000 * 60 * 10);
	}
	
	public String compare(ArrayList<String> alarmList){
		String temp = "24:00";
		
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        final String times = format.format(new Date());
		
		for(String str : alarmList){
			if(temp.compareTo(str) > 0 && str.compareTo(times) > 0)
				temp = str;
		}
		
		return temp;
	}
}
