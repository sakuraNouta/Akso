package com.akso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView temp;
	static TextView time;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
  
        Button btn1=(Button)findViewById(R.id.button1);
		Button btn2=(Button)findViewById(R.id.button2);
		Button btn4=(Button)findViewById(R.id.button4);
		Button btntest=(Button)findViewById(R.id.button5);
		
		temp = (TextView) findViewById(R.id.temp);
		time = (TextView) findViewById(R.id.textView1);
		
        
        //等待连接
        new Thread(new socketWait()).start();

		//实时获取并更新温度
		setTemp();

		//查找药盒按钮--发送信号控制蜂鸣器
		btntest.setOnClickListener(new smartlinear());
		
		/*
		 * 跳转页面
		 */
		btn1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,ClockActivity.class);
				startActivity(intent);
			}
		});
		
		btn2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,Medinfo.class);
				startActivity(intent);
			}
		});
	
		btn4.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
				startActivity(intent);
			}
		});
    
   }
    
    //实时更新温度
    private Handler handlers = new Handler();
    String str = null;
   
    private void setTemp(){
    	Timer timer = new Timer(); 	
        //建立一个定时任务
        TimerTask timerTask = new TimerTask(){
            @Override
            public void run() {	
            	new Thread(new tempThread()).start();
                handlers.post(new Runnable(){
                    @Override
                    public void run() {
                    	temp.setText(str + "°");
                    }
                });
            }
        };
        //定时执行
        timer.schedule(timerTask,0,1000);
    }
    	
    public class tempThread implements Runnable{
    	@Override
    	public void run() {
    		if(socketWait.socketList.isEmpty()){
    			str = "30";
    		}
    		else
    			for(Socket s : socketWait.socketList){
    				try {
    					BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
    					str = br.readLine();
    					if(str == null){
    						str  = "30";
    						Thread.sleep(1000);
    					}
    				} catch (IOException e) {
    					e.printStackTrace();
    				} catch(InterruptedException e){
    					e.printStackTrace();
    				}
    			}
    	}
    }
}



class smartlinear implements android.view.View.OnClickListener{
	int flag = 0;
	@Override
	public void onClick(View v) {
		if(flag == 0) {	
			//发送on开蜂鸣器
			new Thread(new ServerThread("on")).start();
			flag = 1;
		}else{
			new Thread(new ServerThread("off")).start();
			flag = 0;
		}
	}
}
