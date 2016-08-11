package com.tjcj.carrental.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tjcj.carrental.R;
import com.tjcj.carrental.activity.CreditActivity;
import com.tjcj.carrental.activity.GassattionActivity;
import com.tjcj.carrental.activity.GoodsMineActivity;
import com.tjcj.carrental.activity.GoodsSourceActivity;
import com.tjcj.carrental.activity.GoodsPubActivity;
import com.tjcj.carrental.activity.TruckMineActivity;
import com.tjcj.carrental.activity.TruckSourceActivity;
import com.tjcj.carrental.activity.TruckPubActivity;
import com.tjcj.carrental.adapter.MyGridadapter;
import com.tjcj.carrental.view.AdView;
import com.tjcj.carrental.view.Advertise;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Created by wpf on 2016/7/14.
 */
public class FindFragment extends Fragment {

    private GridView gridView;
    private GridViewWithHeaderAndFooter mGridView, mGridViewHeader;
    private MyGridadapter myGridadapter, myGridadapterHeader;
    private LinearLayout src_goods, src_trucks, pub_goods, pub_trucks;
    Bundle bundle;

    private final static int[] imagesHeader = new int[]{R.drawable.src_goods,
            R.drawable.src_trucks, R.drawable.pub_goods, R.drawable.pub_trucks};
    private final static String[] namesHeader = new String[]{"货源", "车源", "发布货源", "发布车源"};

    private final static int[] images = new int[]{R.drawable.find_w1,
            R.drawable.find_w2, R.drawable.find_w1,
            R.drawable.find_w2, R.drawable.find_w3, R.drawable.find_w1,};
    private final static String[] names = new String[]{"货主常用", "车主常用", "附近加油", "违章查询", "物流圈", "信誉认证"};

    private AdView adView;
    private ViewPager ad_viewPage;
    /**
     * 物流圈
     * 显示的文字Textview
     */
    private TextView tv_msg;
    /**
     * 添加小圆点的线性布局
     */
    private LinearLayout ll_dian;
    /**
     * 轮播图对象列表
     */
    List<Advertise> listAdvertise;
    /**
     * 本地图片资源
     */
    private int[] ids = {R.drawable.ad_one, R.drawable.ad_two, R.drawable.ad_three,
            R.drawable.ad_four};
    /**
     * 显示文字
     */
    private String[] des = {"中石油祝您一路顺风", "轻松加油", "美桑--润滑油专家", "森松集团"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_find, null);

        //toolbar头部的四大功能按钮
        myGridadapterHeader = new MyGridadapter(getActivity(), R.layout.item_home_header, imagesHeader, namesHeader);
        mGridViewHeader = (GridViewWithHeaderAndFooter) v.findViewById(R.id.find_grid_header);
        mGridViewHeader.setAdapter(myGridadapterHeader);
        mGridViewHeader.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (id == 0) {
                    startActivity(new Intent(getActivity(), GoodsSourceActivity.class));
                } else if (id == 1) {
                    startActivity(new Intent(getActivity(), TruckSourceActivity.class));
                } else if (id == 2) {
                    bundle = new Bundle();
                    bundle.putSerializable("type", "1");
                    Intent intent = new Intent(getActivity(), GoodsPubActivity.class);
                    intent.putExtras(bundle);
                    getActivity().startActivity(intent);
                } else if (id == 3) {
                    bundle = new Bundle();
                    bundle.putSerializable("type", "1");
                    Intent intent = new Intent(getActivity(), TruckPubActivity.class);
                    intent.putExtras(bundle);
                    getActivity().startActivity(intent);
                }
            }
        });

        //主页的功能按钮
        myGridadapter = new MyGridadapter(getActivity(), R.layout.item_home, images, names);
        mGridView = (GridViewWithHeaderAndFooter) v.findViewById(R.id.find_grid);

        /*LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View footerView = layoutInflater.inflate(R.layout.layout_ad_image, null);
        mGridView.addFooterView(footerView);
*/
        mGridView.setAdapter(myGridadapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (id == 0) {
                    startActivity(new Intent(getActivity(), GoodsMineActivity.class));
                } else if (id == 1) {
                    startActivity(new Intent(getActivity(), TruckMineActivity.class));
                } else if (id == 2) {
                    startActivity(new Intent(getActivity(), GassattionActivity.class));
                } else if (id == 5) {
                    startActivity(new Intent(getActivity(), CreditActivity.class));
                }
                /*else if (id == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("currentcar",currentCar);
                    Intent intent = new Intent(getActivity(), BreakActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (id == 2) {
//                    startActivity(new Intent(MainActivity.this, AmapActivity.class));
                    startActivity(new Intent(getActivity(), MapActivity.class));

                }*/
            }

        });

        /*gridView = (GridView) v.findViewById(R.id.find_grid);
        gridView.setAdapter(myGridadapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (id == 0) {
                    startActivity(new Intent(getActivity(), GoodsSourceActivity.class));
                } else if (id == 1) {
                    startActivity(new Intent(getActivity(), GoodsPubActivity.class));
                } else if (id == 2) {
                    startActivity(new Intent(getActivity(), GoodsMineActivity.class));
                } else if (id == 3) {
                    startActivity(new Intent(getActivity(), TruckSourceActivity.class));
                } else if (id == 4) {
                    startActivity(new Intent(getActivity(), TruckPubActivity.class));
                } else if (id == 5) {
                    startActivity(new Intent(getActivity(), TruckMineActivity.class));
                } *//*else if (id == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("currentcar",currentCar);
                    Intent intent = new Intent(getActivity(), BreakActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (id == 2) {
//                    startActivity(new Intent(MainActivity.this, AmapActivity.class));
                    startActivity(new Intent(getActivity(), MapActivity.class));

                }*//*
            }

        });*/

        //广告
        ad_viewPage = (ViewPager) v.findViewById(R.id.ad_viewPage);
        tv_msg = (TextView) v.findViewById(R.id.tv_msg);
        ll_dian = (LinearLayout) v.findViewById(R.id.ll_dian);
        initAD();

        return v;

    }

    /**
     * 初始化轮播图
     */
    private void initAD() {
        listAdvertise = new ArrayList<Advertise>();
        for (int i = 0; i < 4; i++) {
            Advertise bean = new Advertise();
            bean.setAdName(des[i]);
            bean.setId(i + "");
//            bean.setImgUrl(urls[i]);
            bean.setImgPath(ids[i]);
            listAdvertise.add(bean);
        }
        adView = new AdView(ad_viewPage, tv_msg, ll_dian, getActivity(), listAdvertise);
        adView.startViewPager(4000);//动态设置滑动间隔，并且开启轮播图
    }

}
