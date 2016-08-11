package com.tjcj.carrental.chat.activity;

import android.app.Activity;
import android.os.Bundle;

import com.tjcj.carrental.R;
import com.tjcj.carrental.chat.main.SystemMessage;


public class newActivity extends Activity {
	
	public static String ss = "王鹏飞";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newpage);
		SystemMessage.flog=false;
		SystemMessage.number=0;
	}

}
