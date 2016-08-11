package com.tjcj.carrental.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.gotye.api.GotyeAPI;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.tjcj.carrental.R;
import com.tjcj.carrental.chat.main.MessageFragment;
import com.tjcj.carrental.fragment.FindFragment;
import com.tjcj.carrental.fragment.ProfileFragment;

import java.util.List;


/**
 * Created by zz on 2016/7/20.
 */
public class MainActivity extends FragmentActivity {

    private FragmentManager fragmentManager;
    private FindFragment findFragment;
    private MessageFragment messageFragment;
    private ProfileFragment profileFragment;
    private BottomBar mBottomBar;
    private List<Fragment> fragmentList;
    private Context context;
    private static GotyeAPI api;
    //    private static TextView msgTip;
    private CoordinatorLayout coordinatorLayout;
    private long exitTime = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
//        msgTip = (TextView) findViewById(R.id.new_msg_tip);

//        ButterKnife.bind(this);
//        initViewPager();
        createBottomBar(savedInstanceState);
    }

    private void createBottomBar(Bundle savedInstanceState) {

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.myCoordinator);
        mBottomBar = BottomBar.attachShy(coordinatorLayout,
                findViewById(R.id.myScrollingContent), savedInstanceState);
        /*// Customize the colors here
        mBottomBar = BottomBar.attach(this, savedInstanceState,
                Color.parseColor("#FFFFFF"), // Background Color
                ContextCompat.getColor(this, R.color.colorPrimaryDark), // Tab Item Color
                0.25f); // Tab Item Alpha*/

        fragmentManager = getSupportFragmentManager();
        mBottomBar.setItems(R.menu.bottombar_menu_three_items);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                Fragment fragment = FragmentFactory.getInstanceByIndex(menuItemId);
                Fragment fragment = null;
                hideFragments(transaction);
                switch (menuItemId) {
                    case R.id.bb_menu_home:
                        if (findFragment == null) {
                            findFragment = new FindFragment();
                            transaction.add(R.id.myScrollingContent, findFragment);
                        } else {
                            transaction.show(findFragment);
                        }
                        break;
                    case R.id.bb_menu_message:
                        if (messageFragment == null) {
                            messageFragment = new MessageFragment();
                            transaction.add(R.id.myScrollingContent, messageFragment);
                        } else {
                            transaction.show(messageFragment);
                        }
                        break;
                    case R.id.bb_menu_mine:
                        if (profileFragment == null) {
                            profileFragment = new ProfileFragment();
                            transaction.add(R.id.myScrollingContent, profileFragment);
                        } else {
                            transaction.show(profileFragment);
                        }
                        break;
                }
                transaction.commit();
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {

            }
        });
        /*mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment fragment = FragmentFactory.getInstanceByIndex(menuItemId);
                transaction.replace(R.id.content, fragment);
                transaction.commit();
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {

            }
        });*/
       /* mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorAccent));
        mBottomBar.mapColorForTab(1, 0xFF5D4037);
        mBottomBar.mapColorForTab(2, "#7B1FA2");
        mBottomBar.mapColorForTab(3, "#FF5252");
        mBottomBar.mapColorForTab(4, "#FF9800");*/
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mBottomBar.onSaveInstanceState(outState);
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (messageFragment != null) {
            transaction.hide(messageFragment);
        }
        if (findFragment != null) {
            transaction.hide(findFragment);
        }
        if (profileFragment != null) {
            transaction.hide(profileFragment);
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {

            int notify = intent.getIntExtra("notify", 0);
            if (notify == 1) {
                clearNotify();
            }

        }
    }

    //清理推送通知
    private void clearNotify() {
        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(0);
    }


    public static void myStart() {
       /* msgTip.setVisibility(View.VISIBLE);
        if (unreadsum+SystemMessage.number > 0 && unreadsum+SystemMessage.number < 100) {
            msgTip.setText(String.valueOf(unreadsum+SystemMessage.number));
        } else if (unreadsum+SystemMessage.number >= 100) {
            msgTip.setText("99");
        } else {
            msgTip.setVisibility(View.GONE);
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {

                Snackbar.make(coordinatorLayout, "再按一次退出程序", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                exitTime = System.currentTimeMillis();
            } else {
                onDestroy();
                finish();
                System.exit(0);
            }

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
