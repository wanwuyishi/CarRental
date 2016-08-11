package com.tjcj.carrental.chat.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeChatTarget;
import com.gotye.api.GotyeChatTargetType;
import com.gotye.api.GotyeCustomerService;
import com.gotye.api.GotyeDelegate;
import com.gotye.api.GotyeGroup;
import com.gotye.api.GotyeMedia;
import com.gotye.api.GotyeMessage;
import com.gotye.api.GotyeMessageStatus;
import com.gotye.api.GotyeNotify;
import com.gotye.api.GotyeRoom;
import com.gotye.api.GotyeStatusCode;
import com.gotye.api.GotyeUser;
import com.tjcj.carrental.R;
import com.tjcj.carrental.activity.*;
import com.tjcj.carrental.chat.MyApplication;
import com.tjcj.carrental.chat.WelcomePage;
import com.tjcj.carrental.chat.activity.ChatPage;
import com.tjcj.carrental.chat.activity.newActivity;
import com.tjcj.carrental.chat.adapter.MessageListAdapter;
import com.tjcj.carrental.chat.util.BeepManager;
import com.tjcj.carrental.chat.util.ImageCache;
import com.tjcj.carrental.chat.view.SwipeMenu;
import com.tjcj.carrental.chat.view.SwipeMenuCreator;
import com.tjcj.carrental.chat.view.SwipeMenuItem;
import com.tjcj.carrental.chat.view.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

//import android.app.Fragment;

//此页面为回话历史页面，由客户端自己实现
@SuppressLint("NewApi")
public class MessageFragment extends Fragment {
	private SwipeMenuListView listView;
	private static MessageListAdapter adapter;
	public static List<GotyeChatTarget> sessions;
	private TextView msgTip;

	public static final String fixName = "系统消息";
	private static GotyeAPI api;
	private BeepManager beep;
	private static int unreadsum=0;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.layout_message_page, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		api = GotyeAPI.getInstance();
		api.addListener(mDelegate);
		beep = new BeepManager(getContext());
		beep.updatePrefs();
		initView();
	}

	private void initView() {
		listView = (SwipeMenuListView) getView().findViewById(R.id.listview);
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// Create different menus depending on the view type
				switch (menu.getViewType()) {
				case 0:
					createMenu1(menu);
					break;
				case 1:
					createMenu2(menu);
					break;
				}
			}
		};
		listView.setMenuCreator(creator);
		listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
			public boolean onMenuItemClick(int position, SwipeMenu menu,
					int index) {
				GotyeChatTarget target = adapter.getItem(position);
				api.deleteSession(target, false);
				updateList();
				return false;
			}
		});
		int state=api.isOnline();
		if(state!=1){
			setErrorTip(0);
		}else{
			setErrorTip(1);
		}
		updateList();
		setListener();
		myStart();
		Log.e("llllllllllllll","lll");
	}

	private Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			if(msg.what==0x123){
				SystemMessage.content=(String)msg.obj;
//				SystemMessage.systime=""+new Date();
				SystemMessage.flog=true;
				refresh();
				com.tjcj.carrental.activity.MainActivity mainActivity = null;
				mainActivity.myStart();
			}
		};
	};
	private void createMenu1(SwipeMenu menu) {
		 
	}

	
	
	private void createMenu2(SwipeMenu menu) {
		SwipeMenuItem item2 = new SwipeMenuItem(getActivity());
		item2.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
		item2.setWidth(dp2px(70));
		item2.setIcon(R.drawable.ic_action_discard);
		menu.addMenuItem(item2);
	}

	public void myStart(){
		SystemMessage sm=new SystemMessage(mHandler);
		sm.Start();
	}
	
	
	private void setListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				GotyeChatTarget target = (GotyeChatTarget) adapter
						.getItem(arg2);
				////////////////////////////////////////////////////////////
				if (target.getName().equals(fixName)) {
					SystemMessage.flog = false;
					Intent i = new Intent(getActivity(), newActivity.class);
					startActivity(i);
				} else {
					GotyeAPI.getInstance().markMessagesAsRead(target,true);
					if (target.getType() == GotyeChatTargetType.GotyeChatTargetTypeUser) {
						Intent toChat = new Intent(getActivity(),
								ChatPage.class);
						toChat.putExtra("user", (GotyeUser) target);
						startActivity(toChat);
						// updateList();
					} else if (target.getType() == GotyeChatTargetType.GotyeChatTargetTypeRoom) {
						Intent toChat = new Intent(getActivity(),
								ChatPage.class);
						toChat.putExtra("room", (GotyeRoom) target);
						startActivity(toChat);

					} else if (target.getType() == GotyeChatTargetType.GotyeChatTargetTypeGroup) {
						Intent toChat = new Intent(getActivity(),
								ChatPage.class);
						toChat.putExtra("group", (GotyeGroup) target);
						startActivity(toChat);
					} else if (target.getType() == GotyeChatTargetType.GotyeChatTargetTypeCustomerService){
						Intent toChat = new Intent(getActivity(),
								ChatPage.class);
						toChat.putExtra("cserver", (GotyeCustomerService) target);
						startActivity(toChat);
					}
					refresh();
				}
			}
		});
	}

	public static void update(){
		adapter.notifyDataSetChanged();
	}

	public void updateList() {
		sessions = api.getSessionList();

		GotyeChatTarget target = new GotyeUser(fixName);

		if (sessions == null) {
			sessions = new ArrayList<GotyeChatTarget>();
			sessions.add(target);
		} else {
			sessions.add(0, target);
		}
		if (adapter == null) {
			adapter = new MessageListAdapter(MessageFragment.this, sessions);
			listView.setAdapter(adapter);
		} else {
			adapter.setData(sessions);
		}
	}

	public void refresh() {
		updateList();
	}

	@Override
	public void onDestroy() {
		GotyeAPI.getInstance().removeListener(mDelegate);
		super.onDestroy();

	}
	
	private void setErrorTip(int code){
//		code=api.getOnLineState();
		if(code==1){
			getView().findViewById(R.id.error_tip).setVisibility(View.GONE);
		}else{
			getView().findViewById(R.id.error_tip).setVisibility(View.VISIBLE);
			if(code==-1){
				getView().findViewById(R.id.loading).setVisibility(View.VISIBLE);
				((TextView)getView().findViewById(R.id.showText)).setText("连接中...");
				getView().findViewById(R.id.error_tip_icon).setVisibility(View.GONE);
			}else{
				getView().findViewById(R.id.loading).setVisibility(View.GONE);
				((TextView)getView().findViewById(R.id.showText)).setText("未连接");
				getView().findViewById(R.id.error_tip_icon).setVisibility(View.VISIBLE);
			}
		}
	}
	
	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}
	
	/*private GotyeDelegate mDelegate = new GotyeDelegate(){
		
		@Override
		public void onDownloadMedia(int code, GotyeMedia media) {
			// TODO Auto-generated method stub
			if(getActivity().isTaskRoot()){
				adapter.notifyDataSetChanged();
			}
		}

		@Override
		public void onLogout(int code) {
			setErrorTip(0);
		}
		@Override
		public void onLogin(int code, GotyeUser currentLoginUser) {
			setErrorTip(1);
		}
		@Override
		public void onReconnecting(int code, GotyeUser currentLoginUser) {
			setErrorTip(-1);
		}
	};*/

	private boolean returnNotify = false;

	private GotyeDelegate mDelegate = new GotyeDelegate(){

		/*// 此处处理账号在另外设备登陆造成的被动下线
		@Override
		public void onLogout(int code) {
//			 FragmentTransaction t=fragmentManager.beginTransaction();
//			 t.remove(messageFragment);
//			 t.commit();
			ImageCache.getInstance().clear();


			if (code == GotyeStatusCode.CodeForceLogout) {
				Toast.makeText(getActivity(), "您的账号在另外一台设备上登录了！", Toast.LENGTH_SHORT).show();
				MyApplication.clearHasLogin(MainActivity.this);
				Intent intent = new Intent(getBaseContext(), WelcomePage.class);
				startActivity(intent);
				finish();
			} else if (code == GotyeStatusCode.CodeNetworkDisConnected) {

				// Toast.makeText(this, "您的账号掉线了！", Toast.LENGTH_SHORT).show();
				*//*
				 * Intent intent = new Intent(getBaseContext(), LoginPage.class);
				 * intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				 * startActivity(intent);
				 *//*
			} else {
				Toast.makeText(MainActivity.this, "退出登陆！", Toast.LENGTH_SHORT).show();
				MyApplication.clearHasLogin(MainActivity.this);
				Intent i = new Intent(MainActivity.this, WelcomePage.class);
				startActivity(i);
				finish();
			}

		}*/


		// 收到消息（此处只是单纯的更新聊天历史界面，不涉及聊天消息处理，当然你也可以处理，若你非要那样做）
		@Override
		public void onReceiveMessage(GotyeMessage message) {
			if (returnNotify) {
				return;
			}
			refresh();
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
			refresh();
		}

		// 收到群邀请信息
		@Override
		public void onReceiveNotify(GotyeNotify notify) {
			if (returnNotify) {
				return;
			}
			refresh();
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
			refresh();
//            contactsFragment.refresh();
		}

		@Override
		public void onAddFriend(int code, GotyeUser user) {
			// TODO Auto-generated method stub
			if (returnNotify) {
				return;
			}
           /* if (currentPosition == 1) {
                contactsFragment.refresh();
            }*/
		}

		@Override
		public void onGetMessageList(int code, List<GotyeMessage> list) {
//			if(list != null && list.size() > 0){
//            mainRefresh();
//			}
		};

	};

	// 更新提醒
	public void updateUnReadTip() {
		int unreadCount = api.getTotalUnreadMessageCount();
		int unreadNotifyCount = api.getUnreadNotifyCount();
		unreadCount += unreadNotifyCount;
       /* msgTip.setVisibility(View.VISIBLE);
        if (unreadCount+ SystemMessage.number > 0 && unreadCount+SystemMessage.number < 100) {
            msgTip.setText(String.valueOf(unreadCount+SystemMessage.number));
        } else if (unreadCount+SystemMessage.number >= 100) {
            msgTip.setText("99");
        } else {
            msgTip.setVisibility(View.GONE);
        }*/
		unreadsum = unreadCount;
	}

	@Override
	public void onPause() {
		returnNotify = true;

		super.onPause();

	}
}
