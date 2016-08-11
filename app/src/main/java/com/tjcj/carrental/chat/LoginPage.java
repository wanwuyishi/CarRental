package com.tjcj.carrental.chat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeUser;
import com.tjcj.carrental.R;
import com.tjcj.carrental.action.UserAction;
import com.tjcj.carrental.activity.PhoneActivity;
import com.tjcj.carrental.activity.RegiserActivity;
import com.tjcj.carrental.chat.util.ProgressDialogUtil;
import com.tjcj.carrental.model.User;
import com.tjcj.carrental.util.SerializeUtils;
import com.tjcj.carrental.util.UserUtil;

public class LoginPage extends Fragment {
	Button mButLogin, mButLogout;
	EditText mEdtName, mEdtPsd;
	String mUsername;
	String mPassword;
	UserAction userAction;
	private Button btn_register;
	private Button btn_password;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.layout_login, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		userAction=new UserAction();
		initView();
	}
	public void initView() {
		mButLogin = (Button) getView().findViewById(R.id.start);
		mEdtName = (EditText) getView().findViewById(R.id.username);
		mEdtPsd = (EditText) getView().findViewById(R.id.userpsd);

		btn_register= (Button) getView().findViewById(R.id.btn_register);
		btn_password= (Button) getView().findViewById(R.id.btn_password);

		String user[] = getUser(LoginPage.this.getActivity());
		String hasUserName = user[0];
		String hasPassWord = user[1];

		mUsername = hasUserName;
		mPassword = hasPassWord;
		if (mUsername != null) {
			mEdtName.setText(hasUserName);
			mEdtName.setSelection(mEdtName.getText().length());
		}

		btn_register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), PhoneActivity.class);
				intent.putExtra("mlayout", "register");
				startActivity(intent);
			}
		});

		btn_password.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), PhoneActivity.class);
				intent.putExtra("mlayout", "forget");
				startActivity(intent);
			}
		});
		mButLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (checkUser()) {
					GotyeUser u= GotyeAPI.getInstance().getLoginUser();
					u= GotyeAPI.getInstance().getLoginUser();
					Log.d("", u.getName());
					
					// 登录的时候要传入登录监听，当重复登录时会直接返回登录状态
					User us=new User();
					us.setAccount(mUsername);
					us.setPassword(mEdtPsd.getText().toString().trim());

					User user=userAction.checkUser(SerializeUtils.serialize(us));
					UserUtil.user=user;
					saveUser(LoginPage.this.getActivity(), false,user);
					Intent login = new Intent(getActivity(), GotyeService.class);
					login.setAction(GotyeService.ACTION_LOGIN);
					login.putExtra("name", mUsername);
					if (TextUtils.isEmpty(mEdtPsd.getText().toString().trim())) {
						// login.putExtra("pwd", null);
					} else {
						login.putExtra("pwd", mEdtPsd.getText().toString()
								.trim());
					}
					getActivity().startService(login);
					ProgressDialogUtil.showProgress(
							LoginPage.this.getActivity(), "正在登录...");
				}
			}
		});
	}

	private boolean checkUser() {
		mUsername = mEdtName.getText().toString().trim();
		mPassword = mEdtPsd.getText().toString().trim();
		boolean isValid = true;
		if (mUsername == null || mUsername.length() == 0) {
			Toast.makeText(this.getActivity(), "请输入用户名", Toast.LENGTH_SHORT)
					.show();
			isValid = false;
		}else if (mPassword == null || mPassword.length() ==0){
			Toast.makeText(this.getActivity(), "请输入密码", Toast.LENGTH_SHORT)
					.show();
			isValid = false;
		}
		return isValid;
	}

	public static final String CONFIG = "login_config";

	public static void saveUser(Context context,boolean haslogin,User user) {
		if (user==null||TextUtils.isEmpty(user.getAccount())||TextUtils.isEmpty(user.getPassword())) {
			return;
		}
		SharedPreferences sp = context.getSharedPreferences(CONFIG,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor edit = sp.edit();
		edit.putString("account", user.getAccount());
		edit.putString("password", user.getPassword());
		
		edit.putBoolean("haslogin", haslogin);
		edit.putString("user", SerializeUtils.serialize(user));

		edit.commit();
	}
	public static void saveUser(Context context,boolean haslogin) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor edit = sp.edit();
		edit.putBoolean("haslogin", haslogin);
		edit.commit();
	}
	public static String[] getUser(Context context) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG,
				Context.MODE_PRIVATE);
		String name = sp.getString("account", null);
		String password = sp.getString("password", null);
		String hasUser = sp.getString("user", null);
		String[] user = new String[3];
		user[0] = name;
		user[1] = password;
		user[2] = hasUser;

		return user;
	}
	

	

	
}
