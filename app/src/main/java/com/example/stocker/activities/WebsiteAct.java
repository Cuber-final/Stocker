package com.example.stocker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stocker.R;
import com.example.stocker.toolsOpe.g_webView;

public class WebsiteAct extends AppCompatActivity {
    private g_webView mBrowser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);
        mBrowser = findViewById(R.id.mWeb);
        Intent intent=getIntent();
        String url=intent.getStringExtra("webUrl");
        //调用控件加载个人网址
        assert url != null;
        mBrowser.loadUrl(url);
        mBrowser.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mBrowser.init();
        //需要调用次方法初始化WebSetting才能加载打开网址的js脚本
        //设置WebChromeClient类

    }

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mBrowser.canGoBack()) {
            mBrowser.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //销毁WebView
    @Override
    protected void onDestroy() {
        if (mBrowser != null) {
            mBrowser.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            //替换加载内容为空内容以便销毁
            mBrowser.clearHistory();
            ((ViewGroup) mBrowser.getParent()).removeView(mBrowser);
            mBrowser.destroy();
            mBrowser = null;
        }
        super.onDestroy();
    }

}