package com.tjcj.carrental.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tjcj.carrental.R;
import com.tjcj.carrental.activity.GoodsDetailActivity;
import com.tjcj.carrental.activity.MainActivity;
import com.tjcj.carrental.activity.OrderDetailActivity;
import com.tjcj.carrental.activity.TruckDetailActivity;
import com.tjcj.carrental.model.Order;
import com.tjcj.carrental.util.UserUtil;

import java.util.List;


public class OrdersAdapter extends BaseAdapter {

    private List<Order> items;
    private final OnClickListener itemButtonClickListener;
    private final Context context;
    private final int res;
    Bundle bundle;

    public OrdersAdapter(Context context, List<Order> items, int res, OnClickListener itemButtonClickListener) {
        this.context = context;
        this.res = res;
        this.items = items;
        this.itemButtonClickListener = itemButtonClickListener;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Order getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (items.get(position) == null) return null;
        final ViewHolder holder;

        if (convertView == null) {
//            convertView = LayoutInflater.from(context).inflate(R.layout.list_order_card, null);
            convertView = LayoutInflater.from(context).inflate(res, null);
            holder = new ViewHolder();
            if (res == R.layout.layout_card_mine)
                holder.goodsminedetail = convertView.findViewById(R.id.goods_mine_detail);

            holder.gooddetail = convertView.findViewById(R.id.goods_detail);
            holder.orderid = (TextView) convertView.findViewById(R.id.orderid);
            holder.ordertype = (TextView) convertView.findViewById(R.id.order_type);
            holder.titleStart = (TextView) convertView.findViewById(R.id.titleStart);
            holder.subtitleStart = (TextView) convertView.findViewById(R.id.subtitleStart);
            holder.titleEnd = (TextView) convertView.findViewById(R.id.titleEnd);
            holder.subtitleEnd = (TextView) convertView.findViewById(R.id.subtitleEnd);
            holder.goodsurvey = (TextView) convertView.findViewById(R.id.good_survey);
            holder.createTime = (TextView) convertView.findViewById(R.id.create_time);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (items.get(position).getGoodssrc() != null) {
            final Order order = items.get(position);

//        holder.orderid.setText(order.getId());
            holder.orderid.setText(order.getNumber());
            holder.ordertype.setText(tip(order));
            holder.ordertype.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.titleStart.setText(order.getGoodssrc().getStartArea());
            holder.subtitleStart.setText(order.getGoodssrc().getStartCity());
            holder.titleEnd.setText(order.getGoodssrc().getEndArea());
            holder.subtitleEnd.setText(order.getGoodssrc().getEndCity());
            holder.goodsurvey.setText(order.getGoodssrc().getRemark());
            holder.createTime.setText("" + order.getGoodssrc().getOrdertime());

            if (itemButtonClickListener != null) {
                if (res == R.layout.layout_card) {
                    holder.gooddetail.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "click listview" + order.getId(), Toast.LENGTH_SHORT).show();
                            bundle = new Bundle();
                            bundle.putSerializable("goods", items.get(position).getGoodssrc());
                            bundle.putSerializable("type", "1");
                            Intent intent = new Intent(context, GoodsDetailActivity.class);
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                    });
                }
                if (res == R.layout.layout_card_mine) {
                    holder.goodsminedetail.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "click listview" + order.getId(), Toast.LENGTH_SHORT).show();
                            bundle = new Bundle();
                            bundle.putSerializable("goods", items.get(position).getGoodssrc());
                            if (holder.ordertype.getText().equals("我发的货"))
                                bundle.putSerializable("type", "2");
                            if (holder.ordertype.getText().equals("我接的货"))
                                bundle.putSerializable("type", "3");
                            Intent intent = new Intent(context, GoodsDetailActivity.class);
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                    });
                }
            }
        }
        if (items.get(position).getCarsrc() != null) {
            final Order order = items.get(position);

//        holder.orderid.setText(order.getId());
            holder.orderid.setText(order.getNumber());
            holder.ordertype.setText(tip(order));
            holder.ordertype.setTextColor(context.getResources().getColor(R.color.colorAccent));
            holder.titleStart.setText(order.getCarsrc().getStartArea());
            holder.subtitleStart.setText(order.getCarsrc().getStartCity());
            holder.titleEnd.setText(order.getCarsrc().getEndArea());
            holder.subtitleEnd.setText(order.getCarsrc().getEndCity());
            holder.goodsurvey.setText(order.getCarsrc().getRemark());
            holder.createTime.setText("" + order.getCarsrc().getOrdertime());

            if (itemButtonClickListener != null) {
                if (res == R.layout.layout_card) {
                    holder.gooddetail.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "click listview" + order.getId(), Toast.LENGTH_SHORT).show();
                            bundle = new Bundle();
                            bundle.putSerializable("truck", items.get(position).getCarsrc());
                            bundle.putSerializable("type", "1");
                            Intent intent = new Intent(context, TruckDetailActivity.class);
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                    });
                }
                if (res == R.layout.layout_card_mine) {
                    holder.goodsminedetail.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "click listview" + order.getId(), Toast.LENGTH_SHORT).show();
                            bundle = new Bundle();
                            bundle.putSerializable("truck", items.get(position).getCarsrc());
                            if (holder.ordertype.getText().equals("我发的车"))
                                bundle.putSerializable("type", "2");
                            if (holder.ordertype.getText().equals("我定的车"))
                                bundle.putSerializable("type", "3");
                            Intent intent = new Intent(context, TruckDetailActivity.class);
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                    });
                }
            }
        }

        return convertView;
    }

    /**
     * 我接的货"我定的车"我发的货"我发的车
     * 标签
     * */
    private String tip(Order order){
        String string = null;
        Log.d( "tip1","1:"+order.getUserByUserid().getId());
        Log.d( "tip1","2:"+(order.getUserByUserid().getId().equals(UserUtil.user.getId())));
        if (order.getUserByUserid().getId().equals(UserUtil.user.getId())){
            if (order.getGoodssrc() != null){
                if (order.getGoodssrc().getUser().getId().equals(UserUtil.user.getId()))
                    string = "我接的货";
                else string = "我定的车";
            }
            else string = "我定的车";
        }if (!order.getUserByUserid().getId().equals(UserUtil.user.getId())){
            if (order.getGoodssrc() != null){
                if (order.getGoodssrc().getUser().getId().equals(UserUtil.user.getId()))
                    string="我发的货";
                else string="我发的车";
            }
            else string = "我发的车";
        }
        return string;
    }

    private static class ViewHolder {
        private View gooddetail;
        private View goodsminedetail;
        private TextView orderid;
        private TextView ordertype;
        private TextView titleStart;
        private TextView subtitleStart;
        private TextView titleEnd;
        private TextView subtitleEnd;
        private TextView goodsurvey;
        private TextView account;
        private TextView histOrders;
        private TextView createTime;
        private Button userPhone;
    }

}
