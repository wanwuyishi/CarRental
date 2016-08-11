package com.tjcj.carrental.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tjcj.carrental.R;

/**
 * Created by wang on 16-7-26.
 */
public class SettingActivity extends BaseActivity {

    private Context mContext;
    private Button btn_help;
    private Button btn_feed;
    private Button btn_about;
    private Button loginout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mContext = this;
        initView();
        initToolbar();
    }

    private void initView(){
        btn_help = (Button) findViewById(R.id.btn_help);
        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_feed = (Button) findViewById(R.id.btn_feed);
        btn_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_about = (Button) findViewById(R.id.btn_about);
        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        loginout = (Button) findViewById(R.id.login_out);
        loginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle("设置");
    }
}
