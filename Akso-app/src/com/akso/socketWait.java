package com.akso;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import android.util.Log;

public class socketWait implements Runnable {
	 
	public static ArrayList<Socket> socketList = new ArrayList<Socket>();
	
	@Override
	public void run() {
		// 
		try {
			ServerSocket ss = new ServerSocket(1028);
		
	        while(true){
	        	Log.i("tag","server is waiting");
	        	Socket s = ss.accept();
				Log.i("tag","client is socket");
	        	socketList.add(s);
	        	//new Thread(new ServerThread(s)).start();
	        }
	        
     } catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void send(int flag) {
		try {
			if(socketList.isEmpty()){
				Log.i("tag","no socket");
			}
			else
				for(Socket s : socketList){
					Log.i("tag", "send" + s);
					OutputStream os = s.getOutputStream();
					os.write((flag + "" + "\n").getBytes("utf-8"));
					Log.i("tag", "send" + flag);
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
