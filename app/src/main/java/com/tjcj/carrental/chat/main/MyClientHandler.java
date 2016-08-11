package com.tjcj.carrental.chat.main;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class MyClientHandler extends IoHandlerAdapter {

	private Handler mHandler;
	
	public MyClientHandler(Handler mHandler) {
		super();
		this.mHandler = mHandler;
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String s = (String) message;
		System.out.println("messageReceived: " + s);
		
		// 每当读到来自服务器的数据之后，发送消息通知程序界面显示该数据

		Message msg = new Message();
		
		msg.what = 0x123;

		msg.obj = s;

		Log.e("zcl", "接收内容："+msg.obj.toString());
		
		SystemMessage.number++;
		
		mHandler.sendMessage(msg);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("messageSent");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("sessionClosed");
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("sessionCreated");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		System.out.println("sessionIdle");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("sessionOpened");
	}
	
}