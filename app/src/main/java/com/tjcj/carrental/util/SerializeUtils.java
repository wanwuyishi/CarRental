package com.tjcj.carrental.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URLEncoder;

public class SerializeUtils {

	/**
	 * 对象序列化为字符串
	 */
	public static String serialize(Object obj) {
		String serStr = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					byteArrayOutputStream);

			objectOutputStream.writeObject(obj);
			serStr = byteArrayOutputStream.toString("ISO-8859-1");// 必须是ISO-8859-1
			serStr = URLEncoder.encode(serStr, "UTF-8");// 编码后字符串不是乱码（不编也不影响功能）
			Log.i(HttpUtil.TAG, "对象obj：【" + obj + "】序列化serStr：【" + serStr + "】");

			objectOutputStream.close();
			byteArrayOutputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return serStr;
	}

	/**
	 * 字符串 反序列化为 对象
	 */
	public static Object unSerialize(String serStr) {
		Object obj = null;
		try {
			String redStr = java.net.URLDecoder.decode(serStr, "UTF-8");
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
					redStr.getBytes("ISO-8859-1"));
			ObjectInputStream objectInputStream = new ObjectInputStream(
					byteArrayInputStream);
			obj = objectInputStream.readObject();
			Log.i("zcl", "对象obj：【" + obj + "】反序列化serStr：【" + serStr + "】");

			objectInputStream.close();
			byteArrayInputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	public static synchronized Drawable stringToDrawable(String icon) {

		byte[] img = Base64.decode(icon.getBytes(), Base64.DEFAULT);
		Bitmap bitmap;
		if (img != null) {

			bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
			@SuppressWarnings("deprecation")
			Drawable drawable = new BitmapDrawable(bitmap);

			return drawable;
		}
		return null;

	}

	public static synchronized Bitmap stringToBitmap(String icon) {

		byte[] img = Base64.decode(icon.getBytes(), Base64.DEFAULT);
		Bitmap bitmap;
		if (img != null) {

			bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);

			return bitmap;
		}
		return null;

	}

	public static synchronized String drawableToString(Drawable drawable) {
		String stricon = null;
		int num = 3;
		if (drawable != null) {
			Bitmap bitmap = Bitmap
					.createBitmap(
							drawable.getIntrinsicWidth() * num,
							drawable.getIntrinsicHeight() * num,
							drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
									: Bitmap.Config.RGB_565);
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth() * num,
					drawable.getIntrinsicHeight() * num);
			drawable.draw(canvas);
			int size = bitmap.getWidth() * bitmap.getHeight() * 4;

			// 创建一个字节数组输出流,流的大小为size
			ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
			// 设置位图的压缩格式，质量为100%，并放入字节数组输出流中
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
			// 将字节数组输出流转化为字节数组byte[]
			byte[] imagedata = baos.toByteArray();

			stricon = Base64.encodeToString(imagedata, Base64.DEFAULT);
			return stricon;
		}
		return null;
	}

	public static synchronized String bitmapToString(Bitmap bitmap) {

		// 创建一个字节数组输出流,流的大小为size
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 设置位图的压缩格式，质量为100%，并放入字节数组输出流中
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		// baos.close();
		// 将字节数组输出流转化为字节数组byte[]
		byte[] imagedata = baos.toByteArray();
		System.out.println("图片的大小：" + imagedata.length);
		String stricon = Base64.encodeToString(imagedata, 0, imagedata.length,
				Base64.DEFAULT);
		return stricon;
	}

}
