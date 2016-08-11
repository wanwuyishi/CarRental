package com.tjcj.carrental.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.tjcj.carrental.R;

/**
 * Created by wang on 16-8-7.
 */
public class LoginActivity extends Activity{

    private Context context;
    private EditText mAccount,mPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        iniView();
    }

    private void iniView() {

        mAccount = (EditText) findViewById(R.id.account);
        mPassword = (EditText) findViewById(R.id.password);
        if (mAccount.getText().toString() == null || mAccount.length() == 0){
            Toast.makeText(context, "请输入用户名", Toast.LENGTH_SHORT)
                    .show();
        }else if (mPassword.getText().toString() == null || mPassword.length() == 0){
            Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT)
                    .show();
        }else {

        }
    }
}
