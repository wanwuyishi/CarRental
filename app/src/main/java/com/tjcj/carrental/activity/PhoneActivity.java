package com.tjcj.carrental.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tjcj.carrental.R;
import com.tjcj.carrental.action.UserAction;
import com.tjcj.carrental.util.Configs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static com.mob.tools.utils.R.getStringRes;


public class PhoneActivity extends Activity implements View.OnClickListener {


    private Button getCode;
    private Button btn_confirm_phone;

    private EditText PhoneEd;
    private EditText CodeEd;
    private TextView tv_old_phone;
    private TextView tv_newphone_hint;
    private TextView tv_code_hint;
    private TextView tv_title;

    private String AppKey = "12b114969b230";
    private String APPSECRET = "8574731b557d7d655dbaa60d74e995b7";
    public String phone;
    private String oldPhone;
    private String newPhone;
    private int time = 60;
    private boolean flag = true;
    private String CodeText;
    private String lay;

    private LayoutInflater inflater;
    private LinearLayout lin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_phone);
        init();
        Intent intent = getIntent();
        oldPhone = intent.getStringExtra("phone");
        tv_old_phone.setText(oldPhone);

        lay = intent.getStringExtra("mlayout");
        if ("replacePhone".equals(lay)) {
        /*更换手机号*/

        }

        if ("register".equals(lay)) {

            /*注册新用户*/
            tv_title.setText("注册用户");
            lin.setVisibility(View.GONE);
            tv_newphone_hint.setText("手机号");
            btn_confirm_phone.setText("下一步");
        }

        if ("forget".equals(lay)) {

            /*注册新用户*/
            tv_title.setText("找回密码");
            lin.setVisibility(View.GONE);
            btn_confirm_phone.setText("下一步");
            tv_newphone_hint.setText("手机号");
        }
        SMSSDK.initSDK(this, AppKey, APPSECRET);
        EventHandler eh = new EventHandler() {

            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }

        };
        SMSSDK.registerEventHandler(eh);
    }


    private void init() {
        getCode = (Button) findViewById(R.id.getCode);
        btn_confirm_phone = (Button) findViewById(R.id.btn_confirm_phone);
        PhoneEd = (EditText) findViewById(R.id.PhoneEd);//手机号
        CodeEd = (EditText) findViewById(R.id.Code);//验证码
        getCode.setOnClickListener(this);
        btn_confirm_phone.setOnClickListener(this);

        tv_old_phone = (TextView) findViewById(R.id.tv_old_phone);
        tv_newphone_hint = (TextView) findViewById(R.id.tv_newphone_hint);
        tv_code_hint = (TextView) findViewById(R.id.tv_code_hint);
        tv_title = (TextView) findViewById(R.id.tv_title);


        inflater = LayoutInflater.from(this);
        lin = (LinearLayout) findViewById(R.id.ll_phone_title);

    }

    Handler handlerText = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (time > 0) {
                    getCode.setText("验证码已发送" + time + "秒");
                    time--;
                    handlerText.sendEmptyMessageDelayed(1, 1000);
                    getCode.setEnabled(false);
                } else {
                    time = 60;
                    getCode.setText("重新获取");
                    getCode.setEnabled(true);
                }
            }
        }

        ;
    };

    //验证码送成功后提示文字
    private void reminderText() {
        handlerText.sendEmptyMessageDelayed(1, 1000);
    }

    //验证码验证发送
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            if (result == SMSSDK.RESULT_COMPLETE) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {

                    tv_newphone_hint.setText("提交验证码成功");
                    tv_newphone_hint.setTextColor(Color.parseColor("#0A9D7E"));
                    newPhone = PhoneEd.getText().toString().trim();

                    if ("replacePhone".equals(lay)) {
                    /*更换手机号*/
                        setResult(Configs.SystemPicture.PHONE, (new Intent()).setAction(newPhone));

                    }

                    if ("register".equals(lay)) {
                        Intent register=new Intent(PhoneActivity.this,RegiserActivity.class);
                        register.putExtra("phone",phone);
                        startActivity(register);
                    }

                    if ("forget".equals(lay)) {
                        UserAction userAction=new UserAction();
                        if(userAction.findUserByPhone(phone)){
                            Intent forget=new Intent(PhoneActivity.this,NewPasswordActivity.class);
                            forget.putExtra("message",phone);
                            forget.putExtra("condition","phone");
                            startActivity(forget);

                        }else{
                            Toast.makeText(PhoneActivity.this,"该手机号未注册，请先注册",Toast.LENGTH_SHORT).show();
                        }


                    }
                    finish();

                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    // 已经验证
                    reminderText();
                    tv_newphone_hint.setText("验证码已经发送");
                    tv_newphone_hint.setTextColor(Color.parseColor("#0A9D7E"));
                }
            } else {
                if (flag) {

                    tv_newphone_hint.setText("验证码获取失败，请重新获取!");
                    tv_newphone_hint.setTextColor(Color.parseColor("#FF0000"));
                    PhoneEd.requestFocus();
                } else {
                    ((Throwable) data).printStackTrace();
                    int resId = getStringRes(PhoneActivity.this, "smssdk_network_error");

                    tv_code_hint.setText("验证码错误!");
                    tv_code_hint.setTextColor(Color.parseColor("#FF0000"));
                    CodeEd.selectAll();

                    if (resId > 0) {
                        Toast.makeText(PhoneActivity.this, resId, Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }

    };

    private class SmsObserver extends ContentObserver {

        public SmsObserver(Handler handler) {
            super(handler);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onChange(boolean selfChange) {
            // TODO Auto-generated method stub
            StringBuilder sb = new StringBuilder();
            Cursor cursor = getContentResolver().query(
                    Uri.parse("content://sms/inbox"), null, null, null, null);
            cursor.moveToNext();
            sb.append("body=" + cursor.getString(cursor.getColumnIndex("body")));

            System.out.println(sb.toString());
            Pattern pattern = Pattern.compile("[^0-9]");
            Matcher matcher = pattern.matcher(sb.toString());
            CodeText = matcher.replaceAll("");
            CodeEd.setText(CodeText);
            cursor.close();
            super.onChange(selfChange);
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.getCode: // 获取验证码的过程.
                if (!TextUtils.isEmpty(PhoneEd.getText().toString().trim())) {
                    if (PhoneEd.getText().toString().trim().length() == 11) {
                        getContentResolver().registerContentObserver(
                                Uri.parse("content://sms"), true,
                                new SmsObserver(new Handler()));
                        SMSSDK.getVerificationCode("86", PhoneEd.getText().toString());
                        phone = PhoneEd.getText().toString().trim();
                    } else {

                        tv_newphone_hint.setText("请输入完整电话号码!");
                        tv_newphone_hint.setTextColor(Color.parseColor("#FF0000"));
                        PhoneEd.requestFocus();
                    }
                } else {

                    tv_newphone_hint.setText("电话号码不能为空!");
                    tv_newphone_hint.setTextColor(Color.parseColor("#FF0000"));
                }
                break;
            case R.id.btn_confirm_phone:
                if (!TextUtils.isEmpty(PhoneEd.getText().toString().trim())) {
                    if (CodeEd.getText().toString().trim().length() == 4) {
                        SMSSDK.submitVerificationCode("86", phone, CodeEd.getText()
                                .toString().trim());
                        flag = false;
                    } else {
                        tv_code_hint.setText("请输入完整验证码!");
                        tv_code_hint.setTextColor(Color.parseColor("#FF0000"));
                        CodeEd.requestFocus();
                    }
                } else {

                    tv_code_hint.setText("请输入验证码!");
                    tv_code_hint.setTextColor(Color.parseColor("#FF0000"));
                    CodeEd.requestFocus();
                }


                break;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
        getContentResolver().unregisterContentObserver(new SmsObserver(handler));
    }
}
