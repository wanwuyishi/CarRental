package com.tjcj.carrental.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.tjcj.carrental.util.HttpUtil;
import com.tjcj.carrental.util.SerializeUtils;

public class BaseAction<T> {

	public boolean modify(String url,T entity){
		url=HttpUtil.BASE_URL+url;
		boolean flag=false;
		String str = SerializeUtils.serialize(entity);

		Log.e(HttpUtil.TAG, str);
		Map<String,String> map=new HashMap<String, String>();
		map.put("strObj", str);
		
		try {
			Log.e(HttpUtil.TAG, new JSONObject().put("strObj", entity).toString());
			JSONObject obj=new JSONObject(HttpUtil.postRequest(url, map));
			flag=obj.getBoolean("flag");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	public List<T> query(String url){
		url=HttpUtil.BASE_URL+url;
		Map<String,String> map=new HashMap<String, String>();
		JSONObject obj=null;
		List<T> list=null;
		try {
			obj=new JSONObject(HttpUtil.postRequest(url, map));
			String str=obj.getString("list");
			list=(List<T>)SerializeUtils.unSerialize(str);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public List<T> query(String url,Object object){
		url=HttpUtil.BASE_URL+url;
		Map<String,String> map=new HashMap<String, String>();
		map.put("strObj", ""+object);
		JSONObject obj=null;
		List<T> list=null;
		try {
			obj=new JSONObject(HttpUtil.postRequest(url, map));
			String str=obj.getString("list");
			list=(List<T>)SerializeUtils.unSerialize(str);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public T getModel(String url,String object){
		url=HttpUtil.BASE_URL+url;
		Map<String,String> map=new HashMap<String, String>();
		map.put("strObj", object);
		JSONObject obj=null;
		T t=null;
		try {
			obj=new JSONObject(HttpUtil.postRequest(url, map));
			String str=obj.getString("obj");
			t=(T)SerializeUtils.unSerialize(str);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}
	public boolean modify(String url,String object){
		url=HttpUtil.BASE_URL+url;
		Map<String,String> map=new HashMap<String, String>();
		map.put("strObj", object);
		JSONObject obj=null;
		boolean t=false;
		try {
			obj=new JSONObject(HttpUtil.postRequest(url, map));
			t=obj.getBoolean("flag");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}
}
