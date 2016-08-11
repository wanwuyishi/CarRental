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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.resource.Resource;
import com.tjcj.carrental.R;
import com.tjcj.carrental.action.UserAction;
import com.tjcj.carrental.activity.UploadPhotoActivity;
import com.tjcj.carrental.model.Authentication;
import com.tjcj.carrental.model.User;
import com.tjcj.carrental.util.AsyncTaskImageLoad;
import com.tjcj.carrental.util.Configs;
import com.tjcj.carrental.util.HttpUtil;
import com.tjcj.carrental.util.SerializeUtils;
import com.tjcj.carrental.util.UserUtil;
import com.tjcj.carrental.view.SelectCard;

import org.json.JSONObject;

/**
 * Created by yp on 16-7-16.
 */
public class DriverFragment extends Fragment {
    private ImageView iv_card;
    private ImageView iv_driver_license;
    private ImageView iv_car_driving_license;
    private ImageView iv_practise_license;
    private ImageView iv_road_license;
    private ImageView iv_door;
    private ImageView iv_business;
    private ImageView iv_code;

    private EditText et_driver_name;//姓名
    private EditText et_driver_identify;//身份证号


    private EditText et_driver_car;//车牌号
    private TextView tv_driver_car;//车牌

    private Button btn_driver_submit;

    //状态
    private TextView tv_card;
    private TextView tv_driver_license;
    private TextView tv_car_driving_license;
    private TextView tv_practise_license;
    private TextView tv_road_license;
    private TextView tv_door;
    private TextView tv_business;
    private TextView tv_code;


    private String pid = null;

    private String name, identify, cardnum;//姓名，身份证号，车牌

    private Authentication authentication;
    private UserAction userAction;
    private User u;

    SelectCard mCardView;

    private String mstatus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.credit_driver_fragment, container,
                false);

        authentication = new Authentication();
        userAction = new UserAction();
        u = new User();
        u.setAccount(UserUtil.user.getAccount());
        u.setPassword(UserUtil.user.getPassword());
        UserUtil.user = userAction.checkUser(SerializeUtils.serialize(u));
        init(view);
        if (UserUtil.user.getAuthentication() == null) return view;
        getDrawale();


        //信誉认证状态
        if (UserUtil.user.getAuthentication().getIdentityStatus() == null)
            tv_card.setText("未认证");
        else tv_card.setText(getStatus(UserUtil.user.getAuthentication().getIdentityStatus()));

        if (UserUtil.user.getAuthentication().getDriversLicenseStatus() == null)
            tv_driver_license.setText("未认证");
        else tv_driver_license.setText(getStatus(UserUtil.user.getAuthentication().getDriversLicenseStatus()));


        if (UserUtil.user.getAuthentication().getDrivingLicenseStatus() == null)
            tv_car_driving_license.setText("未认证");
        else tv_car_driving_license.setText(getStatus(UserUtil.user.getAuthentication().getDrivingLicenseStatus()));


        if (UserUtil.user.getAuthentication().getQfcfStatus() == null)
            tv_practise_license.setText("未认证");
        else tv_practise_license.setText(getStatus(UserUtil.user.getAuthentication().getQfcfStatus()));



        if (UserUtil.user.getAuthentication().getRtblStatus() == null)
            tv_road_license.setText("未认证");
        else tv_road_license.setText(getStatus(UserUtil.user.getAuthentication().getRtblStatus()));

        if (UserUtil.user.getAuthentication().getDoorStatus() == null)
            tv_door.setText("未认证");
        else tv_door.setText(getStatus(UserUtil.user.getAuthentication().getDoorStatus()));

        if (UserUtil.user.getAuthentication().getBusinessLicenseStatus() == null)
            tv_business.setText("未认证");
        else tv_business.setText(getStatus(UserUtil.user.getAuthentication().getBusinessLicenseStatus()));

        if (UserUtil.user.getAuthentication().getOccStatus() == null)
            tv_code.setText("未认证");
        else tv_code.setText(getStatus(UserUtil.user.getAuthentication().getOccStatus()));

        String na = UserUtil.user.getAuthentication().getName();
        String ide = UserUtil.user.getAuthentication().getIdentity();
        if (null != na) {
            et_driver_name.setText(UserUtil.user.getAuthentication().getName() + "");
        }
        if (null != ide) {
            et_driver_identify.setText(UserUtil.user.getAuthentication().getIdentity() + "");
        }
        String numb = UserUtil.user.getAuthentication().getCarNumber();
        if (null != numb) {
            tv_driver_car.setText(numb.substring(0, 2) + "");
            et_driver_car.setText(numb.substring(2, numb.length()) + "");
        }


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

    public void getDrawale() {
        AsyncTaskImageLoad.LoadImage(iv_card, UserUtil.user.getAuthentication().getIdentityPhoto());
        AsyncTaskImageLoad.LoadImage(iv_driver_license, UserUtil.user.getAuthentication().getDriversLicense());
        AsyncTaskImageLoad.LoadImage(iv_car_driving_license, UserUtil.user.getAuthentication().getDrivingLicense());
        AsyncTaskImageLoad.LoadImage(iv_practise_license, UserUtil.user.getAuthentication().getQfcf());
        AsyncTaskImageLoad.LoadImage(iv_road_license, UserUtil.user.getAuthentication().getRtbl());
        AsyncTaskImageLoad.LoadImage(iv_door, UserUtil.user.getAuthentication().getDoor());
        AsyncTaskImageLoad.LoadImage(iv_business, UserUtil.user.getAuthentication().getBusinessLicense());
        AsyncTaskImageLoad.LoadImage(iv_code, UserUtil.user.getAuthentication().getOcc());

    }

    public void setDrawable() {
        Bitmap bitmap, mbitmap;
        Resources resources = this.getContext().getResources();
        mbitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_bacgroud);

        bitmap = ((BitmapDrawable) iv_card.getDrawable()).getBitmap();
        if (!isEqual(iv_card))
            authentication.setIdentityPhoto(SerializeUtils.bitmapToString(bitmap));

        bitmap = ((BitmapDrawable) iv_driver_license.getDrawable()).getBitmap();
        if (!isEqual(iv_driver_license))
            authentication.setDriversLicense(SerializeUtils.bitmapToString(bitmap));

        bitmap = ((BitmapDrawable) iv_car_driving_license.getDrawable()).getBitmap();
        if (!isEqual(iv_car_driving_license))
            authentication.setDrivingLicense(SerializeUtils.bitmapToString(bitmap));

        bitmap = ((BitmapDrawable) iv_practise_license.getDrawable()).getBitmap();
        if (!isEqual(iv_practise_license))
            authentication.setQfcf(SerializeUtils.bitmapToString(bitmap));

        bitmap = ((BitmapDrawable) iv_road_license.getDrawable()).getBitmap();
        if (!isEqual(iv_road_license))
            authentication.setRtbl(SerializeUtils.bitmapToString(bitmap));

        bitmap = ((BitmapDrawable) iv_door.getDrawable()).getBitmap();
        if (!isEqual(iv_door))
            authentication.setDoor(SerializeUtils.bitmapToString(bitmap));

        bitmap = ((BitmapDrawable) iv_business.getDrawable()).getBitmap();
        if (!isEqual(iv_business))
            authentication.setBusinessLicense(SerializeUtils.bitmapToString(bitmap));

        bitmap = ((BitmapDrawable) iv_code.getDrawable()).getBitmap();
        if (!isEqual(iv_code))
            authentication.setOcc(SerializeUtils.bitmapToString(bitmap));
    }

    private boolean isEqual(ImageView v) {
        return v.getDrawable().getCurrent().getConstantState() == getResources().getDrawable(R.drawable.icon_bacgroud).getConstantState();
    }

    public void init(View view) {
        iv_card = (ImageView) view.findViewById(R.id.iv_card);
        iv_card.setImageDrawable(getResources().getDrawable(R.drawable.icon_bacgroud));
        iv_driver_license = (ImageView) view.findViewById(R.id.iv_driver_license);
        iv_driver_license.setImageDrawable(getResources().getDrawable(R.drawable.icon_bacgroud));
        iv_car_driving_license = (ImageView) view.findViewById(R.id.iv_car_driving_license);
        iv_car_driving_license.setImageDrawable(getResources().getDrawable(R.drawable.icon_bacgroud));
        iv_practise_license = (ImageView) view.findViewById(R.id.iv_practise_license);
        iv_practise_license.setImageDrawable(getResources().getDrawable(R.drawable.icon_bacgroud));
        iv_road_license = (ImageView) view.findViewById(R.id.iv_road_license);
        iv_road_license.setImageDrawable(getResources().getDrawable(R.drawable.icon_bacgroud));
        iv_door = (ImageView) view.findViewById(R.id.iv_door);
        iv_door.setImageDrawable(getResources().getDrawable(R.drawable.icon_bacgroud));
        iv_business = (ImageView) view.findViewById(R.id.iv_business);
        iv_business.setImageDrawable(getResources().getDrawable(R.drawable.icon_bacgroud));
        iv_code = (ImageView) view.findViewById(R.id.iv_code);
        iv_code.setImageDrawable(getResources().getDrawable(R.drawable.icon_bacgroud));


        et_driver_name = (EditText) view.findViewById(R.id.et_driver_name);
        et_driver_identify = (EditText) view.findViewById(R.id.et_driver_identify);

        btn_driver_submit = (Button) view.findViewById(R.id.btn_driver_submit);

        tv_driver_car = (TextView) view.findViewById(R.id.tv_driver_car);
        et_driver_car = (EditText) view.findViewById(R.id.et_driver_car);

        //状态
        tv_card = (TextView) view.findViewById(R.id.tv_card);
        tv_driver_license = (TextView) view.findViewById(R.id.tv_driver_license);
        tv_car_driving_license = (TextView) view.findViewById(R.id.tv_car_driving_license);
        tv_practise_license = (TextView) view.findViewById(R.id.tv_practise_license);
        tv_road_license = (TextView) view.findViewById(R.id.tv_road_license);
        tv_door = (TextView) view.findViewById(R.id.tv_door);
        tv_business = (TextView) view.findViewById(R.id.tv_business);
        tv_code = (TextView) view.findViewById(R.id.tv_code);

        btn_driver_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = et_driver_name.getText().toString().trim();
                identify = et_driver_identify.getText().toString().trim();
                cardnum = tv_driver_car.getText().toString() + et_driver_car.getText().toString().trim();
                authentication.setName(name);
                authentication.setIdentity(identify);
                authentication.setCarNumber(cardnum);
                setDrawable();
                if (name.length() == 0) {
                    et_driver_name.requestFocus();
                    Toast.makeText(getActivity(), "请输入真实姓名", Toast.LENGTH_SHORT).show();
                } else if (identify.length() < 18) {
                    Toast.makeText(getActivity(), "请输入完整的身份证号", Toast.LENGTH_SHORT).show();
                } else if (cardnum.length() == 0) {
                    Toast.makeText(getActivity(), "请输入车牌号", Toast.LENGTH_SHORT).show();
                } else {
                    UserUtil.user.setAuthentication(authentication);
                    boolean flag = userAction.authenticate(UserUtil.user);
                    if (flag) {
                        UserUtil.user=userAction.checkUser(SerializeUtils.serialize(u));
                        Toast.makeText(getActivity(), "上传成功,身份：" + identify, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "上传失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        /*选择车牌号*/

        tv_driver_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCardView = new SelectCard(getActivity(), tv_driver_car);
                //显示窗口
                mCardView.showAtLocation(getActivity().findViewById(R.id.btn_driver_submit), Gravity.CENTER, 0, 0); //

            }
        });

    /*/选择车牌号*/

        iv_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UploadPhotoActivity.class);
                pid = "0";
                intent.putExtra("pid", pid);
                startActivityForResult(intent, Configs.SystemPicture.PHOTO_QULIIFICATION_0);

            }
        });
        iv_driver_license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UploadPhotoActivity.class);
                pid = "1";
                intent.putExtra("pid", pid);
                startActivityForResult(intent, Configs.SystemPicture.PHOTO_QULIIFICATION_1);
            }
        });
        iv_car_driving_license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UploadPhotoActivity.class);
                pid = "2";
                intent.putExtra("pid", pid);
                startActivityForResult(intent, Configs.SystemPicture.PHOTO_QULIIFICATION_2);
            }
        });
        iv_practise_license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UploadPhotoActivity.class);
                pid = "3";
                intent.putExtra("pid", pid);
                startActivityForResult(intent, Configs.SystemPicture.PHOTO_QULIIFICATION_3);
            }
        });
        iv_road_license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UploadPhotoActivity.class);
                pid = "4";
                intent.putExtra("pid", pid);
                startActivityForResult(intent, Configs.SystemPicture.PHOTO_QULIIFICATION_4);

            }
        });

        iv_door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UploadPhotoActivity.class);
                pid = "5";
                intent.putExtra("pid", pid);
                startActivityForResult(intent, Configs.SystemPicture.PHOTO_QULIIFICATION_5);
            }
        });
        iv_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UploadPhotoActivity.class);
                pid = "6";
                intent.putExtra("pid", pid);
                startActivityForResult(intent, Configs.SystemPicture.PHOTO_QULIIFICATION_6);
            }
        });
        iv_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UploadPhotoActivity.class);
                pid = "7";
                intent.putExtra("pid", pid);
                startActivityForResult(intent, Configs.SystemPicture.PHOTO_QULIIFICATION_7);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == Configs.SystemPicture.PHOTO_QULIIFICATION_0) {
                iv_card.setImageBitmap(convertStringToIcon(data.getAction()));
            }
            if (requestCode == Configs.SystemPicture.PHOTO_QULIIFICATION_1) {
                iv_driver_license.setImageBitmap(convertStringToIcon(data.getAction()));
            }
            if (requestCode == Configs.SystemPicture.PHOTO_QULIIFICATION_2) {
                iv_car_driving_license.setImageBitmap(convertStringToIcon(data.getAction()));
            }
            if (requestCode == Configs.SystemPicture.PHOTO_QULIIFICATION_3) {
                iv_practise_license.setImageBitmap(convertStringToIcon(data.getAction()));
            }
            if (requestCode == Configs.SystemPicture.PHOTO_QULIIFICATION_4) {
                iv_road_license.setImageBitmap(convertStringToIcon(data.getAction()));
            }
            if (requestCode == Configs.SystemPicture.PHOTO_QULIIFICATION_5) {
                iv_door.setImageBitmap(convertStringToIcon(data.getAction()));
            }
            if (requestCode == Configs.SystemPicture.PHOTO_QULIIFICATION_6) {
                iv_business.setImageBitmap(convertStringToIcon(data.getAction()));
            }
            if (requestCode == Configs.SystemPicture.PHOTO_QULIIFICATION_7) {
                iv_code.setImageBitmap(convertStringToIcon(data.getAction()));
            }
        }
    }

    /**
     * string转成bitmap
     *
     * @param st
     */
    public static Bitmap convertStringToIcon(String st) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap =
                    BitmapFactory.decodeByteArray(bitmapArray, 0,
                            bitmapArray.length);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

}
