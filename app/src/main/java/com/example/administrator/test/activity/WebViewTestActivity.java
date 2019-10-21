package com.example.administrator.test.activity;

import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.test.R;

public class WebViewTestActivity extends AppCompatActivity {

    private static final String TAG = "JINO";

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        setContentView(webView);
        initWebView();
        webView.loadUrl("http://www.wanandroid.com");
    }

    private void initWebView() {
        webView.setWebViewClient(new WebViewClient() {
            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.w(TAG, "url:" + url);
                return super.shouldInterceptRequest(view, url);
            }
        });
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(Environment.getExternalStorageDirectory().getAbsolutePath()+"/web_cache");
    }
}
