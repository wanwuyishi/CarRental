package com.tjcj.carrental.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;

import com.tjcj.carrental.R;

/**
 * Created by yp on 16-7-21.
 */
public class SelectClassifyWindow extends PopupWindow {
    private View cMenuView;
    private GridView gv_classify;
    private Button btn_classify_cancel;
    public AlertDialog dialog;

    public SelectClassifyWindow(Activity context, AdapterView.OnItemClickListener classifyOnClick,SimpleAdapter simpleAdapter) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cMenuView = inflater.inflate(R.layout.classifydialog, null);

        gv_classify= (GridView) cMenuView.findViewById(R.id.gv_classify);
        btn_classify_cancel= (Button) cMenuView.findViewById(R.id.btn_classify_cancel);

        gv_classify.setAdapter(simpleAdapter);
        gv_classify.setOnItemClickListener(classifyOnClick);

        /*this.setContentView(cMenuView);
        this.setWidth(LinearLayout.LayoutParams.FILL_PARENT);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.ActionSheetDialogAnimation);
*/

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(cMenuView);

        dialog = builder.create();//获取Dialog
        dialog.show();//显示对话框
        btn_classify_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        cMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = cMenuView.findViewById(R.id.classify_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    }
}
