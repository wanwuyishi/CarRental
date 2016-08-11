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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tjcj.carrental.R;
import com.tjcj.carrental.activity.GoodsDetailActivity;
import com.tjcj.carrental.model.Goodssrc;

import java.util.List;


public class GoodsAdapter extends BaseAdapter {

    private List<Goodssrc> items;
    private final OnClickListener itemButtonClickListener;
    private final Context context;
    private final int res;
    Bundle bundle;

    public GoodsAdapter(Context context, List<Goodssrc> items, int res, OnClickListener itemButtonClickListener) {
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
    public Goodssrc getItem(int position) {
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
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.account = (TextView) convertView.findViewById(R.id.account);
            holder.histOrders = (TextView) convertView.findViewById(R.id.hist_orders);
            holder.createTime = (TextView) convertView.findViewById(R.id.create_time);
            holder.userPhone = (Button) convertView.findViewById(R.id.user_phone);
            holder.order_type = (TextView) convertView.findViewById(R.id.order_type);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Goodssrc goodssrc = items.get(position);

        holder.titleStart.setText(goodssrc.getStartArea());
        holder.subtitleStart.setText(goodssrc.getStartCity());
        holder.titleEnd.setText(goodssrc.getEndArea());
        holder.subtitleEnd.setText(goodssrc.getEndCity());
        holder.goodsurvey.setText(goodssrc.getGoodsType() + "/" + goodssrc.getWeight() +
                goodssrc.getWeightSel());
        holder.createTime.setText(goodssrc.getOrdertime().toString());

        if (res == R.layout.layout_card) {
            holder.type.setText("货主");
            holder.type.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.account.setText(goodssrc.getUser().getAccount());
//            holder.image.setImageIcon(goodssrc.getUser().getIcon());
//            holder.histOrders.setText(items_type.get(position).getUser().getOrders().size());

            if (itemButtonClickListener != null) {
                holder.userPhone.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "click button" + goodssrc.getId(), Toast.LENGTH_SHORT).show();

                    }
                });
                holder.gooddetail.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "click listview" + goodssrc.getId(), Toast.LENGTH_SHORT).show();
                        bundle = new Bundle();
                        bundle.putSerializable("goods", items.get(position));
                        bundle.putSerializable("type", "1");
                        Intent intent = new Intent(context, GoodsDetailActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
            }
        }
        if (res == R.layout.layout_card_mine) {

            holder.order_type.setText("我发的货");
            holder.order_type.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.goodsminedetail.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "click listview" + goodssrc.getId(), Toast.LENGTH_SHORT).show();
                    bundle = new Bundle();
                    bundle.putSerializable("goods", items.get(position));
                    bundle.putSerializable("type", "2");
                    Intent intent = new Intent(context, GoodsDetailActivity.class);
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
        private TextView account;
        private TextView order_type;
        private ImageView image;
        private TextView histOrders;
        private TextView ordernum;
        private TextView createTime;
        private Button userPhone;
    }

}
