package com.tjcj.carrental.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tjcj.carrental.R;

import java.util.Arrays;

/**
 * Created by yp on 16-7-31.
 */
public class SelectCard extends PopupWindow {

    private static String[] PROVINCES =null;
    private static String[] LOCALITY =null;
    private View mCardView;
    AlertDialog dialog;

    private Button btn_card_cancel;
    private Button btn_select_card;

    private WheelView wl_card_pro;
    private WheelView wl_car_loca;
    private String provinces="京";
    private String locality="A";
    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }


    public String getProvinces() {
        return provinces;
    }
    public void setProvinces(String provinces) {
        this.provinces = provinces;
    }

    public SelectCard(Activity context, final TextView textView) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCardView = inflater.inflate(R.layout.card_dialog, null);
        PROVINCES =context.getResources().getStringArray(R.array.card_pro);
        LOCALITY =context.getResources().getStringArray(R.array.car_letters);

        btn_card_cancel= (Button) mCardView.findViewById(R.id.btn_card_cancel);
        btn_select_card= (Button) mCardView.findViewById(R.id.btn_select_card);

        wl_card_pro= (WheelView) mCardView.findViewById(R.id.wl_card_pro);
        wl_car_loca= (WheelView) mCardView.findViewById(R.id.wl_car_loca);



        wl_card_pro.setOffset(2);
        wl_card_pro.setItems(Arrays.asList(PROVINCES));
        wl_card_pro.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                provinces = wl_card_pro.getSeletedItem();
            }
        });

        wl_car_loca.setOffset(2);
        wl_car_loca.setItems(Arrays.asList(LOCALITY));
        wl_car_loca.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                locality = wl_car_loca.getSeletedItem();
            }
        });

        btn_select_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                textView.setText(provinces+locality);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(mCardView);

        dialog = builder.create();//获取Dialog
        dialog.show();//显示对话框
        btn_card_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dialog.dismiss();
            }
        });
    }
}
