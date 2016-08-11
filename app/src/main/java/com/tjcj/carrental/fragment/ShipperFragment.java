package com.tjcj.carrental.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tjcj.carrental.R;
import com.tjcj.carrental.action.UserAction;
import com.tjcj.carrental.activity.UploadPhotoActivity;
import com.tjcj.carrental.model.Authentication;
import com.tjcj.carrental.model.User;
import com.tjcj.carrental.util.AsyncTaskImageLoad;
import com.tjcj.carrental.util.Configs;
import com.tjcj.carrental.util.SerializeUtils;
import com.tjcj.carrental.util.UserUtil;

/**
 * Created by yp on 16-7-16.
 */
public class ShipperFragment extends Fragment {
    private ImageView iv_shipper_identify;

    private EditText et_shipper_name;//货主姓名
    private EditText et_shipper_identify;//身份证号

    private TextView tv_shipper_identify;

    private Button btn_shipper_submit;

    private String name, identify;
    private String pid = "";

    private Authentication authentication;
    private UserAction userAction;
    private String mstatus;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.credit_shipper_fragment, container,
                false);

        authentication = new Authentication();
        userAction = new UserAction();
        User u = new User();
        u.setAccount(UserUtil.user.getAccount());
        u.setPassword(UserUtil.user.getPassword());
        UserUtil.user = userAction.checkUser(SerializeUtils.serialize(u));

        iv_shipper_identify = (ImageView) view.findViewById(R.id.iv_shipper_identify);
        iv_shipper_identify.setImageDrawable(getResources().getDrawable(R.drawable.icon_bacgroud));
        if (UserUtil.user.getAuthentication() == null) return view;
        AsyncTaskImageLoad.LoadImage(iv_shipper_identify, UserUtil.user.getAuthentication().getIdentityPhoto());

        et_shipper_name = (EditText) view.findViewById(R.id.et_shipper_name);
        et_shipper_identify = (EditText) view.findViewById(R.id.et_shipper_identify);
        tv_shipper_identify= (TextView) view.findViewById(R.id.tv_shipper_identify);

        btn_shipper_submit = (Button) view.findViewById(R.id.btn_shipper_submit);

        String na = UserUtil.user.getAuthentication().getName();
        String ide = UserUtil.user.getAuthentication().getIdentity();
        if (null != na) {
            et_shipper_name.setText(UserUtil.user.getAuthentication().getName() + "");
        }
        if (null != ide) {
            et_shipper_identify.setText(UserUtil.user.getAuthentication().getIdentity() + "");
        }

        if (UserUtil.user.getAuthentication().getIdentityStatus() == null)
            tv_shipper_identify.setText("未认证");
        else tv_shipper_identify.setText(getStatus(UserUtil.user.getAuthentication().getIdentityStatus()));




        //提交
        btn_shipper_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = et_shipper_name.getText().toString().trim();
                identify = et_shipper_identify.getText().toString().trim();
                authentication.setName(name);
                authentication.setIdentity(identify);
                setDrawable();
                if (name.length() == 0) {
                    et_shipper_name.requestFocus();
                    Toast.makeText(getActivity(), "请输入真实姓名", Toast.LENGTH_SHORT).show();
                } else if (identify.length() < 18) {
                    et_shipper_identify.requestFocus();
                    Toast.makeText(getActivity(), "请输入完整的身份证号", Toast.LENGTH_SHORT).show();
                } else {
                    UserUtil.user.setAuthentication(authentication);
                    boolean flag = userAction.authenticate(UserUtil.user);
                    if (flag) {
                        Toast.makeText(getActivity(), "上传成功,身份：" + identify, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "上传失败", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        iv_shipper_identify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UploadPhotoActivity.class);
                pid = "8";
                intent.putExtra("pid", pid);
                startActivityForResult(intent, Configs.SystemPicture.PHOTO_QULIIFICATION_5);
            }
        });

        return view;
    }


    public String getStatus(int status) {
        if (status == 0) {
            mstatus = "未审核";
        }
        if (status == 1) {
            mstatus = "等待审核";
        }
        if (status == 2) {
            mstatus = "审核通过";
        }
        if (status == 3) {
            mstatus = "等待失败";
        }
        return mstatus;
    }

    public void setDrawable() {
        Bitmap bitmap, mbitmap;
        Resources resources = this.getContext().getResources();
        mbitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_bacgroud);

        bitmap = ((BitmapDrawable) iv_shipper_identify.getDrawable()).getBitmap();
        if (!isEqual(iv_shipper_identify))
            authentication.setIdentityPhoto(SerializeUtils.bitmapToString(bitmap));
    }

    private boolean isEqual(ImageView v) {
        return v.getDrawable().getCurrent().getConstantState() == getResources().getDrawable(R.drawable.icon_bacgroud).getConstantState();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null) {
            if (requestCode == Configs.SystemPicture.PHOTO_QULIIFICATION_5) {
                iv_shipper_identify.setImageBitmap(convertStringToIcon(data.getAction()));
            }
        }
    }
    /**
     * string转成bitmap
     *
     * @param st
     */
    public static Bitmap convertStringToIcon(String st)
    {
        Bitmap bitmap = null;
        try
        {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap =
                    BitmapFactory.decodeByteArray(bitmapArray, 0,
                            bitmapArray.length);
            return bitmap;
        }
        catch (Exception e)
        {
            return null;
        }
    }

}
