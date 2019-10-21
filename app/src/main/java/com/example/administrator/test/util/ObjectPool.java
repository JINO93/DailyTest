package com.example.administrator.test.util;

import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 18:05 2019/8/8
 */
public class ObjectPool<T> {

    private Object[] mPools;

    private int mPoolSize;

    public ObjectPool(int maxPoolSize) {
        mPools = new Object[maxPoolSize];
    }

    public T acquire() {
        if (mPoolSize > 0) {
            mPoolSize--;
            T obj = (T) mPools[mPoolSize];
            mPools[mPoolSize] = null;
            return obj;
        }
        return null;
    }

    public void release(T instance) {
        if (instance == null) {
            return;
        }
        if (mPoolSize >= mPools.length - 1) {
            return;
        }
        if (instance instanceof WebView) {
            WebView webView = (WebView) instance;
            if (webView.getParent() != null) {
                ((ViewGroup)webView.getParent()).removeView(webView);
            }
            webView.stopLoading();
            webView.loadUrl("");
            webView.setWebViewClient(null);
            webView.setWebChromeClient(null);
            webView.clearHistory();
            webView.clearView();
            webView.removeAllViews();
//            webView.destroy();
            mPools[mPoolSize++] = webView;
        }
    }
}
