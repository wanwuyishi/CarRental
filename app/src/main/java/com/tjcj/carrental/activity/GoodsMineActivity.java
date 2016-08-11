package com.tjcj.carrental.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import com.tjcj.carrental.action.GoodsSrcAction;
import com.tjcj.carrental.action.OrderAction;
import com.tjcj.carrental.adapter.GoodsAdapter;
import com.tjcj.carrental.adapter.OrdersAdapter;
import com.tjcj.carrental.model.Goodssrc;
import com.tjcj.carrental.model.Order;
import com.tjcj.carrental.util.ListItemButtonClickListener;
import com.tjcj.carrental.util.UserUtil;
import com.tjcj.carrental.view.refreshLoad.MaterialRefreshLayout;
import com.tjcj.carrental.view.refreshLoad.MaterialRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 16-7-28.
 */
public class GoodsMineActivity extends BaseActivity {

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
        setTitle("货主常用");
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

        private GoodsAdapter goodsAdapter;
        private OrdersAdapter ordersAdapter;
        private List<Goodssrc> items;
        private List<Order> items1;
        private ListView mListView;
        private MaterialRefreshLayout mRefreshLayout;
        private OrderAction orderAction;
        private GoodsSrcAction goodsSrcAction;
        private long refreshTime1 = 0;
        private long refreshTime2 = 0;

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
            orderAction = new OrderAction();
            goodsSrcAction = new GoodsSrcAction();



            //判断当前fragment
            /**
             * 我发的货
             * */
            if ((getArguments().getInt(ARG_SECTION_NUMBER)) == 1) {
                rootView = inflater.inflate(R.layout.layout_list, container, false);

                items = new ArrayList<Goodssrc>();
                mListView = (ListView) rootView.findViewById(R.id.material_listview);
                mRefreshLayout = (MaterialRefreshLayout) rootView.findViewById(R.id.material_refresh_layout);
                setRefreshLayout(1);
                items = goodsSrcAction.findAllByUserId(UserUtil.user.getId());
//                items = orderAction.findGoodsTest();

                goodsAdapter = new GoodsAdapter(getActivity(), items, R.layout.layout_card_mine, new ListItemButtonClickListener());
                mListView.setAdapter(goodsAdapter);

            }
            /**
             * 我定的车
             * */
            if ((getArguments().getInt(ARG_SECTION_NUMBER)) == 2) {
                rootView = inflater.inflate(R.layout.layout_list, container, false);

                items1 = new ArrayList<Order>();
                mListView = (ListView) rootView.findViewById(R.id.material_listview);
                mRefreshLayout = (MaterialRefreshLayout) rootView.findViewById(R.id.material_refresh_layout);
                setRefreshLayout(2);
                items1 = orderAction.findCarSrcOrder(UserUtil.user.getId());
//                items = orderAction.findGoodsTest();

                ordersAdapter = new OrdersAdapter(getActivity(), items1, R.layout.layout_card_mine, new ListItemButtonClickListener());
                mListView.setAdapter(ordersAdapter);

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
                                items = goodsSrcAction.findAllByUserId(1);
                                goodsAdapter.notifyDataSetChanged();
                            }
                            if (page == 2){
                                items1 = orderAction.findCarSrcOrder(UserUtil.user.getId());
                                ordersAdapter.notifyDataSetChanged();
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
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "我发的货";
                case 1:
                    return "我定的车";
            }
            return null;
        }
    }


}
