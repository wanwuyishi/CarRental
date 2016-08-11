package com.tjcj.carrental.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tjcj.carrental.R;

import java.util.Arrays;

public class SelectCarWindow extends PopupWindow {


    private Button btn_select_car, btn_cancel;
    private View mMenuView;
    public WheelView car_name,car_length,car_numb;
    private String name="敞篷车";//车型名称
    private String carlength="4.2米";//车长
    private String carnum="1辆";//车数
    AlertDialog dialog;
    private static final String[] PLANETS = new String[]{"敞篷车", "金杯车", "平板车",
            "高拦车", "箱式车", "高低板", "保温车", "冷藏车", "危险品车", "集装箱", "自卸货车",
            "飞翼车", "半封闭车"};
    private static final String[] CARLENGTH = new String[]{"4.2米", "4.8米",
            "5.2米", "5.8米", "6.2米", "6.8米", "7.2米", "8.6米", "9.6米", "12米",
            "12.5米", "13.5米", "16米", "17.5米"};

    private static final String[] CARNUMB = new String[]{"1辆", "2辆", "3辆", "4辆", "5辆",
            "6辆", "7辆", "8辆", "9辆", "10辆", "11辆", "12辆", "13辆", "14辆", "15辆", "16辆",
            "17辆", "18辆" , "19辆", "20辆", "21辆", "22辆", "23辆", "24辆", "25辆", "26辆",
            "27辆", "28辆", "29辆", "30辆"};
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getCarlength(){
        return this.carlength;
    }


    public SelectCarWindow(Activity context, final TextView textView) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.alertdialog_car, null);

        car_name = (WheelView) mMenuView.findViewById(R.id.wl_car_name);
        car_length = (WheelView) mMenuView.findViewById(R.id.wl_car_length);
        car_numb = (WheelView) mMenuView.findViewById(R.id.wl_car_numb);

        btn_select_car = (Button) mMenuView.findViewById(R.id.btn_select_car);
        btn_cancel = (Button) mMenuView.findViewById(R.id.btn_car_cancel);

        car_name.setOffset(2);
        car_name.setItems(Arrays.asList(PLANETS));
        car_name.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                name = car_name.getSeletedItem();
            }
        });

        car_length.setOffset(2);
        car_length.setItems(Arrays.asList(CARLENGTH));
        car_length.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                carlength = item;
            }
        });
        car_numb.setOffset(2);
        car_numb.setItems(Arrays.asList(CARNUMB));
        car_numb.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                carnum = item;
            }
        });


        //设置按钮监听
        btn_select_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(name+","+carlength+","+carnum);
                dialog.dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(mMenuView);

        dialog = builder.create();//获取Dialog
        dialog.show();//显示对话框
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dialog.dismiss();
            }
        });

        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    }


}
