package com.tjcj.carrental.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tjcj.carrental.R;
import com.tjcj.carrental.activity.OrderActivity;
import com.tjcj.carrental.activity.SettingActivity;
import com.tjcj.carrental.chat.activity.ContactsActivity;
import com.tjcj.carrental.chat.activity.ProfileActivity;

/**
 * Created by wpf on 2016/7/14.
 */
public class ProfileFragment extends Fragment {

    private Button btn_profile;
    private Button btn_myorder;
    private Button btn_myfriend;
    private Button btn_myfeedback;
    private Button btn_security;
    private Button setting;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile,null);

        btn_profile = (Button) v.findViewById(R.id.profile);
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ProfileActivity.class);
                startActivity(intent);
            }
        });

        btn_myorder = (Button) v.findViewById(R.id.btn_myorder);
        btn_myorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),OrderActivity.class);
                startActivity(intent);
            }
        });

        btn_myfriend = (Button) v.findViewById(R.id.btn_myfriend);
        btn_myfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ContactsActivity.class);
                startActivity(intent);
            }
        });
        btn_myfeedback = (Button) v.findViewById(R.id.my_feedback);
        btn_myfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),OrderActivity.class);
                startActivity(intent);
            }
        });
        btn_security = (Button) v.findViewById(R.id.my_security);
        btn_security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),OrderActivity.class);
                startActivity(intent);
            }
        });
        setting = (Button) v.findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SettingActivity.class);
                startActivity(intent);
            }
        });

        return v;


    }
}
