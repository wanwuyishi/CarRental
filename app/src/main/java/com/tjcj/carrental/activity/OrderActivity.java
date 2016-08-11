package com.tjcj.carrental.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.tjcj.carrental.R;
import com.tjcj.carrental.action.OrderAction;
import com.tjcj.carrental.adapter.OrdersAdapter;
import com.tjcj.carrental.model.Order;
import com.tjcj.carrental.view.refreshLoad.MaterialRefreshLayout;
import com.tjcj.carrental.view.refreshLoad.MaterialRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends BaseActivity {

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
        setContentView(R.layout.activity_order);
        initToolbar();
/*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle("我的订单");
    }

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

        private OrdersAdapter ordersAdapter;
        private List<Order> items;
        private ListView mListView;
        private MaterialRefreshLayout mRefreshLayout;
        private OrderAction orderAction;

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
            //判断当前fragment
            /**
             * 全部订单
             * */
            View rootView = null;
            if ((getArguments().getInt(ARG_SECTION_NUMBER)) == 1) {
                rootView = inflater.inflate(R.layout.layout_list, container, false);

                orderAction = new OrderAction();
                items = new ArrayList<Order>();

                mListView = (ListView) rootView.findViewById(R.id.material_listview);
                mRefreshLayout = (MaterialRefreshLayout) rootView.findViewById(R.id.material_refresh_layout);
                setRefreshLayout(1);

                items = orderAction.findAll(1);

                ordersAdapter = new OrdersAdapter(getActivity(), items, R.layout.layout_card_order, new ListItemButtonClickListener());
                mListView.setAdapter(ordersAdapter);
            }
            /**
             * 进行中
             * */
            if ((getArguments().getInt(ARG_SECTION_NUMBER)) == 2) {
                rootView = inflater.inflate(R.layout.layout_list, container, false);

                orderAction = new OrderAction();
                items = new ArrayList<Order>();

                mListView = (ListView) rootView.findViewById(R.id.material_listview);
                mRefreshLayout = (MaterialRefreshLayout) rootView.findViewById(R.id.material_refresh_layout);
                setRefreshLayout(2);

                items = orderAction.findAll(1);

                ordersAdapter = new OrdersAdapter(getActivity(), items, R.layout.layout_card_order, new ListItemButtonClickListener());
                mListView.setAdapter(ordersAdapter);
            }
            /**
             * 已完成
             * */
            if ((getArguments().getInt(ARG_SECTION_NUMBER)) == 3) {
                rootView = inflater.inflate(R.layout.layout_list, container, false);

                orderAction = new OrderAction();
                items = new ArrayList<Order>();

                mListView = (ListView) rootView.findViewById(R.id.material_listview);
                mRefreshLayout = (MaterialRefreshLayout) rootView.findViewById(R.id.material_refresh_layout);
                setRefreshLayout(3);

                items = orderAction.findAll(1);

                ordersAdapter = new OrdersAdapter(getActivity(), items, R.layout.layout_card_order, new ListItemButtonClickListener());
                mListView.setAdapter(ordersAdapter);
            }
            return rootView;
        }
        private void setRefreshLayout(int pager) {

            mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
                @Override
                public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {

                    materialRefreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            materialRefreshLayout.finishRefresh();
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
                    Toast.makeText(getContext(), "finish", Toast.LENGTH_LONG).show();
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
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "全部";
                case 1:
                    return "正在进行";
                case 2:
                    return "已完成";
            }
            return null;
        }
    }

    private static final class ListItemButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

        }
    }
}
