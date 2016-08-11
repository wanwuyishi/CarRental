package com.tjcj.carrental.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.tjcj.carrental.R;
import com.tjcj.carrental.action.GoodsSrcAction;
import com.tjcj.carrental.model.Carsrc;
import com.tjcj.carrental.model.Goodssrc;
import com.tjcj.carrental.model.Order;
import com.tjcj.carrental.util.UserUtil;
import com.tjcj.carrental.view.CityPickerView;
import com.tjcj.carrental.view.SelectCarWindow;
import com.tjcj.carrental.view.SelectClassifyWindow;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class GoodsPubActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_select_car;
    SelectCarWindow menuWindow;
    SelectClassifyWindow classifyWindow;
    private Button btn_release, btn_release_friends;
    private TextView tv_start;//出发地
    private TextView tv_destination;//目的地
    private TextView tv_goods_classify;//货物分类
    private EditText et_goods_name;//货物名称
    private EditText et_goods_weight;//货物重量
    private EditText et_goods_volume;//货物体积
    private EditText et_price;//意向价格
    private EditText et_phone;//联系电话
    private EditText et_note;//备注信息
    private Spinner sp_goods_weight;//重量单位
    private Spinner sp_price;//单价
    String startCity, startArea, endCity, endArea, start, destination, select_good, name,
            weight, goods_weight_sel, price_sel, volume, price, phone, note, select_car;

    private static final String[] classify = new String[]{"设备", "矿产", "建材", "食品", "蔬菜", "生鲜", "药品", "化工", "木材", "家畜",
            "纺织品", "日用品", "电子电器", "农副产品", "其他类型"};

    ArrayAdapter<String> adapterw, adapterp;
    String w[] = {"吨", "公斤"};
    String p[] = {"元/车", "元/吨", "元/批", "面议"};
    private Goodssrc goodssrc;
    private GoodsSrcAction goodsSrcAction;
    //判断页面是新的发布还是修改重发
    private String page_type;
    Intent intent;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_publish);
        init();
        initToolbar();
    }


    private void init() {
        tv_select_car = (TextView) findViewById(R.id.tv_select_car);
        tv_select_car.setOnClickListener(this);
        tv_start = (TextView) findViewById(R.id.tv_start);
        tv_start.setOnClickListener(this);
        tv_destination = (TextView) findViewById(R.id.tv_destination);
        tv_destination.setOnClickListener(this);
        tv_goods_classify = (TextView) findViewById(R.id.tv_goods_classify);
        tv_goods_classify.setOnClickListener(this);
        et_goods_name = (EditText) findViewById(R.id.et_goods_name);
        et_goods_weight = (EditText) findViewById(R.id.et_goods_weight);
        et_goods_volume = (EditText) findViewById(R.id.et_goods_volume);
        et_price = (EditText) findViewById(R.id.et_price);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_note = (EditText) findViewById(R.id.et_note);
        btn_release = (Button) findViewById(R.id.btn_release);
        btn_release.setOnClickListener(this);
        btn_release_friends = (Button) findViewById(R.id.btn_release_friends);
        btn_release_friends.setOnClickListener(this);

        sp_goods_weight = (Spinner) findViewById(R.id.sp_goods_weight);
        adapterw = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, w);
        adapterw.setDropDownViewResource(android.
                R.layout.simple_spinner_dropdown_item);
        sp_goods_weight.setAdapter(adapterw);

        sp_price = (Spinner) findViewById(R.id.sp_price);
        adapterp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, p);
        adapterp.setDropDownViewResource(android.
                R.layout.simple_spinner_dropdown_item);
        sp_price.setAdapter(adapterp);

        intent = getIntent();
        try {
            page_type = (String) intent.getSerializableExtra("type");
            goodssrc = (Goodssrc) intent.getSerializableExtra("goods");
            if (page_type.equals("2")) {
                tv_start.setText(goodssrc.getStartDetails());
                tv_destination.setText(goodssrc.getEndDetails());
                tv_select_car.setText(goodssrc.getCarType());
                tv_goods_classify.setText(goodssrc.getGoodsType());
                et_goods_name.setText(goodssrc.getGoodsname());
                et_goods_weight.setText("" + goodssrc.getWeight());
                et_goods_volume.setText("" + goodssrc.getVolume());
                et_price.setText("" + goodssrc.getPrice());
                et_phone.setText(goodssrc.getUser().getPhone());
                et_note.setText(goodssrc.getRemark());
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle("发布货源");
    }


    public Goodssrc getData() {

        if (page_type.equals("1")) {
            //test
            goodssrc = new Goodssrc(UserUtil.user, note, phone,
                    startCity, startArea, endCity, endArea, start, destination,
                    Double.parseDouble(weight), Double.parseDouble(volume), Double.parseDouble(price),
                    new Timestamp(new Date().getTime()), name, select_car, select_good,
                    goods_weight_sel, price_sel, null, null);
            Order order = new Order();
            order.setGoodssrc(goodssrc);
            Carsrc carsrc = new Carsrc();
            order.setCarsrc(carsrc);
        }
        if (page_type.equals("2")) {
            goodssrc = (Goodssrc) intent.getSerializableExtra("goods");
            Log.d(""+goodssrc.getStartArea(), "wpf");
            if (startArea == null){
                startArea = goodssrc.getStartArea();
                startCity = goodssrc.getStartCity();
            }
            if (endArea == null){
                endArea = goodssrc.getEndArea();
                endCity = goodssrc.getEndCity();
            }
            goodssrc.setUser(UserUtil.user);
            goodssrc.setStartArea(startArea);
            goodssrc.setStartCity(startCity);
            goodssrc.setEndArea(endArea);
            goodssrc.setEndCity(endCity);
            goodssrc.setStartDetails(start);
            goodssrc.setEndDetails(destination);
            goodssrc.setWeight(Double.parseDouble(weight));
            goodssrc.setVolume(Double.parseDouble(volume));
            goodssrc.setPrice(Double.parseDouble(price));
            goodssrc.setGoodsname(name);
            goodssrc.setCarType(select_car);
            goodssrc.setGoodsType(select_good);
        }

        return goodssrc;
    }

    public boolean isOk(View v) {
        start = tv_start.getText().toString();
        destination = tv_destination.getText().toString();
        name = et_goods_name.getText().toString();
        weight = et_goods_weight.getText().toString() == null || et_goods_weight.getText().toString().equals("")
                ? "0" : et_goods_weight.getText().toString();
        goods_weight_sel = sp_goods_weight.getSelectedItem().toString();
        volume = et_goods_volume.getText().toString() == null || et_goods_volume.getText().toString().equals("")
                ? "0" : et_goods_volume.getText().toString();
        price = et_price.getText().toString() == null || et_price.getText().toString().equals("")
                ? "0" : et_price.getText().toString();
        price_sel = sp_price.getSelectedItem().toString();
        phone = et_phone.getText().toString();
        select_car = tv_select_car.getText().toString();
        select_good = tv_goods_classify.getText().toString();
        note = et_note.getText().toString();

        if (start == null || start.equals("")) {
//            Toast.makeText(this, "请选择出发地", Toast.LENGTH_SHORT).show();
            Snackbar.make(v, "请选择出发地", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return false;
        }
        if (destination == null || destination.equals("")) {
            Snackbar.make(v, "请选择目的地", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return false;
        }
        if (name == null || "".equals(name)) {
            Snackbar.make(v, "输入货物名称", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return false;
        } else {
            Snackbar.make(v, "发布成功", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            finish();
            return true;
        }
    }

    private AdapterView.OnItemClickListener classifyOnClick = new AdapterView.OnItemClickListener() {

        public void onItemClick(AdapterView parent, View v, int position, long id) {

            classifyWindow.dialog.dismiss();
            tv_goods_classify.setText(classify[position]);
        }

    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start:
                CityPickerView cityPickerView = new CityPickerView(GoodsPubActivity.this);
                cityPickerView.setTextColor(Color.BLUE);
                cityPickerView.setTextSize(20);
                cityPickerView.setVisibleItems(5);
                cityPickerView.setIsCyclic(false);
                cityPickerView.show();
                cityPickerView.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                    @Override
                    public void onSelected(String... citySelected) {
                        tv_start.setText(citySelected[0] + "-" + citySelected[1] + "-" + citySelected[2]);
                            startCity = citySelected[1];
                            startArea = citySelected[2];
//                            + "\n邮编：" + citySelected[3]
                    }
                });
                break;
            case R.id.tv_destination:
                CityPickerView cityPickerView1 = new CityPickerView(GoodsPubActivity.this);
                cityPickerView1.setTextColor(Color.BLACK);
                cityPickerView1.setTextSize(20);
                cityPickerView1.setVisibleItems(5);
                cityPickerView1.setIsCyclic(false);
                cityPickerView1.show();
                cityPickerView1.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                    @Override
                    public void onSelected(String... citySelected) {
                        tv_destination.setText(citySelected[0] + "-" + citySelected[1] + "-" + citySelected[2]);
                        endCity = citySelected[1];
                        endArea = citySelected[2];
//                            + "\n邮编：" + citySelected[3]
                    }
                });
                break;
            case R.id.tv_goods_classify:
                ArrayList<HashMap<String, Object>> textlist = new ArrayList<HashMap<String, Object>>();
                for (int i = 0; i < classify.length; i++) {
                    HashMap<String, Object> map = new HashMap<String, Object>();

                    map.put("text", classify[i]);
                    textlist.add(map);
                }
                SimpleAdapter simpleAdapter = new SimpleAdapter(this, textlist,
                        R.layout.items_type, new String[]{"text"}, new int[]{
                        R.id.title});
                classifyWindow = new SelectClassifyWindow(GoodsPubActivity.this, classifyOnClick, simpleAdapter);
                //显示窗口
                classifyWindow.showAtLocation(GoodsPubActivity.this.findViewById(R.id.btn_release), Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0); //

                break;
            case R.id.tv_select_car:

                menuWindow = new SelectCarWindow(GoodsPubActivity.this, tv_select_car);
                //显示窗口
                menuWindow.showAtLocation(GoodsPubActivity.this.findViewById(R.id.btn_release), Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0); //

                break;
            case R.id.btn_release://发布
                if (isOk(v)) {
                    goodsSrcAction = new GoodsSrcAction();
                    if (page_type.equals("1")) {
                        if (goodsSrcAction.add(getData())) {
                            Snackbar.make(v, "发布成功", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                            finish();
                        } else {
                            Snackbar.make(v, "发布失败", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                        }
                    }
                    if (page_type.equals("2")) {
                        if (goodsSrcAction.update(getData())) {
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
            case R.id.btn_release_friends://发给常用车

                break;
        }
    }


}
