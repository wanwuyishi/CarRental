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
import com.tjcj.carrental.util.UserUtil;

/**
 * Created by yp on 16-8-5.
 */
public class NewPasswordActivity extends Activity {

    private EditText et_newpass;
    private EditText et_newpass_again;
    private TextView tv_newpass_hint;
    private Button btn_pass_confirm;

    private String newPass, newPassAgain;
    private String message;
    private String condition;
    private UserAction userAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_password);

        et_newpass = (EditText) findViewById(R.id.et_newpass);
        et_newpass_again = (EditText) findViewById(R.id.et_newpass_again);
        tv_newpass_hint = (TextView) findViewById(R.id.tv_newpass_hint);
        btn_pass_confirm = (Button) findViewById(R.id.btn_pass_confirm);

        Intent intent = getIntent();
        message = intent.getStringExtra("message");//电话或密码
        message="13110017937";
        condition = intent.getStringExtra("condition");//上一个Itent的标识


        btn_pass_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newPass = et_newpass.getText().toString().trim();
                newPassAgain = et_newpass_again.getText().toString().trim();
                if (newPass.length() < 6) {
                    tv_newpass_hint.setText("密码长度必须大于六个字符!");
                    tv_newpass_hint.setTextColor(Color.parseColor("#FF0000"));
                    et_newpass.requestFocus();
                } else if (!newPass.equals(newPassAgain)) {
                    tv_newpass_hint.setText("两次密码不一致，请重新输入!");
                    tv_newpass_hint.setTextColor(Color.parseColor("#FF0000"));
                    et_newpass_again.setText("");
                    et_newpass_again.requestFocus();

                } else {
                    if ("phone".equals(condition)) {//忘记密码
                        userAction=new UserAction();
                        if( userAction.modifyPwdByPhone(newPass)){
                            Toast.makeText(NewPasswordActivity.this, "找回密码成功", Toast.LENGTH_SHORT).show();
                           // finish();
                        }else {
                            Toast.makeText(NewPasswordActivity.this, "找回密码失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if ("password".equals(condition)) {//修改密码
                        UserAction userAction=new UserAction();
                        UserUtil.user.setPassword(newPass);
                        userAction.update(UserUtil.user);
                        if (userAction.update(UserUtil.user)){
                            Toast.makeText(NewPasswordActivity.this, "修改密码成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(NewPasswordActivity.this, "修改密码失败", Toast.LENGTH_SHORT).show();

                        }

                    }
                }

            }
        });
    }
}
