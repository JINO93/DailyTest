package com.example.administrator.test.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.test.R;
import com.example.administrator.test.util.ObjectPool;

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 17:11 2019/8/8
 */
public class WebDialog extends DialogFragment {

    private static ObjectPool<WebView> mPools = new ObjectPool<>(2);
    private WebView webView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        webView = mPools.acquire();
        if(webView == null){
            webView = new WebView(getActivity().getApplicationContext());
        }
        webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        webView.loadUrl("http://www.baidu.com");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });
        return webView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            window.getDecorView().setPadding(0, 0, 0, 0);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = 300;
            window.setAttributes(params);
            window.setGravity(Gravity.BOTTOM);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mPools.release(webView);
    }
}
