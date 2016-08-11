package com.tjcj.carrental.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.tjcj.carrental.R;
import com.tjcj.carrental.action.GoodsSrcAction;
import com.tjcj.carrental.action.OrderAction;
import com.tjcj.carrental.adapter.GoodsAdapter;
import com.tjcj.carrental.model.Goodssrc;
import com.tjcj.carrental.util.ListItemButtonClickListener;
import com.tjcj.carrental.view.refreshLoad.MaterialRefreshLayout;
import com.tjcj.carrental.view.refreshLoad.MaterialRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wpf on 2016/7/14.
 */
public class GoodsSourceActivity extends BaseActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);

        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        initToolbar();
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle("货源");
    }

    /*    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = inflater.inflate(R.layout.activity_good, null);

        *//*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*//*
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
//        mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());
        //嵌套的fragment不能用getActivity，要用getchildFragmentManager
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) v.findViewById(R.id.container);

        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        *//*FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*//*

        final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) v.findViewById(R.id.multiple_actions);
        menuMultipleActions.setVisibility(View.VISIBLE);
        final FloatingActionButton actionA = (FloatingActionButton) v.findViewById(R.id.action_a);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionA.setTitle("发布货源");
                Intent intent = new Intent(getActivity(), GoodsPubActivity.class);
                startActivity(intent);
            }
        });
        final FloatingActionButton actionB = (FloatingActionButton) v.findViewById(R.id.action_b);
        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionB.setTitle("发布车源");
            }
        });

        return v;
    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        private GoodsAdapter goodsAdapter;
        private GoodsAdapter goodsAdapter1;
        private GoodsAdapter goodsAdapter2;
        private List<Goodssrc> items;
        private List<Goodssrc> itemsOld;
        private ListView mListView;
        private SwipeRefreshLayout swipeRefreshLayout;
        private MaterialRefreshLayout mRefreshLayout;
        private GoodsSrcAction goodsSrcAction;
        private long exitTime = 0;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = null;
            goodsSrcAction = new GoodsSrcAction();

            //判断当前fragment
            /**
             * 地区货源
             * */
            if ((getArguments().getInt(ARG_SECTION_NUMBER)) == 1) {
                rootView = inflater.inflate(R.layout.layout_list, container, false);

                items = new ArrayList<Goodssrc>();

                mListView = (ListView) rootView.findViewById(R.id.material_listview);
//                swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
                mRefreshLayout = (MaterialRefreshLayout) rootView.findViewById(R.id.material_refresh_layout);
                setRefreshLayout(1);
                if (System.currentTimeMillis() - exitTime > 60000||itemsOld==null) {

                    items = goodsSrcAction.findAllByCityGoodsSrc("天津市");
                    itemsOld = items;
//                    Toast.makeText(getContext(), "已刷新", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                }else {
//                    Toast.makeText(getContext(), "未刷新", Toast.LENGTH_SHORT).show();
                    items = itemsOld;
                }
//                items = orderAction.findGoodsTest();

                goodsAdapter = new GoodsAdapter(getActivity(), items, R.layout.layout_card, new ListItemButtonClickListener());
                mListView.setAdapter(goodsAdapter);

                /*swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
//                                items_type.add(goodsorder2);
                                items = orderAction.findGoodsTest();
                                goodsAdapter.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }, 3000);
                    }
                });*/


            }
            /**
             * 附近货源
             * */
            if ((getArguments().getInt(ARG_SECTION_NUMBER)) == 2) {
                rootView = inflater.inflate(R.layout.layout_list, container, false);

                items = new ArrayList<Goodssrc>();
                mListView = (ListView) rootView.findViewById(R.id.material_listview);
                mRefreshLayout = (MaterialRefreshLayout) rootView.findViewById(R.id.material_refresh_layout);
                setRefreshLayout(2);

//                items = goodsSrcAction.findAll(1);
                if (System.currentTimeMillis() - exitTime > 60000||itemsOld==null) {

                    items = goodsSrcAction.findAll(1);
                    itemsOld = items;
//                    Toast.makeText(getContext(), "已刷新", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                }else {
//                    Toast.makeText(getContext(), "未刷新", Toast.LENGTH_SHORT).show();
                    items = itemsOld;
                }
                goodsAdapter = new GoodsAdapter(getActivity(), items, R.layout.layout_card, new ListItemButtonClickListener());
                mListView.setAdapter(goodsAdapter);

            }
            /**
             * 长途货源
             * */
            if ((getArguments().getInt(ARG_SECTION_NUMBER)) == 3) {
                rootView = inflater.inflate(R.layout.layout_list, container, false);

                items = new ArrayList<Goodssrc>();
                mListView = (ListView) rootView.findViewById(R.id.material_listview);
                mRefreshLayout = (MaterialRefreshLayout) rootView.findViewById(R.id.material_refresh_layout);
                setRefreshLayout(3);

//                items = goodsSrcAction.findAll(1);
                if (System.currentTimeMillis() - exitTime > 60000||itemsOld==null) {

                    items = goodsSrcAction.findAll(1);
                    itemsOld = items;
//                    Toast.makeText(getContext(), "已刷新", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                }else {
//                    Toast.makeText(getContext(), "未刷新", Toast.LENGTH_SHORT).show();
                    items = itemsOld;
                }

                goodsAdapter = new GoodsAdapter(getActivity(), items, R.layout.layout_card, new ListItemButtonClickListener());
                mListView.setAdapter(goodsAdapter);

            }


            return rootView;
        }

        private void setRefreshLayout(final int page) {

            mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
                @Override
                public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {

                    materialRefreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            materialRefreshLayout.finishRefresh();
                            if (page == 1){
                                items = goodsSrcAction.findAllByCityGoodsSrc("天津市");
                                goodsAdapter.notifyDataSetChanged();
                            }
                            if (page == 2){
                                items = goodsSrcAction.findAll(1);
                                goodsAdapter.notifyDataSetChanged();
                            }
                            if (page == 3){
                                items = goodsSrcAction.findAll(1);
                                goodsAdapter.notifyDataSetChanged();
                            }
                        }
                    }, 3000);

                    materialRefreshLayout.finishRefreshLoadMore();
                }

                @Override
                public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
                    super.onRefreshLoadMore(materialRefreshLayout);
                    Toast.makeText(getContext(), "load more", Toast.LENGTH_LONG).show();

                    materialRefreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            materialRefreshLayout.finishRefreshLoadMore();

                        }
                    }, 3000);
                }

                @Override
                public void onfinish() {
//                    Toast.makeText(getContext(), "finish", Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "地区";
                case 1:
                    return "附近";
                case 2:
                    return "长途";
            }
            return null;
        }
    }


}
