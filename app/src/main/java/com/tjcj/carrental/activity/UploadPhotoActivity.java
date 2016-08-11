package com.tjcj.carrental.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.tjcj.carrental.R;
import com.tjcj.carrental.util.Configs;
import com.tjcj.carrental.util.FileTools;
import com.tjcj.carrental.util.SelectPictuerTools;
import com.tjcj.carrental.util.SerializeUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by yp on 16-7-17.
 */
public class UploadPhotoActivity extends Activity implements View.OnClickListener {
    private int[] images;
    private ImageView iv_photo;
    private Button btn_popup;
    private Uri photoUri = null;
    private String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_photo);
        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");
        int mid = Integer.parseInt(pid);
        init();
        iv_photo.setImageResource(images[mid]);

    }

    private void init() {
        iv_photo = (ImageView) findViewById(R.id.iv_photo);
        btn_popup = (Button) findViewById(R.id.btn_popup);
        btn_popup.setOnClickListener(this);
        images = new int[]{R.drawable.img_card, R.drawable.img_driver,
                R.drawable.img_driving, R.drawable.img_practise,
                R.drawable.img_road, R.drawable.img_card,R.drawable.img_card,R.drawable.img_card,R.drawable.img_card,R.drawable.img_card};

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_popup:
                if (!FileTools.hasSdcard()) {
                    Toast.makeText(this, "没有找到SD卡，请检查SD卡是否存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    photoUri = FileTools.getUriByFileDirAndFileName(Configs.SystemPicture.SAVE_DIRECTORY, Configs.SystemPicture.SAVE_PIC_NAME);
                } catch (IOException e) {
                    Toast.makeText(this, "创建文件失败。", Toast.LENGTH_SHORT).show();
                    return;
                }
                SelectPictuerTools.showPhotoDialog(this, photoUri);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Configs.SystemPicture.PHOTO_REQUEST_TAKEPHOTO: // 拍照
                SelectPictuerTools.startPhotoZoom(this, photoUri);
                break;
            case Configs.SystemPicture.PHOTO_REQUEST_GALLERY://相册获取
                if (data == null)
                    return;
                SelectPictuerTools.startPhotoZoom(this, data.getData());
                break;
            case Configs.SystemPicture.PHOTO_REQUEST_CUT:  //接收处理返回的图片结果
                if (data == null)
                    return;

                setImageToHeadView(data);

                break;
        }
    }

    public void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");

            if (pid.equals("0")) {
                Log.e("ddd", "身份证保存成功");
                setResult(Configs.SystemPicture.PHOTO_QULIIFICATION_0, (new Intent()).setAction(SerializeUtils.bitmapToString(photo)));
                finish();
            }
            if (pid.equals("1")) {
                Log.e("ddd", "驾驶证保存成功");
                setResult(Configs.SystemPicture.PHOTO_QULIIFICATION_1, (new Intent()).setAction(convertIconToString(photo)));
                finish();
            }
            if (pid.equals("2")) {
                Log.e("ddd", "行驶证保存成功");
                setResult(Configs.SystemPicture.PHOTO_QULIIFICATION_2, (new Intent()).setAction(convertIconToString(photo)));
                finish();
            }
            if (pid.equals("3")) {
                Log.e("ddd", "从业资格证保存成功");
                setResult(Configs.SystemPicture.PHOTO_QULIIFICATION_3, (new Intent()).setAction(convertIconToString(photo)));
                finish();
            }
            if (pid.equals("4")) {
                Log.e("ddd", "道路经营许可证保存成功");
                setResult(Configs.SystemPicture.PHOTO_QULIIFICATION_4, (new Intent()).setAction(convertIconToString(photo)));
                finish();
            }
            if (pid.equals("5")) {
                Log.e("ddd", "门头");
                setResult(Configs.SystemPicture.PHOTO_QULIIFICATION_5, (new Intent()).setAction(convertIconToString(photo)));
                finish();
            }
            if (pid.equals("6")) {
                Log.e("ddd", "营业执照");
                setResult(Configs.SystemPicture.PHOTO_QULIIFICATION_6, (new Intent()).setAction(convertIconToString(photo)));
                finish();
            }
            if (pid.equals("7")) {
                Log.e("ddd", "组织机构代码证");
                setResult(Configs.SystemPicture.PHOTO_QULIIFICATION_7, (new Intent()).setAction(convertIconToString(photo)));
                finish();
            }

            if (pid.equals("8")) {
                Log.e("ddd", "货主省份证证保存成功");
                setResult(Configs.SystemPicture.PHOTO_QULIIFICATION_8, (new Intent()).setAction(convertIconToString(photo)));
                finish();
            }
            if (pid.equals("9")) {
                Log.e("ddd", "头像保存成功");
                setResult(Configs.SystemPicture.PHOTO_HEAD_6, (new Intent()).setAction(convertIconToString(photo)));
                finish();
            }
        }
    }

    /**
     * 图片转成string
     *
     * @param bitmap
     * @return
     */
    public static String convertIconToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);

    }
}
