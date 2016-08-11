package com.tjcj.carrental.view;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class Advertise {
	
	private String id;
	private String adName;//广告词
	private String imgUrl;//网络图片资源
	private int imgPath=-1;//本地图片资源
	private ImageView mImageView;
	private Bitmap bitmap;
	
	
	public ImageView getmImageView() {
		return mImageView;
	}
	public void setmImageView(ImageView mImageView) {
		this.mImageView = mImageView;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAdName() {
		return adName;
	}
	public void setAdName(String adName) {
		this.adName = adName;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public int getImgPath() {
		return imgPath;
	}
	public void setImgPath(int imgPath) {
		this.imgPath = imgPath;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	
}
