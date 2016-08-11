package com.tjcj.carrental.chat.adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeChatTarget;
import com.gotye.api.GotyeChatTargetType;
import com.gotye.api.GotyeCustomerService;
import com.gotye.api.GotyeMessage;
import com.gotye.api.GotyeMessageType;
import com.gotye.api.GotyeUser;
import com.tjcj.carrental.R;
import com.tjcj.carrental.activity.MainActivity;
import com.tjcj.carrental.chat.main.MessageFragment;
import com.tjcj.carrental.chat.main.SystemMessage;
import com.tjcj.carrental.chat.util.BitmapUtil;
import com.tjcj.carrental.chat.util.ImageCache;
import com.tjcj.carrental.chat.util.TimeUtil;

import java.util.List;

public class MessageListAdapter extends BaseAdapter {
	private MessageFragment messageFragment;
	private List<GotyeChatTarget> sessions;
	private GotyeAPI api;

	public MessageListAdapter(MessageFragment messageFragment,
			List<GotyeChatTarget> sessions) {
		this.messageFragment = messageFragment;
		this.sessions = sessions;
		api = GotyeAPI.getInstance();
	}

	static class ViewHolder {
		ImageView icon;
		TextView title, content, time, count,msgTip;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return sessions.size();
	}

	@Override
	public GotyeChatTarget getItem(int arg0) {
		// TODO Auto-generated method stub
		return sessions.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		GotyeChatTarget t = sessions.get(position);
		// if (t.getName().equals(MessageFragment.fixName)) {
		// return 0;
		// } else {
		return 1;
		// }
	}

	@SuppressWarnings("deprecation")
	@SuppressLint({ "NewApi", "InflateParams" })
	@Override
	public View getView(int arg0, View view, ViewGroup arg2) {
		// GotyeChatTarget t = sessions.get(0);
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if (view == null) {
			view = LayoutInflater.from(messageFragment.getActivity()).inflate(
					R.layout.item_delete, null);
			viewHolder = new ViewHolder();
			viewHolder.icon = (ImageView) view.findViewById(R.id.icon);
			viewHolder.title = (TextView) view.findViewById(R.id.title_tx);
			viewHolder.content = (TextView) view.findViewById(R.id.content_tx);
			viewHolder.time = (TextView) view.findViewById(R.id.time_tx);
			viewHolder.count = (TextView) view.findViewById(R.id.count);
			viewHolder.msgTip = (TextView) view.findViewById(R.id.new_msg_tip);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		final GotyeChatTarget session = getItem(arg0);
		if (session.getName().equals("系统消息")) {
			viewHolder.title.setText(SystemMessage.title);
			viewHolder.content.setText(SystemMessage.content);
			viewHolder.time.setText(SystemMessage.systime);
			viewHolder.icon.setImageResource(R.drawable.contact_group);
			if (SystemMessage.number > 0) {
				viewHolder.count.setVisibility(View.VISIBLE);
				if(SystemMessage.number>99){
					viewHolder.count.setText("99");
				}else{
					viewHolder.count.setText(""+SystemMessage.number);
				}
			} else {
				viewHolder.count.setVisibility(View.GONE);
			}
		}
		Log.d("offLine", "session" + session);
		if (getItemViewType(arg0) == 0) {
			viewHolder.title.setText(session.getName());
			viewHolder.content.setVisibility(View.GONE);
			viewHolder.icon.setImageResource(R.drawable.contact_group);
			viewHolder.time.setVisibility(View.GONE);
			int count = api.getUnreadNotifyCount();
			if (count > 0) {
				viewHolder.count.setVisibility(View.VISIBLE);
				viewHolder.count.setText(String.valueOf(count));
			} else {
				viewHolder.count.setVisibility(View.GONE);
			}

		} else {
			String title = "", content = "";
			int num = 0;
			viewHolder.content.setVisibility(View.VISIBLE);
			// 获取该session最后一条消息记录
			GotyeMessage lastMsg = api.getLastMessage(session);
			if (lastMsg == null) {
				return view;
			}

			// time请*1000还原成正常时间
			String lastMsgTime = TimeUtil
					.dateToMessageTime(lastMsg.getDate() * 1000);
			viewHolder.time.setText(lastMsgTime);

			if (lastMsg.getType() == GotyeMessageType.GotyeMessageTypeText) {
				content = "文本消息：" + lastMsg.getText();
			} else if (lastMsg.getType() == GotyeMessageType.GotyeMessageTypeImage) {
				content = "图片消息";
			} else if (lastMsg.getType() == GotyeMessageType.GotyeMessageTypeAudio) {
				content = "语音消息";
			} else if (lastMsg.getType() == GotyeMessageType.GotyeMessageTypeUserData) {
				content = "自定义消息";
			}

			if (session.getType() == GotyeChatTargetType.GotyeChatTargetTypeUser) {
				setIcon(viewHolder.icon, session);
				GotyeUser user = api.getUserDetail(session, false);
				if (user != null) {
					if (TextUtils.isEmpty(user.getNickname())) {
						title = "好友：" + user.getName();

					} else {
						title = "好友：" + user.getNickname();

					}
				} else {

					title = "好友：" + session.getName();

				}
			} else if (session.getType() == GotyeChatTargetType.GotyeChatTargetTypeCustomerService) {
				viewHolder.icon.setImageResource(R.drawable.contact_group);
				GotyeCustomerService service = (GotyeCustomerService) session;
				if (service != null) {
					title = "客服：" + String.valueOf(service.getGroupId());
				}
			}
			viewHolder.title.setText(title);
			viewHolder.content.setText(content);
			int count = api.getUnreadMessageCount(session);
			if (count > 0) {
				viewHolder.count.setVisibility(View.VISIBLE);
				viewHolder.count.setText(String.valueOf(count));
			} else {
				viewHolder.count.setVisibility(View.GONE);
			}
		}
		/*MainActivity mainActivity = new MainActivity();
		mainActivity.updateUnReadTip();*/
		MessageFragment messageFragment = new MessageFragment();
		messageFragment.updateUnReadTip();
		return view;
	}
	
	private void setIcon(ImageView iconView, GotyeChatTarget target) {
		String name;
		String path;
		if (target.getType() == GotyeChatTargetType.GotyeChatTargetTypeUser) {
			name = target.getName();
			path = api.getUserDetail(target, false).getIcon().getPath();
		} else {
			name = target.getId() + "";
			if (target.getType() == GotyeChatTargetType.GotyeChatTargetTypeGroup) {
				path = api.getGroupDetail(target, false).getIcon().getPath();
			} else {
				path = api.getRoomDetail(target).getIcon().getPath();
			}
		}

		Bitmap bmp = ImageCache.getInstance().get(name);
		if (bmp != null) {
			iconView.setImageBitmap(bmp);
		} else {
			bmp = BitmapUtil.getBitmap(path);
			if (bmp != null) {
				iconView.setImageBitmap(bmp);
				ImageCache.getInstance().put(name, bmp);
			} else {
				iconView.setImageResource(R.drawable.mini_avatar_shadow);
			}
		}
	}

	public void setData(List<GotyeChatTarget> sessions) {
		// TODO Auto-generated method stub
		this.sessions = sessions;
		notifyDataSetChanged();
	}
}
