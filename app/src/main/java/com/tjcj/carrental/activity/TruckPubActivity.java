package com.tjcj.carrental.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tjcj.carrental.R;
import com.tjcj.carrental.action.CarSrcAction;
import com.tjcj.carrental.model.Carsrc;
import com.tjcj.carrental.model.Order;
import com.tjcj.carrental.util.UserUtil;
import com.tjcj.carrental.view.CityPickerView;
import com.tjcj.carrental.view.SelectCarWindow;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

public class TruckPubActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_car_start;//出发地
    private TextView tv_car_destination;//目的地
    private TextView tv_car_type;//车辆类型
    private EditText tv_car_weight;//承载重量
    private EditText tv_car_volume;//承载体积
    private TextView tv_car_time;//发车时间
    private EditText tv_car_note;//备注
    private Button btn_release_car;
    private String startCity, startArea, endCity, endArea, car_start, car_destination,
            car_type, car_weight, car_volume, car_time, car_note;

    SelectCarWindow selectWindow;
    private CarSrcAction carSrcAction;
    //判断页面是新的发布还是修改重发
    private String page_type;
    Intent intent;
    private Carsrc carsrc;
    private static final String[] PLANETS = new String[]{"敞篷车", "金杯车", "平板车",
            "高拦车", "箱式车", "高低板", "保温车", "冷藏车", "危险品车", "集装箱", "自卸货车",
            "飞翼车", "半封闭车"};

    //时间变量
    Dialog dialog;
    private static final int DATE_PICKER_ID = 1;
    private Calendar calendar;
    private int year;
    private int Month;
    private int Day;

    private String initStartDateTime = "2013年9月3日"; // 初始化开始时间


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_publish);
        init();
        initToolbar();
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle("发布车源");
    }

    private void init() {
        tv_car_start = (TextView) findViewById(R.id.tv_car_start);
        tv_car_start.setOnClickListener(this);
        tv_car_destination = (TextView) findViewById(R.id.tv_car_destination);
        tv_car_destination.setOnClickListener(this);
        tv_car_type = (TextView) findViewById(R.id.tv_car_type);
        tv_car_type.setOnClickListener(this);
        tv_car_weight = (EditText) findViewById(R.id.tv_car_weight);
        tv_car_volume = (EditText) findViewById(R.id.tv_car_volume);
        tv_car_time = (TextView) findViewById(R.id.tv_car_time);
        tv_car_time.setOnClickListener(this);
        tv_car_note = (EditText) findViewById(R.id.tv_car_note);
        btn_release_car = (Button) findViewById(R.id.btn_release_car);
        btn_release_car.setOnClickListener(this);


        intent = getIntent();
        try {
            page_type = (String) intent.getSerializableExtra("type");
            carsrc = (Carsrc) intent.getSerializableExtra("truck");
            if (page_type.equals("2")) {
                tv_car_start.setText(carsrc.getStartCity() + "-" + carsrc.getStartArea());
                tv_car_destination.setText(carsrc.getEndCity() + "-" + carsrc.getEndArea());
                tv_car_type.setText(carsrc.getCarType());
                tv_car_weight.setText("" + carsrc.getWeight());
                tv_car_volume.setText("" + carsrc.getVolume());
                tv_car_time.setText("" + carsrc.getOrdertime());
                tv_car_note.setText(carsrc.getRemark());
            }

        } catch (Exception e) {

        }


    }

    private boolean isOk(View v) {
        car_start = tv_car_start.getText().toString();
        car_destination = tv_car_destination.getText().toString();
        car_type = tv_car_type.getText().toString();
        car_weight = tv_car_weight.getText().toString() == null || tv_car_weight.getText().toString().equals("")
                ? "0" : tv_car_weight.getText().toString();
        car_volume = tv_car_volume.getText().toString() == null || tv_car_volume.getText().toString().equals("")
                ? "0" : tv_car_volume.getText().toString();
        car_time = tv_car_time.getText().toString();
        car_note = tv_car_note.getText().toString();
//        car_start = tv_car_start.getText().toString();

        if (car_start == null || car_start.equals("")) {
//            Toast.makeText(this, "请选择出发地", Toast.LENGTH_SHORT).show();
            Snackbar.make(v, "请选择出发地", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return false;
        }
        if (car_destination == null || car_destination.equals("")) {
            Snackbar.make(v, "请选择目的地", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return false;
        }
        if (car_type == null || car_type.equals("")) {
            Snackbar.make(v, "输入车辆类型", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return false;
        } else {
            Snackbar.make(v, "发布成功", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            finish();
            return true;
        }
    }


    public Carsrc getData() {

        if (page_type.equals("1")) {
            carsrc = new Carsrc(UserUtil.user, new Timestamp(new java.util.Date().getTime()), null,
                    startCity, startArea, endCity, endArea, car_note, car_type,
                    Double.parseDouble(car_weight), Double.parseDouble(car_volume), null, null);
        }
        if (page_type.equals("2")){
            carsrc = (Carsrc) intent.getSerializableExtra("truck");
            Log.d(""+carsrc.getStartArea(), "wpf");
            if (startArea == null){
                startArea = carsrc.getStartArea();
                startCity = carsrc.getStartCity();
            }
            if (endArea == null){
                endArea = carsrc.getEndArea();
                endCity = carsrc.getEndCity();
            }
            carsrc.setUser(UserUtil.user);
            carsrc.setStartArea(startArea);
            carsrc.setStartCity(startCity);
            carsrc.setEndArea(endArea);
            carsrc.setEndCity(endCity);
            carsrc.setRemark(car_note);
            carsrc.setCarType(car_type);
            carsrc.setWeight(Double.parseDouble(car_weight));
            carsrc.setVolume(Double.parseDouble(car_volume));
        }
        return carsrc;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_car_type:
                selectWindow = new SelectCarWindow(TruckPubActivity.this, tv_car_type);
                //显示窗口
                selectWindow.showAtLocation(TruckPubActivity.this.findViewById(R.id.btn_release_car), Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0); //

                break;
            case R.id.tv_car_start:
                CityPickerView cityPickerView = new CityPickerView(TruckPubActivity.this);
                cityPickerView.setTextColor(Color.BLUE);
                cityPickerView.setTextSize(20);
                cityPickerView.setVisibleItems(5);
                cityPickerView.setIsCyclic(false);
                cityPickerView.show();
                cityPickerView.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                    @Override
                    public void onSelected(String... citySelected) {
                        tv_car_start.setText(citySelected[0] + "-" + citySelected[1] + "-" + citySelected[2]);
                        startCity = citySelected[1];
                        startArea = citySelected[2];

//                            + "\n邮编：" + citySelected[3]
                    }
                });
                break;
            case R.id.tv_car_destination:
                CityPickerView cityPickerView1 = new CityPickerView(TruckPubActivity.this);
                cityPickerView1.setTextColor(Color.BLACK);
                cityPickerView1.setTextSize(20);
                cityPickerView1.setVisibleItems(5);
                cityPickerView1.setIsCyclic(false);
                cityPickerView1.show();
                cityPickerView1.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                    @Override
                    public void onSelected(String... citySelected) {
                        tv_car_destination.setText(citySelected[0] + "-" + citySelected[1] + "-" + citySelected[2]);
                        endCity = citySelected[1];
                        endArea = citySelected[2];
//                            + "\n邮编：" + citySelected[3]
                    }
                });
                break;
            case R.id.tv_car_time:
                showDialog(DATE_PICKER_ID);
                break;
            case R.id.btn_release_car:
                if (isOk(v)) {
                    carSrcAction = new CarSrcAction();
                    if (page_type.equals("1")) {
                        if (carSrcAction.add(getData())) {
                            Snackbar.make(v, "发布成功", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                            finish();
                        } else {
                            Snackbar.make(v, "发布失败", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                        }
                    }
                    if (page_type.equals("2")) {
                        if (carSrcAction.update(getData())) {
                            Snackbar.make(v, "修改成功", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                            finish();
                        } else {
                            Snackbar.make(v, "修改失败", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                        }
                    }
                }
                break;
        }
    }
    // 设置DatePicker对话框的监听器
    DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            tv_car_time.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
        }
    };

    //复写该方法显示对话框
    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DATE_PICKER_ID) {
            LinearLayout dateTimeLayout = (LinearLayout) this
                    .getLayoutInflater().inflate(R.layout.time_dialog, null);
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            Month = calendar.get(Calendar.MONTH);
            Day = calendar.get(Calendar.DAY_OF_MONTH);
            dialog = new DatePickerDialog(this, datePickerListener, year, Month, Day);
            return dialog;
        }
        return null;
    }
}
