package com.tjcj.carrental.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tjcj.carrental.R;
import com.tjcj.carrental.action.GoodsSrcAction;
import com.tjcj.carrental.action.OrderAction;
import com.tjcj.carrental.adapter.BidingAdapter;
import com.tjcj.carrental.adapter.GoodsAdapter;
import com.tjcj.carrental.model.Goodssrc;
import com.tjcj.carrental.model.Order;
import com.tjcj.carrental.util.ListItemButtonClickListener;
import com.tjcj.carrental.util.UserUtil;
import com.tjcj.carrental.util.Utility;
import com.tjcj.carrental.view.refreshLoad.MaterialRefreshLayout;
import com.tjcj.carrental.view.refreshLoad.MaterialRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yp on 16-7-15.
 */
public class GoodsDetailActivity extends BaseActivity implements LayoutObvious {

    private Context context;
    private TextView titleStart;//  区
    private TextView subtitleStart;//  市
    private TextView titleEnd;
    private TextView subtitleEnd;
    private TextView goodssurvey;
    private TextView goodsType;// 货物类型
    private ImageView iv_head;//货主头像
    private TextView tv_shipper_name;//货主名字
    private RatingBar rb_shipper_star;//货主星级
    private TextView tv_goods_time;//货物发布时间
    private TextView tv_goods_number;//运单号
    /*    private TextView tv_goods_base;//出发地
        private TextView tv_goods_destination;//目的地*/
    private TextView tv_goods_detail;//货物详情
    private TextView tv_car_detail;//车辆需求
    private TextView tv_goods_price;//意向价格
    private TextView tv_goods_note;//备注信息
    private TextView txt_quote;
    private Button quote, btn_release_goods_re, btn_cancel_goods, btn_goods_transfer, btn_goods_finish;
    private LinearLayout layout_profile, layout_quote, layout_release_goods_re, layout_goods_transfer,
            layout_footer, layout_goods_finish;
    private Goodssrc goodssrc;
    private GoodsSrcAction goodsSrcAction;
    private Order order;
    //判断页面是我发的车或者查看别人的车
    private String page_type;
    Bundle bundle;
    Intent intent;
    private OrderAction orderAction;
    //test
    private List<Goodssrc> items;
    private ListView mListView;
    private GoodsAdapter goodsAdapter;
    private MaterialRefreshLayout mRefreshLayout;
    //如果是当前用户货源，加载报价的车主列表
    private BidingAdapter bidingAdapter;
    private List<Order> orders;
    private ListView mListViewBiding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        BidingAdapter.layoutObvious = this;
        context = this;
        orderAction = new OrderAction();
        init();
//        outmessage();
        initData();
        initToolbar();
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle("货源详情");
    }

    private void initData() {

        intent = getIntent();
        goodssrc = (Goodssrc) intent.getSerializableExtra("goods");
        page_type = (String) intent.getSerializableExtra("type");

        titleStart.setText(goodssrc.getStartArea());
        subtitleStart.setText(goodssrc.getStartCity());
        titleEnd.setText(goodssrc.getEndArea());
        subtitleEnd.setText(goodssrc.getEndCity());
        goodssurvey.setText("");
        rb_shipper_star.setMax(250);
        rb_shipper_star.setProgress(190);
        iv_head.setImageResource(R.drawable.user_head_test);
        tv_shipper_name.setText(goodssrc.getUser().getAccount());
        tv_goods_time.setText("" + goodssrc.getOrdertime());
//        tv_goods_number.setText(goodssrc.get);
        tv_goods_detail.setText(goodssrc.getGoodsname() + "," +
                goodssrc.getWeight() + goodssrc.getWeightSel()
                + "," + goodssrc.getVolume() + "立方");
        tv_car_detail.setText(goodssrc.getCarType());
        tv_goods_price.setText("" + goodssrc.getPrice() + goodssrc.getPriceSel());
        tv_goods_note.setText(goodssrc.getRemark());
        goodsType.setText(goodssrc.getGoodsType());
        if (page_type.equals("1")) {
            layout_quote.setVisibility(View.VISIBLE);
            quote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (goodssrc.getUser().getId().equals(UserUtil.user.getId())) {
                        Snackbar.make(v, "不能接自己发的货源。", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {

                        if (txt_quote.getText().toString() == null || txt_quote.getText().toString().equals("")) {
                            Snackbar.make(v, "报价不能为空。", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } else {
//                            orderAction.newOrder(goodssrc, MainActivity.user1);
                            order = new Order();
                            order.setGoodssrc(goodssrc);
                            order.setBiding(Double.parseDouble(txt_quote.getText().toString()));
                            order.setUserByUserid(UserUtil.user);
                            order.setUserByRecevierid(goodssrc.getUser());
                            orderAction.add(order);
                            finish();
                            Intent intent = new Intent(GoodsDetailActivity.this, OrderFinishActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            });
        }
        if (page_type.equals("2")) {
            orders = orderAction.findOrderByGoods(goodssrc.getId());

            if (orders.size() == 1 && orders.get(0).getStatus().equals(2)) {
                layout_goods_finish.setVisibility(View.VISIBLE);
                btn_goods_finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        order = orders.get(0);
                        order.setStatus(3);
                        orderAction.update(order);
                        layout_goods_finish.setVisibility(View.GONE);
                        finish();
                    }
                });
            } else if (orders.size() == 1 && orders.get(0).getStatus().equals(3)) {
                layout_footer.setVisibility(View.GONE);
            } else {
                layout_release_goods_re.setVisibility(View.VISIBLE);
                layout_profile.setVisibility(View.GONE);
//                orders = new ArrayList<Order>();

                mRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.material_refresh_layout);
                setRefreshLayout(1);

                mListViewBiding = (ListView) findViewById(R.id.material_listview);
                bidingAdapter = new BidingAdapter(this, orders, R.layout.layout_card_biding, new ListItemButtonClickListener());
                mListViewBiding.setAdapter(bidingAdapter);
                Utility.setListViewHeightBasedOnChildren(mListViewBiding);

                btn_release_goods_re.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bundle = new Bundle();
                        bundle.putSerializable("goods", goodssrc);
                        bundle.putSerializable("type", "2");
                        Intent intent = new Intent(context, GoodsPubActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                        finish();
                    }
                });
                btn_cancel_goods.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goodsSrcAction = new GoodsSrcAction();
                        goodsSrcAction.delete(goodssrc);
                        finish();
                    }
                });
            }
        }
        if (page_type.equals("3")) {
            orders = orderAction.findOrderByGoods(goodssrc.getId());
            order = orders.get(0);
            if (order.getStatus().equals(1)) {
                layout_goods_transfer.setVisibility(View.VISIBLE);
                layout_profile.setVisibility(View.GONE);
                btn_goods_transfer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        order.setStatus(2);
                        orderAction.update(order);
                        updateBtn();
                        finish();
                    }
                });
            }
        }

    }

    private void test() {
        items = new ArrayList<Goodssrc>();
        mListView = (ListView) findViewById(R.id.material_listview);
        items = orderAction.findGoodsTest();

        goodsAdapter = new GoodsAdapter(this, items, R.layout.layout_card, new ListItemButtonClickListener());
        mListView.setAdapter(goodsAdapter);

    }

    /*    private void outmessage() {
            titleStart.setText("西青区");
            subtitleStart.setText("天津市");
            titleEnd.setText("朝阳区");
            subtitleEnd.setText("北京市");
            rb_shipper_star.setMax(250);
            rb_shipper_star.setProgress(190);
            iv_head.setImageResource(R.drawable.user_head_test);
            tv_shipper_name.setText("里斯");
            tv_goods_time.setText("2016-3-24");
            tv_goods_number.setText("E123654");
    *//*        tv_goods_base.setText("天津市");
        tv_goods_destination.setText("北京市");*//*
        tv_goods_detail.setText("sss");
        tv_car_detail.setText("高拦车");
        tv_goods_note.setText("开看看");
        goodsType.setText("木材");
        quote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_quote.getText() == null || txt_quote.getText().equals("")) {
                    Snackbar.make(v, "报价不能为空。", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    finish();
                    Intent intent = new Intent(GoodsDetailActivity.this, OrderFinishActivity.class);
                    startActivity(intent);
                }
            }
        });
    }*/
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

    private void init() {
        titleStart = (TextView) findViewById(R.id.titleStart);
        subtitleStart = (TextView) findViewById(R.id.subtitleStart);
        titleEnd = (TextView) findViewById(R.id.titleEnd);
        subtitleEnd = (TextView) findViewById(R.id.subtitleEnd);
        goodssurvey = (TextView) findViewById(R.id.good_survey);
        goodsType = (TextView) findViewById(R.id.tv_goods_type);
        iv_head = (ImageView) findViewById(R.id.iv_head);
        rb_shipper_star = (RatingBar) findViewById(R.id.rb_shipper_star);
        tv_shipper_name = (TextView) findViewById(R.id.tv_shipper_name);
        tv_goods_time = (TextView) findViewById(R.id.tv_goods_time);
        tv_goods_number = (TextView) findViewById(R.id.tv_goods_number);
        tv_goods_price = (TextView) findViewById(R.id.tv_goods_price);
/*        tv_goods_base= (TextView) findViewById(R.id.tv_goods_base);
        tv_goods_destination= (TextView) findViewById(R.id.tv_goods_destination);*/
        tv_goods_detail = (TextView) findViewById(R.id.tv_goods_detail);
        tv_car_detail = (TextView) findViewById(R.id.tv_car_detail);
        tv_goods_note = (TextView) findViewById(R.id.tv_goods_note);
        txt_quote = (TextView) findViewById(R.id.txt_quote);
        quote = (Button) findViewById(R.id.quote);
        btn_release_goods_re = (Button) findViewById(R.id.btn_release_goods_re);
        btn_cancel_goods = (Button) findViewById(R.id.btn_cancel_goods);
        btn_goods_transfer = (Button) findViewById(R.id.btn_goods_transfer);
        btn_goods_finish = (Button) findViewById(R.id.btn_goods_finish);
        layout_profile = (LinearLayout) findViewById(R.id.layout_profile);
        layout_quote = (LinearLayout) findViewById(R.id.layout_quote);
        layout_release_goods_re = (LinearLayout) findViewById(R.id.layout_release_goods_re);
        layout_goods_transfer = (LinearLayout) findViewById(R.id.layout_goods_transfer);
        layout_goods_finish = (LinearLayout) findViewById(R.id.layout_goods_finish);
        layout_footer = (LinearLayout) findViewById(R.id.layout_footer);
    }

    @Override
    public void updateLayout() {
        layout_footer.setVisibility(View.GONE);
    }

    @Override
    public void updateBtn() {
        layout_goods_transfer.setVisibility(View.GONE);
    }

    @Override
    public void updateLayoutTruck() {

    }

    @Override
    public void updateBtnTruck() {

    }
}

