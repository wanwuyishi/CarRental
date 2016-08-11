package com.tjcj.carrental.util;

import android.support.v4.app.Fragment;

import com.tjcj.carrental.R;
import com.tjcj.carrental.chat.main.MessageFragment;
import com.tjcj.carrental.fragment.FindFragment;
import com.tjcj.carrental.fragment.ProfileFragment;

/**
 * 弃用
 * Created by admin on 13-11-23.
 */
public class FragmentFactory {
    static  MessageFragment messageFragment = null;
    public static Fragment getInstanceByIndex(int index) {
        Fragment fragment = null;
        switch (index) {
            case R.id.bb_menu_home:
                fragment = new FindFragment();
                break;
            case R.id.bb_menu_message:
                if (messageFragment == null) messageFragment = new MessageFragment();
                fragment = messageFragment;
                break;
            case R.id.bb_menu_mine:
                fragment = new ProfileFragment();
                break;
        }
        return fragment;
    }
}
