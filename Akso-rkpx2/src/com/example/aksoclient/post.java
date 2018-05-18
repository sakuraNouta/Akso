package com.example.aksoclient;


import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


public class post implements Runnable{
    @Override
    public void run() {
        //提交网页
    	Date day = new Date();
    	SimpleDateFormat df = new SimpleDateFormat("MM.dd.HH.mm");
        String value = df.format(day);
        //String path = "http://192.168.43.107:66/record?name="+ value + "&password=" + "11";
        String path = "http://192.168.43.107:66/record?name=user&date=" + value + "&info=1";
        try {
            URL url = new URL(path);
            Log.i("tag","url:" + url);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            InputStreamReader inputReader = new InputStreamReader(con.getInputStream());
            Log.i("tag","post");
            if(con.getResponseCode() == 200){
                InputStream is = con.getInputStream();
                // 把is的内容转换为字符串
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = -1;
                while ((len = is.read(buffer)) != -1) {
                    bos.write(buffer, 0, len);
                }
                String result = new String(bos.toByteArray());
                is.close();
                //Toast.makeText(MainActivity.this, "GET提交成功", Toast.LENGTH_SHORT).show();
            }
            else
                Log.i("tag","fail");
               // Toast.makeText(MainActivity.this, "GET提交失败", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}