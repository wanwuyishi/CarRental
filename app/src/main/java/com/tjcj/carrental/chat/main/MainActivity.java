
package com.tjcj.carrental.chat.main;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeChatTargetType;
import com.gotye.api.GotyeDelegate;
import com.gotye.api.GotyeGroup;
import com.gotye.api.GotyeMessage;
import com.gotye.api.GotyeMessageStatus;
import com.gotye.api.GotyeNotify;
import com.gotye.api.GotyeStatusCode;
import com.gotye.api.GotyeUser;
import com.tjcj.carrental.R;
import com.tjcj.carrental.chat.MyApplication;
import com.tjcj.carrental.chat.WelcomePage;
import com.tjcj.carrental.chat.util.BeepManager;
import com.tjcj.carrental.chat.util.BitmapUtil;
import com.tjcj.carrental.chat.util.ImageCache;
import com.tjcj.carrental.chat.util.URIUtil;

import java.util.List;

/**
 * 已改成activity
 *
 */
/**
 * 椤圭洰鐨勪富Activity锛屾墍鏈夌殑Fragment閮藉祵鍏ュ湪杩欓噷銆�
 * 
 * @author guolin
 */
@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements OnClickListener {
	private MessageFragment messageFragment;
	private ContactsFragment contactsFragment;
	private SettingFragment settingFragment;
	private View messageLayout;
	private View contactsLayout;
	private View settingLayout;
	private ImageView messageImage;
	private ImageView contactsImage;
	private ImageView settingImage;
	private FragmentManager fragmentManager;

	private static TextView msgTip;
	private static int unreadsum=0;
	private int currentPosition = 0;
	private BeepManager beep;
	private static GotyeAPI api;
	private long exitTime = 0;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		api = GotyeAPI.getInstance();
		setContentView(R.layout.layout_main);
		api.addListener(mDelegate);
		beep = new BeepManager(MainActivity.this);
		beep.updatePrefs();
		initViews();
		fragmentManager = getSupportFragmentManager();//getFragmentManager();
		setTabSelection(0);
		// Intent toService=new Intent(this, GotyeService.class);
		// toService.setAction(GotyeService.ACTION_RUN_ON_UI);
		// startService(toService);
		// 清理掉通知栏
		clearNotify();
	}

	private boolean returnNotify = false;

	@Override
	protected void onResume() {
		super.onResume();
		returnNotify = false;
		mainRefresh();
	}

	@Override
	protected void onPause() {
		returnNotify = true;
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		// 保持好习惯，销毁时请移除监听
//		int onlineNumber = api.isOnline();
		api.removeListener(mDelegate);
		// 告诉service已经处于后台运行状态
		// Intent toService=new Intent(this, GotyeService.class);
		// toService.setAction(GotyeService.ACTION_RUN_BACKGROUND);
		// startService(toService);
		super.onDestroy();
	}

	private void initViews() {
		messageLayout = findViewById(R.id.message_layout);
		contactsLayout = findViewById(R.id.contacts_layout);
		settingLayout = findViewById(R.id.setting_layout);
		msgTip = (TextView) findViewById(R.id.new_msg_tip);

		messageImage = (ImageView) findViewById(R.id.message_image);
		contactsImage = (ImageView) findViewById(R.id.contacts_image);
		settingImage = (ImageView) findViewById(R.id.setting_image);
		//三类的事件监听
		messageLayout.setOnClickListener(this);
		contactsLayout.setOnClickListener(this);
		settingLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.message_layout:
			setTabSelection(0);
			break;
		case R.id.contacts_layout:
			setTabSelection(1);
			break;
		case R.id.setting_layout:
			setTabSelection(2);
			break;
		default:
			break;
		}
	}

	@SuppressLint("NewApi")
	private void setTabSelection(int index) {
		updateUnReadTip();
		currentPosition = index;
		clearSelection();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		switch (index) {
		case 0:
			messageImage.setImageResource(R.drawable.message_selected);
			if (messageFragment == null) {
				messageFragment = new MessageFragment();
				transaction.add(R.id.content, messageFragment);
			} else {
				transaction.show(messageFragment);
			}
			break;
		case 1:
			contactsImage.setImageResource(R.drawable.contacts_selected);
			if (contactsFragment == null) {
				contactsFragment = new ContactsFragment();
				transaction.add(R.id.content, contactsFragment);
			} else {
				transaction.show(contactsFragment);
			}
			break;
		case 2:
		default:
			settingImage.setImageResource(R.drawable.setting_selected);
			if (settingFragment == null) {
				settingFragment = new SettingFragment();
				transaction.add(R.id.content, settingFragment);
			} else {
				transaction.show(settingFragment);
			}
			break;
		}
		transaction.commit();
	}

	private void clearSelection() {
		messageImage.setImageResource(R.drawable.message_unselected);
		contactsImage.setImageResource(R.drawable.contacts_unselected);
		settingImage.setImageResource(R.drawable.setting_unselected);
	}

	@SuppressLint("NewApi")
	private void hideFragments(FragmentTransaction transaction) {
		if (messageFragment != null) {
			transaction.hide(messageFragment);
		}
		if (contactsFragment != null) {
			transaction.hide(contactsFragment);
		}
		if (settingFragment != null) {
			transaction.hide(settingFragment);
		}
	}
		
	public static void myStart(){
		msgTip.setVisibility(View.VISIBLE);
		if (unreadsum+SystemMessage.number > 0 && unreadsum+SystemMessage.number < 100) {
			msgTip.setText(String.valueOf(unreadsum+SystemMessage.number));
		} else if (unreadsum+SystemMessage.number >= 100) {
			msgTip.setText("99");
		} else {
			msgTip.setVisibility(View.GONE);
		}
	}
	
	// 更新提醒
	public void updateUnReadTip() {
		int unreadCount = api.getTotalUnreadMessageCount();
		int unreadNotifyCount = api.getUnreadNotifyCount();
		unreadCount += unreadNotifyCount;
		msgTip.setVisibility(View.VISIBLE);
		if (unreadCount+SystemMessage.number > 0 && unreadCount+SystemMessage.number < 100) {
			msgTip.setText(String.valueOf(unreadCount+SystemMessage.number));
		} else if (unreadCount+SystemMessage.number >= 100) {
			msgTip.setText("99");
		} else {
			msgTip.setVisibility(View.GONE);
		}
		unreadsum = unreadCount;
	}

	// 页面刷新
	private void mainRefresh() {
		updateUnReadTip();
		messageFragment.refresh();
		if (contactsFragment != null) {
			contactsFragment.refresh();
		}

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if (intent != null) {
			int tab = intent.getIntExtra("tab", -1);
			if (tab == 1) {
				contactsFragment.refresh();
			}
			int notify = intent.getIntExtra("notify", 0);
			if (notify == 1) {
				clearNotify();
			}

			int selection_index = intent.getIntExtra("selection_index", -1);
			if (selection_index == 1) {
				setTabSelection(1);
			}
		}

	}

	//清理推送通知
	private void clearNotify() {
		NotificationManager notificationManager = (NotificationManager) this
				.getSystemService(NOTIFICATION_SERVICE);
		notificationManager.cancel(0);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 选取图片的返回值
		if (resultCode == RESULT_OK) {
			if (data != null) {
				Uri selectedImage = data.getData();
				if (selectedImage != null) {
					String path = URIUtil.uriToPath(this, selectedImage);
					setPicture(path);
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void setPicture(String path) {
//		if(TextUtils.isEmpty(path)||!path.endsWith(".jpg")){
//			ToastUtil.show(this, "请选择jpg格式图片!");
//			return ;
//		}
		
		String smallImagePath = path;
		smallImagePath = BitmapUtil.check(smallImagePath);
		
		Bitmap smaillBit = BitmapUtil.getSmallBitmap(smallImagePath, 50, 50);
		String smallPath = BitmapUtil.saveBitmapFile(smaillBit);
		settingFragment.modifyUserIcon(smallPath);
	}

	private GotyeDelegate mDelegate = new GotyeDelegate(){
		
		// 此处处理账号在另外设备登陆造成的被动下线
		@Override
		public void onLogout(int code) {
//			 FragmentTransaction t=fragmentManager.beginTransaction();
//			 t.remove(messageFragment);
//			 t.commit();
			  ImageCache.getInstance().clear();
			 
			
			if (code == GotyeStatusCode.CodeForceLogout) {
				Toast.makeText(MainActivity.this, "您的账号在另外一台设备上登录了！", Toast.LENGTH_SHORT).show();
				MyApplication.clearHasLogin(MainActivity.this);
				Intent intent = new Intent(getBaseContext(), WelcomePage.class);
				startActivity(intent);
				finish();
			} else if (code == GotyeStatusCode.CodeNetworkDisConnected) {

				// Toast.makeText(this, "您的账号掉线了！", Toast.LENGTH_SHORT).show();
				/*
				 * Intent intent = new Intent(getBaseContext(), LoginPage.class);
				 * intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				 * startActivity(intent);
				 */
			} else {
				Toast.makeText(MainActivity.this, "退出登陆！", Toast.LENGTH_SHORT).show();
				MyApplication.clearHasLogin(MainActivity.this);
				Intent i = new Intent(MainActivity.this, WelcomePage.class);
				startActivity(i);
				finish();
			}

		}
		
		
		// 收到消息（此处只是单纯的更新聊天历史界面，不涉及聊天消息处理，当然你也可以处理，若你非要那样做）
		@Override
		public void onReceiveMessage(GotyeMessage message) {
			if (returnNotify) {                                                                                                                                                                       
				return;
			}
			messageFragment.refresh();
			if (message.getStatus() == GotyeMessageStatus.GotyeMessageStatusUnread) {
				updateUnReadTip();

				if (!MyApplication.isNewMsgNotify()) {
					return;
				}
				if (message.getReceiver().getType() == GotyeChatTargetType.GotyeChatTargetTypeGroup) {
					if (MyApplication.isNotReceiveGroupMsg()) {
						return;
					}
					if (MyApplication.isGroupDontdisturb(((GotyeGroup) message
							.getReceiver()).getGroupID())) {
						return;
					}
				}
				beep.playBeepSoundAndVibrate();
			}
		}

		// 自己发送的信息统一在此处理
		@Override
		public void onSendMessage(int code, GotyeMessage message) {
			if (returnNotify) {
				return;
			}
			messageFragment.refresh();
		}

		// 收到群邀请信息
		@Override
		public void onReceiveNotify(GotyeNotify notify) {
			if (returnNotify) {
				return;
			}
			messageFragment.refresh();
			updateUnReadTip();
			if (!MyApplication.isNotReceiveGroupMsg()) {
				beep.playBeepSoundAndVibrate();
			}
		}

		@Override
		public void onRemoveFriend(int code, GotyeUser user) {
			if (returnNotify) {
				return;
			}
			api.deleteSession(user, false);
			messageFragment.refresh();
			contactsFragment.refresh();
		}

		@Override
		public void onAddFriend(int code, GotyeUser user) {
			// TODO Auto-generated method stub
			if (returnNotify) {
				return;
			}
			if (currentPosition == 1) {
				contactsFragment.refresh();
			}
		}
		
		@Override
		public void onGetMessageList(int code, List<GotyeMessage> list) {
//			if(list != null && list.size() > 0){
				mainRefresh();
//			}
		};
		
	};
	
	
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
			if(System.currentTimeMillis() - exitTime >2000){
				exitTime =  System.currentTimeMillis();
			}else{
				onDestroy();
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
