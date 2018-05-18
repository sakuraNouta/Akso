package com.akso;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/*
 * 闹钟页面
 */
public class Jump extends Activity {

	MediaPlayer mediaPlayer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jump);
	 
		mediaPlayer = mediaPlayer.create(this,R.raw.a);
        mediaPlayer.start();

		new AlertDialog.Builder(Jump.this).setTitle("提示").setMessage("该吃药了")
				.setPositiveButton("关闭",new DialogInterface.OnClickListener() {
			
					@Override
			public void onClick(DialogInterface dialog, int which) {
				mediaPlayer.stop();
				Jump.this.finish();
			}
		}).show();
	}
}
