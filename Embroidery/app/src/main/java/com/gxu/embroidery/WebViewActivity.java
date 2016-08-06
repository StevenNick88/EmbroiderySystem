package com.gxu.embroidery;

import android.os.Bundle;
import android.webkit.WebView;

import java.util.Set;


public class WebViewActivity extends BaseActivity {
    private WebView webview;
    private String url = "http://www.10kit.com/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //实例化WebView对象
        webview = new WebView(this);
        //设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的网页
        webview.loadUrl(url);
        //设置Web视图
        setContentView(webview);

    }

}
