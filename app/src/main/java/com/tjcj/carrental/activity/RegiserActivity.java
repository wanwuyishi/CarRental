package com.tjcj.carrental.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tjcj.carrental.R;
import com.tjcj.carrental.action.UserAction;
import com.tjcj.carrental.model.User;
import com.tjcj.carrental.util.UserUtil;

/**
 * Created by yp on 16-8-6.
 */
public class RegiserActivity extends Activity implements View.OnClickListener {
    private TextView tv_password_hint;//错误信息提示
    private EditText et_account;
    private EditText et_password;
    private EditText et_password_again;

    private Button btn_confirm;

    private String pass, passAgain, account;
    private String phone;
    private User user;
    private UserAction userAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        user = new User();
        userAction = new UserAction();
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");//注册电话
        init();
    }

    private void init() {
        tv_password_hint = (TextView) findViewById(R.id.tv_password_hint);
        et_account = (EditText) findViewById(R.id.et_account);
        et_password = (EditText) findViewById(R.id.et_password);
        et_password_again = (EditText) findViewById(R.id.et_password_again);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                pass = et_password.getText().toString().trim();
                passAgain = et_password_again.getText().toString().trim();
                account = et_account.getText().toString().trim();

                user.setAccount(account);
                user.setPassword(pass);
                user.setPhone(phone);
                if (account.length() < 6 || account == null) {
                    tv_password_hint.setVisibility(View.VISIBLE);
                    tv_password_hint.setText("用户名长度必须大于六个字符!");
                    tv_password_hint.setTextColor(Color.parseColor("#FF0000"));
                    et_account.requestFocus();
                }else  if (pass.length() < 6 || pass == null) {
                    tv_password_hint.setVisibility(View.VISIBLE);
                    tv_password_hint.setText("密码长度必须大于六个字符!");
                    tv_password_hint.setTextColor(Color.parseColor("#FF0000"));
                    et_password.requestFocus();
                }else if (!pass.equals(passAgain)) {
                    tv_password_hint.setVisibility(View.VISIBLE);
                    tv_password_hint.setText("两次密码不一致，请重新输入!");
                    tv_password_hint.setTextColor(Color.parseColor("#FF0000"));
                    et_password_again.setText("");
                    et_password_again.requestFocus();
                } else {
                    if (userAction.add(user)) {
                        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegiserActivity.this, MainActivity.class);
                        UserUtil.user = user;
                        finish();
                        startActivity(intent);
                    } else {
                        tv_password_hint.setVisibility(View.VISIBLE);
                        tv_password_hint.setText("注册失败!");
                        Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
