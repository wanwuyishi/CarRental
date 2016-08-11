package com.tjcj.carrental.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tjcj.carrental.R;

/**
 * Created by wang on 16-8-2.
 */
public class GassattionActivity extends BaseActivity {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gasstation);
        initToolbar();
        init();
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle("附近加油站");
    }

    private void init(){
        webView = (WebView) findViewById(R.id.webview);
        //WebView加载web资源
        webView.loadUrl("http://m.amap.com/around/?locations=116.470098,39.992838&keywords=加油站&defaultIndex=1&defaultView=&searchRadius=5000&key=7cf256c31a142db7188b362546f87161");
//        webView.loadUrl("http://www.baidu.com");

        //启用支持javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                if (newProgress == 100) {
                    // 网页加载完成

                } else {
                    // 加载中

                }

            }
        });
    }

    /*//改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode== KeyEvent.KEYCODE_BACK)
        {
            if(webView.canGoBack())
            {
                webView.goBack();//返回上一页面
                return true;
            }
            else
            {
                System.exit(0);//退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }*/


}
