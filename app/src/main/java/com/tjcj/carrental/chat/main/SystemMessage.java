package com.tjcj.carrental.chat.main;

import android.os.Handler;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;

public class SystemMessage {

	public static boolean flog = false;
	public static int number = 0;
	public static String title="系统消息";
	public static String content="";
	public static String systime="";
	public void sendSysMess(){
		
	}
	
	private Handler mHandler;
	public SystemMessage(Handler mHandler){
		this.mHandler=mHandler;
	}
	public class myRunnable implements Runnable {

		@Override
		public void run() {
//			NioSocketConnector connector = new NioSocketConnector();
//			connector.setHandler(new MyClientHandler(mHandler));
//			connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory()));
//			ConnectFuture future = connector.connect(new InetSocketAddress("192.168.191.1", 8989));
//			future.awaitUninterruptibly();
			
		}
	}
	public void Start(){
		new Thread(new myRunnable()).start();
	}
}
