package com.example.aksoclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

/*
 * ���ӽ���
 */
public class ClockActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clock);

		new AlertDialog.Builder(ClockActivity.this).setTitle("����").setMessage("wakeup")
				.setPositiveButton("�ر�����",new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						ClockActivity.this.finish();
					}
				}).show();
	}
}
