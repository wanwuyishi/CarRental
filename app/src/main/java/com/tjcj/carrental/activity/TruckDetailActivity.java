package com.tjcj.carrental.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tjcj.carrental.R;
import com.tjcj.carrental.action.CarSrcAction;
import com.tjcj.carrental.action.OrderAction;
import com.tjcj.carrental.adapter.BidingAdapter;
import com.tjcj.carrental.model.Carsrc;
import com.tjcj.carrental.model.Order;
import com.tjcj.carrental.util.ListItemButtonClickListener;
import com.tjcj.carrental.util.UserUtil;
import com.tjcj.carrental.util.Utility;
import com.tjcj.carrental.view.refreshLoad.MaterialRefreshLayout;
import com.tjcj.carrental.view.refreshLoad.MaterialRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 16-7-28.
 */
public class TruckDetailActivity extends BaseActivity implements LayoutObvious{

    private TextView titleStart;//  区
    private TextView subtitleStart;//  市
    private TextView titleEnd;
    private TextView subtitleEnd;
    private TextView goodssurvey;
    private TextView create_time;
    private TextView car_number;
    private TextView car_time;
    private TextView car_type;
    private TextView car_weight;
    private TextView car_volume;
    private TextView car_note;
    private Button book, btn_release_car_re, btn_cancel_car, btn_truck_transfer, btn_truck_finish;
    private LinearLayout layout_profile, layout_book, layout_release_car_re, layout_truck_transfer,
            layout_truck_finish;
    private Carsrc carsrc;
    private CarSrcAction carSrcAction;
    private Order order;
    private MaterialRefreshLayout mRefreshLayout;
    //判断页面是我发的车或者查看别人的车
    private String page_type;
    Bundle bundle;
    private OrderAction orderAction;
    private ListView mListViewBook;
    private BidingAdapter bidingAdapter;
    private List<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trucks_detail);
        BidingAdapter.layoutObvious = this;
        orderAction = new OrderAction();
        initToolbar();
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        page_type = (String) intent.getSerializableExtra("type");
        carsrc = (Carsrc) intent.getSerializableExtra("truck");

        titleStart.setText(carsrc.getStartArea());
        subtitleStart.setText(carsrc.getStartCity());
        titleEnd.setText(carsrc.getEndArea());
        subtitleEnd.setText(carsrc.getEndCity());
        goodssurvey.setText("");
        create_time.setText("" + carsrc.getOrdertime());
        car_number.setText(carsrc.getNumber());
//        car_time.setText(carsrc.get);
        car_type.setText(carsrc.getCarType());
        car_weight.setText("" + carsrc.getWeight());
        car_volume.setText("" + carsrc.getVolume());
        car_note.setText(carsrc.getRemark());
        if (page_type.equals("1")) {
            layout_book.setVisibility(View.VISIBLE);
            book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (carsrc.getUser().getId().equals(UserUtil.user.getId())) {
                        Snackbar.make(v, "不能预订自己发的车源。", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        orderAction = new OrderAction();
                        order = new Order();
                        order.setCarsrc(carsrc);
                        order.setUserByUserid(UserUtil.user);
                        order.setUserByRecevierid(carsrc.getUser());
                        orderAction.add(order);
                        Intent intent = new Intent(TruckDetailActivity.this, OrderFinishActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
        if (page_type.equals("2")) {
            orders = orderAction.findOrderByCar(carsrc.getId());

            if (orders.size() == 1 && orders.get(0).getStatus().equals(2)) {

            }if (orders.size() == 1 && orders.get(0).getStatus().equals(1)){
                layout_truck_transfer.setVisibility(View.VISIBLE);
                layout_profile.setVisibility(View.GONE);
                btn_truck_transfer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        order = orders.get(0);
                        order.setStatus(2);
                        orderAction.update(order);
                        updateBtnTruck();
                        finish();
                    }
                });
            }else {
            layout_release_car_re.setVisibility(View.VISIBLE);

            mRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.material_refresh_layout);
            setRefreshLayout(1);

            mListViewBook = (ListView) findViewById(R.id.material_listview);
            bidingAdapter = new BidingAdapter(this, orders, R.layout.layout_card_bookcar, new ListItemButtonClickListener());
            mListViewBook.setAdapter(bidingAdapter);
            Utility.setListViewHeightBasedOnChildren(mListViewBook);

            btn_release_car_re.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bundle = new Bundle();
                    bundle.putSerializable("truck", carsrc);
                    bundle.putSerializable("type", "2");
                    Intent intent = new Intent(context, TruckPubActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    finish();
                }
            });
            btn_cancel_car.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    carSrcAction = new CarSrcAction();
                    carSrcAction.delete(carsrc);
                    finish();
                }
            });}
        }
        if (page_type.equals("3")){
            orders = orderAction.findOrderByCar(carsrc.getId());
            order = orders.get(0);
            if (order.getStatus().equals(2)){
                layout_truck_transfer.setVisibility(View.VISIBLE);
                layout_profile.setVisibility(View.GONE);
                btn_truck_transfer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        order.setStatus(3);
                        orderAction.update(order);
                        layout_truck_finish.setVisibility(View.GONE);
                        finish();
                    }
                });
            }
        }

    }
    private void setRefreshLayout(final int page) {

        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {

                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefresh();
                        if (page == 1) {

                        }
                    }
                }, 1000);

                materialRefreshLayout.finishRefreshLoadMore();
            }

            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                Toast.makeText(context, "load more", Toast.LENGTH_LONG).show();

                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefreshLoadMore();

                    }
                }, 3000);
            }

            @Override
            public void onfinish() {
                Toast.makeText(context, "finish", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initView() {
        titleStart = (TextView) findViewById(R.id.titleStart);
        subtitleStart = (TextView) findViewById(R.id.subtitleStart);
        titleEnd = (TextView) findViewById(R.id.titleEnd);
        subtitleEnd = (TextView) findViewById(R.id.subtitleEnd);
        goodssurvey = (TextView) findViewById(R.id.good_survey);
        create_time = (TextView) findViewById(R.id.tv_create_time);
        car_number = (TextView) findViewById(R.id.tv_car_number);
        car_time = (TextView) findViewById(R.id.tv_car_time);
        car_type = (TextView) findViewById(R.id.tv_car_type);
        car_weight = (TextView) findViewById(R.id.tv_car_weight);
        car_volume = (TextView) findViewById(R.id.tv_car_volume);
        car_note = (TextView) findViewById(R.id.tv_car_note);
        layout_profile = (LinearLayout) findViewById(R.id.layout_profile);
        layout_book = (LinearLayout) findViewById(R.id.layout_book);
        layout_release_car_re = (LinearLayout) findViewById(R.id.layout_release_car_re);
        layout_truck_transfer = (LinearLayout) findViewById(R.id.layout_truck_transfer);
        layout_truck_finish = (LinearLayout) findViewById(R.id.layout_truck_finish);
        book = (Button) findViewById(R.id.book);
        btn_release_car_re = (Button) findViewById(R.id.btn_release_car_re);
        btn_cancel_car = (Button) findViewById(R.id.btn_cancel_car);
        btn_truck_transfer = (Button) findViewById(R.id.btn_truck_transfer);
        btn_truck_finish = (Button) findViewById(R.id.btn_truck_finish);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle("车源详情");
    }

    @Override
    public void updateLayout() {

    }

    @Override
    public void updateBtn() {

    }

    @Override
    public void updateLayoutTruck() {
        layout_release_car_re.setVisibility(View.GONE);
    }

    @Override
    public void updateBtnTruck() {
        layout_truck_transfer.setVisibility(View.VISIBLE);
    }
}
