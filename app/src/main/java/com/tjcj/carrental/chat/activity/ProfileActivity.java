package com.tjcj.carrental.chat.activity;

/**
 * Created by wang on 16-8-5.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeDelegate;
import com.gotye.api.GotyeMedia;
import com.gotye.api.GotyeStatusCode;
import com.gotye.api.GotyeUser;
import com.tjcj.carrental.R;
import com.tjcj.carrental.activity.BaseActivity;
import com.tjcj.carrental.activity.NewPasswordActivity;
import com.tjcj.carrental.activity.PhoneActivity;
import com.tjcj.carrental.activity.UploadPhotoActivity;
import com.tjcj.carrental.chat.MyApplication;
import com.tjcj.carrental.chat.WelcomePage;
import com.tjcj.carrental.chat.util.BitmapUtil;
import com.tjcj.carrental.chat.util.ImageCache;
import com.tjcj.carrental.chat.util.ToastUtil;
import com.tjcj.carrental.util.Configs;
import com.tjcj.carrental.util.UserUtil;
import com.tjcj.carrental.view.CityPickerView;

import java.io.ByteArrayOutputStream;

//import android.app.Fragment;

@SuppressLint("NewApi")
public class ProfileActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_PIC = 1;
    private GotyeUser user;
    private ImageView iconImageView;
    private EditText nickName;
    private EditText info;
    private GotyeAPI api;
    private Context context;


    private View ll_imageView_head;
    private View ll_nickname;
    private View ll_code;
    private View ll_phone;
    private View ll_adress;
    private View ll_password;
    private ImageView iv_head;

    private String pid = null;

    private TextView tv_nickname;
    private TextView tv_phone;
    private TextView tv_address;

    AlertDialog dialog;
    private Button btn_cancel;
    private Button btn_confirm;

    private EditText et_nickname;

    private String mnickname = "";
    private String phone = "";
    private String minfo="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting);
        context = this;
        initToolbar();
        init();
        api = GotyeAPI.getInstance();
        api.addListener(mdelegate);
        user = api.getLoginUser();
        api.getUserDetail(user, true);
        initView();
        int state = api.isOnline();
        if (state != 1) {
            setErrorTip(0);
        } else {
            setErrorTip(1);
        }
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle("个人设置");
    }

    private void init() {
        ll_imageView_head = findViewById(R.id.ll_imageView_head);//头像
        ll_imageView_head.setOnClickListener(this);
        ll_nickname = findViewById(R.id.ll_nickname);//呢称
        ll_nickname.setOnClickListener(this);
        ll_code = findViewById(R.id.ll_code);//我的二维码
        ll_phone = findViewById(R.id.ll_phone);//手机号
        ll_phone.setOnClickListener(this);
        ll_adress = findViewById(R.id.ll_adress);//我的地址
        ll_adress.setOnClickListener(this);
        ll_password = findViewById(R.id.ll_password);//我的密码
        ll_password.setOnClickListener(this);

        iv_head = (ImageView) findViewById(R.id.iv_head);
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_address = (TextView) findViewById(R.id.tv_address);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_imageView_head:
                iv_head.getDrawable();
                Bitmap headImage = drawableToBitmap(iv_head.getDrawable());
                Intent intent = new Intent(ProfileActivity.this, UploadPhotoActivity.class);
                pid = "9";
                intent.putExtra("pid", pid);
                intent.putExtra("head", convertIconToString(headImage));
                startActivityForResult(intent, Configs.SystemPicture.PHOTO_HEAD_6);
                break;


            case R.id.ll_nickname:
                nicknameDialog(this, tv_nickname);
                break;

            case R.id.ll_phone:
                Intent intentPhone = new Intent(ProfileActivity.this, PhoneActivity.class);
                phone = tv_phone.getText().toString().trim();
                intentPhone.putExtra("phone", phone);
                intentPhone.putExtra("mlayout", "replacePhone");
                startActivityForResult(intentPhone, Configs.SystemPicture.PHONE);
                break;

            case R.id.ll_adress:

                CityPickerView cityPickerView = new CityPickerView(ProfileActivity.this);
                cityPickerView.setTextColor(Color.BLACK);
                cityPickerView.setTextSize(16);
                cityPickerView.setVisibleItems(5);
                cityPickerView.setIsCyclic(false);
                cityPickerView.show();
                cityPickerView.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                    @Override
                    public void onSelected(String... citySelected) {
                        tv_address.setText(citySelected[0] + "," + citySelected[1] + "," + citySelected[2]);
                    }
                });
                break;

            case R.id.ll_password:
                passwordDialog(this, tv_nickname);
                break;
        }
    }

    /**
     * string转成bitmap
     *
     * @param st
     */
    public Bitmap convertStringToIcon(String st) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 图片转成string
     *
     * @param bitmap
     * @return
     */
    public static String convertIconToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);

    }

    /*将图片资源转换为bitmap*/

    public static Bitmap drawableToBitmap(Drawable drawable) {

        Bitmap bitmap = Bitmap.createBitmap(

                drawable.getIntrinsicWidth(),

                drawable.getIntrinsicHeight(),

                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888

                        : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);


        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        drawable.draw(canvas);

        return bitmap;

    }

    /*
    * 设置显示头像
    * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == Configs.SystemPicture.PHOTO_HEAD_6) {
                iv_head.setImageBitmap(convertStringToIcon(data.getAction()));
            }
            if (requestCode == Configs.SystemPicture.PHONE) {
                tv_phone.setText(data.getAction());
            }
        }
    }

    /*
 * 修改呢称对话框
 * */
    public void nicknameDialog(Activity context, final TextView textView) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View nicknameView = inflater.inflate(R.layout.nickname_dialog, null);
        btn_cancel = (Button) nicknameView.findViewById(R.id.btn_cancel);
        btn_confirm = (Button) nicknameView.findViewById(R.id.btn_confirm);

        et_nickname = (EditText) nicknameView.findViewById(R.id.et_nickname);
        mnickname = tv_nickname.getText().toString();
        et_nickname.setText(mnickname);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(nicknameView);
        dialog = builder.create();//获取Dialog
        dialog.show();//显示对话框

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_nickname.getText().toString();
                GotyeUser forModify = new GotyeUser(user.getName());
                forModify.setNickname(name);
                    forModify.setInfo(minfo);
                    forModify.setGender(user.getGender());
                String headPath = null;
                int code = api.reqModifyUserInfo(forModify, headPath);
                Log.d("", "" + code);
                dialog.dismiss();
                textView.setText(name);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dialog.dismiss();
            }
        });

    }

    /**
     * 修改密码对话框
     */
    public void passwordDialog(final Activity context, final TextView textView) {

        final LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View passView = inflater.inflate(R.layout.password_dialog, null);


        final EditText et_old_password = (EditText) passView.findViewById(R.id.et_old_password);
        final TextView tv_pass_hint = (TextView) passView.findViewById(R.id.tv_pass_hint);
        btn_cancel = (Button) passView.findViewById(R.id.btn_cancel);
        btn_confirm = (Button) passView.findViewById(R.id.btn_confirm);

        final String oldPassword = UserUtil.user.getPassword();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(passView);
        dialog = builder.create();//获取Dialog
        dialog.show();//显示对话框

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (oldPassword.equals(et_old_password.getText().toString().trim())) {
                    Intent intent = new Intent(context, NewPasswordActivity.class);
                    intent.putExtra("message", oldPassword);
                    intent.putExtra("condition", "password");
                    startActivity(intent);
                    dialog.dismiss();
                } else {

                    tv_pass_hint.setText("密码错误，请重新输入！");
                    tv_pass_hint.setTextColor(Color.parseColor("#FF0000"));
                    et_old_password.setText("");
                    et_old_password.requestFocus();

                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dialog.dismiss();
            }
        });
    }

    private void initView() {
        iconImageView = (ImageView) findViewById(R.id.icon);
        // nickName = (EditText) findViewById(R.id.nick_name);
//        info = (EditText) findViewById(R.id.info_name);
        Button btn = (Button) findViewById(R.id.logout_btn);
        btn.setText("退出");
      /*  nickName.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        nickName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {

                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    String text = arg0.getText().toString();
                    if (!"".equals(text)) {
                        GotyeUser forModify = new GotyeUser(user.getName());
                        forModify.setNickname(text);
                        forModify.setInfo(info.getText().toString().trim());
                        forModify.setGender(user.getGender());
                        String headPath = null;
                        int code = api.reqModifyUserInfo(forModify, headPath);
                        Log.d("", "" + code);
                    }
                    return true;
                }
                return false;
            }
        });*/
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                int status = api.isOnline();
                int code = api.logout();
                int x = code;
                Log.d("", "code" + code + "" + x);
                if (code == GotyeStatusCode.CodeNotLoginYet) {
                    Intent toLogin = new Intent(context, WelcomePage.class);
                    startActivity(toLogin);
                    finish();
                }
            }
        });
        findViewById(R.id.icon_layout).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        takePic();
                    }
                });

        iconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                takePic();
            }
        });
        CheckBox receiveNewMsg = ((CheckBox) findViewById(
                R.id.new_msg));
        receiveNewMsg.setChecked(MyApplication.isNewMsgNotify());
        receiveNewMsg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                // TODO Auto-generated method stub
                MyApplication.setNewMsgNotify(arg1, user.getName());
            }
        });
//		CheckBox noTipAllGroupMessage = ((CheckBox) getView().findViewById(
//				R.id.group_msg));
//		noTipAllGroupMessage.setChecked(MyApplication.isNotReceiveGroupMsg());
//		noTipAllGroupMessage
//				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//					@Override
//					public void onCheckedChanged(CompoundButton arg0,
//							boolean arg1) {
//						MyApplication.setNotReceiveGroupMsg(arg1,user.getName());
//					}
//				});

        SharedPreferences spf = getSharedPreferences("fifter_cfg", Context.MODE_PRIVATE);
        boolean fifter = spf.getBoolean("fifter", false);
        CheckBox msgFifter = ((CheckBox) findViewById(
                R.id.msg_filter));
        msgFifter.setChecked(fifter);
        msgFifter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0,
                                         boolean arg1) {
                SharedPreferences spf = getSharedPreferences("fifter_cfg", Context.MODE_PRIVATE);
                spf.edit().putBoolean("fifter", arg1).commit();
            }
        });
        findViewById(R.id.clear_cache).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        int code = api.clearCache();
                        Toast.makeText(context, "清理完成!",
                                Toast.LENGTH_SHORT).show();
                    }
                });

        setUserInfo(user);
    }

    boolean hasRequest = false;

    private void setUserInfo(GotyeUser user) {
        if (user.getIcon() == null && !hasRequest) {
            hasRequest = true;
            api.getUserDetail(user, true);
        } else {
            Bitmap bm = BitmapUtil.getBitmap(user.getIcon().getPath());
            if (bm != null) {
                iconImageView.setImageBitmap(bm);
                ImageCache.getInstance().put(user.getName(), bm);
            } else {
                api.downloadMedia(user.getIcon());
            }
        }
        tv_nickname.setText(user.getNickname());
        minfo=user.getName();
       // info.setText(user.getName());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void takePic() {
//		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
//		intent.setType("image/*");
//		getActivity().startActivityForResult(intent, REQUEST_PIC);
        Intent intent;
        intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/jpeg");
        startActivityForResult(intent, REQUEST_PIC);
    }

    public void hideKeyboard() {
        // 隐藏输入法
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        // 显示或者隐藏输入法
        imm.hideSoftInputFromWindow(nickName.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onDestroy() {
//		api.removeListener(this);
        api.removeListener(mdelegate);
        super.onDestroy();
    }

    public void modifyUserIcon(String smallImagePath) {
        String name = nickName.getText().toString().trim();
        GotyeUser forModify = new GotyeUser(user.getName());
        forModify.setNickname(name);
        forModify.setInfo(user.getInfo());
        forModify.setGender(user.getGender());
        api.reqModifyUserInfo(forModify, smallImagePath);
    }

    private void setErrorTip(int code) {
        if (code == 1) {
            findViewById(R.id.error_tip).setVisibility(View.GONE);
        } else {
            findViewById(R.id.error_tip).setVisibility(View.VISIBLE);
            if (code == -1) {
                findViewById(R.id.loading)
                        .setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.showText))
                        .setText("连接中...");
                findViewById(R.id.error_tip_icon).setVisibility(
                        View.GONE);
            } else {
                findViewById(R.id.loading).setVisibility(View.GONE);
                ((TextView) findViewById(R.id.showText))
                        .setText("未连接");
                findViewById(R.id.error_tip_icon).setVisibility(
                        View.VISIBLE);
            }

        }
    }

    private GotyeDelegate mdelegate = new GotyeDelegate() {

        @Override
        public void onDownloadMedia(int code, GotyeMedia media) {
            if (media.getUrl() != null && media.getUrl().equals(user.getIcon().getUrl())) {
                Bitmap bm = BitmapUtil.getBitmap(media.getPath());
                if (bm != null) {
                    iconImageView.setImageBitmap(bm);
                }
            }
        }

        @Override
        public void onGetUserDetail(int code, GotyeUser user) {
            if (user != null && user.getName().equals(ProfileActivity.this.user.getName())) {
                setUserInfo(user);
            }
        }

        @Override
        public void onModifyUserInfo(int code, GotyeUser user) {
            if (code == 0) {
                setUserInfo(user);
                // ToastUtil.show(getActivity(), "修改成功!");
            } else {
                ToastUtil.show(context, "修改失败!");
            }
        }

        @Override
        public void onLogin(int code, GotyeUser currentLoginUser) {
            // TODO Auto-generated method stub
            setErrorTip(1);
        }

        @Override
        public void onLogout(int code) {
            if (code == 0) {
                return;
            }
            setErrorTip(0);
        }

        @Override
        public void onReconnecting(int code, GotyeUser currentLoginUser) {
            // TODO Auto-generated method stub
            setErrorTip(-1);
        }


    };
}

