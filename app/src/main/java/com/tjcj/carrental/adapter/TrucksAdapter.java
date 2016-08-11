package com.tjcj.carrental.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tjcj.carrental.R;
import com.tjcj.carrental.activity.OrderDetailActivity;
import com.tjcj.carrental.activity.TruckDetailActivity;
import com.tjcj.carrental.model.Carsrc;
import com.tjcj.carrental.model.Order;

import java.util.List;


public class TrucksAdapter extends BaseAdapter {

    private List<Carsrc> items;
    private final OnClickListener itemButtonClickListener;
    private final Context context;
    private final int res;
    Bundle bundle;

    public TrucksAdapter(Context context, List<Carsrc> items, int res, OnClickListener itemButtonClickListener) {
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
    public Carsrc getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (items.get(position) == null) return null;
        ViewHolder holder;

        if (convertView == null) {
//            convertView = LayoutInflater.from(context).inflate(R.layout.list_order_card, null);
            convertView = LayoutInflater.from(context).inflate(res, null);
            holder = new ViewHolder();
            holder.gooddetail = convertView.findViewById(R.id.goods_detail);
            holder.goodsminedetail = convertView.findViewById(R.id.goods_mine_detail);
            holder.titleStart = (TextView) convertView.findViewById(R.id.titleStart);
            holder.subtitleStart = (TextView) convertView.findViewById(R.id.subtitleStart);
            holder.titleEnd = (TextView) convertView.findViewById(R.id.titleEnd);
            holder.subtitleEnd = (TextView) convertView.findViewById(R.id.subtitleEnd);
            holder.goodsurvey = (TextView) convertView.findViewById(R.id.good_survey);
            holder.type = (TextView) convertView.findViewById(R.id.type);
            holder.account = (TextView) convertView.findViewById(R.id.account);
            holder.histOrders = (TextView) convertView.findViewById(R.id.hist_orders);
            holder.createTime = (TextView) convertView.findViewById(R.id.create_time);
            holder.userPhone = (Button) convertView.findViewById(R.id.user_phone);
            holder.order_type = (TextView) convertView.findViewById(R.id.order_type);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Carsrc carsrc = items.get(position);


        holder.titleStart.setText(carsrc.getStartArea());
        holder.subtitleStart.setText(carsrc.getStartCity());
        holder.titleEnd.setText(carsrc.getEndArea());
        holder.subtitleEnd.setText(carsrc.getEndCity());
        holder.goodsurvey.setText(carsrc.getRemark());
        if (res == R.layout.layout_card) {

            holder.type.setText("车主");
            holder.type.setTextColor(context.getResources().getColor(R.color.divider_blue));
            holder.account.setText(items.get(position).getUser().getAccount());
//        holder.histOrders.setText(goodsorder.getOrder().getUser().getOrders().size());
//        holder.createTime.setText(goodsorder.getOrder().getCreateTime().toString());

            if (itemButtonClickListener != null) {
                holder.userPhone.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "click button" + carsrc.getId(), Toast.LENGTH_SHORT).show();

                    }
                });
                holder.gooddetail.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bundle = new Bundle();
                        bundle.putSerializable("truck", items.get(position));
                        bundle.putSerializable("type", "1");
                        Intent intent = new Intent(context, TruckDetailActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
            }
        }
        if (res == R.layout.layout_card_mine) {
            holder.order_type.setText("我发的车");
            holder.order_type.setTextColor(context.getResources().getColor(R.color.colorAccent));
            holder.goodsminedetail.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "click listview" + carsrc.getId(), Toast.LENGTH_SHORT).show();
                    bundle = new Bundle();
                    bundle.putSerializable("truck", items.get(position));
                    bundle.putSerializable("type", "2");
                    Intent intent = new Intent(context, TruckDetailActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }

            return convertView;
    }

    private static class ViewHolder {
        private View gooddetail;
        private View goodsminedetail;
        private TextView titleStart;
        private TextView subtitleStart;
        private TextView titleEnd;
        private TextView subtitleEnd;
        private TextView goodsurvey;
        private TextView type;
        private TextView order_type;
        private TextView account;
        private TextView histOrders;
        private TextView createTime;
        private Button userPhone;
    }

}
