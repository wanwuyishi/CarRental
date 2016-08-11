package com.tjcj.carrental.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.tjcj.carrental.R;


public class BaseActivity extends AppCompatActivity {
    public ProgressDialog pd;
    public Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        initProgressDialog();
//        initToolbar();
    }

    private void initProgressDialog(){
        pd=new ProgressDialog(context);
        pd.setMessage("请稍等...");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setIndeterminate(true);
    }

    protected void initToolbar() {
        //toolbar
        Toolbar mToolbar;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        // toolbar.setLogo(R.drawable.ic_launcher);

//        mToolbar.setTitle("");// 标题的文字需在setSupportActionBar之前，不然会无效
        // toolbar.setSubtitle("副标题");
        setSupportActionBar(mToolbar);

        //设置toolbar后调用setDisplayHomeAsUpEnabled
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /* 这些通过ActionBar来设置也是一样的，注意要在setSupportActionBar(toolbar);之后，不然就报错了 */
        // getSupportActionBar().setTitle("标题");
        // getSupportActionBar().setSubtitle("副标题");
        // getSupportActionBar().setLogo(R.drawable.ic_launcher);

		/* 菜单的监听可以在toolbar里设置，也可以像ActionBar那样，通过Activity的onOptionsItemSelected回调方法来处理 */
        /*mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    default:
                        break;
                }
                return true;
            }

        });*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

}
