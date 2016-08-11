package com.tjcj.carrental.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tjcj.carrental.R;

/**
 * Created by wang on 16-7-30.
 */
public class MyGridadapter extends BaseAdapter {
    private LayoutInflater inflater;
    private int[] imageDatas;
    private String[] nameDatas;
    private int res;

    public MyGridadapter(Context context, int res, int[] imageDatas, String[] nameDatas) {
        inflater = LayoutInflater.from(context);
        this.res = res;
        this.imageDatas = imageDatas;
        this.nameDatas = nameDatas;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return imageDatas.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return imageDatas[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(res, null, false);
        ImageView iv_image = (ImageView) convertView
                .findViewById(R.id.iv_image);
        TextView tv_name = (TextView) convertView
                .findViewById(R.id.tv_name);
        String name = nameDatas[position];
        int imageRes = imageDatas[position];
        iv_image.setImageResource(imageRes);
        tv_name.setText(name);
        return convertView;
    }

}
