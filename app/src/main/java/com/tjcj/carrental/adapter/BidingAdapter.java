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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeChatTarget;
import com.gotye.api.GotyeChatTargetType;
import com.gotye.api.GotyeCustomerService;
import com.gotye.api.GotyeGroup;
import com.gotye.api.GotyeRoom;
import com.gotye.api.GotyeUser;
import com.tjcj.carrental.R;
import com.tjcj.carrental.action.OrderAction;
import com.tjcj.carrental.activity.LayoutObvious;
import com.tjcj.carrental.chat.activity.ChatPage;
import com.tjcj.carrental.chat.activity.newActivity;
import com.tjcj.carrental.chat.main.SystemMessage;
import com.tjcj.carrental.model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 16-8-4.
 */
public class BidingAdapter extends BaseAdapter {

    private List<Order> orders;
    private final OnClickListener itemButtonClickListener;
    private final Context context;
    private final int res;
    private OrderAction orderAction;
    public static LayoutObvious layoutObvious;
    Bundle bundle;

    public BidingAdapter(Context context, List<Order> orders, int res, OnClickListener itemButtonClickListener) {
        this.itemButtonClickListener = itemButtonClickListener;
        this.orders = orders;
        this.context = context;
        this.res = res;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Order getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (orders.get(position) == null) return null;
        ViewHolder holder;

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(res, null);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.type = (TextView) convertView.findViewById(R.id.type);
            holder.account = (TextView) convertView.findViewById(R.id.account);
            holder.ordernum = (TextView) convertView.findViewById(R.id.hist_orders);
            holder.createtime = (TextView) convertView.findViewById(R.id.create_time);
            holder.bidingprice = (TextView) convertView.findViewById(R.id.biding_price);
            holder.bidingpricesel = (TextView) convertView.findViewById(R.id.biding_price_sel);
            holder.userPhone = (Button) convertView.findViewById(R.id.btn_phone);
            holder.userMessage = (Button) convertView.findViewById(R.id.btn_message);
            holder.deal = (Button) convertView.findViewById(R.id.btn_deal);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Order order = orders.get(position);

//        holder.image.setImageResource(Integer.parseInt(order.getUser().getIcon()));

        holder.type.setTextColor(context.getResources().getColor(R.color.divider_blue));
        holder.account.setText(order.getUserByUserid().getAccount());
//        holder.histOrders.setText(order.getUser().getOrders().size());
        holder.createtime.setText(""+order.getCreateTime());
//        holder.bidingpricesel.setText();
        holder.userPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.userMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotyeUser target = new GotyeUser();
                target.setName(order.getUserByUserid().getAccount());
//                    GotyeAPI.getInstance().markMessagesAsRead(target,true);
                        Intent toChat = new Intent(context, ChatPage.class);
                        toChat.putExtra("user", (GotyeUser) target);
                        context.startActivity(toChat);
            }
        });
        if (res == R.layout.layout_card_biding){
        holder.type.setText("车主");
            holder.bidingprice.setText(""+order.getBiding());

            if (order.getStatus().equals(1)){
            holder.deal.setEnabled(false);
            holder.deal.setText("等待运输");
            holder.deal.setBackgroundColor((context.getResources().getColor(R.color.gray)));
            layoutObvious.updateLayout();
        }else if (order.getStatus().equals(2)){
            holder.deal.setEnabled(false);
            holder.deal.setText("完成运输");
            holder.deal.setBackgroundColor((context.getResources().getColor(R.color.gray)));
            Log.d("kkk", "kkk");
            layoutObvious.updateBtn();
        }else {
        holder.deal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<Order> list=new ArrayList<Order>();
                    orderAction = new OrderAction();
                    list.addAll(orders);
                    list.remove(order);
                    for(Order o:list){
                        orders.remove(o);
                        o.setStatus(-1);
                        orderAction.update(o);
                    }
                    order.setStatus(1);
                    orderAction.update(order);
                    notifyDataSetChanged();
                    layoutObvious.updateLayout();
                }
            });
        }
        }
        if (res == R.layout.layout_card_bookcar){
            holder.type.setText("货主");

            if (order.getStatus().equals(1)){
                holder.deal.setEnabled(false);
                holder.deal.setText("运输中……");
                holder.deal.setBackgroundColor((context.getResources().getColor(R.color.gray)));
                layoutObvious.updateLayoutTruck();
                layoutObvious.updateBtnTruck();
            }else if (order.getStatus().equals(2)){
                holder.deal.setEnabled(false);
                holder.deal.setText("完成运输");
                holder.deal.setBackgroundColor((context.getResources().getColor(R.color.gray)));

                Log.d("kkk", "kkk");
//                layoutObvious.updateBtn();
            }else {
                holder.deal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<Order> list=new ArrayList<Order>();
                        orderAction = new OrderAction();
                        list.addAll(orders);
                        list.remove(order);
                        for(Order o:list){
                            orders.remove(o);
                            o.setStatus(-1);
                            orderAction.update(o);
                        }
                        order.setStatus(1);
                        orderAction.update(order);
                        notifyDataSetChanged();
                        layoutObvious.updateLayoutTruck();
                        layoutObvious.updateBtnTruck();
                    }
                });
            }
        }

        return convertView;
    }

    private static class ViewHolder {
        private TextView type;
        private TextView account;
        private TextView bidingprice;
        private TextView bidingpricesel;
        private ImageView image;
        private TextView histOrders;
        private TextView ordernum;
        private TextView createtime;
        private Button userPhone;
        private Button userMessage;
        private Button deal;

    }
}
