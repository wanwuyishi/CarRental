package com.tjcj.carrental.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tjcj.carrental.R;


/**
 * Created by ZYMAppOne on 2015/12/16.
 */
public class SelectPictuerTools {



    public static void showPhotoDialog(final Activity context,final Uri uri){
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =inflater.inflate(R.layout.dialog_photo,null);

        final Dialog dialog;
        TextView txt_camara= (TextView) view.findViewById(R.id.txt_camara);
        TextView txt_gallery= (TextView) view.findViewById(R.id.txt_gallery);

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setView(view);
        dialog=builder.create();
        dialog.show();
        txt_camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startCamearPicCut(context, uri);
            }
        });
        txt_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startImageCaptrue(context);
            }
        });
    }

    /****
     * 调用系统的拍照功能
     * @param context Activity上下文对象
     * @param uri  Uri
     */
    private static void startCamearPicCut(Activity context,Uri uri) {
        // 调用系统的拍照功能
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra("camerasensortype", 2);// 调用前置摄像头
        intent.putExtra("autofocus", true);// 自动对焦
        intent.putExtra("fullScreen", true);// 全屏
        intent.putExtra("showActionIcons", false);
        // 指定调用相机拍照后照片的储存路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        context.startActivityForResult(intent, Configs.SystemPicture.PHOTO_REQUEST_TAKEPHOTO);
    }
    /***
     * 调用系统的图库
     * @param context Activity上下文对象
     */
    private static void startImageCaptrue(Activity context) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        context.startActivityForResult(intent, Configs.SystemPicture.PHOTO_REQUEST_GALLERY);
    }



    public static void startPhotoZoom(Activity context,Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        intent.putExtra("return-data", true);

        context.startActivityForResult(intent, Configs.SystemPicture.PHOTO_REQUEST_CUT);
    }

    public static void startHeadZoom(Activity context,Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁

        intent.putExtra("crop", "true");

        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);// 输出图片大小
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);

        context.startActivityForResult(intent, Configs.SystemPicture.PHOTO_REQUEST_CUT);
    }
}
