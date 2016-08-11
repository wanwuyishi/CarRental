package com.tjcj.carrental.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncTaskImageLoad extends AsyncTask<String, Integer, Bitmap> {

//	public static String IMAGE_PATH = "http://192.168.191.1:8080/CarInternet/carLogo/";
	private ImageView Image = null;
	public AsyncTaskImageLoad(ImageView img) {
		Image = img;
	}

	// 运行在子线程中
	protected Bitmap doInBackground(String... params) {
		Bitmap map=null;
		try {
			if(map!=null)return map;
			URL url = new URL(HttpUtil.IMAGE_URL + params[0]);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(5000);
			if (conn.getResponseCode() == 200) {
				InputStream input = conn.getInputStream();
				map = BitmapFactory.decodeStream(input);
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected void onPostExecute(Bitmap result) {
		if (Image != null && result != null) {
			Image.setImageBitmap(result);
		}

		super.onPostExecute(result);
	}
	public static void LoadImage(ImageView img, String path) {
        // 异步加载图片资源
        AsyncTaskImageLoad async = new AsyncTaskImageLoad(img);
        // 执行异步加载，并把图片的路径传送过去
        async.execute(path);

    }


}
