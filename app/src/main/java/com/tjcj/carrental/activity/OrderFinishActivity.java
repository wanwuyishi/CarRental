package com.tjcj.carrental.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.tjcj.carrental.R;
import com.tjcj.carrental.adapter.GoodsAdapter;
import com.tjcj.carrental.model.Order;

import java.util.List;

/**
 * Created by wang on 16-7-21.
 */
public class OrderFinishActivity extends BaseActivity {

    private GoodsAdapter goodsAdapter;
    private List<Order> items;
    private ListView mListView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_rec_finish);

        /*items_type = new ArrayList<Order>();
        mListView = (ListView) findViewById(R.id.material_listview);

        Goodssrc goodsorder = new Goodssrc();
        Order order = new Order();
        User user = new User();
        goodsorder.setId(4);
        goodsorder.setStartArea("西青区");
        goodsorder.setStartCity("天津");
        goodsorder.setEndArea("山海关区");
        goodsorder.setEndCity("秦皇岛");
        goodsorder.setRemark("木材/十吨/高架");
        user.setAccount("来来");
        order.setUser(user);
        goodsorder.setOrder(order);

        Goodssrc goodsorder1 = new Goodssrc();
        goodsorder1.setId(6);
        goodsorder1.setStartArea("浦东新区");
        goodsorder1.setStartCity("上海");
        goodsorder1.setEndArea("经济技术开发区");
        goodsorder1.setEndCity("威海");
        goodsorder1.setRemark("木材/十吨/高架");
        goodsorder1.setOrder(order);

        final Goodssrc goodsorder2 = new Goodssrc();
        goodsorder2.setId(6);
        goodsorder2.setStartArea("刷新");
        goodsorder2.setStartCity("上海");
        goodsorder2.setEndArea("经济技术开发区");
        goodsorder2.setEndCity("威海");
        goodsorder2.setRemark("木材/十吨/高架");
        goodsorder2.setOrder(order);

        items_type.add(goodsorder);
        items_type.add(goodsorder1);
        items_type.add(goodsorder);
        items_type.add(goodsorder1);
        items_type.add(goodsorder);
        items_type.add(goodsorder1);

        goodsAdapter = new GoodsAdapter(this, items_type, R.layout.layout_card, new ListItemButtonClickListener());
        mListView.setAdapter(goodsAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        items_type.add(goodsorder2);
                        goodsAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });*/
    }
}
