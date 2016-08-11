package com.tjcj.carrental.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tjcj.carrental.R;
import com.tjcj.carrental.fragment.DriverFragment;
import com.tjcj.carrental.fragment.ShipperFragment;

/**
 * Created by yp on 16-7-16.
 */
public class CreditActivity extends BaseActivity implements View.OnClickListener{
    private ImageView iv_driver;
    private ImageView iv_shipper;
    private LayoutParams para;

    //司机和货主两个布局
    private RelativeLayout rl_driver;
    private RelativeLayout rl_shipper;

    //主要内容标签切换的Fragment
    private Fragment driverFragment,shipperFragment,currentFragment;
    //身份标签文本
    private TextView tv_driver,tv_shipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
        init();
        initidentity();
        initToolbar();
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle("信誉认证");
    }

    //初始化
    private void initidentity() {

        if(driverFragment==null){

            driverFragment=new DriverFragment();

        }
        if (!driverFragment.isAdded()){

            // 提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_layout, driverFragment).commit();

            // 记录当前Fragment
            currentFragment = driverFragment;
            // 设置图片文本的变化
            iv_driver.setImageResource(R.drawable.img_driver_pre);
            tv_driver.setTextColor(getResources()
                    .getColor(R.color.bottomtab_press));
            iv_shipper.setImageResource(R.drawable.img_shipper_nor);
            tv_shipper.setTextColor(getResources().getColor(
                    R.color.bottomtab_normal));
        }

    }

    private void init() {


        //设置货主和车主图片的大小
        iv_driver=(ImageView)findViewById(R.id.iv_driver);
        iv_shipper=(ImageView)findViewById(R.id.iv_shipper);
        para=iv_driver.getLayoutParams();
        para=iv_shipper.getLayoutParams();
        para.height = 80;
        para.width =80;
        iv_driver.setLayoutParams(para);
        iv_shipper.setLayoutParams(para);

        //司机、货主两个布局
        rl_driver= (RelativeLayout) findViewById(R.id.rl_driver);
        rl_shipper= (RelativeLayout) findViewById(R.id.rl_shipper);
        rl_driver.setOnClickListener(this);
        rl_shipper.setOnClickListener(this);
        tv_driver= (TextView) findViewById(R.id.tv_driver);
        tv_shipper= (TextView) findViewById(R.id.tv_shipper);

    }

    /**
     * 点击司机
     */
    private void clickdriverLayout() {
        if (driverFragment == null) {
            driverFragment = new DriverFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), driverFragment);

        // 设置图片和文本变化
        iv_driver.setImageResource(R.drawable.img_driver_pre);
        tv_driver.setTextColor(getResources().getColor(R.color.bottomtab_press));
        iv_shipper.setImageResource(R.drawable.img_shipper_nor);
        tv_shipper.setTextColor(getResources().getColor(
                R.color.bottomtab_normal));


    }
    /**
     * 点击货主
     */
    private void clickshipperLayout() {
        if (shipperFragment == null) {
            shipperFragment = new ShipperFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), shipperFragment);

        // 设置图片和文本变化
        iv_driver.setImageResource(R.drawable.img_driver_nor);
        tv_driver.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        iv_shipper.setImageResource(R.drawable.img_shipper_pre);
        tv_shipper.setTextColor(getResources().getColor(
                R.color.bottomtab_press));
    }

    /**
     * 添加或者显示碎片
     *
     * @param transaction
     * @param fragment
     */
    private void addOrShowFragment(FragmentTransaction transaction,
                                   Fragment fragment) {
        if (currentFragment == fragment)

            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment)
                    .add(R.id.content_layout, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();

        }
        currentFragment = fragment;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_driver :
                clickdriverLayout();
                break;
            case R.id.rl_shipper:
                clickshipperLayout();
                break;

        }
    }



}
