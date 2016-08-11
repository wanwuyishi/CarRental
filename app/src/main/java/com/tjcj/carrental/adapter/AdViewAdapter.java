package com.tjcj.carrental.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;


import com.tjcj.carrental.view.Advertise;

import java.util.List;

public class AdViewAdapter extends PagerAdapter {
	List<Advertise> listAdvertise =null;
	OnItemClickListener onItemClickListener;
	public AdViewAdapter(List<Advertise> listAdvertise) {
		this.listAdvertise = listAdvertise;
		
	}
	public int getCount() {
		//把这个条数数值很大很大
		if(listAdvertise != null && listAdvertise.size()==1){
			return 1;
		}else{
			return Integer.MAX_VALUE;
		}
		
	}
	/**相当于getView:实例化每个页面的View和添加View
	 * container:ViewPage 容器
	 * position 位置
	 */
	public Object instantiateItem(ViewGroup container, final int position) {
		
		//根据位置取出某一个View
		ImageView view = null;
		if(listAdvertise.size()==1){
			view = listAdvertise.get(0).getmImageView();
			if(listAdvertise.get(0).getImgPath() != -1){
				view.setBackgroundResource(listAdvertise.get(0).getImgPath());
			}
		}else{
			view = listAdvertise.get(position% listAdvertise.size()).getmImageView();
			if(listAdvertise.get(position% listAdvertise.size()).getImgPath() != -1){
				view.setBackgroundResource(listAdvertise.get(position% listAdvertise.size()).getImgPath());
			}
			ViewGroup parent = (ViewGroup) view.getParent();
			 if (parent != null ) {
				 //parent.removeView(view);
				 container.removeView(view);
			 } 
		}
		
		
		
		view.setScaleType(ScaleType.FIT_XY);
		
		//添加到容器
		container.addView(view);
		/**
		 * 添加点击事件
		 */
		view.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				onItemClickListener.OnItemClick(v, position% listAdvertise.size());
			}
		});
		
		return view;//返回实例化的View
	}
	/**
	 * 判断view和instantiateItem返回的对象是否一样
	 * Object : 时instantiateItem返回的对象
	 */
	public boolean isViewFromObject(View view, Object object) {
		if(view == object){
			return true;
		}else{
			return false;
		}
	}

	
	/**|
	 * 实例化两张图片,最多只能装三张图片
	 */
	public void destroyItem(ViewGroup container, int position, Object object) {
		
		//释放资源,
		//container.removeView((View) object);
		
	}
	
	public synchronized void notifyImages(List<Advertise> listAdvertise){
		this.listAdvertise = listAdvertise;
		notifyDataSetChanged();
	}
	
	public interface OnItemClickListener{
		void OnItemClick(View view, int position);
	}
	
	public void setOnItemClickListener(OnItemClickListener onItemClickListener){
		this.onItemClickListener = onItemClickListener;
	}
}
